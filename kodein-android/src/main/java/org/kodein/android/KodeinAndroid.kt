package org.kodein.android

import android.app.Dialog
import android.app.Fragment
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Loader
import android.view.View
import org.kodein.*


/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val Context.appKodein: () -> Kodein get() = { (applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val Fragment.appKodein: () -> Kodein get() = { (activity.applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val android.support.v4.app.Fragment.appKodein: () -> Kodein get() = { (activity.applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val Dialog.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val View.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val AbstractThreadedSyncAdapter.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val Loader<*>.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }

/**
 * Function to get the application global Kodein, provided that the Application object is `KodeinAware`.
 */
val android.support.v4.content.Loader<*>.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }
