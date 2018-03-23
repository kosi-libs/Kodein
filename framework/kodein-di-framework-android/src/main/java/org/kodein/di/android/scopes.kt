package org.kodein.di.android

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import org.kodein.di.bindings.MultiItemScopeRegistry
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.SimpleScope
import org.kodein.di.bindings.WeakContextScope
import android.support.v4.app.Fragment as SupportFragment

private object AndroidComponentsWeakScope : WeakContextScope<Any>()

/**
 * A scope for Android components.
 */
@Suppress("UNCHECKED_CAST")
fun <T: Any> androidScope() = AndroidComponentsWeakScope as SimpleScope<T>

private const val SCOPE_FRAGMENT_TAG = "org.kodein.android.ActivityRetainedScope.RetainedScopeFragment"

/**
 * A scope that allows to get an activity-scoped singleton that's independent from the activity restart.
 */
object activityRetainedScope : SimpleScope<Activity> {

    /** @suppress */
    class RetainedScopeFragment: Fragment() {
        val registry = MultiItemScopeRegistry()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            retainInstance = true
        }

        override fun onDestroy() {
            registry.clear()
            super.onDestroy()
        }
    }

    override fun getRegistry(receiver: Any?, context: Activity): ScopeRegistry {
        val fragment = context.fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment ?: run {
            synchronized(context) {
                context.fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment ?: run {
                    RetainedScopeFragment().also { context.fragmentManager.beginTransaction().add(it, SCOPE_FRAGMENT_TAG).commit() }
                }
            }
        }
        return fragment.registry
    }

}
