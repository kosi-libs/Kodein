package org.kodein.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton

@OptIn(ExperimentalTestApi::class)
class RetrievingTest {
    class A
    class B(val value: String)
    class C
    class D

    @Test
    fun rememberInstance_should_retrieve_instance() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindSingleton { A() }
            },
        ) {
            val a by rememberInstance<A>()
            assertNotNull(a)
            assertIs<A>(a)
            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberInstance_with_arg_should_retrieve_instance() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindFactory { value: String -> B(value) }
            },
        ) {
            val b by rememberInstance<String, B>(arg = "test")
            assertNotNull(b)
            assertIs<B>(b)
            assertTrue { b.value == "test" }
            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberProvider_should_retrieve_provider() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindProvider { C() }
            },
        ) {
            val cProvider by rememberProvider<C>()
            assertNotNull(cProvider)

            val c = cProvider()
            assertNotNull(c)
            assertIs<C>(c)

            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberFactory_should_retrieve_factory() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindFactory { value: String -> B(value) }
            },
        ) {
            val bFactory by rememberFactory<String, B>()
            assertNotNull(bFactory)

            val b = bFactory("test")
            assertNotNull(b)
            assertIs<B>(b)
            assertTrue { b.value == "test" }

            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberNamedInstance_should_retrieve_instance() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindSingleton(tag = "d") { D() }
            },
        ) {
            val d by rememberNamedInstance<D>()
            assertNotNull(d)
            assertIs<D>(d)
            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberNamedInstance_with_arg_should_retrieve_instance() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindFactory(tag = "b") { value: String -> B(value) }
            },
        ) {
            val b by rememberNamedInstance<String, B>(arg = "named-test")
            assertNotNull(b)
            assertIs<B>(b)
            assertTrue { b.value == "named-test" }
            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberInstance_with_function_arg_should_retrieve_instance() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindFactory { value: String -> B(value) }
            },
        ) {
            val b by rememberInstance<String, B>(fArg = { "function-test" })
            assertNotNull(b)
            assertIs<B>(b)
            assertTrue { b.value == "function-test" }
            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberProvider_with_arg_should_retrieve_provider() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindFactory { value: String -> B(value) }
            },
        ) {
            val bProvider by rememberProvider<String, B>(arg = "provider-test")
            assertNotNull(bProvider)

            val b = bProvider()
            assertNotNull(b)
            assertIs<B>(b)
            assertTrue { b.value == "provider-test" }

            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberProvider_with_function_arg_should_retrieve_provider() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = withDI(
            DI {
                bindFactory { value: String -> B(value) }
            },
        ) {
            val bProvider by rememberProvider<String, B>(fArg = { "provider-function-test" })
            assertNotNull(bProvider)

            val b = bProvider()
            assertNotNull(b)
            assertIs<B>(b)
            assertTrue { b.value == "provider-function-test" }

            testPassed = true
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }
}
