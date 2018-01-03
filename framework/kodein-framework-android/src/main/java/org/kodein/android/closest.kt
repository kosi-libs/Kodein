package org.kodein.android

import android.app.Dialog
import android.app.Fragment
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.ContextWrapper
import android.content.Loader
import android.view.View
import org.kodein.Kodein
import org.kodein.KodeinAware
import kotlin.reflect.KProperty
import android.support.v4.app.Fragment as SupportFragment
import android.support.v4.content.Loader as SupportLoader


class ClosestKodeinPropertyDelegate(val getContext: () -> Context) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>?): Kodein {
        val rootContext = getContext()
        var context: Context? = rootContext
        while (context != null) {
            if (context != thisRef && context is KodeinAware) {
                return context.kodein
            }
            context = if (context is ContextWrapper) context.baseContext else null
        }
        return (rootContext.applicationContext as KodeinAware).kodein
    }
}

class ClosestKodeinPropertyDelegateProvider {
    operator fun provideDelegate(thisRef: Context, property: KProperty<*>): ClosestKodeinPropertyDelegate =
            ClosestKodeinPropertyDelegate { thisRef }
}

fun closestKodein() = ClosestKodeinPropertyDelegateProvider()
fun closestKodein(context: Context) = ClosestKodeinPropertyDelegate { context }
fun closestKodein(getContext: () -> Context) = ClosestKodeinPropertyDelegate(getContext)

fun Fragment.closestKodein() = closestKodein { activity }

fun SupportFragment.closestKodein() = closestKodein { activity!! }

fun Dialog.closestKodein() = closestKodein { context }

fun View.closestKodein() = closestKodein { context }

fun AbstractThreadedSyncAdapter.closestKodein() = closestKodein { context }

fun Loader<*>.closestKodein() = closestKodein { context }

fun SupportLoader<*>.closestKodein() = closestKodein { context }

