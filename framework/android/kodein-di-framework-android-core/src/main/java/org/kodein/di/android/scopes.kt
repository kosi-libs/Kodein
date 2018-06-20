package org.kodein.di.android

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import org.kodein.di.bindings.*

object AndroidComponentsWeakScope : WeakContextScope<Any?, Any?>({ MultiItemScopeRegistry() })

/**
 * A scope for Android components.
 */
@Deprecated("Use AndroidWeakComponentScope or AndroidWeakComponentScope<T>()", ReplaceWith("AndroidComponentsWeakScope<T>()"))
fun <T: Any> androidScope() = AndroidComponentsWeakScope<T>()

private const val SCOPE_FRAGMENT_TAG = "org.kodein.android.ActivityRetainedScope.RetainedScopeFragment"

/**
 * A scope that allows to get an activity-scoped singleton that's independent from the activity restart.
 */
open class ActivityRetainedScope private constructor(private val repositoryType: ScopeRepositoryType) : SimpleScope<Activity, Any?> {

    object Key {
        const val scopeRepositoryTypeOrdinal = "org.kodein.di.android.scopeRepositoryTypeOrdinal"
    }

    companion object multiItem: ActivityRetainedScope(ScopeRepositoryType.MULTI_ITEM)

    object singleItem: ActivityRetainedScope(ScopeRepositoryType.SINGLE_ITEM)

    /** @suppress */
    class RetainedScopeFragment: Fragment() {
        val registry by lazy {
            val ordinal = arguments.getInt(Key.scopeRepositoryTypeOrdinal)
            newScopeRegistry<Any?>(ScopeRepositoryType.values()[ordinal])
        }

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
                        it.arguments = Bundle().apply { putInt("scope", repositoryType.ordinal) }
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
