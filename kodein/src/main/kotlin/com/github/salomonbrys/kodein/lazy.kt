package com.github.salomonbrys.kodein


interface LazyKodeinAwareBase {
    val kodein: LazyKodein
}

/**
 * An object that wraps a Kodein [Lazy] object and acts both as a [Lazy] property delegate & a function.
 *
 * @param k The lazy property delegate to wrap.
 */
class LazyKodein(k: Lazy<Kodein>) : Lazy<Kodein> by k, () -> Kodein, LazyKodeinAwareBase {
    override val kodein: LazyKodein get() = this

    override fun invoke(): Kodein = value
}

/**
 * You can use the result of this function as a property delegate *or* as a function.
 *
 * @param f The function to get a Kodein, guaranteed to be called only once.
 */
fun lazyKodein(f: () -> Kodein) = LazyKodein(lazy(f))



/**
 * Gets a lazy factory for the given type, tag and argument type.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a factory of [T].
 * @throws Kodein.NotFoundException When accessing the property, if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> LazyKodeinAwareBase.factory(tag: Any? = null) : Lazy<(A) -> T> = lazy { kodein().factory<A, T>(tag) }

/**
 * Gets a lazy factory for the given type, tag and argument type, or null if none is found.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a factory of [T], or null if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
 */
inline fun <reified A, reified T : Any> LazyKodeinAwareBase.factoryOrNull(tag: Any? = null) : Lazy<((A) -> T)?> = lazy { kodein().factoryOrNull<A, T>(tag) }

/**
 * Gets a lazy provider for the given type and tag.
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of [T].
 * @throws Kodein.NotFoundException When accessing the property, if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> LazyKodeinAwareBase.provider(tag: Any? = null) : Lazy<() -> T> = lazy { kodein().provider<T>(tag) }

/**
 * Gets a lazy provider for the given type and tag, or null if none is found.
 *
 * @param T The type of object to retrieve with the provider held by this property.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields a provider of [T], or null if no provider was found.
 * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> LazyKodeinAwareBase.providerOrNull(tag: Any? = null) : Lazy<(() -> T)?> = lazy { kodein().providerOrNull<T>(tag) }

/**
 * Gets a lazy instance for the given type and tag.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields an instance of [T].
 * @throws Kodein.NotFoundException When accessing the property, if no provider was found.
 * @throws Kodein.DependencyLoopException When accessing the property, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> LazyKodeinAwareBase.instance(tag: Any? = null) : Lazy<T> = lazy { kodein().instance<T>(tag) }

/**
 * Gets a lazy instance for the given type and tag, or null is none is found.
 *
 * @param T The type of object to retrieve.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param tag The bound tag, if any.
 * @return A lazy property that yields an instance of [T], or null if no provider is found.
 * @throws Kodein.DependencyLoopException When accessing the property, if the value construction triggered a dependency loop.
 */
inline fun <reified T : Any> LazyKodeinAwareBase.instanceOrNull(tag: Any? = null) : Lazy<T?> = lazy { kodein().instanceOrNull<T>(tag) }

/**
 * Allows to get a lazy provider or instance from a lazy factory with a curried argument.
 *
 * @param A The type of argument that the factory takes.
 * @property kodein The Kodein provider to use for retrieval.
 * @property arg The argument to provide to the factory when retrieving values.
 * @property argType The type of argument that the factory takes.
 */
class CurriedLazyKodeinFactory<A>(val kodein: () -> Kodein, val arg: A, val argType: TypeToken<A>) {

    /**
     * Gets a lazy provider of [T] for the given tag from a curried factory with an [A] argument.
     *
     * @param T The type of object to retrieve with the provider held by this property.
     * @param tag The bound tag, if any.
     * @return A lazy property that yields a provider of [T].
     * @throws Kodein.NotFoundException When accessing the property, if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    inline fun <reified T : Any> provider(tag: Any? = null): Lazy<() -> T> = lazy { kodein().typed.factory(argType, typeToken<T>(), tag) } .toProvider(arg)

    /**
     * Gets a lazy provider of [T] for the given tag from a curried factory with an [A] argument, or null if none is found.
     *
     * @param T The type of object to retrieve with the provider held by this property.
     * @param tag The bound tag, if any.
     * @return A lazy property that yields a provider of [T], or null if no factory is found.
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    inline fun <reified T : Any> providerOrNull(tag: Any? = null): Lazy<(() -> T)?> = lazy { kodein().typed.factoryOrNull(argType, typeToken<T>(), tag) } .toProvider(arg)

    /**
     * Gets a lazy instance of [T] for the given tag from a curried factory with an [A] argument.
     *
     * @param T The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return A lazy instance of [T].
     * @throws Kodein.NotFoundException When accessing the property, if no factory was found.
     * @throws Kodein.DependencyLoopException When accessing the property, if the value construction triggered a dependency loop.
     */
    inline fun <reified T : Any> instance(tag: Any? = null): Lazy<T> = lazy { kodein().typed.factory(argType, typeToken<T>(), tag) } .toInstance(arg)

    /**
     * Gets a lazy instance of [T] for the given tag from a curried factory with an [A] argument, or null if none is found.
     *
     * @param T The type of object to retrieve.
     * @param tag The bound tag, if any.
     * @return A lazy instance of [T], or null if no factory was found.
     * @throws Kodein.DependencyLoopException When accessing the property, if the value construction triggered a dependency loop.
     */
    inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Lazy<T?> = lazy { kodein().typed.factoryOrNull(argType, typeToken<T>(), tag) } .toInstance(arg)
}

/**
 * Allows to get a lazy provider or instance from a curried factory with an [A] argument.
 *
 * @param A The type of argument the factory takes.
 * @receiver Either a [LazyKodein] instance or a [LazyKodeinAware] class.
 * @param arg The argument that will be passed to the factory.
 * @return An object from which you can get an instance or a provider.
 */
inline fun <reified A> LazyKodeinAwareBase.with(arg: A): CurriedLazyKodeinFactory<A> = CurriedLazyKodeinFactory(kodein, arg, typeToken())



/**
 * Any class that extends this interface can use Kodein to "seemlessly" get lazy properties.
 */
interface LazyKodeinAware : LazyKodeinAwareBase



/**
 * Transforms a lazy factory property into a lazy provider property by currying the factory argument.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver The factory to curry.
 * @param arg The argument that will be passed to the factory.
 * @return A property that yields a provider of [T].
 */
fun <A, T : Any> Lazy<(A) -> T>.toProvider(arg: A): Lazy<() -> T> = lazy { { value(arg) } }

/**
 * Transforms a lazy nullable factory property into a lazy nullable provider property by currying the factory argument.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver The factory to curry.
 * @param arg The argument that will be passed to the factory.
 * @return A property that yields a provider of [T], or null if no factory was found.
 */
@JvmName("toNullableProvider")
fun <A, T : Any> Lazy<((A) -> T)?>.toProvider(arg: A): Lazy<(() -> T)?> = lazy { val factory = value ; if (factory != null) return@lazy { factory(arg) } else return@lazy null }

/**
 * Transforms a lazy factory property into a lazy instance property by currying the factory argument.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver The factory to curry.
 * @param arg The argument that will be passed to the factory.
 * @return A property that yields an instance of [T].
 */
fun <A, T : Any> Lazy<(A) -> T>.toInstance(arg: A): Lazy<T> = lazy { value(arg) }

/**
 * Transforms a lazy nullable factory property into a lazy nullable instance property by currying the factory argument.
 *
 * @param A The type of argument the factory held by this property takes.
 * @param T The type of object to retrieve with the factory held by this property.
 * @receiver The factory to curry.
 * @param arg The argument that will be passed to the factory.
 * @return A property that yields an instance of [T], or null if no factory was found.
 */
@JvmName("toNullableInstance")
fun <A, T : Any> Lazy<((A) -> T)?>.toInstance(arg: A): Lazy<T?> = lazy { val factory = value ; if (factory != null) factory(arg) else null }
