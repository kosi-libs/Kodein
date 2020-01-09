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
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di(thisRef, rootContext)"), DeprecationLevel.ERROR)
private fun kodein(thisRef: Any?, rootContext: Context): DI = di(thisRef, rootContext)

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIPropertyDelegateProvider<T>"), DeprecationLevel.ERROR)
typealias KodeinPropertyDelegateProvider<T> = DIPropertyDelegateProvider<T>
/**
 * Provides a `Lazy<DI>`, to be used as a property delegate.
 *
 * @param T The receiver type.
 */
interface DIPropertyDelegateProvider<in T> {
    /** @suppress */
    operator fun provideDelegate(thisRef: T, property: KProperty<*>?): Lazy<DI>
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("ContextDIPropertyDelegateProvider"), DeprecationLevel.ERROR)
private typealias ContextKodeinPropertyDelegateProvider = ContextDIPropertyDelegateProvider

private class ContextDIPropertyDelegateProvider : DIPropertyDelegateProvider<Context> {
    override operator fun provideDelegate(thisRef: Context, property: KProperty<*>?) = lazy { di(thisRef, thisRef) }
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("LazyContextDIPropertyDelegateProvider"), DeprecationLevel.ERROR)
private typealias LazyContextKodeinPropertyDelegateProvider = LazyContextDIPropertyDelegateProvider

private class LazyContextDIPropertyDelegateProvider(private val getContext: () -> Context) : DIPropertyDelegateProvider<Any?> {
    override operator fun provideDelegate(thisRef: Any?, property: KProperty<*>?) = lazy { di(thisRef, getContext()) }
}

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * To be used on Android's `Context` classes, such as `Activity` or `Service`.
 */
fun di(): DIPropertyDelegateProvider<Context> = ContextDIPropertyDelegateProvider()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun kodein(): DIPropertyDelegateProvider<Context> = ContextDIPropertyDelegateProvider()

/**
 * Alias to `di`
 */
fun closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun closestKodein() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * @param context The Android context to use to walk up the context hierarchy.
 */
fun di(context: Context): DIPropertyDelegateProvider<Any?> = LazyContextDIPropertyDelegateProvider { context }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di(context)"), DeprecationLevel.ERROR)
fun kodein(context: Context): DIPropertyDelegateProvider<Any?> = LazyContextDIPropertyDelegateProvider { context }

/**
 * Alias to `di`
 */
fun closestDI(context: Context) = di(context)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI(context)"), DeprecationLevel.ERROR)
fun closestKodein(context: Context) = di(context)

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 *
 * @param getContext A function that returns the Android context to use to walk up the context hierarchy.
 */
fun di(getContext: () -> Context): DIPropertyDelegateProvider<Any?> = LazyContextDIPropertyDelegateProvider(getContext)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di(getContext)"), DeprecationLevel.ERROR)
fun kodein(getContext: () -> Context): DIPropertyDelegateProvider<Any?> = LazyContextDIPropertyDelegateProvider(getContext)

/**
 * Alias to `di`
 */
fun closestDI(getContext: () -> Context) = di(getContext)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI(getContext)"), DeprecationLevel.ERROR)
fun closestKodein(getContext: () -> Context) = di(getContext)

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Fragment.di() = di { activity }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun Fragment.kodein() = di { activity }

/**
 * Alias to `di`
 */
fun Fragment.closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun Fragment.closestKodein() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Dialog.di() = di { context }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun Dialog.kodein() = di { context }

/**
 * Alias to `di`
 */
fun Dialog.closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun Dialog.closestKodein() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun View.di() = di { context }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun View.kodein() = di { context }

/**
 * Alias to `di`
 */
fun View.closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun View.closestKodein() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun AbstractThreadedSyncAdapter.di() = di { context }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun AbstractThreadedSyncAdapter.kodein() = di { context }

/**
 * Alias to `di`
 */
fun AbstractThreadedSyncAdapter.closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun AbstractThreadedSyncAdapter.closestKodein() = di()

/**
 * Returns the closest DI (or the app DI, if no closest DI could be found).
 */
fun Loader<*>.di() = di { context }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di()"), DeprecationLevel.ERROR)
fun Loader<*>.kodein() = di { context }

/**
 * Alias to `di`
 */
fun Loader<*>.closestDI() = di()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestDI()"), DeprecationLevel.ERROR)
fun Loader<*>.closestKodein() = di()
