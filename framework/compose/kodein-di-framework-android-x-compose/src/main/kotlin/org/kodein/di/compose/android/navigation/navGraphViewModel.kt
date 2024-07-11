package org.kodein.di.compose.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import org.kodein.di.compose.viewmodel.KodeinViewModelScopedFactory
import org.kodein.di.compose.viewmodel.KodeinViewModelScopedSingleton
import org.kodein.di.compose.localDI
import org.kodein.type.erased

/**
 * Gets an instance of a [VM] as an android [ViewModel], scoped on a [NavGraph], for the given [tag].
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
public inline fun <reified VM : ViewModel> NavBackStackEntry.rememberNavGraphViewModel(
    navHostController: NavHostController,
    tag: String? = null
): ViewModelLazy<VM> = with(
    localDI()
) {
    remember(this@rememberNavGraphViewModel) {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { navHostController.getBackStackEntry(getParentId()).viewModelStore },
            factoryProducer = { KodeinViewModelScopedSingleton(di = this, tag = tag) }
        )
    }
}

/**
 * Gets an instance of a [VM] as an android [ViewModel], scoped on a [NavGraph], for the given [tag].
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
    message = "Use rememberNavGraphViewModel instead",
    ReplaceWith("rememberNavGraphViewModel", "org.kodein.di.compose.android.navigation"),
    DeprecationLevel.WARNING
)
public inline fun <reified VM : ViewModel> NavBackStackEntry.navGraphViewModel(
    navHostController: NavHostController,
    tag: String? = null
): VM = with(localDI()) {
    remember(this@navGraphViewModel) {
        val provider = ViewModelProvider(
            navHostController.getBackStackEntry(getParentId()).viewModelStore,
            KodeinViewModelScopedSingleton(di = di, tag = tag)
        )
        if (tag == null) {
            provider[VM::class.java]
        } else {
            provider[tag.toString(), VM::class.java]
        }
    }
}

/**
 * Gets an instance of a lazy [VM] as an android [ViewModel], scoped on a [NavGraph], for the given [tag].
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
public inline fun <reified A: Any, reified VM : ViewModel> NavBackStackEntry.rememberNavGraphViewModel(
    navHostController: NavHostController,
    tag: String? = null,
    arg: A,
): ViewModelLazy<VM> = with(
    localDI()
) {
    remember(this@rememberNavGraphViewModel) {
        ViewModelLazy(
            viewModelClass = VM::class,
            storeProducer = { navHostController.getBackStackEntry(getParentId()).viewModelStore },
            factoryProducer = {
                KodeinViewModelScopedFactory(
                    di = di,
                    argType = erased<A>(),
                    arg = arg,
                    tag = tag,
                )
            }
        )
    }
}

/**
 * Gets an instance of a [VM] as an android [ViewModel], scoped on a [NavGraph], for the given [tag].
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
    message = "Use rememberNavGraphViewModel instead",
    ReplaceWith("rememberNavGraphViewModel", "org.kodein.di.compose.android.navigation"),
    DeprecationLevel.WARNING
)
public inline fun <reified A: Any, reified VM : ViewModel> NavBackStackEntry.navGraphViewModel(
    navHostController: NavHostController,
    tag: String? = null,
    arg: A,
): VM = with(localDI()) {
    remember(this@navGraphViewModel) {
        val provider = ViewModelProvider(
            navHostController.getBackStackEntry(getParentId()).viewModelStore,
            KodeinViewModelScopedFactory(
                di = di,
                argType = erased<A>(),
                arg = arg,
                tag = tag,
            )
        )
        if (tag == null) {
            provider[VM::class.java]
        } else {
            provider[tag.toString(), VM::class.java]
        }
    }
}

@PublishedApi
internal fun NavBackStackEntry.getParentId(): Int =
    destination.parent?.id ?: error("Missing NavGraph parent for $this")
