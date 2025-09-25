package org.kodein.di.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.compose.withDI
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class ViewModelTest {
    // Test ViewModels
    class SimpleViewModel : ViewModel()
    class ParameterizedViewModel(val param: String) : ViewModel()

    // Mock ViewModelStoreOwner for testing
    private val testViewModelStoreOwner = object : ViewModelStoreOwner {
        override val viewModelStore: ViewModelStore = ViewModelStore()
    }

    @Test
    fun rememberViewModel_should_create_viewModel_instance() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = CompositionLocalProvider(
            LocalViewModelStoreOwner provides testViewModelStoreOwner,
        ) {
            withDI(
                DI {
                    bindSingleton { SimpleViewModel() }
                },
            ) {
                val viewModel by rememberViewModel<SimpleViewModel>()
                assertNotNull(viewModel)
                assertIs<SimpleViewModel>(viewModel)
                testPassed = true
            }
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberViewModel_with_tag_should_create_viewModel_instance() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = CompositionLocalProvider(
            LocalViewModelStoreOwner provides testViewModelStoreOwner,
        ) {
            withDI(
                DI {
                    bindSingleton(tag = "tagged") { SimpleViewModel() }
                },
            ) {
                val viewModel by rememberViewModel<SimpleViewModel>(tag = "tagged")
                assertNotNull(viewModel)
                assertIs<SimpleViewModel>(viewModel)
                testPassed = true
            }
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberViewModel_with_parameter_should_create_viewModel_instance() = runComposeUiTest {
        var testPassed = false

        @Composable
        fun TestComposable() = CompositionLocalProvider(
            LocalViewModelStoreOwner provides testViewModelStoreOwner,
        ) {
            withDI(
                DI {
                    bindFactory { param: String -> ParameterizedViewModel(param) }
                },
            ) {
                val viewModel by rememberViewModel<String, ParameterizedViewModel>(arg = "test-param")
                assertNotNull(viewModel)
                assertIs<ParameterizedViewModel>(viewModel)
                assertTrue { viewModel.param == "test-param" }
                testPassed = true
            }
        }

        setContent { TestComposable() }
        assertTrue { testPassed }
    }

    @Test
    fun rememberViewModel_with_parameter_and_tag_should_create_viewModel_instance() =
        runComposeUiTest {
            var testPassed = false

            @Composable
            fun TestComposable() = CompositionLocalProvider(
                LocalViewModelStoreOwner provides testViewModelStoreOwner,
            ) {
                withDI(
                    DI {
                        bindFactory(tag = "tagged") { param: String -> ParameterizedViewModel(param) }
                    },
                ) {
                    val viewModel by rememberViewModel<String, ParameterizedViewModel>(
                        tag = "tagged",
                        arg = "test-param",
                    )
                    assertNotNull(viewModel)
                    assertIs<ParameterizedViewModel>(viewModel)
                    assertTrue { viewModel.param == "test-param" }
                    testPassed = true
                }
            }

            setContent { TestComposable() }
            assertTrue { testPassed }
        }

    @Test
    fun kodeinViewModelScopedSingleton_should_create_viewModel_instance() = runComposeUiTest {
        val di = DI {
            bindSingleton { SimpleViewModel() }
        }

        val factory = KodeinViewModelScopedSingleton(di)
        val viewModel = factory.create(
            SimpleViewModel::class,
            androidx.lifecycle.viewmodel.CreationExtras.Empty,
        )

        assertNotNull(viewModel)
        assertIs<SimpleViewModel>(viewModel)
    }

    @Test
    fun kodeinViewModelScopedFactory_should_create_viewModel_instance() = runComposeUiTest {
        val di = DI {
            bindFactory { param: String -> ParameterizedViewModel(param) }
        }

        val factory = KodeinViewModelScopedFactory(
            di = di,
            argType = org.kodein.type.generic<String>(),
            arg = "test-param",
        )

        val viewModel = factory.create(
            ParameterizedViewModel::class,
            androidx.lifecycle.viewmodel.CreationExtras.Empty,
        )

        assertNotNull(viewModel)
        assertIs<ParameterizedViewModel>(viewModel)
        assertTrue { viewModel.param == "test-param" }
    }
}
