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
    override operator fun provideDelegate(thisRef: Context, property: KProperty<*>?) = lazy { closestDI(thisRef, thisRef) }
}

class LazyContextDIPropertyDelegateProvider(private val getContext: () -> Context) : DIPropertyDelegateProvider<Any?> {
    override operator fun provideDelegate(thisRef: Any?, property: KProperty<*>?) = lazy { closestDI(thisRef, getContext()) }
}

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun di(): DIPropertyDelegateProvider<Context> = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * To be used on Android's `Context` classes, such as `Activity` or `Service`.
 */
fun closestDI(): DIPropertyDelegateProvider<Context> = ContextDIPropertyDelegateProvider()

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI(context)","org.kodein.di.android"))
fun di(context: Context): DIPropertyDelegateProvider<Any?> = closestDI(context)

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * @param context The Android context to use to walk up the context hierarchy.
 */
fun closestDI(context: Context): LazyContextDIPropertyDelegateProvider = LazyContextDIPropertyDelegateProvider { context }

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI(getContext)","org.kodein.di.android"))
fun di(getContext: () -> Context): DIPropertyDelegateProvider<Any?> = closestDI(getContext)

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * @param getContext A function that returns the Android context to use to walk up the context hierarchy.
 */
fun closestDI(getContext: () -> Context): DIPropertyDelegateProvider<Any?> = LazyContextDIPropertyDelegateProvider(getContext)

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun Fragment.di(): DIPropertyDelegateProvider<Any?> = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Fragment.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { activity }

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun Dialog.di(): DIPropertyDelegateProvider<Any?> = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Dialog.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun View.di(): DIPropertyDelegateProvider<Context> = org.kodein.di.android.closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun View.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun AbstractThreadedSyncAdapter.di(): DIPropertyDelegateProvider<Any?> = closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun AbstractThreadedSyncAdapter.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }

@Deprecated("di() function leads to import conflicts. please replace with closestDI().", replaceWith = ReplaceWith("closestDI()","org.kodein.di.android"))
fun Loader<*>.di(): DIPropertyDelegateProvider<Context> = org.kodein.di.android.closestDI()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Loader<*>.closestDI(): DIPropertyDelegateProvider<Any?> = closestDI { context }