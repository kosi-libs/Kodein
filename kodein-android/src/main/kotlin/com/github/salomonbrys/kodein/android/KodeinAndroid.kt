package com.github.salomonbrys.kodein.android

import android.app.Dialog
import android.app.Fragment
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Loader
import android.view.View
import com.github.salomonbrys.kodein.*


val Context.appKodein: () -> Kodein get() = { (applicationContext as KodeinAware).kodein }

val Fragment.appKodein: () -> Kodein get() = { (activity.applicationContext as KodeinAware).kodein }

val android.support.v4.app.Fragment.appKodein: () -> Kodein get() = { (activity.applicationContext as KodeinAware).kodein }

val Dialog.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }

val View.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }

val AbstractThreadedSyncAdapter.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }

val Loader<*>.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }

val android.support.v4.content.Loader<*>.appKodein: () -> Kodein get() = { (context.applicationContext as KodeinAware).kodein }


// Cannot use extension functions :( https://youtrack.jetbrains.com/issue/KT-9630

fun KodeinAware.withContext(ctx: Context): CurriedKodeinFactory<Context> = with(ctx)
fun KodeinInjected.withContext(ctx: Context): CurriedInjectorFactory<Context> = with(ctx)
fun LazyKodeinAware.withContext(ctx: Context): CurriedLazyKodeinFactory<Context> = with(ctx)

fun KodeinAware.withContext(f: Fragment): CurriedKodeinFactory<Context> = with { f.activity }
fun KodeinInjected.withContext(f: Fragment): CurriedInjectorFactory<Context> = with { f.activity }
fun LazyKodeinAware.withContext(f: Fragment): CurriedLazyKodeinFactory<Context> = with { f.activity }

fun KodeinAware.withContext(f: android.support.v4.app.Fragment): CurriedKodeinFactory<Context> = with { f.activity }
fun KodeinInjected.withContext(f: android.support.v4.app.Fragment): CurriedInjectorFactory<Context> = with { f.activity }
fun LazyKodeinAware.withContext(f: android.support.v4.app.Fragment): CurriedLazyKodeinFactory<Context> = with { f.activity }

fun KodeinAware.withContext(d: Dialog): CurriedKodeinFactory<Context> = with { d.context }
fun KodeinInjected.withContext(d: Dialog): CurriedInjectorFactory<Context> = with { d.context }
fun LazyKodeinAware.withContext(d: Dialog): CurriedLazyKodeinFactory<Context> = with { d.context }

fun KodeinAware.withContext(v: View): CurriedKodeinFactory<Context> = with { v.context }
fun KodeinInjected.withContext(v: View): CurriedInjectorFactory<Context> = with { v.context }
fun LazyKodeinAware.withContext(v: View): CurriedLazyKodeinFactory<Context> = with { v.context }

fun KodeinAware.withContext(a: AbstractThreadedSyncAdapter): CurriedKodeinFactory<Context> = with { a.context }
fun KodeinInjected.withContext(a: AbstractThreadedSyncAdapter): CurriedInjectorFactory<Context> = with { a.context }
fun LazyKodeinAware.withContext(a: AbstractThreadedSyncAdapter): CurriedLazyKodeinFactory<Context> = with { a.context }

fun KodeinAware.withContext(l: Loader<*>): CurriedKodeinFactory<Context> = with { l.context }
fun KodeinInjected.withContext(l: Loader<*>): CurriedInjectorFactory<Context> = with { l.context }
fun LazyKodeinAware.withContext(l: Loader<*>): CurriedLazyKodeinFactory<Context> = with { l.context }

fun KodeinAware.withContext(l: android.support.v4.content.Loader<*>): CurriedKodeinFactory<Context> = with { l.context }
fun KodeinInjected.withContext(l: android.support.v4.content.Loader<*>): CurriedInjectorFactory<Context> = with { l.context }
fun LazyKodeinAware.withContext(l: android.support.v4.content.Loader<*>): CurriedLazyKodeinFactory<Context> = with { l.context }
