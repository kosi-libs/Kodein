@file:Suppress("DEPRECATION")

package org.kodein.di.android

import android.app.Dialog
import android.app.Fragment
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.ContextWrapper
import android.content.Loader
import android.view.View
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import kotlin.reflect.KProperty


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
interface KodeinPropertyDelegateProvider<in T> {
    /** @suppress */
    operator fun provideDelegate(thisRef: T, property: KProperty<*>?): Lazy<Kodein>
}

private class ContextKodeinPropertyDelegateProvider : KodeinPropertyDelegateProvider<Context> {
    override operator fun provideDelegate(thisRef: Context, property: KProperty<*>?) = lazy { kodein(thisRef, thisRef) }
}

private class LazyContextKodeinPropertyDelegateProvider(private val getContext: () -> Context) : KodeinPropertyDelegateProvider<Any?> {
    override operator fun provideDelegate(thisRef: Any?, property: KProperty<*>?) = lazy { kodein(thisRef, getContext()) }
}

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * To be used on Android's `Context` classes, such as `Activity` or `Service`.
 */
fun kodein(): KodeinPropertyDelegateProvider<Context> = ContextKodeinPropertyDelegateProvider()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * @param context The Android context to use to walk up the context hierarchy.
 */
fun kodein(context: Context): KodeinPropertyDelegateProvider<Any?> = LazyContextKodeinPropertyDelegateProvider { context }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * @param getContext A function that returns the Android context to use to walk up the context hierarchy.
 */
fun kodein(getContext: () -> Context): KodeinPropertyDelegateProvider<Any?> = LazyContextKodeinPropertyDelegateProvider(getContext)

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Fragment.kodein() = kodein { activity }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Dialog.kodein() = kodein { context }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun View.kodein() = kodein { context }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun AbstractThreadedSyncAdapter.kodein() = kodein { context }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Loader<*>.kodein() = kodein { context }
