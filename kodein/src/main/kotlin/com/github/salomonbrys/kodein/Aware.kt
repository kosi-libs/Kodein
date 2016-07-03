package com.github.salomonbrys.kodein

interface KodeinAwareBase {
    val kodein: Kodein
}

/**
 * Gets a factory for the given argument type, return type and tag.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.factory(tag: Any? = null): (A) -> T = kodein.typed.factory(typeToken<A>(), typeToken<T>(), tag)

/**
 * Gets a factory for the given argument type, return type and tag, or null if non is found.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.factoryOrNull(tag: Any? = null): ((A) -> T)? = kodein.typed.factoryOrNull(typeToken<A>(), typeToken<T>(), tag)

/**
 * Gets a provider for the given type and tag.
 *
 * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
 */
inline fun <reified T : Any> KodeinAwareBase.provider(tag: Any? = null): () -> T = kodein.typed.provider(typeToken<T>(), tag)

/**
 * Gets a provider for the given type and tag, or null if none is found.
 *
 * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> KodeinAwareBase.providerOrNull(tag: Any? = null): (() -> T)? = kodein.typed.providerOrNull(typeToken<T>(), tag)

/**
 * Gets an instance for the given type and tag.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 */
inline fun <reified T : Any> KodeinAwareBase.instance(tag: Any? = null): T = kodein.typed.instance(typeToken<T>(), tag)

/**
 * Gets an instance for the given type and tag, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 */
inline fun <reified T : Any> KodeinAwareBase.instanceOrNull(tag: Any? = null): T? = kodein.typed.instanceOrNull(typeToken<T>(), tag)

class CurriedKodeinFactory<A>(val kodein: Kodein, val arg: A, val argType: TypeToken<A>) {

    inline fun <reified T : Any> provider(tag: Any? = null): (() -> T) = kodein.typed.factory(argType, typeToken<T>(), tag).toProvider(arg)

    inline fun <reified T : Any> providerOrNull(tag: Any? = null): (() -> T)? = kodein.typed.factoryOrNull(argType, typeToken<T>(), tag)?.toProvider(arg)

    inline fun <reified T : Any> instance(tag: Any? = null): T = kodein.typed.factory(argType, typeToken<T>(), tag).invoke(arg)

    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): T? = kodein.typed.factoryOrNull(argType, typeToken<T>(), tag)?.invoke(arg)
}

inline fun <reified A> KodeinAwareBase.with(arg: A) = CurriedKodeinFactory(kodein, arg, typeToken<A>())

inline fun <reified A, reified T : Any> KodeinAwareBase.providerFromFactory(arg: A, tag: Any? = null): () -> T = factory<A, T>(tag).toProvider(arg)

inline fun <reified A, reified T : Any> KodeinAwareBase.providerFromFactoryOrNull(arg: A, tag: Any? = null): (() -> T)? = factoryOrNull<A, T>(tag)?.toProvider(arg)

inline fun <reified A, reified T : Any> KodeinAwareBase.instanceFromFactory(arg: A, tag: Any? = null): T = factory<A, T>(tag).invoke(arg)

inline fun <reified A, reified T : Any> KodeinAwareBase.instanceFromFactoryOrNull(arg: A, tag: Any? = null): T? = factoryOrNull<A, T>(tag)?.invoke(arg)


interface KodeinAware : KodeinAwareBase
