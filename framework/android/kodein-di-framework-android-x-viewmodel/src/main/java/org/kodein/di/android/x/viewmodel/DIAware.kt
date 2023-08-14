@file:Suppress("UNCHECKED_CAST")

package org.kodein.di.android.x.viewmodel

import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.*
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.provider

/**
 * Returns a [Lazy] property delegate to access the [AppCompatActivity]'s [ViewModel], which will be obtained from a
 * Kodein DI provider.
 *
 * ```
 * class MyAppCompatActivity : AppCompatActivity(), DIAware {
 *     override val di: DI by closestDI()
 *     private val viewmodel: MyViewModel by viewModel()
 * }
 * ```
 * @param A Extension function applies to [AppCompatActivity] which implements [DIAware]
 * @param VM The type of [ViewModel] to obtain
 * @param tag The bound Kodein DI tag
 * @throws IllegalArgumentException if accessed before the [AppCompatActivity] is attached to the [Application]
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
@MainThread
public inline fun <A, reified VM> A.viewModel(
    tag: Any? = null,
): Lazy<VM> where A : AppCompatActivity, A : DIAware, VM : ViewModel {
    val factoryProducer = { object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val vmProvider = direct.provider<VM>(tag)
            return vmProvider() as T
        }
    } }
    return ViewModelLazy(VM::class, { viewModelStore }, factoryProducer)
}

/**
 * Returns a [Lazy] property delegate to access a [ViewModel] by **default** scoped to this [Fragment], which will
 * be obtained from a Kodein DI provider.
 * ```
 * class MyFragment : Fragment(), DIAware {
 *     override val di: DI by closesDI()
 *     private val viewmodel: MyViewModel by viewModel()
 * }
 * ```
 *
 * Default scope may be overridden with parameter [ownerProducer]:
 * ```
 * class MyFragment : Fragment(), DIAware {
 *     override val di: DI by closestDI()
 *     private val viewmodel: MyViewModel by viewModel (ownerProducer = { requireParentFragment() })
 * }
 * ```
 *
 * @param F Extension function applies to [Fragment] which implements [DIAware]
 * @param VM The type of [ViewModel] to obtain
 * @param ownerProducer Optionally override the default [ViewModel] owner
 * @param tag The bound Kodein DI tag
 * @throws IllegalArgumentException if accessed before this [Fragment] is attached
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
@MainThread
public inline fun <F, reified VM> F.viewModel(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    tag: Any? = null,
): Lazy<VM> where F : Fragment, F : DIAware, VM : ViewModel {
    return createViewModelLazy(
        viewModelClass = VM::class,
        storeProducer = { ownerProducer().viewModelStore },
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val vmProvider = direct.provider<VM>(tag)
                    return vmProvider() as T
                }
            }
        }
    )
}
