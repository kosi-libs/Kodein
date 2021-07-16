@file:Suppress("UNCHECKED_CAST")

package org.kodein.di.android.x.viewmodel

import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.*
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.type.generic


/**
 * Returns a [Lazy] property delegate to access the ComponentActivity's ViewModel, which will be obtained from a Kodein DI
 * factory with a SavedStateHandle argument.
 *
 * ```
 * class MyComponentActivity : ComponentActivity(), DIAware {
 *     override val di: DI by closestDI()
 *     private val viewmodel: MyViewModel by viewModelSavedStateFactory()
 * }
 * ```
 * @param A Extension function applies to [androidx.activity.ComponentActivity] which implements [org.kodein.di.DIAware]
 * @param VM The type of [androidx.lifecycle.ViewModel] to obtain
 * @param tag The bound Kodein DI tag
 * @throws IllegalArgumentException if accessed before the Activity is attached to the Application
 * @throws DI.NotFoundException If no factory was found.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@MainThread
inline fun <A, reified VM> A.viewModelSavedStateFactory(
        tag: Any? = null,
): Lazy<VM> where A : AppCompatActivity, A : DIAware, VM : ViewModel {
    val factoryProducer = { object : AbstractSavedStateViewModelFactory(this, null) {
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
            val factory = direct.Factory(generic<SavedStateHandle>(), generic<VM>(), tag)
            return factory(handle) as T
        }
    } }
    return ViewModelLazy(VM::class, { viewModelStore }, factoryProducer)
}

/**
 * Returns a [Lazy] property delegate to access a [ViewModel] by **default** scoped to this [Fragment], which will
 * be obtained from a Kodein DI factory with a SavedStateHandle argument.
 * ```
 * class MyFragment : Fragment(), DIAware {
 *     override val di: DI by closesDI()
 *     private val viewmodel: MyViewModel by viewModelSavedStateFactory()
 * }
 * ```
 *
 * Default scope may be overridden with parameter [ownerProducer]:
 * ```
 * class MyFragment : Fragment(), DIAware {
 *     override val di: DI by closestDI()
 *     private val viewmodel: MyViewModel by viewModelSavedStateFactory ({requireParentFragment()})
 * }
 * ```
 *
 * @param F Extension function applies to [androidx.fragment.app.Fragment] which implements [org.kodein.di.DIAware]
 * @param VM The type of [androidx.lifecycle.ViewModel] to obtain
 * @param ownerProducer Optionally override the default ViewModel owner
 * @param tag The bound Kodein DI tag
 * @throws IllegalArgumentException if accessed before this Fragment is attached
 * @throws DI.NotFoundException If no factory was found.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
@MainThread
inline fun <F, reified VM> F.viewModelSavedStateFactory(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this },
        tag: Any? = null,
): Lazy<VM> where F : Fragment, F : DIAware, VM : ViewModel {
    return createViewModelLazy(
            VM::class,
            { ownerProducer().viewModelStore },
            { object : AbstractSavedStateViewModelFactory(this, arguments) {
                override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                    val factory = direct.Factory(generic<SavedStateHandle>(), generic<VM>(), tag)
                    return factory(handle) as T
                }
            } }
    )
}
