package org.kodein

import org.kodein.internal.DKodeinImpl

open class KodeinContext<C>(
        val type: TypeToken<in C>,
        val value: C
)

@Suppress("UNCHECKED_CAST")
internal val KodeinContext<*>.anyType get() = type as TypeToken<in Any?>

object AnyKodeinContext : KodeinContext<Any?>(AnyToken, null)


/**
 * Base [KodeinAware] interface.
 *
 * It is separate from [KodeinAware] because [Kodein] implements itself [KodeinAware] but not [KodeinAware].<br />
 * This is because there are some extension functions to [KodeinAware] that would not make sense applied to the [Kodein] object.<br />
 */
interface KodeinAware {
    /**
     * A Kodein Aware class must be within reach of a Kodein object.
     */
    val kodein: Kodein

    val kodeinContext: KodeinContext<*> get() = AnyKodeinContext

    val kodeinPropertyMode: PropMode get() = PropMode.LAZY

    val injector: KodeinInjector? get() = null
}

class KodeinAwareWrapper(
        override val kodein: Kodein,
        override val kodeinContext: KodeinContext<*>,
        override val kodeinPropertyMode: PropMode = PropMode.LAZY,
        override val injector: KodeinInjector? = null
) : KodeinAware {
    constructor(base: KodeinAware, kodeinContext: KodeinContext<*> = base.kodeinContext, kodeinPropertyMode: PropMode = base.kodeinPropertyMode, injector: KodeinInjector? = base.injector) : this(base.kodein, kodeinContext, kodeinPropertyMode, injector)
}

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
fun <A, T : Any> KodeinAware.Factory(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null): KodeinProperty<(A) -> T> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.factory(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, receiver) }

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
fun <A, T : Any> KodeinAware.FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null): KodeinProperty<((A) -> T)?> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.factoryOrNull(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, receiver) }

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
fun <T : Any> KodeinAware.Provider(type: TypeToken<out T>, tag: Any? = null): KodeinProperty<() -> T> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.provider(Kodein.Key(kodeinContext.anyType, UnitToken, type, tag), kodeinContext.value, receiver) }

fun <A, T : Any> KodeinAware.Provider(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): KodeinProperty<() -> T> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.factory(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, receiver).toProvider(arg) }

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * @param T The type of object to retrieve with the returned provider.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @return A provider of `T`, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAware.ProviderOrNull(type: TypeToken<out T>, tag: Any? = null): KodeinProperty<(() -> T)?> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.providerOrNull(Kodein.Key(kodeinContext.anyType, UnitToken, type, tag), kodeinContext.value, receiver) }

fun <A, T : Any> KodeinAware.ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): KodeinProperty<(() -> T)?> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.factoryOrNull(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, receiver)?.toProvider(arg) }

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
fun <T : Any> KodeinAware.Instance(type: TypeToken<out T>, tag: Any? = null): KodeinProperty<T> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.provider(Kodein.Key(kodeinContext.anyType, UnitToken, type, tag), kodeinContext.value, receiver).invoke() }

fun <A, T : Any> KodeinAware.Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): KodeinProperty<T> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.factory(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, receiver).invoke(arg()) }

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of `T`, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAware.InstanceOrNull(type: TypeToken<out T>, tag: Any? = null): KodeinProperty<T?> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.providerOrNull(Kodein.Key(kodeinContext.anyType, UnitToken, type, tag), kodeinContext.value, receiver)?.invoke() }

fun <A, T : Any> KodeinAware.InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<out T>, tag: Any? = null, arg: () -> A): KodeinProperty<T> =
        KodeinProperty(kodeinPropertyMode, injector) { receiver -> kodein.container.factory(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, receiver).invoke(arg()) }


val KodeinAware.direct: DKodein get() = DKodeinImpl(kodein, kodeinContext, null)

fun KodeinAware.on(context: KodeinContext<*> = kodeinContext, mode: PropMode = kodeinPropertyMode) = KodeinAwareWrapper(this, kodeinContext = context, kodeinPropertyMode = mode)

/**
 * Allows to create a new instance of an unbound object with the same API as when bounding one.
 *
 * @param T The type of object to create.
 * @param creator A function that do create the object.
 */
inline fun <T> KodeinAware.newInstance(creator: DKodein.() -> T): T = kodein.direct.run(creator)


///**
// * Any class that extends this interface can use Kodein "seamlessly".
// */
//interface KodeinAware : KodeinContextAware<Unit>
