package org.kodein.di.android

import android.app.Activity
import org.kodein.di.*

@Deprecated(DEPRECATE_7X)
inline fun subKodein(noinline parentKodein: () -> Kodein, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = Kodein.lazy(allowSilentOverride) {
    extend(parentKodein(), copy = copy)
    init()
}

@Deprecated(DEPRECATE_7X)
inline fun subKodein(parentKodein: Lazy<Kodein>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = subKodein({ parentKodein.value }, allowSilentOverride, copy, init)

@Deprecated(DEPRECATE_7X)
inline fun <T> T.subKodein(parentKodein: KodeinPropertyDelegateProvider<T>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = subKodein(parentKodein.provideDelegate(this, null), allowSilentOverride, copy, init)


@Deprecated(DEPRECATE_7X)
inline fun Activity.retainedSubKodein(noinline parentKodein: () -> Kodein, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = retainedKodein(allowSilentOverride) {
    extend(parentKodein(), copy = copy)
    init()
}

@Deprecated(DEPRECATE_7X)
inline fun Activity.retainedSubKodein(parentKodein: Lazy<Kodein>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = retainedSubKodein({ parentKodein.value }, allowSilentOverride, copy, init)

@Deprecated(DEPRECATE_7X)
inline fun Activity.retainedSubKodein(parentKodein: KodeinPropertyDelegateProvider<Activity>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = retainedSubKodein(parentKodein.provideDelegate(this, null), allowSilentOverride, copy, init)
