package org.kodein.di.android

import android.app.Activity
import android.app.Fragment
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import org.kodein.di.bindings.*
import org.kodein.di.internal.synchronizedIfNull
import java.util.*
import android.support.v4.app.Fragment as SupportFragment

object AndroidComponentsWeakScope : WeakContextScope<Any?, Any?>({ MultiItemScopeRegistry() })

/**
 * A scope for Android components.
 */
@Deprecated("Use AndroidWeakComponentScope or AndroidWeakComponentScope<T>()", ReplaceWith("AndroidComponentsWeakScope<T>()"))
fun <T: Any> androidScope() = AndroidComponentsWeakScope<T>()

private object RepositoryType {
    const val KEY = "RepositoryType"
    const val MULTI_ITEM = 0
    const val SINGLE_ITEM = 1
}

private fun scopeRegistry(repositoryType: Int) = when (repositoryType) {
    0 -> MultiItemScopeRegistry<Any?>()
    1 -> SingleItemScopeRegistry<Any?>()
    else -> throw IllegalStateException()
}

open class AndroidLifecycleScope private constructor(private val repositoryType: Int) : SimpleScope<Any?, Any?> {

    companion object multiItem: AndroidLifecycleScope(RepositoryType.MULTI_ITEM)

    object singleItem: AndroidLifecycleScope(RepositoryType.SINGLE_ITEM)

    private val map = HashMap<LifecycleOwner, ScopeRegistry<in Any?>>()

    override fun getRegistry(receiver: Any?, context: Any?): ScopeRegistry<in Any?> {
        val owner = (context as? LifecycleOwner) ?: (receiver as LifecycleOwner) ?: throw IllegalStateException("Either the receiver or the context must be LifecycleOwner")
        return synchronizedIfNull(
                lock = map,
                predicate = { map[owner] },
                ifNotNull = { it },
                ifNull = {
                    val registry = scopeRegistry(repositoryType)
                    map[owner] = registry
                    owner.lifecycle.addObserver(object : LifecycleObserver {
                        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                        fun onDestroy() {
                            owner.lifecycle.removeObserver(this)
                            registry.clear()
                        }
                    })
                    registry
                }
        )
    }
}


private const val SCOPE_FRAGMENT_TAG = "org.kodein.android.ActivityRetainedScope.RetainedScopeFragment"

/**
 * A scope that allows to get an activity-scoped singleton that's independent from the activity restart.
 */
open class ActivityRetainedScope private constructor(private val repositoryType: Int) : SimpleScope<Activity, Any?> {

    companion object multiItem: ActivityRetainedScope(RepositoryType.MULTI_ITEM)

    object singleItem: ActivityRetainedScope(RepositoryType.SINGLE_ITEM)

    /** @suppress */
    class RetainedScopeFragment: Fragment() {
        val registry = scopeRegistry(arguments.getInt(RepositoryType.KEY))

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onDestroy() {
            registry.clear()
            super.onDestroy()
        }
    }

    override fun getRegistry(receiver: Any?, context: Activity): ScopeRegistry<Any?> {
        val fragment = context.fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment ?: run {
            synchronized(context) {
                context.fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment ?: run {
                    RetainedScopeFragment().also {
                        it.arguments = Bundle().apply { putInt(RepositoryType.KEY, repositoryType) }
                        context.fragmentManager.beginTransaction().add(it, SCOPE_FRAGMENT_TAG).commit()
                    }
                }
            }
        }
        return fragment.registry
    }

}

@Deprecated("use ActivityRetainedScope", ReplaceWith("ActivityRetainedScope"), DeprecationLevel.WARNING)
val activityRetainedScope: ActivityRetainedScope get() = ActivityRetainedScope
