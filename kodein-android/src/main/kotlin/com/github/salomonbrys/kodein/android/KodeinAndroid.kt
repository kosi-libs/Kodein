package com.github.salomonbrys.kodein.android

import android.app.Dialog
import android.app.Fragment
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Loader
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.LazyKodein


interface KodeinApplication {
    val kodein: Kodein
}



fun Context.lazyKodeinFromApp() = LazyKodein(lazy { (applicationContext as KodeinApplication).kodein })

fun Fragment.lazyKodeinFromApp() = LazyKodein(lazy { (activity.applicationContext as KodeinApplication).kodein })

fun android.support.v4.app.Fragment.lazyKodeinFromApp() = LazyKodein(lazy { (activity.applicationContext as KodeinApplication).kodein })

fun Dialog.lazyKodeinFromApp() = LazyKodein(lazy { (context.applicationContext as KodeinApplication).kodein })

fun View.lazyKodeinFromApp() = LazyKodein(lazy { (context.applicationContext as KodeinApplication).kodein })

fun AbstractThreadedSyncAdapter.lazyKodeinFromApp() = LazyKodein(lazy { (context.applicationContext as KodeinApplication).kodein })

fun Loader<*>.lazyKodeinFromApp() = LazyKodein(lazy { (context.applicationContext as KodeinApplication).kodein })

fun android.support.v4.content.Loader<*>.lazyKodeinFromApp() = LazyKodein(lazy { (context.applicationContext as KodeinApplication).kodein })



val Context.appKodein: () -> Kodein get() = { (applicationContext as KodeinApplication).kodein }

val Fragment.appKodein: () -> Kodein get() = { (activity.applicationContext as KodeinApplication).kodein }

val android.support.v4.app.Fragment.appKodein: () -> Kodein get() = { (activity.applicationContext as KodeinApplication).kodein }

val Dialog.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinApplication).kodein }

val View.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinApplication).kodein }

val AbstractThreadedSyncAdapter.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinApplication).kodein }

val Loader<*>.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinApplication).kodein }

val android.support.v4.content.Loader<*>.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinApplication).kodein }
