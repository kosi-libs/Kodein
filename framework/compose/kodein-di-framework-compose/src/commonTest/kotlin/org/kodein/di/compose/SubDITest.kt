package org.kodein.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

@OptIn(ExperimentalTestApi::class)
class SubDITest {
    class A
    class B
    class C
    class D

    @Test
    fun subDI_should_extend_parent_DI() = runComposeUiTest {
        var testPassed = false

        val parentDI = DI {
            bindSingleton { A() }
            bindSingleton { B() }
        }

        @Composable
        fun TestComposable() = withDI(parentDI) {
            subDI(
                diBuilder = {
                    bindSingleton { C() }
                },
                content = {
                    val currentDI = localDI()
                    assertNotNull(currentDI)

                    // Should have access to parent bindings
                    val a by currentDI.instance<A>()
                    val b by currentDI.instance<B>()
                    assertIs<A>(a)
                    assertIs<B>(b)

                    // Should have access to own bindings
                    val c by currentDI.instance<C>()
                    assertIs<C>(c)

                    testPassed = true
                },
            )
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun subDI_should_override_parent_bindings() = runComposeUiTest {
        var testPassed = false

        val parentDI = DI {
            bindSingleton { A() }
            bindProvider { B() }
        }

        val parentA by parentDI.instance<A>()
        val parentB by parentDI.instance<B>()

        @Composable
        fun TestComposable() = withDI(parentDI) {
            subDI(
                allowSilentOverride = true,
                diBuilder = {
                    bindSingleton { B() } // Override provider with singleton
                },
                content = {
                    val currentDI = localDI()
                    assertNotNull(currentDI)

                    // Should have access to parent bindings
                    val childA by currentDI.instance<A>()
                    assertEquals(parentA, childA)

                    // Should have access to overridden binding
                    val childB by currentDI.instance<B>()
                    assertNotEquals(parentB, childB)

                    testPassed = true
                },
            )
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun subDI_with_explicit_parent_should_work() = runComposeUiTest {
        var testPassed = false

        val parentDI = DI {
            bindSingleton { A() }
        }

        @Composable
        fun TestComposable() = subDI(
            parentDI = parentDI,
            diBuilder = {
                bindSingleton { D() }
            },
            content = {
                val currentDI = localDI()
                assertNotNull(currentDI)

                // Should have access to parent bindings
                val a by currentDI.instance<A>()
                assertIs<A>(a)

                // Should have access to own bindings
                val d by currentDI.instance<D>()
                assertIs<D>(d)

                testPassed = true
            },
        )

        setContent { TestComposable() }
        assertTrue { testPassed }
    }
}
