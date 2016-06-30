package com.github.salomonbrys.kodein.android

import android.app.Dialog
import android.app.Fragment
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Loader
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.with


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



inline fun <reified T, reified R : Any> T.instanceForContext(tag: Any? = null): Lazy<R> where T : KodeinInjected, T : Context = with(this as Context).instance<R>(tag)
inline fun <reified T, reified R : Any> T.providerForContext(tag: Any? = null): Lazy<() -> R> where T : KodeinInjected, T : Context = with(this as Context).provider<R>(tag)

inline fun <reified T, reified R : Any> T.instanceForContext(tag: Any? = null): Lazy<R> where T : KodeinInjected, T : Fragment = with(activity as Context).instance<R>(tag)
inline fun <reified T, reified R : Any> T.providerForContext(tag: Any? = null): Lazy<() -> R> where T : KodeinInjected, T : Fragment = with(activity as Context).provider<R>(tag)

inline fun <reified T, reified R : Any> T.instanceForContext(tag: Any? = null): Lazy<R> where T : KodeinInjected, T : android.support.v4.app.Fragment = with(activity as Context).instance<R>(tag)
inline fun <reified T, reified R : Any> T.providerForContext(tag: Any? = null): Lazy<() -> R> where T : KodeinInjected, T : android.support.v4.app.Fragment = with(activity as Context).provider<R>(tag)

inline fun <reified T, reified R : Any> T.instanceForContext(tag: Any? = null): Lazy<R> where T : KodeinInjected, T : Dialog = with(context as Context).instance<R>(tag)
inline fun <reified T, reified R : Any> T.providerForContext(tag: Any? = null): Lazy<() -> R> where T : KodeinInjected, T : Dialog = with(context as Context).provider<R>(tag)

inline fun <reified T, reified R : Any> T.instanceForContext(tag: Any? = null): Lazy<R> where T : KodeinInjected, T : View = with(context as Context).instance<R>(tag)
inline fun <reified T, reified R : Any> T.providerForContext(tag: Any? = null): Lazy<() -> R> where T : KodeinInjected, T : View = with(context as Context).provider<R>(tag)

inline fun <reified T, reified R : Any> T.instanceForContext(tag: Any? = null): Lazy<R> where T : KodeinInjected, T : AbstractThreadedSyncAdapter = with(context as Context).instance<R>(tag)
inline fun <reified T, reified R : Any> T.providerForContext(tag: Any? = null): Lazy<() -> R> where T : KodeinInjected, T : AbstractThreadedSyncAdapter = with(context as Context).provider<R>(tag)

inline fun <reified T, reified R : Any> T.instanceForContext(tag: Any? = null): Lazy<R> where T : KodeinInjected, T : Loader<*> = with(context as Context).instance<R>(tag)
inline fun <reified T, reified R : Any> T.providerForContext(tag: Any? = null): Lazy<() -> R> where T : KodeinInjected, T : Loader<*> = with(context as Context).provider<R>(tag)

inline fun <reified T, reified R : Any> T.instanceForContext(tag: Any? = null): Lazy<R> where T : KodeinInjected, T : android.support.v4.content.Loader<*> = with(context as Context).instance<R>(tag)
inline fun <reified T, reified R : Any> T.providerForContext(tag: Any? = null): Lazy<() -> R> where T : KodeinInjected, T : android.support.v4.content.Loader<*> = with(context as Context).provider<R>(tag)
