package com.github.salomonbrys.kodein.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.autoScopedSingleton
import com.github.salomonbrys.kodein.scopedSingleton
import java.util.*


private val _activityScopes = WeakHashMap<Activity, HashMap<Any, Any>>()

fun activityScope(activity: Activity): HashMap<Any, Any> = synchronized(_activityScopes) { _activityScopes.getOrPut(activity) { HashMap() }  }


private var _currentActivity: Activity? = null

val ActivityScopeLifecycleManager = object : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(act: Activity?, b: Bundle?) { _currentActivity = act }
    override fun onActivityStarted(act: Activity)              { _currentActivity = act }
    override fun onActivityResumed(act: Activity)              { _currentActivity = act }

    override fun onActivityPaused(act: Activity) {}
    override fun onActivityStopped(act: Activity) {}
    override fun onActivityDestroyed(act: Activity) { if (act == _currentActivity) _currentActivity = null }
    override fun onActivitySaveInstanceState(act: Activity, b: Bundle?) {}
}

fun activityScope(): Pair<Activity, HashMap<Any, Any>> {
    val activity = _currentActivity ?: throw IllegalStateException("There are no current activity. This can either mean that you forgot to register the ActivityScopeLifecycleManager in your application or that there is currently no activity in the foreground.")
    return activity to activityScope(activity)
}


inline fun <reified T : Any> Kodein.Builder.activitySingleton(noinline creator: Kodein.(Activity) -> T) = scopedSingleton(::activityScope, creator)

inline fun <reified T : Any> Kodein.Builder.autoActivitySingleton(noinline creator: Kodein.(Activity) -> T) = autoScopedSingleton(::activityScope, creator)
