package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Typed access to Kodein dependency injection. Can be used in Java.
 *
 * Each method works either with a [TypeToken], a `Class` or a [Type].
 *
 * In Java, to create a [TypeToken], you should use the following syntax: `new TypeReference<Type<SubType>>(){}`.
 * In Kotlin, simply use the [typeToken] function.
 *
 * This class contains utility functions that will all evebtually use the associated [KodeinContainer]
 *
 * @property _container The container to forward call to.
 */
@Suppress("UNCHECKED_CAST", "unused")
class TKodein(private val _container: KodeinContainer) {

    /**
     * Gets a factory for the given argument type, return type and tag.
     *
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun factory(argType: Type, type: Type, tag: Any? = null): (Any) -> Any = _container.nonNullFactory(Kodein.Key(Kodein.Bind(type, tag), argType))

    /**
     * Gets a factory of `T` for the given argument type, return type and tag.
     *
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory ot `T`.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> factory(argType: Type, type: Class<T>, tag: Any? = null): (Any) -> T = factory(argType, type as Type, tag) as (Any) -> T

    /**
     * Gets a factory of `T` for the given argument type, return type and tag.
     *
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> factory(argType: Type, type: TypeToken<T>, tag: Any? = null): (Any) -> T = factory(argType, type.type, tag) as (Any) -> T

    /**
     * Gets a factory for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A> factory(argType: Class<A>, type: Type, tag: Any? = null): (A) -> Any = factory(argType as Type, type, tag) as (A) -> Any

    /**
     * Gets a factory of `T` for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A, T : Any> factory(argType: Class<A>, type: Class<T>, tag: Any? = null): (A) -> T = factory(argType as Type, type as Type, tag) as (A) -> T

    /**
     * Gets a factory of `T` for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A, T : Any> factory(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): (A) -> T = factory(argType as Type, type.type, tag) as (A) -> T

    /**
     * Gets a factory for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A> factory(argType: TypeToken<A>, type: Type, tag: Any? = null): (A) -> Any = factory(argType.type, type, tag) as (A) -> Any

    /**
     * Gets a factory of `T` for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A, T : Any> factory(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): (A) -> T = factory(argType.type, type as Type, tag) as (A) -> T

    /**
     * Gets a factory of `T` for the given argument type, return type and tag.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A, T : Any> factory(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): (A) -> T = factory(argType.type, type.type, tag) as (A) -> T



    /**
     * Gets a factory for the given argument type, return type and tag, or null if none is found.
     *
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun factoryOrNull(argType: Type, type: Type, tag: Any? = null): ((Any) -> Any)? = _container.factoryOrNull(Kodein.Key(Kodein.Bind(type, tag), argType))

    /**
     * Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.
     *
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> factoryOrNull(argType: Type, type: Class<T>, tag: Any? = null): ((Any) -> T)? = factoryOrNull(argType, type as Type, tag) as ((Any) -> T)?

    /**
     * Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.
     *
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> factoryOrNull(argType: Type, type: TypeToken<T>, tag: Any? = null): ((Any) -> T)? = factoryOrNull(argType, type.type, tag) as ((Any) -> T)?

    /**
     * Gets a factory for the given argument type, return type and tag, or null if none is found.
     *
     * @param A The type of argument the returned factory takes.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A> factoryOrNull(argType: Class<A>, type: Type, tag: Any? = null): ((A) -> Any)? = factoryOrNull(argType as Type, type, tag) as ((A) -> Any)?

    /**
     * Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A, T : Any> factoryOrNull(argType: Class<A>, type: Class<T>, tag: Any? = null): ((A) -> T)? = factoryOrNull(argType as Type, type as Type, tag) as ((A) -> T)?

    /**
     * Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A, T : Any> factoryOrNull(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): ((A) -> T)? = factoryOrNull(argType as Type, type.type, tag) as ((A) -> T)?

    /**
     * Gets a factory for the given argument type, return type and tag, or null if none is found.
     *
     * @param A The type of argument the returned factory takes.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A> factoryOrNull(argType: TypeToken<A>, type: Type, tag: Any? = null): ((A) -> Any)? = factoryOrNull(argType.type, type, tag) as ((A) -> Any)?

    /**
     * Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): ((A) -> T)? = factoryOrNull(argType.type, type as Type, tag) as ((A) -> T)?

    /**
     * Gets a factory of `T` for the given argument type, return type and tag, or null if none is found.
     *
     * @param A The type of argument the returned factory takes.
     * @param T The type of object to retrieve with the returned factory.
     * @param argType The type of argument the returned factory takes.
     * @param type The type of object to retrieve with the returned factory.
     * @param tag The bound tag, if any.
     * @return A factory of `T`, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): ((A) -> T)? = factoryOrNull(argType.type, type.type, tag) as ((A) -> T)?



    /**
     * Gets a provider for the given type and tag.
     *
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun provider(type: Type, tag: Any? = null): () -> Any = _container.nonNullProvider(Kodein.Bind(type, tag))

    /**
     * Gets a provider of `T` for the given type and tag.
     *
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider of `T`.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> provider(type: Class<T>, tag: Any? = null): () -> T = provider(type as Type, tag) as () -> T

    /**
     * Gets a provider of `T` for the given type and tag.
     *
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider of `T`.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> provider(type: TypeToken<T>, tag: Any? = null): () -> T = provider(type.type, tag) as () -> T



    /**
     * Gets a provider for the given type and tag, or null if none is found.
     *
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider, or null if no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun providerOrNull(type: Type, tag: Any? = null): (() -> Any)? = _container.providerOrNull(Kodein.Bind(type, tag))

    /**
     * Gets a provider of `T` for the given type and tag, or null if none is found.
     *
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider of `T`, or null if no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> providerOrNull(type: Class<T>, tag: Any? = null): (() -> T)? = providerOrNull(type as Type, tag) as (() -> T)?

    /**
     * Gets a provider of `T` for the given type and tag, or null if none is found.
     *
     * @param T The type of object to retrieve with the returned provider.
     * @param type The type of object to retrieve with the returned provider.
     * @param tag The bound tag, if any.
     * @return A provider of `T`, or null if no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> providerOrNull(type: TypeToken<T>, tag: Any? = null): (() -> T)? = providerOrNull(type.type, tag) as (() -> T)?



    /**
     * Gets an instance for the given type and tag.
     *
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun instance(type: Type, tag: Any? = null): Any = _container.nonNullProvider(Kodein.Bind(type, tag)).invoke()

    /**
     * Gets an instance of `T` for the given type and tag.
     *
     * @param T The type of object to retrieve.
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> instance(type: Class<T>, tag: Any? = null): T = instance(type as Type, tag) as T

    /**
     * Gets an instance of `T` for the given type and tag.
     *
     * @param T The type of object to retrieve.
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> instance(type: TypeToken<T>, tag: Any? = null): T = instance(type.type, tag) as T



    /**
     * Gets an instance for the given type and tag, or null if none is found.
     *
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance, or null if no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun instanceOrNull(type: Type, tag: Any? = null): Any? = _container.providerOrNull(Kodein.Bind(type, tag))?.invoke()

    /**
     * Gets an instance of `T` for the given type and tag, or null if none is found.
     *
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`, or null if no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> instanceOrNull(type: Class<T>, tag: Any? = null): T? = instanceOrNull(type as Type, tag) as T?

    /**
     * Gets an instance of `T` for the given type and tag, or null if none is found.
     *
     * @param type The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return An instance of `T`, or null if no provider was found.
     * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
     */
    @JvmOverloads
    fun <T : Any> instanceOrNull(type: TypeToken<T>, tag: Any? = null): T? = instanceOrNull(type.type, tag) as T?
}
