package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.*

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
class KodeinInjector() : KodeinInjectedBase {

    override val injector = this

    /**
     * Exception thrown when trying to access the [value][InjectedProperty.value] of an [InjectedProperty]
     * before the [KodeinInjector] that created this property is [injected][KodeinInjector.inject].
     */
    class UninjectedException : RuntimeException("Value has not been injected")

    /**
     * List of injected property delegate created by this injector to inject when being [injected][KodeinInjector.inject].
     */
    private val _list = LinkedList<InjectedProperty<*>>()

    /**
     * Kodein instance that was used to inject. Is null before calling [KodeinInjector.inject].
     */
    private var _kodein: Kodein? = null

    /**
     * List of callbacks to call when being [injected][KodeinInjector.inject].
     */
    private var _onInjecteds = ArrayList<(Kodein) -> Unit>()

    override fun onInjected(cb: (Kodein) -> Unit) {
        val k1 = _kodein
        if (k1 != null)
            cb(k1)
        else
            synchronized(this@KodeinInjector) {
                val k2 = _kodein
                if (k2 != null)
                    cb(k2)
                else
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
        val k1 = _kodein
        if (k1 != null)
            injected._inject(k1.container)
        else
            synchronized(this@KodeinInjector) {
                val k2 = _kodein
                if (k2 != null)
                    injected._inject(k2.container)
                else
                    _list.add(injected)
            }

        return injected
    }

    @Suppress("UNCHECKED_CAST", "CAST_NEVER_SUCCEEDS")
    inner class TInjector {

        /**
         * Creates an injected factory property delegate.
         *
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun factory(argType: Type, type: Type, tag: Any? = null): InjectedProperty<(Any?) -> Any> = _register(InjectedFactoryProperty(Kodein.Key(Kodein.Bind(type, tag), argType)))

        /**
         * Creates an injected factory property delegate.
         *
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <T : Any> factory(argType: Type, type: Class<T>, tag: Any? = null): InjectedProperty<(Any?) -> T> = factory(argType, type as Type, tag) as InjectedProperty<(Any?) -> T>

        /**
         * Creates an injected factory property delegate.
         *
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <T : Any> factory(argType: Type, type: TypeToken<T>, tag: Any? = null): InjectedProperty<(Any?) -> T> = factory(argType, type.type, tag) as InjectedProperty<(Any?) -> T>

        /**
         * Creates an injected factory property delegate.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A> factory(argType: Class<A>, type: Type, tag: Any? = null): InjectedProperty<(A) -> Any> = factory(argType as Type, type, tag)

        /**
         * Creates an injected factory property delegate.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A, T : Any> factory(argType: Class<A>, type: Class<T>, tag: Any? = null): InjectedProperty<(A) -> T> = factory(argType as Type, type as Type, tag) as InjectedProperty<(A) -> T>

        /**
         * Creates an injected factory property delegate.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A, T : Any> factory(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): InjectedProperty<(A) -> T> = factory(argType as Type, type.type, tag) as InjectedProperty<(A) -> T>

        /**
         * Creates an injected factory property delegate.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A> factory(argType: TypeToken<A>, type: Type, tag: Any? = null): InjectedProperty<(A) -> Any> = factory(argType.type, type, tag)

        /**
         * Creates an injected factory property delegate.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A, T : Any> factory(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): InjectedProperty<(A) -> T> = factory(argType.type, type as Type, tag) as InjectedProperty<(A) -> T>

        /**
         * Creates an injected factory property delegate.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A, T : Any> factory(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): InjectedProperty<(A) -> T> = factory(argType.type, type.type, tag) as InjectedProperty<(A) -> T>



        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory, or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun factoryOrNull(argType: Type, type: Type, tag: Any? = null): InjectedProperty<((Any?) -> Any)?> = _register(InjectedNullableFactoryProperty(Kodein.Key(Kodein.Bind(type, tag), argType)))

        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T], or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <T : Any> factoryOrNull(argType: Type, type: Class<T>, tag: Any? = null): InjectedProperty<((Any?) -> T)?> = factoryOrNull(argType, type as Type, tag) as InjectedProperty<((Any?) -> T)?>

        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T], or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <T : Any> factoryOrNull(argType: Type, type: TypeToken<T>, tag: Any? = null): InjectedProperty<((Any?) -> T)?> = factoryOrNull(argType, type.type, tag) as InjectedProperty<((Any?) -> T)?>

        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory, or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A> factoryOrNull(argType: Class<A>, type: Type, tag: Any? = null): InjectedProperty<((A) -> Any)?> = factoryOrNull(argType as Type, type, tag)

        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T], or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: Class<A>, type: Class<T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = factoryOrNull(argType as Type, type as Type, tag) as InjectedProperty<((A) -> T)?>

        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T], or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: Class<A>, type: TypeToken<T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = factoryOrNull(argType as Type, type.type, tag) as InjectedProperty<((A) -> T)?>

        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory, or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A> factoryOrNull(argType: TypeToken<A>, type: Type, tag: Any? = null): InjectedProperty<((A) -> Any)?> = factoryOrNull(argType.type, type, tag)

        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T], or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: Class<T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = factoryOrNull(argType.type, type as Type, tag) as InjectedProperty<((A) -> T)?>

        /**
         * Creates a property delegate that will hold a factory, or null if none is found.
         *
         * @param A The type of argument the factory held by this property takes.
         * @param T The type of object to retrieve with the factory held by this property.
         * @param argType The type of argument the factory held by this property takes.
         * @param type The type of object to retrieve with the factory held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a factory of [T], or null if no factory was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the factory, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <A, T : Any> factoryOrNull(argType: TypeToken<A>, type: TypeToken<T>, tag: Any? = null): InjectedProperty<((A) -> T)?> = factoryOrNull(argType.type, type.type, tag) as InjectedProperty<((A) -> T)?>



        /**
         * Creates an injected provider property delegate.
         *
         * @param type The type of object to retrieve with the provider held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a provider.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun provider(type: Type, tag: Any? = null): InjectedProperty<() -> Any> = _register(InjectedProviderProperty(Kodein.Bind(type, tag)))

        /**
         * Creates an injected provider property delegate.
         *
         * @param T The type of object to retrieve with the provider held by this property.
         * @param type The type of object to retrieve with the provider held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a provider of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <T : Any> provider(type: Class<T>, tag: Any? = null): InjectedProperty<() -> T> = provider(type as Type, tag) as InjectedProperty<() -> T>

        /**
         * Creates an injected provider property delegate.
         *
         * @param T The type of object to retrieve with the provider held by this property.
         * @param type The type of object to retrieve with the provider held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a provider of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <T : Any> provider(type: TypeToken<T>, tag: Any? = null): InjectedProperty<() -> T> = provider(type.type, tag) as InjectedProperty<() -> T>



        /**
         * Creates a property delegate that will hold a provider, or null if none is found.
         *
         * @param type The type of object to retrieve with the provider held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a provider, or null if no provider was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun providerOrNull(type: Type, tag: Any? = null): InjectedProperty<(() -> Any)?> = _register(InjectedNullableProviderProperty(Kodein.Bind(type, tag)))

        /**
         * Creates a property delegate that will hold a provider, or null if none is found.
         *
         * @param T The type of object to retrieve with the provider held by this property.
         * @param type The type of object to retrieve with the provider held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a provider of [T], or null if no provider was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <T : Any> providerOrNull(type: Class<T>, tag: Any? = null): InjectedProperty<(() -> T)?> = providerOrNull(type as Type, tag) as InjectedProperty<(() -> T)?>

        /**
         * Creates a property delegate that will hold a provider, or null if none is found.
         *
         * @param T The type of object to retrieve with the provider held by this property.
         * @param type The type of object to retrieve with the provider held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide a provider of [T], or null if no provider was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         * @throws Kodein.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
         */
        @JvmOverloads
        fun <T : Any> providerOrNull(type: TypeToken<T>, tag: Any? = null): InjectedProperty<(() -> T)?> = providerOrNull(type.type, tag) as InjectedProperty<(() -> T)?>



        /**
         * Creates an injected instance property delegate.
         *
         * @param type The type of object that will held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide an instance.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         */
        @JvmOverloads
        fun instance(type: Type, tag: Any? = null): InjectedProperty<Any> = _register(InjectedInstanceProperty(Kodein.Bind(type, tag)))

        /**
         * Creates an injected instance property delegate.
         *
         * @param T The type of object that will held by this property.
         * @param type The type of object that will held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide an instance of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         */
        @JvmOverloads
        fun <T : Any> instance(type: Class<T>, tag: Any? = null): InjectedProperty<T> = instance(type as Type, tag) as InjectedProperty<T>

        /**
         * Creates an injected instance property delegate.
         *
         * @param T The type of object that will held by this property.
         * @param type The type of object that will held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide an instance of [T].
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         */
        @JvmOverloads
        fun <T : Any> instance(type: TypeToken<T>, tag: Any? = null): InjectedProperty<T> = instance(type.type, tag) as InjectedProperty<T>



        /**
         * Creates a property delegate that will hold an instance, or null if none is found.
         *
         * @param type The type of object that will held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide an instance, or null if no provider was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         */
        fun instanceOrNull(type: Type, tag: Any? = null): InjectedProperty<Any?> = _register(InjectedNullableInstanceProperty(Kodein.Bind(type, tag)))

        /**
         * Creates a property delegate that will hold an instance, or null if none is found.
         *
         * @param T The type of object that will held by this property.
         * @param type The type of object that will held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide an instance of [T], or null if no provider was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         */
        @JvmOverloads
        fun <T : Any> instanceOrNull(type: Class<T>, tag: Any? = null): InjectedProperty<T?> = instanceOrNull(type as Type, tag) as InjectedProperty<T?>

        /**
         * Creates a property delegate that will hold an instance, or null if none is found.
         *
         * @param T The type of object that will held by this property.
         * @param type The type of object that will held by this property.
         * @param tag The bound tag, if any.
         * @return A property delegate that will lazily provide an instance of [T], or null if no provider was found.
         * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
         */
        @JvmOverloads
        fun <T : Any> instanceOrNull(type: TypeToken<T>, tag: Any? = null): InjectedProperty<T?> = instanceOrNull(type.type, tag) as InjectedProperty<T?>

    }

    /**
     * Allows to access all typed API (meaning the API where you provide [Type], [TypeToken] or [Class] objects).
     */
    val typed = TInjector()

    /**
     * Creates a property delegate that will hold a Kodein instance.
     *
     * @return A property delegate that will lazily provide a Kodein instance.
     * @throws KodeinInjector.UninjectedException When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject].
     */
    fun kodein(): Lazy<Kodein> = lazy { _kodein ?: throw KodeinInjector.UninjectedException() }

    override fun inject(kodein: Kodein) {
        if (_kodein != null)
            return

        synchronized(this@KodeinInjector) {
            if (_kodein != null)
                return

            _kodein = kodein
        }

        _list.forEach { it._inject(kodein.container) }
        _list.clear()

        _onInjecteds.forEach { it(kodein) }
        _onInjecteds.clear()
    }
}
