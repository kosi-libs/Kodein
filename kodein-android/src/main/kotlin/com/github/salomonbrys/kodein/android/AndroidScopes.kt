package com.github.salomonbrys.kodein.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.github.salomonbrys.kodein.*
import java.util.*


object activityScope : AutoScope<Activity> {

    private val _activityScopes = WeakHashMap<Activity, ScopeRegistry>()

    private var _currentActivity: Activity? = null


    override fun getRegistry(key: Kodein.Key, context: Activity)
            = synchronized(_activityScopes) { _activityScopes.getOrPut(context) { ScopeRegistry() } }

    override fun getContext(key: Kodein.Key)
            = _currentActivity ?: throw IllegalStateException("There are no current activity. This can either mean that you forgot to register the activityScope.lifecycleManager in your application or that there is currently no activity in the foreground.")


    object lifecycleManager : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(act: Activity?, b: Bundle?) { _currentActivity = act }
        override fun onActivityStarted(act: Activity)              { _currentActivity = act }
        override fun onActivityResumed(act: Activity)              { _currentActivity = act }

        override fun onActivityPaused(act: Activity) {}
        override fun onActivityStopped(act: Activity) {}
        override fun onActivityDestroyed(act: Activity) { if (act == _currentActivity) _currentActivity = null }
        override fun onActivitySaveInstanceState(act: Activity, b: Bundle?) {}
    }

}


inline fun <reified T : Any> Kodein.Builder.activitySingleton(noinline creator: Kodein.(Activity) -> T) = scopedSingleton(activityScope, creator)

inline fun <reified T : Any> Kodein.Builder.autoActivitySingleton(noinline creator: Kodein.(Activity) -> T) = autoScopedSingleton(activityScope, creator)
