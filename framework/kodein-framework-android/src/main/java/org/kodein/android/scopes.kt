package org.kodein.android

import android.app.Activity
import android.app.Fragment
import android.app.Service
import android.content.BroadcastReceiver
import android.os.Bundle
import org.kodein.bindings.MultiItemScopeRegistry
import org.kodein.bindings.Scope
import org.kodein.bindings.ScopeRegistry
import org.kodein.bindings.WeakContextScope
import android.support.v4.app.Fragment as SupportFragment

private val _androidScope = WeakContextScope<Any>()

fun <T> androidScope() = _androidScope as Scope<T, T>

private const val SCOPE_FRAGMENT_TAG = "org.kodein.android.ActivityRetainedScope.RetainedScopeFragment"

object activityRetainedScope : Scope<Activity, Activity> {

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

    override fun getBindingContext(envContext: Activity) = envContext

    override fun getRegistry(receiver: Any?, envContext: Activity, bindContext: Activity): ScopeRegistry {
        val fragment = envContext.fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment ?: run {
            synchronized(envContext) {
                envContext.fragmentManager.findFragmentByTag(SCOPE_FRAGMENT_TAG) as? RetainedScopeFragment ?: run {
                    RetainedScopeFragment().also { envContext.fragmentManager.beginTransaction().add(it, SCOPE_FRAGMENT_TAG).commit() }
                }
            }
        }
        return fragment.registry
    }

}
