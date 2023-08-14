package org.kodein.di.android

import android.app.Activity
import org.kodein.di.*

public inline fun subDI(noinline parentDI: () -> DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit): LazyDI = DI.lazy(allowSilentOverride) {
    extend(parentDI(), copy = copy)
    init()
}

public inline fun subDI(parentDI: Lazy<DI>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit): LazyDI = subDI({ parentDI.value }, allowSilentOverride, copy, init)

public inline fun <T> T.subDI(parentDI: DIPropertyDelegateProvider<T>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit): LazyDI = subDI(parentDI.provideDelegate(this, null), allowSilentOverride, copy, init)

public inline fun Activity.retainedSubDI(noinline parentDI: () -> DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit): Lazy<DI> = retainedDI(allowSilentOverride) {
    extend(parentDI(), copy = copy)
    init()
}

public inline fun Activity.retainedSubDI(parentDI: Lazy<DI>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit): Lazy<DI> = retainedSubDI({ parentDI.value }, allowSilentOverride, copy, init)

public inline fun Activity.retainedSubDI(parentDI: DIPropertyDelegateProvider<Activity>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit): Lazy<DI> = retainedSubDI(parentDI.provideDelegate(this, null), allowSilentOverride, copy, init)