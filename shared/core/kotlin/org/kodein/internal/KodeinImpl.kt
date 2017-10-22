package org.kodein.internal

import org.kodein.*
import org.kodein.bindings.BindingKodein

/**
 * Kodein implementation.
 *
 * Contains almost nothing because the Kodein object itself contains very few logic.
 * Everything is delegated wither to [typed] or to [container].
 */
internal open class KodeinImpl internal constructor(private val _container: KodeinContainer) : Kodein {

    private @Volatile var _init: (() -> Unit)? = null

    /**
     * Creates a Kodein object with a [Kodein.Builder]'s internal.
     *
     * - Uses the [KodeinContainer.Builder] to create the [container].
     * - Calls all callbacks registered in [Kodein.Builder._callbacks].
     *
     * @param builder The builder to use.
     */
    @Suppress("unused")
    private constructor(builder: Kodein.MainBuilder, runCallbacks: Boolean) : this(
            KodeinContainerImpl(builder.containerBuilder, builder.externalSource)
    ) {
        val init: () -> Unit = {
            val dkodein = KodeinImpl(container).direct
            builder._callbacks.forEach { @Suppress("UNUSED_EXPRESSION") it(direct) }
            builder._bindingCallbacks.forEach { it.second.invoke(BindingKodeinImpl(dkodein, it.first, null, 0)) }
        }

        if (runCallbacks)
            init()
        else {
            val lock = Any()
            _init = init@ {
                synchronizedIfNotNull(
                        lock = lock,
                        predicate = this::_init,
                        ifNull = {},
                        ifNotNull = {
                            _init = null
                            init()
                        }
                )
            }
        }
    }

    /**
     * "Main" constructor.
     */
    constructor(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit) : this(_newBuilder(allowSilentOverride, init), true)

    companion object {
        private fun _newBuilder(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit) = Kodein.MainBuilder(allowSilentOverride).apply(init)

        fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit): Pair<Kodein, () -> Unit> {
            val kodein = KodeinImpl(_newBuilder(allowSilentOverride, init), false)
            return kodein to { kodein._init?.invoke() ; Unit }
        }
    }

    final override val container: KodeinContainer by lazy {
        if (_init != null)
            throw IllegalStateException("Kodein has not been initialized")
        _container
    }

}

@Suppress("UNCHECKED_CAST")
internal open class BindingKodeinImpl<A, out T: Any> internal constructor(
        val dkodein: DKodein,
        private val _key: Kodein.Key<A, T>,
        override val receiver: Any?,
        private val _overrideLevel: Int
) : DKodein by dkodein, BindingKodein {
    override fun overriddenFactory(): (Any?) -> Any = kodein.container.overriddenFactory(_key, receiver, _overrideLevel)
    override fun overriddenFactoryOrNull(): ((Any?) -> Any)? = kodein.container.overriddenFactoryOrNull(_key, receiver, _overrideLevel)
}
