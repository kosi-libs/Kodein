package org.kodein.di.compose.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.kodein.di.compose.localDI
import org.kodein.type.erased
import org.kodein.type.generic

/**
 * Gets an instance of a [VM] as an android [ViewModel] for the given [tag].
 *
 * VM generic will be preserved!
 *
 * @param VM The type of the [ViewModel] to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of [VM].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Composable
public inline fun <reified VM : ViewModel> rememberViewModel(
    tag: String? = null
): ViewModelLazy<VM> = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = { KodeinViewModelScopedSingleton(di = di, tag = tag) }
        )
    }
}

/**
 * Gets an instance of a [VM] as an android [ViewModel] for the given [tag].
 *
 * VM generic will be preserved!
 *
 * @param VM The type of the [ViewModel] to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of [VM].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Composable
@Deprecated(
    message = "Use the new Compose Multiplatform rememberViewModel instead from Kodein Framework Compose",
    ReplaceWith("rememberViewModel", "org.kodein.di.compose.viewmodel"),
    DeprecationLevel.WARNING
)
@Suppress("DEPRECATION")
public inline fun <reified VM : ViewModel> viewModel(
    tag: String? = null
): VM = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember {
        val provider = ViewModelProvider(viewModelStoreOwner, KodeinViewModelScopedSingleton(di = di, tag = tag))
        if (tag == null) {
            provider[VM::class.java]
        } else {
            provider[tag.toString(), VM::class.java]
        }
    }
}

/**
 * Gets an instance of a [VM] as an android [ViewModel] for the given [tag].
 *
 * VM generic will be preserved!
 *
 * @param VM The type of the [ViewModel] to retrieve.
 * @param tag The bound tag, if any.
 * @param A The type of argument the returned factory takes.
 * @param argType The type of argument the returned factory takes.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [VM].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Composable
public inline fun <reified A : Any, reified VM : ViewModel> rememberViewModel(
    tag: String? = null,
    arg: A,
): ViewModelLazy<VM> = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = {
                KodeinViewModelScopedFactory(
                    di = di,
                    argType = generic<A>(),
                    arg = arg,
                    tag = tag
                )
            }
        )
    }
}

/**
 * Gets an instance of a [VM] as an android [ViewModel] for the given [tag].
 *
 * VM generic will be preserved!
 *
 * @param VM The type of the [ViewModel] to retrieve.
 * @param tag The bound tag, if any.
 * @param A The type of argument the returned factory takes.
 * @param argType The type of argument the returned factory takes.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [VM].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Composable
@Deprecated(
    message = "Use the new Compose Multiplatform rememberViewModel instead from Kodein Framework Compose",
    ReplaceWith("rememberViewModel", "org.kodein.di.compose.viewmodel"),
    DeprecationLevel.WARNING
)
@Suppress("DEPRECATION")
public inline fun <reified A : Any, reified VM : ViewModel> viewModel(
    tag: String? = null,
    arg: A,
): VM = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember {
        val provider = ViewModelProvider(
            viewModelStoreOwner,
            KodeinViewModelScopedFactory(di = di, argType = generic<A>(), arg = arg, tag = tag)
        )
        if (tag == null) {
            provider[VM::class.java]
        } else {
            provider[tag.toString(), VM::class.java]
        }
    }
}
