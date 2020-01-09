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

@Deprecated(DEPRECATE_7X)
private fun kodein(thisRef: Any?, rootContext: Context): Kodein {
    var context: Context? = rootContext
    while (context != null) {
        if (context != thisRef && context is KodeinAware) {
            return context.kodein
        }
        context = if (context is ContextWrapper) context.baseContext else null
    }
    return (rootContext.applicationContext as KodeinAware).kodein
}

/**
 * Provides a `Lazy<Kodein>`, to be used as a property delegate.
 *
 * @param T The receiver type.
 */
@Deprecated(DEPRECATE_7X)
interface KodeinPropertyDelegateProvider<in T> {
    /** @suppress */
    operator fun provideDelegate(thisRef: T, property: KProperty<*>?): Lazy<Kodein>
}

@Deprecated(DEPRECATE_7X)
private class ContextKodeinPropertyDelegateProvider : KodeinPropertyDelegateProvider<Context> {
    override operator fun provideDelegate(thisRef: Context, property: KProperty<*>?) = lazy { kodein(thisRef, thisRef) }
}

@Deprecated(DEPRECATE_7X)
private class LazyContextKodeinPropertyDelegateProvider(private val getContext: () -> Context) : KodeinPropertyDelegateProvider<Any?> {
    override operator fun provideDelegate(thisRef: Any?, property: KProperty<*>?) = lazy { kodein(thisRef, getContext()) }
}

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * To be used on Android's `Context` classes, such as `Activity` or `Service`.
 */
@Deprecated(DEPRECATE_7X)
fun kodein(): KodeinPropertyDelegateProvider<Context> = ContextKodeinPropertyDelegateProvider()

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * @param context The Android context to use to walk up the context hierarchy.
 */
@Deprecated(DEPRECATE_7X)
fun kodein(context: Context): KodeinPropertyDelegateProvider<Any?> = LazyContextKodeinPropertyDelegateProvider { context }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun closestKodein(context: Context) = kodein(context)

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * @param getContext A function that returns the Android context to use to walk up the context hierarchy.
 */
@Deprecated(DEPRECATE_7X)
fun kodein(getContext: () -> Context): KodeinPropertyDelegateProvider<Any?> = LazyContextKodeinPropertyDelegateProvider(getContext)

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun closestKodein(getContext: () -> Context) = kodein(getContext)

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
@Deprecated(DEPRECATE_7X)
fun Fragment.kodein() = kodein { activity }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun Fragment.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
@Deprecated(DEPRECATE_7X)
fun Dialog.kodein() = kodein { context }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun Dialog.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
@Deprecated(DEPRECATE_7X)
fun View.kodein() = kodein { context }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun View.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
@Deprecated(DEPRECATE_7X)
fun AbstractThreadedSyncAdapter.kodein() = kodein { context }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun AbstractThreadedSyncAdapter.closestKodein() = kodein()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
@Deprecated(DEPRECATE_7X)
fun Loader<*>.kodein() = kodein { context }

/**
 * Alias to `kodein`
 */
@Deprecated(DEPRECATE_7X)
fun Loader<*>.closestKodein() = kodein()
