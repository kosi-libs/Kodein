package com.github.salomonbrys.kodein

import kotlin.reflect.KClass

interface KodeinInjectedBase {
    val injector: KodeinInjector
}

inline fun <reified A, reified T : Any> KodeinInjectedBase.factory(tag: Any? = null): InjectedProperty<(A) -> T> = injector.typed.factory(typeToken<A>(), typeToken<T>(), tag)
inline fun <reified A, reified T : Any> KodeinInjectedBase.factoryOrNull(tag: Any? = null): InjectedProperty<((A) -> T)?> = injector.typed.factoryOrNull(typeToken<A>(), typeToken<T>(), tag)

inline fun <reified T : Any> KodeinInjectedBase.provider(tag: Any? = null): InjectedProperty<() -> T> = injector.typed.provider(typeToken<T>(), tag)
inline fun <reified T : Any> KodeinInjectedBase.providerOrNull(tag: Any? = null): InjectedProperty<(() -> T)?> = injector.typed.providerOrNull(typeToken<T>(), tag)

inline fun <reified T : Any> KodeinInjectedBase.instance(tag: Any? = null): InjectedProperty<T> = injector.typed.instance(typeToken<T>(), tag)
inline fun <reified T : Any> KodeinInjectedBase.instanceOrNull(tag: Any? = null): InjectedProperty<T?> = injector.typed.instanceOrNull(typeToken<T>(), tag)

class CurriedInjectorFactory<A>(val injector: KodeinInjector, val arg: A, val argType: TypeToken<A>) {

    inline fun <reified T : Any> provider(tag: Any? = null): Lazy<() -> T> = injector.typed.factory(argType, typeToken<T>(), tag).toProvider(arg)
    inline fun <reified T : Any> providerOrNull(tag: Any? = null): Lazy<(() -> T)?> = injector.typed.factoryOrNull(argType, typeToken<T>(), tag).toNullableProvider(arg)

    inline fun <reified T : Any> instance(tag: Any? = null): Lazy<T> = injector.typed.factory(argType, typeToken<T>(), tag).toInstance(arg)
    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Lazy<T?> = injector.typed.factoryOrNull(argType, typeToken<T>(), tag).toNullableInstance(arg)
}

inline fun <reified A> KodeinInjectedBase.with(arg: A) = CurriedInjectorFactory(injector, arg, typeToken<A>())

inline fun <reified A, reified T : Any> KodeinInjectedBase.providerFromFactory(arg: A, tag: Any? = null): Lazy<() -> T> = factory<A, T>(tag).toProvider(arg)
inline fun <reified A, reified T : Any> KodeinInjectedBase.providerFromFactoryOrNull(arg: A, tag: Any? = null): Lazy<(() -> T)?> = factoryOrNull<A, T>(tag).toNullableProvider(arg)

inline fun <reified A, reified T : Any> KodeinInjectedBase.instanceFromFactory(arg: A, tag: Any? = null): Lazy<T> = factory<A, T>(tag).toInstance(arg)
inline fun <reified A, reified T : Any> KodeinInjectedBase.instanceFromFactoryOrNull(arg: A, tag: Any? = null): Lazy<T?> = factoryOrNull<A, T>(tag).toNullableInstance(arg)


interface KodeinInjected : KodeinInjectedBase {

    fun inject(kodein: Kodein) = injector.inject(kodein)

    fun onInjected(cb: (Kodein) -> Unit) = injector.onInjected(cb)

}

inline fun <reified T : KodeinInjected, reified R : Any> T.instanceForClass(tag: Any? = null): Lazy<R> = with(T::class as KClass<*>).instance<R>(tag)

inline fun <reified T : KodeinInjected, reified R : Any> T.providerForClass(tag: Any? = null): Lazy<() -> R> = with(T::class as KClass<*>).provider<R>(tag)
