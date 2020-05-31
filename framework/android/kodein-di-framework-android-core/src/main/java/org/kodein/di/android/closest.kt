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

private fun di(thisRef: Any?, rootContext: Context): DI {
    var context: Context? = rootContext
    while (context != null) {
        if (context != thisRef && context is DIAware) {
            return context.di
        }
        context = if (context is ContextWrapper) context.baseContext else null
    }
    return (rootContext.applicationContext as DIAware).di
}

/**
 * Provides a `Lazy<DI>`, to be used as a property delegate.
 *
 * @param T The receiver type.
 */
interface DIPropertyDelegateProvider<in T> {
    /** @suppress */
    operator fun provideDelegate(thisRef: T, property: KProperty<*>?): Lazy<DI>
}

private class ContextDIPropertyDelegateProvider : DIPropertyDelegateProvider<Context> {
    override operator fun provideDelegate(thisRef: Context, property: KProperty<*>?) = lazy { di(thisRef, thisRef) }
}

private class LazyContextDIPropertyDelegateProvider(private val getContext: () -> Context) : DIPropertyDelegateProvider<Any?> {
    override operator fun provideDelegate(thisRef: Any?, property: KProperty<*>?) = lazy { di(thisRef, getContext()) }
}

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * To be used on Android's `Context` classes, such as `Activity` or `Service`.
 */
fun di(): DIPropertyDelegateProvider<Context> = ContextDIPropertyDelegateProvider()

/**
 * Alias to `di`
 */
fun closestDI() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * @param context The Android context to use to walk up the context hierarchy.
 */
fun di(context: Context): DIPropertyDelegateProvider<Any?> = LazyContextDIPropertyDelegateProvider { context }

/**
 * Alias to `di`
 */
fun closestDI(context: Context) = di(context)

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * @param getContext A function that returns the Android context to use to walk up the context hierarchy.
 */
fun di(getContext: () -> Context): DIPropertyDelegateProvider<Any?> = LazyContextDIPropertyDelegateProvider(getContext)

/**
 * Alias to `di`
 */
fun closestDI(getContext: () -> Context) = di(getContext)

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Fragment.di() = di { activity }

/**
 * Alias to `di`
 */
fun Fragment.closestDI() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Dialog.di() = di { context }

/**
 * Alias to `di`
 */
fun Dialog.closestDI() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun View.di() = di { context }

/**
 * Alias to `di`
 */
fun View.closestDI() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun AbstractThreadedSyncAdapter.di() = di { context }

/**
 * Alias to `di`
 */
fun AbstractThreadedSyncAdapter.closestDI() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Loader<*>.di() = di { context }

/**
 * Alias to `di`
 */
fun Loader<*>.closestDI() = di()