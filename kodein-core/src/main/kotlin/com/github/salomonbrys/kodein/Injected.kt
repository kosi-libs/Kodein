package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Base [KodeinInjected] interface.
 *
 * It is separate from [KodeinInjected] because [KodeinInjector] implements itself [KodeinInjectedBase] but not [KodeinInjected].<br />
 * This is because there are some extension functions to [KodeinInjected] that would not make sense applied to the [KodeinInjector] object.<br />
 * For example, [KodeinInjected.withClass], if applied to [KodeinInjector], would create a very un-expected result.
 */
interface KodeinInjectedBase {

    /**
     * A Kodein Injected class must be within reach of a Kodein Injector object.
     */
    val injector: KodeinInjector

    /**
     * Will inject all properties that were created with the [injector] with the values found in the provided Kodein object.
     *
     * Will also call all callbacks that were registered with [onInjected].
     *
     * @param kodein The kodein object to use to inject all properties.
     * @throws Kodein.NotFoundException if one the properties is requesting a value that is not bound.
     * @throws Kodein.DependencyLoopException if one the instance properties is requesting a value whose construction triggered a dependency loop.
     */
    fun inject(kodein: Kodein): Unit = injector.inject(kodein)

    /**
     * Registers a callback to be called once the [injector] gets injected with a [Kodein] object.
     *
     * If the injector has already been injected, the callback will be called instantly.
     *
     * The callback is guaranteed to be called only once.
     *
     * @param cb The callback to register
     */
    fun onInjected(cb: (Kodein) -> Unit): Unit = injector.onInjected(cb)
}

/**
 * Gets a lazy factory for the given type, tag and argument type.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * A & T generics will be kept.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a factory of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinInjectedBase.genericFactory(tag: Any? = null): InjectedProperty<(A) -> T> = injector.typed.factory(genericToken<A>(), genericToken<T>(), tag)

/**
 * Gets a lazy factory for the given type, tag and argument type.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a factory of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinInjectedBase.erasedFactory(tag: Any? = null): InjectedProperty<(A) -> T> = injector.typed.factory(typeClass<A>(), T::class.java, tag)

/**
 * Gets a lazy factory for the given type, tag and argument type, or null if none is found
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * A & T generics will be kept.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either a factory of `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinInjectedBase.genericFactoryOrNull(tag: Any? = null): InjectedProperty<((A) -> T)?> = injector.typed.factoryOrNull(genericToken<A>(), genericToken<T>(), tag)

/**
 * Gets a lazy factory for the given type, tag and argument type, or null if none is found
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields either a factory of `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinInjectedBase.erasedFactoryOrNull(tag: Any? = null): InjectedProperty<((A) -> T)?> = injector.typed.factoryOrNull(typeClass<A>(), T::class.java, tag)



/**
 * Gets a lazy provider for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinInjectedBase.genericProvider(tag: Any? = null): InjectedProperty<() -> T> = injector.typed.provider(genericToken<T>(), tag)

/**
 * Gets a lazy provider for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinInjectedBase.erasedProvider(tag: Any? = null): InjectedProperty<() -> T> = injector.typed.provider(T::class.java, tag)

/**
 * Gets a lazy provider for the given type and tag, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T` or null if no provider was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinInjectedBase.genericProviderOrNull(tag: Any? = null): InjectedProperty<(() -> T)?> = injector.typed.providerOrNull(genericToken<T>(), tag)

/**
 * Gets a lazy provider for the given type and tag, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T` or null if no provider was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinInjectedBase.erasedProviderOrNull(tag: Any? = null): InjectedProperty<(() -> T)?> = injector.typed.providerOrNull(T::class.java, tag)



/**
 * Gets a lazy instance for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> KodeinInjectedBase.genericInstance(tag: Any? = null): InjectedProperty<T> = injector.typed.instance(genericToken<T>(), tag)

/**
 * Gets a lazy instance for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> KodeinInjectedBase.erasedInstance(tag: Any? = null): InjectedProperty<T> = injector.typed.instance(T::class.java, tag)

/**
 * Gets a lazy instance for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> KodeinInjectedBase.genericInstanceOrNull(tag: Any? = null): InjectedProperty<T?> = injector.typed.instanceOrNull(genericToken<T>(), tag)

/**
 * Gets a lazy instance for the given type and tag.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> KodeinInjectedBase.erasedInstanceOrNull(tag: Any? = null): InjectedProperty<T?> = injector.typed.instanceOrNull(T::class.java, tag)



/**
 * Gets a lazy [Kodein] object.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @return A lazy property that yields a [Kodein].
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
fun KodeinInjectedBase.kodein(): Lazy<Kodein> = injector.kodein()



/**
 * Used to inject lazy providers or instances for factory bound types.
 *
 * @param A The type of argument that the factory takes.
 * @property injector The injector to use for injections.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @property argType The type of argument that the factory takes.
 */
class CurriedInjectorFactory<out A>(val injector: KodeinInjector, val arg: () -> A, val argType: Type)

/**
 * Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve with the provider.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> CurriedInjectorFactory<*>.genericProvider(tag: Any? = null): Lazy<() -> T> = injector.typed.factory(argType, genericToken<T>(), tag).toProvider(arg)

/**
 * Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve with the provider.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> CurriedInjectorFactory<*>.erasedProvider(tag: Any? = null): Lazy<() -> T> = injector.typed.factory(argType, T::class.java, tag).toProvider(arg)

/**
 * Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve with the provider.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> CurriedInjectorFactory<*>.genericProviderOrNull(tag: Any? = null): Lazy<(() -> T)?> = injector.typed.factoryOrNull(argType, genericToken<T>(), tag).toProvider(arg)

/**
 * Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve with the provider.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> CurriedInjectorFactory<*>.erasedProviderOrNull(tag: Any? = null): Lazy<(() -> T)?> = injector.typed.factoryOrNull(argType, T::class.java, tag).toProvider(arg)

/**
 * Gets a lazy instance of `T` for the given tag from a factory with an `A` argument.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> CurriedInjectorFactory<*>.genericInstance(tag: Any? = null): Lazy<T> = injector.typed.factory(argType, genericToken<T>(), tag).toInstance(arg)

/**
 * Gets a lazy instance of `T` for the given tag from a factory with an `A` argument.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T`.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> CurriedInjectorFactory<*>.erasedInstance(tag: Any? = null): Lazy<T> = injector.typed.factory(argType, T::class.java, tag).toInstance(arg)

/**
 * Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> CurriedInjectorFactory<*>.genericInstanceOrNull(tag: Any? = null): Lazy<T?> = injector.typed.factoryOrNull(argType, genericToken<T>(), tag).toInstance(arg)

/**
 * Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found.
 *
 * The returned property should not be accessed before calling [KodeinInjectedBase.inject].
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a `T` or null if no factory was found.
 * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
 */
inline fun <reified T : Any> CurriedInjectorFactory<*>.erasedInstanceOrNull(tag: Any? = null): Lazy<T?> = injector.typed.factoryOrNull(argType, T::class.java, tag).toInstance(arg)

/**
 * Allows to inject a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified A> KodeinInjectedBase.withGeneric(noinline arg: () -> A) = CurriedInjectorFactory(injector, arg, genericToken<A>().type)

/**
 * Allows to inject a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified A> KodeinInjectedBase.withErased(noinline arg: () -> A) = CurriedInjectorFactory(injector, arg, typeClass<A>())

/**
 * Allows to inject a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param arg The argument that will be passed to the factory.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified A> KodeinInjectedBase.withGeneric(arg: A) = withGeneric { arg }

/**
 * Allows to inject a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [KodeinInjector] instance or a [KodeinInjected] class.
 * @param arg The argument that will be passed to the factory.
 * @return An object from which you can inject an instance or a provider.
 */
inline fun <reified A> KodeinInjectedBase.withErased(arg: A) = withErased { arg }



/**
 * Any class that extends this interface can be injected "seamlessly".
 */
interface KodeinInjected : KodeinInjectedBase
