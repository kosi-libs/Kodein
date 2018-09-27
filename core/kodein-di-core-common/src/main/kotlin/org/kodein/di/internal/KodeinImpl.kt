package org.kodein.di.internal

import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinContainer
import org.kodein.di.bindings.BindingKodein

/**
 * Kodein implementation.
 *
 * Contains almost nothing because the Kodein object itself contains very few logic.
 * Everything is delegated wither to [container].
 */
internal open class KodeinImpl internal constructor(private val _container: KodeinContainerImpl) : Kodein {

    @Suppress("unused")
    private constructor(builder: KodeinMainBuilderImpl, runCallbacks: Boolean) : this(KodeinContainerImpl(builder.containerBuilder, builder.externalSource, runCallbacks))

    constructor(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit) : this(newBuilder(allowSilentOverride, init), true)

    companion object {
        private fun newBuilder(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit) = KodeinMainBuilderImpl(allowSilentOverride).apply(init)

        fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: Kodein.MainBuilder.() -> Unit): Pair<Kodein, () -> Unit> {
            val kodein = KodeinImpl(newBuilder(allowSilentOverride, init), false)
            return kodein to { kodein._container.initCallbacks?.invoke() ; Unit }
        }
    }

    final override val container: KodeinContainer by lazy {
        if (_container.initCallbacks != null)
            throw IllegalStateException("Kodein has not been initialized")
        _container
    }

}

@Suppress("UNCHECKED_CAST")
internal open class BindingKodeinImpl<out C, out A, out T: Any> internal constructor(
        override val dkodein: DKodein,
        private val _key: Kodein.Key<C, A, T>,
        override val context: C,
        override val receiver: Any?,
        private val _overrideLevel: Int
) : DKodein by dkodein, BindingKodein<C> {
    override fun overriddenFactory(): (Any?) -> Any = container.factory(_key, context, receiver, _overrideLevel + 1) as (Any?) -> Any
    override fun overriddenFactoryOrNull(): ((Any?) -> Any)? = container.factoryOrNull(_key, context, receiver, _overrideLevel + 1) as ((Any?) -> Any)?
}
