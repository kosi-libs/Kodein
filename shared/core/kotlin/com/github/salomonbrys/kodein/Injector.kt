package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.internal.synchronizedIfNull

/**
 * An injector is an object which creates injected property delegates **before** having access to a Kodein instance.
 *
 * For example, in Android, you can't access the Kodein instance before onCreate is called:
 *
 * ```kotlin
 * class MyActivity : Activity() {
 *     val injector: KodeinInjector()
 *
 *     val engine: Engine by injector.instance()
 *     val random: () -> Int by injector.provider("random")
 *
 *     fun onCreate(savedInstanceState: Bundle) {
 *         injector.inject(appKodein()) // See Android's documentation for appKodein
 *         // Here, you can now access engine and random properties.
 *     }
 * }
 * ```
 */
@Suppress("unused")
class KodeinInjector : KodeinInjectedBase {

    override val injector = this

    private val _lock = Any()

    /**
     * Exception thrown when trying to access the [value][InjectedProperty.value] of an [InjectedProperty]
     * before the [KodeinInjector] that created this property is [injected][KodeinInjector.inject].
     */
    class UninjectedException : RuntimeException("Value has not been injected")

    /**
     * List of injected property delegate created by this injector to inject when being [injected][KodeinInjector.inject].
     */
    private val _list = ArrayList<InjectedProperty<*>>()

    /**
     * Kodein instance that was used to inject. Is null before calling [KodeinInjector.inject].
     */
    private @Volatile var _kodein: Kodein? = null

    /**
     * List of callbacks to call when being [injected][KodeinInjector.inject].
     */
    private var _onInjecteds = ArrayList<(Kodein) -> Unit>()

    override fun onInjected(cb: (Kodein) -> Unit): Unit {
        synchronizedIfNull(_lock, this::_kodein, cb) {
            _onInjecteds.add(cb)
        }
    }

    /**
     * Registers an injected property to be injected by this injector when it will itself be [injected][KodeinInjector.inject].
     *
     * Every [InjectedProperty] created by this injector must be registered by calling this method so that it will be injected later.
     *
     * @param injected The property to register (and later, inject).
     * @return The property, for ease of use.
     */
    private fun <T> _register(injected: InjectedProperty<T>): InjectedProperty<T> {
        synchronizedIfNull(_lock, this::_kodein, { return injected.apply { _inject(it.container) } }) {
            _list.add(injected)
        }
        return injected
    }

    /**
     * Creates an injected factory property delegate.
     *
     * @param A The type of argument the factory held by this property takes.
     * @param T The type of object to retrieve with the factory held by this property.
     * @param argType The type of argument the factory held by this property takes.
     * @param type The type of object to retrieve with the factory held by this property.
     * @param tag The bound tag, if any.
     * @return A property delegate that will lazily provide a factory of `T`.
     * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    fun <A, T : Any> Factory(argType: TypeToken<out A>, type: TypeToken<out T>, tag: Any? = null): InjectedProperty<(A) -> T> = _register(InjectedFactoryProperty(Kodein.Key(Kodein.Bind(type, tag), argType)))

    /**
     * Creates a property delegate that will hold a factory, or null if none is found.
     *
     * @param A The type of argument the factory held by this property takes.
     * @param T The type of object to retrieve with the factory held by this property.
     * @param argType The type of argument the factory held by this property takes.
     * @param type The type of object to retrieve with the factory held by this property.
     * @param tag The bound tag, if any.
     * @return A property delegate that will lazily provide a factory of `T`, or null if no factory was found.
     * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
     * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
     */
    fun <A, T : Any> FactoryOrNull(argType: TypeToken<out A>, type: TypeToken<out T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = _register(InjectedNullableFactoryProperty(Kodein.Key(Kodein.Bind(type, tag), argType)))

    /**
     * Creates an injected provider property delegate.
     *
     * @param T The type of object to retrieve with the provider held by this property.
     * @param type The type of object to retrieve with the provider held by this property.
     * @param tag The bound tag, if any.
     * @return A property delegate that will lazily provide a provider of `T`.
     * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    fun <T : Any> Provider(type: TypeToken<out T>, tag: Any? = null): InjectedProperty<() -> T> = _register(InjectedProviderProperty(Kodein.Bind(type, tag)))

    /**
     * Creates a property delegate that will hold a provider, or null if none is found.
     *
     * @param T The type of object to retrieve with the provider held by this property.
     * @param type The type of object to retrieve with the provider held by this property.
     * @param tag The bound tag, if any.
     * @return A property delegate that will lazily provide a provider of `T`, or null if no provider was found.
     * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
     * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
     */
    fun <T : Any> ProviderOrNull(type: TypeToken<out T>, tag: Any? = null): InjectedProperty<(() -> T)?> =  _register(InjectedNullableProviderProperty(Kodein.Bind(type, tag)))

    /**
     * Creates an injected instance property delegate.
     *
     * @param T The type of object that will held by this property.
     * @param type The type of object that will held by this property.
     * @param tag The bound tag, if any.
     * @return A property delegate that will lazily provide an instance of `T`.
     * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
     */
    fun <T : Any> Instance(type: TypeToken<out T>, tag: Any? = null): InjectedProperty<T> = _register(InjectedInstanceProperty(Kodein.Bind(type, tag)))

    /**
     * Creates a property delegate that will hold an instance, or null if none is found.
     *
     * @param T The type of object that will held by this property.
     * @param type The type of object that will held by this property.
     * @param tag The bound tag, if any.
     * @return A property delegate that will lazily provide an instance of `T`, or null if no provider was found.
     * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
     */
    fun <T : Any> InstanceOrNull(type: TypeToken<out T>, tag: Any? = null): InjectedProperty<T?> = _register(InjectedNullableInstanceProperty(Kodein.Bind(type, tag)))

    /**
     * Creates a property delegate that will hold a Kodein instance.
     *
     * @return A property delegate that will lazily provide a Kodein instance.
     * @throws UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
     */
    fun kodein(): Lazy<Kodein> = lazy { _kodein ?: throw UninjectedException() }

    override fun inject(kodein: Kodein) {
        synchronizedIfNull(_lock, this::_kodein, { return }) {
            _kodein = kodein

            _list.forEach { it._inject(kodein.container) }
            _list.clear()
        }

        _onInjecteds.forEach { it(kodein) }
        _onInjecteds.clear()
    }
}
