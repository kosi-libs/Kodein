@file:Suppress("FunctionName")

package org.kodein.di

import org.kodein.di.internal.DirectDIImpl

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIContext<C>"), DeprecationLevel.ERROR)
typealias KodeinContext<C> = DIContext<C>

/**
 * Defines a context and its type to be used by Di
 */
interface DIContext<C : Any> {
        /**
         * The type of the context, used to lookup corresponding bindings.
         */
        val type: TypeToken<in C>

        /**
         * The context itself.
         */
        val value: C

    /**
     * Defines a context and its type to be used by DI
     */
    data class Value<C : Any>(override val type: TypeToken<in C>, override val value: C) : DIContext<C>

    /**
     * Defines a context and its type to be used by DI
     */
    class Lazy<C : Any>(override val type: TypeToken<in C>, val getValue: () -> C) : DIContext<C> {
        override val value by lazy(getValue)
    }

    companion object {
        operator fun <C : Any> invoke(type: TypeToken<in C>, value: C): DIContext<C> = Value(type, value)
        operator fun <C : Any> invoke(type: TypeToken<in C>, getValue: () -> C): DIContext<C> = Lazy(type, getValue)
    }
}



@Suppress("UNCHECKED_CAST")
internal inline val DIContext<*>.anyType get() = type as TypeToken<in Any>

private object Contexes {
    val AnyDIContext = DIContext<Any>(AnyToken, Any())
}

/**
 * Default DI context, means no context.
 */
val AnyDIContext get() = Contexes.AnyDIContext
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("AnyDIContext"), DeprecationLevel.ERROR)
val AnyKodeinContext = AnyDIContext

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIAware"), DeprecationLevel.ERROR)
typealias KodeinAware = DIAware

/**
 * Any class that extends this interface can use DI "seamlessly".
 */
interface DIAware {

    /**
     * A DI Aware class must be within reach of a [DI] object.
     */
    val di: DI
    @Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di"), DeprecationLevel.ERROR)
    val kodein: DI get() = di

    /**
     * A DI Aware class can define a context that is for all retrieval by overriding this property.
     *
     * Note that even if you override this property, all bindings that do not use a Context or are not scoped will still work!
     */
    val diContext: DIContext<*> get() = AnyDIContext
    @Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("diContext"), DeprecationLevel.ERROR)
    val kodeinContext: DIContext<*> get() = diContext

    /**
     * Trigger to use that define when the retrieval will be done.
     *
     * By default, retrieval happens on first property access.
     * However, you can use a [DITrigger] to force retrieval at a given time of your choice.
     */
    val diTrigger: DITrigger? get() = null
    @Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("diTrigger"), DeprecationLevel.ERROR)
    val kodeinTrigger: DITrigger? get() = diTrigger
}

/**
 * Gets a factory of [T] for the given argument type, return type and tag.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param argType The type of argument the returned factory takes.
 * @param type The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A factory of [T].
 * @throws DI.NotFoundException If no factory was found.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
fun <A, T : Any> DIAware.Factory(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null): DIProperty<(A) -> T> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.factory(DI.Key(ctx.anyType, argType, type, tag), ctx.value) }

/**
 * Gets a factory of [T] for the given argument type, return type and tag, or null if none is found.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param argType The type of argument the returned factory takes.
 * @param type The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A factory of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
fun <A, T : Any> DIAware.FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null): DIProperty<((A) -> T)?> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.factoryOrNull(DI.Key(ctx.anyType, argType, type, tag), ctx.value) }

/**
 * Gets a provider of [T] for the given type and tag.
 *
 * @param T The type of object to retrieve with the returned provider.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <T : Any> DIAware.Provider(type: TypeToken<out T>, tag: Any? = null): DIProperty<() -> T> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.provider(DI.Key(ctx.anyType, UnitToken, type, tag), ctx.value) }

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param argType The type of argument the curried factory takes.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <A, T : Any> DIAware.Provider(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): DIProperty<() -> T> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.factory(DI.Key(ctx.anyType, argType, type, tag), ctx.value).toProvider(arg) }

/**
 * Gets a provider of [T] for the given type and tag, or null if none is found.
 *
 * @param T The type of object to retrieve with the returned provider.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @return A provider of [T], or null if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <T : Any> DIAware.ProviderOrNull(type: TypeToken<out T>, tag: Any? = null): DIProperty<(() -> T)?> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.providerOrNull(DI.Key(ctx.anyType, UnitToken, type, tag), ctx.value) }

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param argType The type of argument the curried factory takes.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <A, T : Any> DIAware.ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): DIProperty<(() -> T)?> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.factoryOrNull(DI.Key(ctx.anyType, argType, type, tag), ctx.value)?.toProvider(arg) }

/**
 * Gets an instance of [T] for the given type and tag.
 *
 * @param T The type of object to retrieve.
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <T : Any> DIAware.Instance(type: TypeToken<out T>, tag: Any? = null): DIProperty<T> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.provider(DI.Key(ctx.anyType, UnitToken, type, tag), ctx.value).invoke() }

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param argType The type of argument the curried factory takes.
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <A, T : Any> DIAware.Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): DIProperty<T> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.factory(DI.Key(ctx.anyType, argType, type, tag), ctx.value).invoke(arg()) }

/**
 * Gets an instance of [T] for the given type and tag, or null if none is found.
 *
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of [T], or null if no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <T : Any> DIAware.InstanceOrNull(type: TypeToken<out T>, tag: Any? = null): DIProperty<T?> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.providerOrNull(DI.Key(ctx.anyType, UnitToken, type, tag), ctx.value)?.invoke() }

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <A, T : Any> DIAware.InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): DIProperty<T?> =
        DIProperty(diTrigger, diContext) { ctx, _ -> di.container.factoryOrNull(DI.Key(ctx.anyType, argType, type, tag), ctx.value)?.invoke(arg()) }

/**
 * Return a direct [DirectDI] instance, with its receiver and context set to this DIAware receiver and context.
 */
val DIAware.direct: DirectDI get() = DirectDIImpl(di.container, diContext)

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIWrapper"), DeprecationLevel.ERROR)
private typealias KodeinWrapper = DIWrapper

private class DIWrapper(
        private val _base: DI,
        override val diContext: DIContext<*>,
        override val diTrigger: DITrigger? = null
) : DI {
    internal constructor(base: DIAware, diContext: DIContext<*> = base.diContext, trigger: DITrigger? = base.diTrigger) : this(base.di, diContext, trigger)

    override val di: DI get() = this

    override val container: DIContainer get() = _base.container
}

/**
 * Allows to create a new DI object with a context and/or a trigger set.
 *
 * @param context The new context of the new DI.
 * @param trigger The new trigger of the new DI.
 * @return A DI object that uses the same container as this one, but with its context and/or trigger changed.
 */
fun DIAware.On(context: DIContext<*> = this.diContext, trigger: DITrigger? = this.diTrigger): DI = DIWrapper(this, diContext = context, trigger = trigger)

/**
 * Allows to create a new instance of an unbound object with the same API as when bounding one.
 *
 * @param T The type of object to create.
 * @param creator A function that do create the object.
 */
fun <T> DIAware.newInstance(creator: DirectDI.() -> T): DIProperty<T> = DIProperty(diTrigger, diContext) { ctx, _ -> di.direct.On(ctx).run(creator) }

