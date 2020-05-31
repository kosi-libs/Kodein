package org.kodein.di.android

import android.app.Activity
import org.kodein.di.*

inline fun subDI(noinline parentDI: () -> DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = DI.lazy(allowSilentOverride) {
    extend(parentDI(), copy = copy)
    init()
}

inline fun subDI(parentDI: Lazy<DI>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI({ parentDI.value }, allowSilentOverride, copy, init)

inline fun <T> T.subDI(parentDI: DIPropertyDelegateProvider<T>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI(parentDI.provideDelegate(this, null), allowSilentOverride, copy, init)

inline fun Activity.retainedSubDI(noinline parentDI: () -> DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = retainedDI(allowSilentOverride) {
    extend(parentDI(), copy = copy)
    init()
}

inline fun Activity.retainedSubDI(parentDI: Lazy<DI>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = retainedSubDI({ parentDI.value }, allowSilentOverride, copy, init)

inline fun Activity.retainedSubDI(parentDI: DIPropertyDelegateProvider<Activity>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = retainedSubDI(parentDI.provideDelegate(this, null), allowSilentOverride, copy, init)