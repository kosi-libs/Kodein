package com.github.salomonbrys.kodein.android

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import com.github.salomonbrys.kodein.android.androidActivityScope.lifecycleManager
import com.github.salomonbrys.kodein.bindings.AutoScope
import com.github.salomonbrys.kodein.bindings.Scope
import com.github.salomonbrys.kodein.bindings.ScopeRegistry
import java.util.*
import android.support.v4.app.Fragment as SupportFragment

/**
 * Base interface from all Android scopes.
 *
 * @param T The type of context of the scope.
 */
interface AndroidScope<in T> : Scope<T> {

    /**
     * Removes a context from the scope to prevent it's leak.
     *
     * @param context The context to remove.
     */
    fun removeFromScope(context: T): ScopeRegistry?
}

/**
 * Map that associates a ScopeRegistry to a context.
 *
 * Because it's a weak hash map, this prevents the context from being leaked.
 */
private val _contextScopes = WeakHashMap<Context, ScopeRegistry>()

/**
 * Android's context scope. Allows to register context-specific singletons.
 */
object androidContextScope : Scope<Context> {

    /**
     * Get a registry for a given context. Will always return the same registry for the same context.
     *
     * @param context The context associated with the returned registry.
     * @return The registry associated with the given context.
     */
    override fun getRegistry(context: Context): ScopeRegistry
        = synchronized(_contextScopes) { _contextScopes.getOrPut(context) { ScopeRegistry() } }

}

/**
 * Android's activity scope. Allows to register activity-specific singletons.
 *
 * Scope that can be used both as a scope or as an auto-scope.
 *
 * /!\ If used as an auto-scope, you need to register the [lifecycleManager].
 */
object androidActivityScope : AndroidScope<Activity>, AutoScope<Activity> {

    /**
     * The last activity that was displayed to the screen. Used when this scope is used as an auto-scope.
     */
    private var _currentActivity: Activity? = null


    /**
     * Get a registry for a given activity. Will always return the same registry for the same activity.
     *
     * @param context The activity associated with the returned registry.
     * @return The registry associated with the given activity.
     */
    override fun getRegistry(context: Activity): ScopeRegistry
        = synchronized(_contextScopes) { _contextScopes.getOrPut(context) { ScopeRegistry() } }

    /**
     * Allows for cleaning up after an activity has been destroyed
     */
    override fun removeFromScope(context: Activity) = _contextScopes.remove(context)

    /**
     * @return The last activity that was displayed to the screen..
     */
    override fun getContext()
            = _currentActivity ?: throw IllegalStateException("There are no current activity. This can either mean that you forgot to register the androidActivityScope.lifecycleManager in your application or that there is currently no activity in the foreground.")


    /**
     * If you use `autoScopedSingleton(androidActivityScope)`, you **must** register this lifecycle manager in your application's oncreate:
     *
     * ```kotlin
     * class MyActivity : Activity {
     *     override fun onCreate() {
     *         registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
     *     }
     * }
     * ```
     */
    object lifecycleManager : Application.ActivityLifecycleCallbacks {
        /** @suppress */
        override fun onActivityCreated(act: Activity?, b: Bundle?) { _currentActivity = act }
        /** @suppress */
        override fun onActivityStarted(act: Activity)              { _currentActivity = act }
        /** @suppress */
        override fun onActivityResumed(act: Activity)              { _currentActivity = act }

        /** @suppress */
        override fun onActivityPaused(act: Activity) {}
        /** @suppress */
        override fun onActivityStopped(act: Activity) {}
        /** @suppress */
        override fun onActivityDestroyed(act: Activity) { if (act == _currentActivity) _currentActivity = null }
        /** @suppress */
        override fun onActivitySaveInstanceState(act: Activity, b: Bundle?) {}
    }

}

/**
 * Android's fragment scope. Allows to register fragment-specific singletons.
 */
object androidFragmentScope : AndroidScope<Fragment> {

    /**
     * Map that associates a ScopeRegistry to a fragment.
     *
     * Because it's a weak hash map, this does not prevent the fragment from being destroyed.
     */
    private val _fragmentScopes = WeakHashMap<Fragment, ScopeRegistry>()

    /**
     * Get a registry for a given fragment. Will always return the same registry for the same fragment.
     *
     * @param context The fragment associated with the returned registry.
     * @return The registry associated with the given fragment.
     */
    override fun getRegistry(context: Fragment): ScopeRegistry
        = synchronized(_fragmentScopes) { _fragmentScopes.getOrPut(context) { ScopeRegistry() } }

    /**
     * Allows for cleaning up after a fragment has been destroyed
     */
    override fun removeFromScope(context: Fragment) = _fragmentScopes.remove(context)

}

/**
 * Android's support fragment scope. Allows to register support fragment-specific singletons.
 */
object androidSupportFragmentScope : AndroidScope<SupportFragment> {

    /**
     * Map that associates a ScopeRegistry to a support fragment.
     *
     * Because it's a weak hash map, this does not prevent the support fragment from being destroyed.
     */
    private val _fragmentScopes = WeakHashMap<SupportFragment, ScopeRegistry>()

    /**
     * Get a registry for a given support fragment. Will always return the same registry for the same support fragment.
     *
     * @param context The support fragment associated with the returned registry.
     * @return The registry associated with the given support fragment.
     */
    override fun getRegistry(context: SupportFragment): ScopeRegistry
        = synchronized(_fragmentScopes) { _fragmentScopes.getOrPut(context) { ScopeRegistry() } }

    /**
     * Allows for cleaning up after a support fragment has been destroyed
     */
    override fun removeFromScope(context: SupportFragment) = _fragmentScopes.remove(context)

}

/**
 * Android's service scope. Allows to register service-specific singletons.
 */
object androidServiceScope : AndroidScope<Service> {

    /**
     * Get a registry for a given service. Will always return the same registry for the same service.
     *
     * @param context The service associated with the returned registry.
     * @return The registry associated with the given service.
     */
    override fun getRegistry(context: Service): ScopeRegistry
        = synchronized(_contextScopes) { _contextScopes.getOrPut(context) { ScopeRegistry() } }

    /**
     * Allows for cleaning up after a service has been destroyed
     */
    override fun removeFromScope(context: Service) = _contextScopes.remove(context)
}

/**
 * Android's broadcast receiver scope. Allows to register broadcast receiver-specific singletons.
 */
object androidBroadcastReceiverScope : AndroidScope<BroadcastReceiver> {

    /**
     * Map that associates a ScopeRegistry to a broadcast receiver.
     *
     * Because it's a weak hash map, this does not prevent the broadcast receiver from being leaked.
     */
    private val _broadcastReceiverScopes = WeakHashMap<BroadcastReceiver, ScopeRegistry>()

    /**
     * Get a registry for a given broadcast receiver. Will always return the same registry for the same broadcast receiver.
     *
     * @param context The broadcast receiver associated with the returned registry.
     * @return The registry associated with the given broadcast receiver.
     */
    override fun getRegistry(context: BroadcastReceiver): ScopeRegistry
        = synchronized(_broadcastReceiverScopes) { _broadcastReceiverScopes.getOrPut(context) { ScopeRegistry() } }

    /**
     * Allows for cleaning up after a broadcast receiver has been destroyed
     */
    override fun removeFromScope(context: BroadcastReceiver) = _broadcastReceiverScopes.remove(context)

}
