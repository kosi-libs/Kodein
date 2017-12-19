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

val AndroidScope = WeakContextScope<Any>()

val FragmentScope: Scope<Fragment> get() = AndroidScope
val SupportFragmentScope: Scope<SupportFragment> get() = AndroidScope
val ServiceScope: Scope<Service> get() = AndroidScope
val BoradcastReceiverScope: Scope<BroadcastReceiver> get() = AndroidScope

private const val SCOPE_FRAGMENT_TAG = "org.kodein.bindings.AndroidActivityScope.RetainedScopeFragment"

object ActivityScope : Scope<Activity> {

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
