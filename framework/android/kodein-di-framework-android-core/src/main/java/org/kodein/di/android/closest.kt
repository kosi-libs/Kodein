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


private fun closestKodein(thisRef: Any?, rootContext: Context): Kodein {
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
    operator fun provideDelegate(thisRef: T, property: KProperty<*>): Lazy<Kodein>
}

private class ClosestKodeinInContextPropertyDelegateProvider : KodeinPropertyDelegateProvider<Context> {
    override operator fun provideDelegate(thisRef: Context, property: KProperty<*>) = lazy { closestKodein(thisRef, thisRef) }
}

private class ClosestKodeinPropertyDelegateProvider(private val getContext: () -> Context) : KodeinPropertyDelegateProvider<Any?> {
    override operator fun provideDelegate(thisRef: Any?, property: KProperty<*>) = lazy { closestKodein(thisRef, getContext()) }
}

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * To be used on Android's `Context` classes, such as `Activity` or `Service`.
 */
fun closestKodein(): KodeinPropertyDelegateProvider<Context> = ClosestKodeinInContextPropertyDelegateProvider()

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * @param context The Android context to use to walk up the context hierarchy.
 */
fun closestKodein(context: Context): KodeinPropertyDelegateProvider<Any?> = ClosestKodeinPropertyDelegateProvider { context }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 *
 * @param getContext A function that returns the Android context to use to walk up the context hierarchy.
 */
fun closestKodein(getContext: () -> Context): KodeinPropertyDelegateProvider<Any?> = ClosestKodeinPropertyDelegateProvider(getContext)

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Fragment.closestKodein() = closestKodein { activity }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Dialog.closestKodein() = closestKodein { context }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun View.closestKodein() = closestKodein { context }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun AbstractThreadedSyncAdapter.closestKodein() = closestKodein { context }

/**
 * Returns the closest Kodein (or the app Kodein, if no closest Kodein could be found).
 */
fun Loader<*>.closestKodein() = closestKodein { context }
