@file:Suppress("UNCHECKED_CAST")

package org.kodein.di.android.x.viewmodel.savedstate

import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.type.generic

/**
 * Returns a [Lazy] property delegate to access the [AppCompatActivity]'s [ViewModel], which will be obtained from a
 * Kodein DI factory with a [SavedStateHandle] argument.
 *
 * ```
 * class MyAppCompatActivity : AppCompatActivity(), DIAware {
 *     override val di: DI by closestDI()
 *     private val viewmodel: MyViewModel by viewModelWithSavedStateHandle()
 * }
 * ```
 * @param A Extension function applies to [AppCompatActivity] which implements [DIAware]
 * @param VM The type of [ViewModel] to obtain
 * @param tag The bound Kodein DI tag
 * @throws IllegalArgumentException if accessed before the [AppCompatActivity] is attached to the [Application]
 * @throws DI.NotFoundException If no factory was found.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@MainThread
public inline fun <A, reified VM> A.viewModelWithSavedStateHandle(
    tag: Any? = null,
): Lazy<VM> where A : AppCompatActivity, A : DIAware, VM : ViewModel {
    val factoryProducer = { object : AbstractSavedStateViewModelFactory(this@viewModelWithSavedStateHandle, null) {
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
            val factory = direct.Factory(generic<SavedStateHandle>(), generic<VM>(), tag)
            return factory(handle) as T
        }
    } }
    return ViewModelLazy(VM::class, { viewModelStore }, factoryProducer)
}

/**
 * Returns a [Lazy] property delegate to access a [ViewModel] by **default** scoped to this [Fragment], which will
 * be obtained from a Kodein DI factory with a [SavedStateHandle] argument.
 * ```
 * class MyFragment : Fragment(), DIAware {
 *     override val di: DI by closesDI()
 *     private val viewmodel: MyViewModel by viewModelWithSavedStateHandle()
 * }
 * ```
 *
 * Default scope may be overridden with parameter [ownerProducer]:
 * ```
 * class MyFragment : Fragment(), DIAware {
 *     override val di: DI by closestDI()
 *     private val viewmodel: MyViewModel by viewModelWithSavedStateHandle(ownerProducer = { requireParentFragment() })
 * }
 * ```
 *
 * @param F Extension function applies to [Fragment] which implements [DIAware]
 * @param VM The type of [ViewModel] to obtain
 * @param ownerProducer Optionally override the default ViewModel owner
 * @param tag The bound Kodein DI tag
 * @throws IllegalArgumentException if accessed before this Fragment is attached
 * @throws DI.NotFoundException If no factory was found.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@MainThread
public inline fun <F, reified VM> F.viewModelWithSavedStateHandle(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    tag: Any? = null,
): Lazy<VM> where F : Fragment, F : DIAware, VM : ViewModel {
    return createViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { ownerProducer().viewModelStore},
        factoryProducer = {
            object : AbstractSavedStateViewModelFactory(this@viewModelWithSavedStateHandle, arguments) {
                override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                    val factory = direct.Factory(generic<SavedStateHandle>(), generic<VM>(), tag)
                    return factory(handle) as T
                }
            }
        }
    )
}
