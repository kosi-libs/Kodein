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
class CompositionLocalTest {
    class A
    class B

    @Test
    fun withDI_should_provide_DI_container() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            {
                bindSingletonOf(::A)
                bindSingletonOf(::B)
            },
        ) {
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
    fun withDI_should_provide_DI_container_with_existing_DI() = runComposeUiTest {
        var testPassed = false

        val di = DI {
            bindSingleton { A() }
            bindSingleton { B() }
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
}
