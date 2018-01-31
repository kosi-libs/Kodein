package org.kodein.erased

import org.kodein.DKodein
import org.kodein.DKodeinAware
import org.kodein.erased
import org.kodein.toProvider

inline fun <reified A, reified T : Any> DKodeinAware.factory(tag: Any? = null) = dkodein.Factory<A, T>(erased(), erased(), tag)
/**
 * Gets a factory of `T` for the given argument type, return type and tag, or nul if none is found.
 *
 * Whether this factory will re-create a new instance at each call or not depends on the binding scope.
 *
 * A & T generics will be erased.
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> DKodeinAware.factoryOrNull(tag: Any? = null) = dkodein.FactoryOrNull<A, T>(erased(), erased(), tag)

inline fun <reified A, reified T : Any> DKodeinAware.allFactories(tag: Any? = null) = dkodein.AllFactories<A, T>(erased(), erased(), tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased.
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.provider(tag: Any? = null) = dkodein.Provider<T>(erased(), tag)

inline fun <reified A, reified T : Any> DKodeinAware.provider(tag: Any? = null, arg: A) = dkodein.Factory<A, T>(erased(), erased(), tag).toProvider { arg }

inline fun <reified A, reified T : Any> DKodeinAware.provider(tag: Any? = null, noinline fArg: () -> A) = dkodein.Factory<A, T>(erased(), erased(), tag).toProvider(fArg)
/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * Whether this provider will re-create a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased.
 *
 * @param T The type of object the provider returns.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A provider, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> DKodeinAware.providerOrNull(tag: Any? = null) = dkodein.ProviderOrNull<T>(erased(), tag)

inline fun <reified A, reified T : Any> DKodeinAware.providerOrNull(tag: Any? = null, arg: A) = dkodein.FactoryOrNull<A, T>(erased(), erased(), tag)?.toProvider { arg }

inline fun <reified A, reified T : Any> DKodeinAware.providerOrNull(tag: Any? = null, noinline fArg: () -> A) = dkodein.FactoryOrNull<A, T>(erased(), erased(), tag)?.toProvider(fArg)

inline fun <reified T : Any> DKodeinAware.allProviders(tag: Any? = null) = dkodein.AllProviders<T>(erased(), tag)

inline fun <reified A, reified T : Any> DKodeinAware.allProviders(tag: Any? = null, arg: A) = dkodein.AllFactories<A, T>(erased(), erased(), tag).map { it.toProvider { arg } }

inline fun <reified A, reified T : Any> DKodeinAware.allProviders(tag: Any? = null, noinline fArg: () -> A) = dkodein.AllFactories<A, T>(erased(), erased(), tag).map { it.toProvider(fArg) }

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws Kodein.NotFoundException if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.instance(tag: Any? = null) = dkodein.Instance<T>(erased(), tag)

inline fun <reified A, reified T : Any> DKodeinAware.instance(tag: Any? = null, arg: A) = dkodein.Factory<A, T>(erased(), erased(), tag).invoke(arg)

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * Whether the returned object is a new instance at each call or not depends on the binding scope.
 *
 * T generics will be erased.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [Kodein] instance or a [KodeinAware] class.
 * @param tag The bound tag, if any.
 * @return An instance, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
 */
inline fun <reified T : Any> DKodeinAware.instanceOrNull(tag: Any? = null) = dkodein.InstanceOrNull<T>(erased(), tag)

inline fun <reified A, reified T : Any> DKodeinAware.instanceOrNull(tag: Any? = null, arg: A) = dkodein.FactoryOrNull<A, T>(erased(), erased(), tag)?.invoke(arg)

inline fun <reified T : Any> DKodeinAware.allInstances(tag: Any? = null) = dkodein.AllInstances<T>(erased(), tag)

inline fun <reified A, reified T : Any> DKodeinAware.allInstances(tag: Any? = null, arg: A) = dkodein.AllFactories<A, T>(erased(), erased(), tag).map { it.invoke(arg) }

inline fun <reified C> DKodeinAware.on(context: C, receiver: Any? = DKodein.SAME_RECEIVER) = dkodein.On(kcontext(context), receiver)

fun DKodeinAware.on(@Suppress("UNUSED_PARAMETER") _0: Nothing? = null, receiver: Any?) = dkodein.On(DKodein.SAME_CONTEXT, receiver)
