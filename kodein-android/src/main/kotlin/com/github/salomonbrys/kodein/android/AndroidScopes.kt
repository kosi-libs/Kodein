package com.github.salomonbrys.kodein.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.activityScope.lifecycleManager
import java.util.*


/**
 * Android's activity scope. Allows to register activity-specific singletons.
 *
 * Scope that can be used both as a scope or as an auto-scope.
 *
 * /!\ If used as an auto-scope, you need to register the [lifecycleManager].
 */
object activityScope : AutoScope<Activity> {

    /**
     * Map that associates a ScopeRegistry to an activity.
     *
     * Because it's a weak hash map, this does not prevent the activity from being released.
     */
    private val _activityScopes = WeakHashMap<Activity, ScopeRegistry>()

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
            = synchronized(_activityScopes) { _activityScopes.getOrPut(context) { ScopeRegistry() } }

    /**
     * @return The last activity that was displayed to the screen..
     */
    override fun getContext()
            = _currentActivity ?: throw IllegalStateException("There are no current activity. This can either mean that you forgot to register the activityScope.lifecycleManager in your application or that there is currently no activity in the foreground.")


    /**
     * If you use [autoActivitySingleton], you **must** register this lifecycle manager in your application's oncreate:
     *
     * ```kotlin
     * class MyActivity : Activity {
     *     override fun onCreate() {
     *         registerActivityLifecycleCallbacks(activityScope.lifecycleManager)
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
 * Creates an activity scoped singleton factory, effectively a `factory { Activity -> T }`.
 *
 * @param T The singleton type.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist for the activity argument.
 * @return The factory to bind.
 */
inline fun <reified T : Any> Kodein.Builder.activitySingleton(noinline creator: Kodein.(Activity) -> T): Factory<Activity, T> = scopedSingleton(activityScope, creator)

/**
 * Creates an activity auto-scoped singleton factory, effectively a `provider { -> T }`.
 *
 * Note that, to use this, you **must** register the [activityScope.lifecycleManager].
 *
 * @param T The singleton type.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist for the activity argument.
 * @return The provider to bind.
 */
inline fun <reified T : Any> Kodein.Builder.autoActivitySingleton(noinline creator: Kodein.(Activity) -> T): Factory<Unit, T> = autoScopedSingleton(activityScope, creator)
