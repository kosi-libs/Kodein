package org.kodein.di.android

import android.app.Activity
import org.kodein.di.*

inline fun subDI(noinline parentDI: () -> DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = DI.lazy(allowSilentOverride) {
    extend(parentDI(), copy = copy)
    init()
}
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("subDI(parentDI, allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun subKodein(noinline parentDI: () -> DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI(parentDI, allowSilentOverride, copy, init)

inline fun subDI(parentDI: Lazy<DI>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI({ parentDI.value }, allowSilentOverride, copy, init)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("subDI(parentDI, allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun subKodein(parentDI: Lazy<DI>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI(parentDI, allowSilentOverride, copy, init)

inline fun <T> T.subDI(parentDI: DIPropertyDelegateProvider<T>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI(parentDI.provideDelegate(this, null), allowSilentOverride, copy, init)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("subDI(parentDI, allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun <T> T.subKodein(parentDI: DIPropertyDelegateProvider<T>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI(parentDI, allowSilentOverride, copy, init)

inline fun Activity.retainedSubDI(noinline parentDI: () -> DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = retainedDI(allowSilentOverride) {
    extend(parentDI(), copy = copy)
    init()
}
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("retainedSubDI(parentDI, allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun Activity.retainedSubKodein(noinline parentDI: () -> DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) =retainedSubDI(parentDI, allowSilentOverride, copy, init)

inline fun Activity.retainedSubDI(parentDI: Lazy<DI>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = retainedSubDI({ parentDI.value }, allowSilentOverride, copy, init)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("retainedSubDI(parentDI, allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun Activity.retainedSubKodein(parentDI: Lazy<DI>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = retainedSubDI(parentDI, allowSilentOverride, copy, init)

inline fun Activity.retainedSubDI(parentDI: DIPropertyDelegateProvider<Activity>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = retainedSubDI(parentDI.provideDelegate(this, null), allowSilentOverride, copy, init)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("retainedSubDI(parentDI, allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun Activity.retainedSubKodein(parentDI: DIPropertyDelegateProvider<Activity>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = retainedSubDI(parentDI, allowSilentOverride, copy, init)
