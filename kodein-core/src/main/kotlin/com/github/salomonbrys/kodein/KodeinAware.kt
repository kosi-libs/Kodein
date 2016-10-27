package com.github.salomonbrys.kodein

import java.lang.reflect.Type

/**
 * Base [KodeinAware] interface.
 *
 * It is separate from [KodeinAware] because [Kodein] implements itself [KodeinAwareBase] but not [KodeinAware].<br />
 * This is because there are some extension functions to [KodeinAware] that would not make sense applied to the [Kodein] object.<br />
 * For example, [KodeinAware.withClass], if applied to [Kodein], would create a very un-expected result.
 */
interface KodeinAwareBase {

    /**
     * A Kodein Aware class must be within reach of a Kodein object.
     */
    val kodein: Kodein
}

/**
 * Gets a factory of `T` for the given argument type, return type and tag.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * A & T generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.genericFactory(tag: Any? = null): (A) -> T = kodein.typed.factory(genericToken<A>(), genericToken<T>(), tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.erasedFactory(tag: Any? = null): (A) -> T = kodein.typed.factory(typeClass<A>(), T::class.java, tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * A & T generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.genericFactoryOrNull(tag: Any? = null): ((A) -> T)? = kodein.typed.factoryOrNull(genericToken<A>(), genericToken<T>(), tag)

/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * A & T generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> KodeinAwareBase.erasedFactoryOrNull(tag: Any? = null): ((A) -> T)? = kodein.typed.factoryOrNull(typeClass<A>(), T::class.java, tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.genericProvider(tag: Any? = null): () -> T = kodein.typed.provider(genericToken<T>(), tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased!
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.erasedProvider(tag: Any? = null): () -> T = kodein.typed.provider(T::class.java, tag)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> KodeinAwareBase.genericProviderOrNull(tag: Any? = null): (() -> T)? = kodein.typed.providerOrNull(genericToken<T>(), tag)

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased!
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> KodeinAwareBase.erasedProviderOrNull(tag: Any? = null): (() -> T)? = kodein.typed.providerOrNull(T::class.java, tag)

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.genericInstance(tag: Any? = null): T = kodein.typed.instance(genericToken<T>(), tag)

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.erasedInstance(tag: Any? = null): T = kodein.typed.instance(T::class.java, tag)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.genericInstanceOrNull(tag: Any? = null): T? = kodein.typed.instanceOrNull(genericToken<T>(), tag)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> KodeinAwareBase.erasedInstanceOrNull(tag: Any? = null): T? = kodein.typed.instanceOrNull(T::class.java, tag)

/**
 * Allows to get a provider or an instance from a factory with a curried argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument that the factory takes.
 * @property kodein The Kodein instance to use for retrieval.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @property argType The type of argument that the factory takes.
 */
class CurriedKodeinFactory<out A>(val kodein: () -> Kodein, val arg: () -> A, val argType: Type)

/**
 * Gets a provider of `T` for the given tag from a curried factory with an `A` argument.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedKodeinFactory<A>.genericProvider(tag: Any? = null): () -> T = kodein().typed.factory(argType, genericToken<T>(), tag).toProvider(arg)

/**
 * Gets a provider of `T` for the given tag from a curried factory with an `A` argument.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased!
 *
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedKodeinFactory<A>.erasedProvider(tag: Any? = null): () -> T = kodein().typed.factory(argType, T::class.java, tag).toProvider(arg)

/**
 * Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedKodeinFactory<A>.genericProviderOrNull(tag: Any? = null): (() -> T)? = kodein().typed.factoryOrNull(argType, genericToken<T>(), tag)?.toProvider(arg)

/**
 * Gets a provider of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased!
 *
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedKodeinFactory<A>.erasedProviderOrNull(tag: Any? = null): (() -> T)? = kodein().typed.factoryOrNull(argType, T::class.java, tag)?.toProvider(arg)

/**
 * Gets an instance of `T` for the given tag from a curried factory with an `A` argument.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedKodeinFactory<A>.genericInstance(tag: Any? = null): T = kodein().typed.factory(argType, genericToken<T>(), tag).invoke(arg())

/**
 * Gets an instance of `T` for the given tag from a curried factory with an `A` argument.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedKodeinFactory<A>.erasedInstance(tag: Any? = null): T = kodein().typed.factory(argType, T::class.java, tag).invoke(arg())

/**
 * Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be kept.
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedKodeinFactory<A>.genericInstanceOrNull(tag: Any? = null): T? = kodein().typed.factoryOrNull(argType, genericToken<T>(), tag)?.invoke(arg())

/**
 * Gets an instance of `T` for the given tag from a curried factory with an `A` argument, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <A, reified T : Any> CurriedKodeinFactory<A>.erasedInstanceOrNull(tag: Any? = null): T? = kodein().typed.factoryOrNull(argType, T::class.java, tag)?.invoke(arg())

/**
 * Allows to get a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> KodeinAwareBase.withGeneric(noinline arg: () -> A): CurriedKodeinFactory<A> = CurriedKodeinFactory({ kodein }, arg, genericToken<A>().type)

/**
 * Allows to get a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg A function that provides the argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> KodeinAwareBase.withErased(noinline arg: () -> A): CurriedKodeinFactory<A> = CurriedKodeinFactory({ kodein }, arg, typeClass<A>())

/**
 * Allows to get a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be kept.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg The argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> KodeinAwareBase.withGeneric(arg: A): CurriedKodeinFactory<A> = withGeneric { arg }

/**
 * Allows to get a provider or an instance from a curried factory with an `A` argument.
 *
 * A generics will be erased!
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @property arg The argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> KodeinAwareBase.withErased(arg: A): CurriedKodeinFactory<A> = withErased { arg }

/**
 * Any class that extends this interface can use Kodein "seamlessly".
 */
interface KodeinAware : KodeinAwareBase
