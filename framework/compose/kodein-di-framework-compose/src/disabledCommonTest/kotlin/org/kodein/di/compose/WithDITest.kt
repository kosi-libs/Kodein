package org.kodein.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.bindSingletonOf
import org.kodein.di.instance
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class WithDITest {
    class A
    class B
    class C

    @Test
    fun withDI_builder_should_provide_DI_container() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            {
                bindSingletonOf(::A)
            },
        ) {
            val currentDI = localDI()
            assertNotNull(currentDI)
            val a by currentDI.instance<A>()
            assertIs<A>(a)
            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun withDI_modules_should_provide_DI_container() = runComposeUiTest {
        var testPassed = false

        val moduleA = DI.Module("moduleA") {
            bindSingletonOf(::A)
        }

        val moduleB = DI.Module("moduleB") {
            bindSingletonOf(::B)
        }

        @Composable
        fun TestComposable() = withDI(moduleA, moduleB) {
            val currentDI = localDI()
            assertNotNull(currentDI)
            val a by currentDI.instance<A>()
            val b by currentDI.instance<B>()
            assertIs<A>(a)
            assertIs<B>(b)
            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun withDI_container_should_provide_DI_container() = runComposeUiTest {
        var testPassed = false

        val di = DI {
            bindSingletonOf(::A)
            bindSingletonOf(::B)
        }

        @Composable
        fun TestComposable() = withDI(di) {
            val currentDI = localDI()
            assertNotNull(currentDI)
            val a by currentDI.instance<A>()
            val b by currentDI.instance<B>()
            assertIs<A>(a)
            assertIs<B>(b)
            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun onDIContext_should_provide_context() = runComposeUiTest {
        var testPassed = false

        val di = DI {
            bindSingleton { C() }
        }

        @Composable
        fun TestComposable() = withDI(di) {
            val context = "test-context"
            onDIContext(context) {
                val currentDI = localDI()
                assertNotNull(currentDI)

                // We can't directly test the context value, but we can verify
                // that the DI container is properly set up with the context
                // by checking that it's not the same as the original DI container
                assertTrue { currentDI !== di }

                testPassed = true
            }
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }
}
