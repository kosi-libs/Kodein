@file:Suppress("DEPRECATION")
package org.kodein.di

import org.kodein.type.TypeToken

/**
 * Gets all factories that match the the given argument type, return type and tag.
 *
 * @param A The type of argument the factories take.
 * @param T The type of object to retrieve with the factories.
 * @param argType The type of argument the factories take.
 * @param type The type of object to retrieve with the factories.
 * @param tag The bound tag, if any.
 * @return A list of factories of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
public fun <A, T : Any> DIAware.AllFactories(
    argType: TypeToken<in A>,
    type: TypeToken<out T>,
    tag: Any? = null,
): LazyDelegate<List<(A) -> T>> = DIProperty(diTrigger, diContext) { ctx, _ ->
    di.container.allFactories(DI.Key(ctx.anyType, argType, type, tag), ctx.value)
}

/**
 * Gets all providers that match the the given return type and tag.
 *
 * @param T The type of object to retrieve with the providers.
 * @param type The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @return A list of providers of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
public fun <T : Any> DIAware.AllProviders(
    type: TypeToken<out T>,
    tag: Any? = null,
): LazyDelegate<List<() -> T>> = DIProperty(diTrigger, diContext) { ctx, _ ->
    di.container.allProviders(DI.Key(ctx.anyType, TypeToken.Unit, type, tag), ctx.value)
}

/**
 * Gets all providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param argType The type of argument the curried factories take.
 * @param type The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A list of providers of [T].
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
public fun <A, T : Any> DIAware.AllProviders(
        argType: TypeToken<in A>,
        type: TypeToken<out T>,
        tag: Any? = null,
        arg: () -> A,
): LazyDelegate<List<() -> T>> = DIProperty(diTrigger, diContext) { ctx, _ ->
        di.container.allFactories(DI.Key(ctx.anyType, argType, type, tag),
            ctx.value).map { it.toProvider(arg) }
    }

/**
 * Gets all instances from providers that match the the given return type and tag.
 *
 * @param T The type of object to retrieve with the providers.
 * @param type The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @return A list of [T] instances.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
public fun <T : Any> DIAware.AllInstances(type: TypeToken<out T>, tag: Any? = null): LazyDelegate<List<T>> =
    DIProperty(diTrigger, diContext) { ctx, _ ->
        di.container.allProviders(DI.Key(ctx.anyType,
            TypeToken.Unit,
            type,
            tag), ctx.value).map { it.invoke() }
    }

/**
 * Gets all instances from providers that match the the given return type and tag, curried from factories that take an argument [A].
 *
 * @param A The type of argument the curried factories take.
 * @param T The type of object to retrieve with the providers.
 * @param argType The type of argument the curried factories take.
 * @param type The type of object to retrieve with the providers.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A list of [T] instances.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
public fun <A, T : Any> DIAware.AllInstances(
        argType: TypeToken<in A>,
        type: TypeToken<T>,
        tag: Any? = null,
        arg: () -> A,
): LazyDelegate<List<T>> = DIProperty(diTrigger, diContext) { ctx, _ ->
        di.container.allFactories(DI.Key(ctx.anyType, argType, type, tag),
            ctx.value).map { it.invoke(arg()) }
    }