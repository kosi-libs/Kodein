package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.bindings.BindingKodein
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer

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
    private constructor(builder: Kodein.Builder, runCallbacks: Boolean) : this(KodeinContainerImpl(builder.container)) {
        val init: () -> Unit = {
            builder._callbacks.forEach { @Suppress("UNUSED_EXPRESSION") it() }
            builder._bindingCallbacks.forEach { it.second.invoke(BindingKodeinImpl(container, it.first, 0)) }
        }

        if (runCallbacks)
            init()
        else {
            val lock = Any()
            _init = init@ {
                synchronizedIfNotNull(lock, this::_init, { return@init }) {
                    _init = null
                    init()
                }
            }
        }
    }

    /**
     * "Main" constructor.
     */
    constructor(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit) : this(Kodein.Builder(KodeinContainer.Builder(true, allowSilentOverride, CMap()), ArrayList(), ArrayList(), init), true)

    companion object {
        fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit): Pair<Kodein, () -> Unit> {
            val kodein = KodeinImpl(Kodein.Builder(KodeinContainer.Builder(true, allowSilentOverride, CMap()), ArrayList(), ArrayList(), init), false)
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
internal open class BindingKodeinImpl internal constructor(container: KodeinContainer, private val _key: Kodein.Key<*, *>, private val _overrideLevel: Int) : KodeinImpl(container), BindingKodein {
    override fun overriddenFactory(): (Any?) -> Any = container.overriddenNonNullFactory(_key, _overrideLevel)
    override fun overriddenFactoryOrNull(): ((Any?) -> Any)? = container.overriddenFactoryOrNull(_key, _overrideLevel) as (Any?) -> Any
}
