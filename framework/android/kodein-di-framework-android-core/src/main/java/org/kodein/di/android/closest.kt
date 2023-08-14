@file:Suppress("DEPRECATION")

package org.kodein.di.android

import android.app.Dialog
import android.app.Fragment
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.ContextWrapper
import android.content.Loader
import android.view.View
import org.kodein.di.*
import kotlin.reflect.KProperty

private fun closestDI(thisRef: Any?, rootContext: Context): DI {
    var context: Context? = rootContext
    while (context != null) {
        if (context != thisRef && context is DIAware) {
            return context.di
        }
        context = if (context is ContextWrapper) context.baseContext else null
    }
    val appContext = rootContext.applicationContext as? DIAware
        ?: error("Trying to find closest DI, but no DI container was found at all. Your Application should be DIAware.")
    return appContext.di
}

/**
 * Provides a `Lazy<DI>`, to be used as a property delegate.
 *
 * @param T The receiver type.
 */
public interface DIPropertyDelegateProvider<in T> {
    /** @suppress */
    public operator fun provideDelegate(thisRef: T, property: KProperty<*>?): Lazy<DI>
}

private class ContextDIPropertyDelegateProvider : DIPropertyDelegateProvider<Context> {
    override operator fun provideDelegate(thisRef: Context, property: KProperty<*>?) = lazy { closestDI(thisRef, thisRef) }
}

public class LazyContextDIPropertyDelegateProvider(private val getContext: () -> Context) : DIPropertyDelegateProvider<Any?> {
    override operator fun provideDelegate(thisRef: Any?, property: KProperty<*>?): Lazy<DI> = lazy { closestDI(thisRef, getContext()) }
}

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * To be used on Android's `Context` classes, such as `Activity` or `Service`.
 */
public fun closestDI(): DIPropertyDelegateProvider<Context> = ContextDIPropertyDelegateProvider()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * @param context The Android context to use to walk up the context hierarchy.
 */
public fun closestDI(context: Context): LazyContextDIPropertyDelegateProvider = LazyContextDIPropertyDelegateProvider { context }

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * @param getContext A function that returns the Android context to use to walk up the context hierarchy.
 */
public fun closestDI(getContext: () -> Context): DIPropertyDelegateProvider<Any?> = LazyContextDIPropertyDelegateProvider(getContext)

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
public fun Fragment.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { activity }

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
public fun Dialog.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
public fun View.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
public fun AbstractThreadedSyncAdapter.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
public fun Loader<*>.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }
