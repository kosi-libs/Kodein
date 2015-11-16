package com.github.salomonbrys.kodein.android

import android.app.Dialog
import android.app.Fragment
import android.app.Service
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.LazyKodein

public interface KodeinApplication {
    public val kodein: Kodein
}

public fun Context.lazyKodeinFromApp() = LazyKodein(lazy { (applicationContext as KodeinApplication).kodein })

public fun Fragment.lazyKodeinFromApp() = LazyKodein(lazy { (activity.applicationContext as KodeinApplication).kodein })

public fun android.support.v4.app.Fragment.lazyKodeinFromApp() = LazyKodein(lazy { (activity.applicationContext as KodeinApplication).kodein })

public fun Dialog.lazyKodeinFromApp() = LazyKodein(lazy { (context.applicationContext as KodeinApplication).kodein })


public val Context.appKodein: () -> Kodein get() = { (applicationContext as KodeinApplication).kodein }

public val Fragment.appKodein: () -> Kodein get() = { (activity.applicationContext as KodeinApplication).kodein }

public val android.support.v4.app.Fragment.appKodein: () -> Kodein get() = { (activity.applicationContext as KodeinApplication).kodein }

public val Dialog.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinApplication).kodein }

public val AbstractThreadedSyncAdapter.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinApplication).kodein }

