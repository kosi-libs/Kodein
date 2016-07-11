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



inline fun <reified T, reified R : Any> T.withContext(): CurriedKodeinFactory<Context> where T : KodeinAware, T : Context = with(this as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedInjectorFactory<Context> where T : KodeinInjected, T : Context = with(this as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedLazyKodeinFactory<Context> where T : LazyKodeinAware, T : Context = with(this as Context)

inline fun <reified T, reified R : Any> T.withContext(): CurriedKodeinFactory<Context> where T : KodeinAware, T : Fragment = with(activity as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedInjectorFactory<Context> where T : KodeinInjected, T : Fragment = with(activity as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedLazyKodeinFactory<Context> where T : LazyKodeinAware, T : Fragment = with(activity as Context)

inline fun <reified T, reified R : Any> T.withContext(): CurriedKodeinFactory<Context> where T : KodeinAware, T : android.support.v4.app.Fragment = with(activity as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedInjectorFactory<Context> where T : KodeinInjected, T : android.support.v4.app.Fragment = with(activity as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedLazyKodeinFactory<Context> where T : LazyKodeinAware, T : android.support.v4.app.Fragment = with(activity as Context)

inline fun <reified T, reified R : Any> T.withContext(): CurriedKodeinFactory<Context> where T : KodeinAware, T : Dialog = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedInjectorFactory<Context> where T : KodeinInjected, T : Dialog = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedLazyKodeinFactory<Context> where T : LazyKodeinAware, T : Dialog = with(context as Context)

inline fun <reified T, reified R : Any> T.withContext(): CurriedKodeinFactory<Context> where T : KodeinAware, T : View = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedInjectorFactory<Context> where T : KodeinInjected, T : View = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedLazyKodeinFactory<Context> where T : LazyKodeinAware, T : View = with(context as Context)

inline fun <reified T, reified R : Any> T.withContext(): CurriedKodeinFactory<Context> where T : KodeinAware, T : AbstractThreadedSyncAdapter = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedInjectorFactory<Context> where T : KodeinInjected, T : AbstractThreadedSyncAdapter = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedLazyKodeinFactory<Context> where T : LazyKodeinAware, T : AbstractThreadedSyncAdapter = with(context as Context)

inline fun <reified T, reified R : Any> T.withContext(): CurriedKodeinFactory<Context> where T : KodeinAware, T : Loader<*> = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedInjectorFactory<Context> where T : KodeinInjected, T : Loader<*> = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedLazyKodeinFactory<Context> where T : LazyKodeinAware, T : Loader<*> = with(context as Context)

inline fun <reified T, reified R : Any> T.withContext(): CurriedKodeinFactory<Context> where T : KodeinAware, T : android.support.v4.content.Loader<*> = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedInjectorFactory<Context> where T : KodeinInjected, T : android.support.v4.content.Loader<*> = with(context as Context)
inline fun <reified T, reified R : Any> T.withContext(): CurriedLazyKodeinFactory<Context> where T : LazyKodeinAware, T : android.support.v4.content.Loader<*> = with(context as Context)
