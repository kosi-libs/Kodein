package org.kodein.android

import android.app.Dialog
import android.app.Fragment
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Loader
import android.view.View
import org.kodein.Kodein
import org.kodein.KodeinAware
import android.support.v4.app.Fragment as SupportFragment
import android.support.v4.content.Loader as SupportLoader
import android.content.ContextWrapper
import android.app.Activity



private fun Context.toActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null

}

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val Context.appKodein: Lazy<Kodein> get() = lazy { (applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val Fragment.appKodein: Lazy<Kodein> get() = lazy { (activity.applicationContext as KodeinAware).kodein }

val Fragment.activityKodein: Lazy<Kodein> get() = lazy { (activity as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val SupportFragment.appKodein: Lazy<Kodein> get() = lazy { (activity!!.applicationContext as KodeinAware).kodein }

val SupportFragment.activityKodein: Lazy<Kodein> get() = lazy { (activity!! as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val Dialog.appKodein: Lazy<Kodein> get() = lazy { (context.applicationContext as KodeinAware).kodein }

val Dialog.activityKodein: Lazy<Kodein> get() = lazy { (ownerActivity as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val View.appKodein: Lazy<Kodein> get() = lazy { (context.applicationContext as KodeinAware).kodein }

val View.activityKodein: Lazy<Kodein> get() = lazy { (context.toActivity() as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val AbstractThreadedSyncAdapter.appKodein: Lazy<Kodein> get() = lazy { (context.applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val Loader<*>.appKodein: Lazy<Kodein> get() = lazy { (context.applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val SupportLoader<*>.appKodein: Lazy<Kodein> get() = lazy { (context.applicationContext as KodeinAware).kodein }


fun Kodein.MainBuilder.extend(kodein: Lazy<Kodein>, allowOverride: kotlin.Boolean = false) = extend(kodein.value)
