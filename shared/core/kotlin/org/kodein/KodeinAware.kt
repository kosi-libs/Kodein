package org.kodein

import org.kodein.internal.DKodeinImpl

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

    val propMode: PropMode get() = PropMode.LAZY
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
fun <A, T : Any> KodeinAwareBase.Factory(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null): KodeinProperty<(A) -> T> =
        KodeinProperty(propMode) { receiver -> kodein.container.factory(Kodein.Key(Kodein.Bind(type, tag), argType), receiver) }

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
fun <A, T : Any> KodeinAwareBase.FactoryOrNull(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null): KodeinProperty<((A) -> T)?> =
        KodeinProperty(propMode) { receiver -> kodein.container.factoryOrNull(Kodein.Key(Kodein.Bind(type, tag), argType), receiver) }

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
fun <T : Any> KodeinAwareBase.Provider(type: TypeToken<T>, tag: Any? = null): KodeinProperty<() -> T> =
        KodeinProperty(propMode) { receiver -> kodein.container.provider(Kodein.Bind(type, tag), receiver) }

fun <A, T : Any> KodeinAwareBase.Provider(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): KodeinProperty<() -> T> =
        KodeinProperty(propMode) { receiver -> kodein.container.factory(Kodein.Key(Kodein.Bind(type, tag), argType), receiver).toProvider(arg) }

/**
 * Gets a provider of `T` for the given type and tag, or null if none is found.
 *
 * @param T The type of object to retrieve with the returned provider.
 * @param type The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @return A provider of `T`, or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAwareBase.ProviderOrNull(type: TypeToken<T>, tag: Any? = null): KodeinProperty<(() -> T)?> =
        KodeinProperty(propMode) { receiver -> kodein.container.providerOrNull(Kodein.Bind(type, tag), receiver) }

fun <A, T : Any> KodeinAwareBase.ProviderOrNull(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): KodeinProperty<(() -> T)?> =
        KodeinProperty(propMode) { receiver -> kodein.container.factoryOrNull(Kodein.Key(Kodein.Bind(type, tag), argType), receiver)?.toProvider(arg) }

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
fun <T : Any> KodeinAwareBase.Instance(type: TypeToken<T>, tag: Any? = null): KodeinProperty<T> =
        KodeinProperty(propMode) { receiver -> kodein.container.provider(Kodein.Bind(type, tag), receiver).invoke() }

fun <A, T : Any> KodeinAwareBase.Instance(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): KodeinProperty<T> =
        KodeinProperty(propMode) { receiver -> kodein.container.factory(Kodein.Key(Kodein.Bind(type, tag), argType), receiver).invoke(arg()) }

/**
 * Gets an instance of `T` for the given type and tag, or null if none is found.
 *
 * @param type The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance of `T`, or null if no provider was found.
 * @throws Kodein.DependencyLoopException If the value construction triggered a dependency loop.
 */
fun <T : Any> KodeinAwareBase.InstanceOrNull(type: TypeToken<T>, tag: Any? = null): KodeinProperty<T?> =
        KodeinProperty(propMode) { receiver -> kodein.container.providerOrNull(Kodein.Bind(type, tag), receiver)?.invoke() }

fun <A, T : Any> KodeinAwareBase.InstanceOrNull(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any? = null, arg: () -> A): KodeinProperty<T> =
        KodeinProperty(propMode) { receiver -> kodein.container.factory(Kodein.Key(Kodein.Bind(type, tag), argType), receiver).invoke(arg()) }


val KodeinAwareBase.direct: DKodein get() = DKodeinImpl(kodein, null)

fun KodeinAwareBase.directOn(receiver: Any?): DKodein = DKodeinImpl(kodein, receiver)

fun KodeinAwareBase.mode(mode: PropMode): Kodein = object : Kodein by kodein {
    override val kodein: Kodein get() = this
    override val propMode: PropMode get() = mode
}

/**
 * Allows to create a new instance of an unbound object with the same API as when bounding one.
 *
 * @param T The type of object to create.
 * @param creator A function that do create the object.
 */
inline fun <T> KodeinAwareBase.newInstance(creator: DKodein.() -> T): T = kodein.direct.run(creator)



/**
 * Any class that extends this interface can use Kodein "seamlessly".
 */
interface KodeinAware : KodeinAwareBase
