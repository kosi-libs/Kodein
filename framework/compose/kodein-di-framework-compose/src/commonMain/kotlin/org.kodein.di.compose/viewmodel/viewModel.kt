package org.kodein.di.compose.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.kodein.di.compose.localDI
import org.kodein.type.generic

/**
 * Composable function that remembers a ViewModel instance in a Composable tree using Kodein DI container.
 * It returns ViewModelLazy, a property delegate that lazily accesses the ViewModel instance.
 *
 * @param VM the type of the ViewModel to be remembered.
 * @param tag an optional tag to filter the bindings in the DI container.
 * If changed during composition, a new instance will be created.
 * @return ViewModelLazy<VM> an instance of ViewModelLazy with the specified ViewModel type.
 * @throws IllegalStateException if no DI container is attached to the Composable tree.
 */
@Composable
public inline fun <reified VM : ViewModel> rememberViewModel(
    tag: String? = null
): ViewModelLazy<VM> = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember(di, tag) {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { viewModelStoreOwner.viewModelStore },
            factoryProducer = { KodeinViewModelScopedSingleton(di = di, tag = tag) }
        )
    }
}

/**
 * Creates a ViewModel instance using Kodein dependency injection and remembers it for recomposition.
 * The ViewModel will be scoped to the current scope defined by the ViewModelStoreOwner.
 *
 * @param A The type of argument to be passed to the ViewModel constructor.
 * @param VM The type of ViewModel to be created.
 * @param tag The optional tag to be used for resolving ViewModel instance from DI container. If changed during
 * composition, a new instance will be created
 * @param arg The argument value to be passed to the ViewModel constructor.
 * If changed during composition, a new instance will be created.
 * @return [ViewModelLazy] instance representing the remembered ViewModel.
 * @throws [error] if ViewModelStoreOwner is missing for LocalViewModelStoreOwner.
 */
@Composable
public inline fun <reified A : Any, reified VM : ViewModel> rememberViewModel(
    tag: String? = null,
    arg: A,
): ViewModelLazy<VM> = with(localDI()) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("ViewModelStoreOwner is missing for LocalViewModelStoreOwner.")

    remember(tag, arg, di) {
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
