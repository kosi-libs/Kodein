package org.kodein.di.android.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.kodein.di.compose.localDI
import org.kodein.di.direct
import org.kodein.di.instance
import org.kodein.type.erased

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
    tag: Any? = null
): ViewModelLazy<VM> = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = { KodeinViewModelInstance(di = di, vmType = erased<VM>(), tag = tag,) }
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
public inline fun <reified A : Any, reified VM : ViewModel> rememberViewModel(
    tag: Any? = null,
    arg: A,
): ViewModelLazy<VM> = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = {
                KodeinViewModelFactory(
                    di = di,
                    vmType = erased<VM>(),
                    argType = erased<A>(),
                    arg = arg,
                    tag = tag,
                )
            }
        )
    }
}