@file:Suppress("FunctionName")

package org.kodein.di

import org.kodein.di.internal.DKodeinImpl

/**
 * Defines a context and its type to be used by Kodein
 */
@Deprecated(DEPRECATE_7X)
interface KodeinContext<C> {
        /**
         * The type of the context, used to lookup corresponding bindings.
         */
        val type: TypeToken<in C>

        /**
         * The context itself.
         */
        val value: C

    /**
     * Defines a context and its type to be used by Kodein
     */
    data class Value<C>(override val type: TypeToken<in C>, override val value: C) : KodeinContext<C>

    /**
     * Defines a context and its type to be used by Kodein
     */
    class Lazy<C>(override val type: TypeToken<in C>, val getValue: () -> C) : KodeinContext<C> {
        override val value by lazy(getValue)
    }

    companion object {
        operator fun <C> invoke(type: TypeToken<in C>, value: C): KodeinContext<C> = Value(type, value)
        operator fun <C> invoke(type: TypeToken<in C>, getValue: () -> C): KodeinContext<C> = Lazy(type, getValue)
    }
}



@Suppress("UNCHECKED_CAST")
@Deprecated(DEPRECATE_7X)
internal inline val KodeinContext<*>.anyType get() = type as TypeToken<in Any?>

@Deprecated(DEPRECATE_7X)
private object Contexes {
    val AnyKodeinContext = KodeinContext<Any?>(AnyToken, null)
}

/**
 * Default Kodein context, means no context.
 */
//object AnyKodeinContext : KodeinContext<Any?>(AnyToken, null)
val AnyKodeinContext get() = Contexes.AnyKodeinContext


/**
 * Any class that extends this interface can use Kodein "seamlessly".
 */
@Deprecated(DEPRECATE_7X)
interface KodeinAware {
    /**
     * A Kodein Aware class must be within reach of a [Kodein] object.
     */
    @Deprecated(DEPRECATE_7X)
    val kodein: Kodein

    /**
     * A Kodein Aware class can define a context that is for all retrieval by overriding this property.
     *
     * Note that even if you override this property, all bindings that do not use a Context or are not scoped will still work!
     */
    @Deprecated(DEPRECATE_7X)
    val kodeinContext: KodeinContext<*> get() = AnyKodeinContext

    /**
     * Trigger to use that define when the retrieval will be done.
     *
     * By default, retrieval happens on first property access.
     * However, you can use a [KodeinTrigger] to force retrieval at a given time of your choice.
     */
    @Deprecated(DEPRECATE_7X)
    val kodeinTrigger: KodeinTrigger? get() = null
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
 * @throws Kodein.NotFoundException If no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
fun <A, T : Any> KodeinAware.Factory(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null): KodeinProperty<(A) -> T> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.factory(Kodein.Key(ctx.anyType, argType, type, tag), ctx.value) }

/**
 * Gets a factory of [T] for the given argument type, return type and tag, or null if none is found.
 *
 * @param A The type of argument the returned factory takes.
 * @param T The type of object to retrieve with the returned factory.
 * @param argType The type of argument the returned factory takes.
 * @param type The type of object to retrieve with the returned factory.
 * @param tag The bound tag, if any.
 * @return A factory of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
fun <A, T : Any> KodeinAware.FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null): KodeinProperty<((A) -> T)?> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.factoryOrNull(Kodein.Key(ctx.anyType, argType, type, tag), ctx.value) }

/**
 * Gets a provider of [T] for the given type and tag.
 *
 * @param T The type of object to retrieve with the returned provider.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @return A provider of [T].
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAware.Provider(type: TypeToken<out T>, tag: Any? = null): KodeinProperty<() -> T> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.provider(Kodein.Key(ctx.anyType, UnitToken, type, tag), ctx.value) }

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <A, T : Any> KodeinAware.Provider(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): KodeinProperty<() -> T> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.factory(Kodein.Key(ctx.anyType, argType, type, tag), ctx.value).toProvider(arg) }

/**
 * Gets a provider of [T] for the given type and tag, or null if none is found.
 *
 * @param T The type of object to retrieve with the returned provider.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @return A provider of [T], or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAware.ProviderOrNull(type: TypeToken<out T>, tag: Any? = null): KodeinProperty<(() -> T)?> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.providerOrNull(Kodein.Key(ctx.anyType, UnitToken, type, tag), ctx.value) }

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
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <A, T : Any> KodeinAware.ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): KodeinProperty<(() -> T)?> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.factoryOrNull(Kodein.Key(ctx.anyType, argType, type, tag), ctx.value)?.toProvider(arg) }

/**
 * Gets an instance of [T] for the given type and tag.
 *
 * @param T The type of object to retrieve.
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of [T].
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAware.Instance(type: TypeToken<out T>, tag: Any? = null): KodeinProperty<T> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.provider(Kodein.Key(ctx.anyType, UnitToken, type, tag), ctx.value).invoke() }

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
 * @throws Kodein.NotFoundException If no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <A, T : Any> KodeinAware.Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): KodeinProperty<T> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.factory(Kodein.Key(ctx.anyType, argType, type, tag), ctx.value).invoke(arg()) }

/**
 * Gets an instance of [T] for the given type and tag, or null if none is found.
 *
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of [T], or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAware.InstanceOrNull(type: TypeToken<out T>, tag: Any? = null): KodeinProperty<T?> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.providerOrNull(Kodein.Key(ctx.anyType, UnitToken, type, tag), ctx.value)?.invoke() }

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A], or null if none is found.
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg A function that returns the argument that will be given to the factory when curried.
 * @return An instance of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <A, T : Any> KodeinAware.InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): KodeinProperty<T?> =
        KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.container.factoryOrNull(Kodein.Key(ctx.anyType, argType, type, tag), ctx.value)?.invoke(arg()) }

/**
 * Return a direct [DKodein] instance, with its receiver and context set to this KodeinAware receiver and context.
 */
val KodeinAware.direct: DKodein get() = DKodeinImpl(kodein.container, kodeinContext)

@Deprecated(DEPRECATE_7X)
private class KodeinWrapper(
        private val _base: Kodein,
        override val kodeinContext: KodeinContext<*>,
        override val kodeinTrigger: KodeinTrigger? = null
) : Kodein {
    internal constructor(base: KodeinAware, kodeinContext: KodeinContext<*> = base.kodeinContext, trigger: KodeinTrigger? = base.kodeinTrigger) : this(base.kodein, kodeinContext, trigger)

    override val kodein: Kodein get() = this

    override val container: KodeinContainer get() = _base.container
}

/**
 * Allows to create a new Kodein object with a context and/or a trigger set.
 *
 * @param context The new context of the new Kodein.
 * @param trigger The new trigger of the new Kodein.
 * @return A Kodein object that uses the same container as this one, but with its context and/or trigger changed.
 */
fun KodeinAware.On(context: KodeinContext<*> = this.kodeinContext, trigger: KodeinTrigger? = this.kodeinTrigger): Kodein = KodeinWrapper(this, kodeinContext = context, trigger = trigger)

/**
 * Allows to create a new instance of an unbound object with the same API as when bounding one.
 *
 * @param T The type of object to create.
 * @param creator A function that do create the object.
 */
fun <T> KodeinAware.newInstance(creator: DKodein.() -> T): KodeinProperty<T> = KodeinProperty(kodeinTrigger, kodeinContext) { ctx, _ -> kodein.direct.On(ctx).run(creator) }

