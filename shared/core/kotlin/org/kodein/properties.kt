package org.kodein

import kotlin.reflect.KProperty

enum class PropMode {
    LAZY,
    LAZY_NTS,
    DIRECT
}

class KodeinProperty<out V>(@PublishedApi internal val mode: PropMode, internal val injector: KodeinInjector?, @PublishedApi internal val get: (Any?) -> V) {

    operator fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> =
            when (mode) {
                PropMode.LAZY -> lazy { get(receiver) } .also { injector?.addProperty(it) }
                PropMode.LAZY_NTS -> lazy(LazyThreadSafetyMode.NONE) { get(receiver) } .also { injector?.addProperty(it) }
                PropMode.DIRECT -> lazyOf(get(receiver))
            }
}

//fun <A, T: Any> KodeinProperty<(A) -> T>.toProvider(arg: () -> A): KodeinProperty<() -> T> = KodeinProperty(mode) { receiver -> get(receiver).toProvider(arg) }
//
//fun <A, T: Any> KodeinProperty<((A) -> T)?>.toProviderOrNull(arg: () -> A): LazyYield<(() -> T)?> = LazyYield { receiver -> get(receiver)?.toProvider(arg) }
//
//fun <A, T: Any> KodeinProperty<(A) -> T>.toInstance(arg: () -> A): LazyYield<T> = LazyYield { receiver -> get(receiver).invoke(arg()) }
//
//fun <A, T: Any> KodeinProperty<((A) -> T)?>.toInstanceOrNull(arg: () -> A): LazyYield<T?> = LazyYield { receiver -> get(receiver)?.invoke(arg()) }
//
//fun <T: Any> KodeinProperty<() -> T>.toInstance(): LazyYield<T> = LazyYield { receiver -> get(receiver).invoke() }
//
//fun <T: Any> KodeinProperty<(() -> T)?>.toInstanceOrNull(): LazyYield<T?> = LazyYield { receiver -> get(receiver)?.invoke() }
