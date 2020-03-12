package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.di.bindings.BindingDI

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIImpl"), DeprecationLevel.ERROR)
internal typealias KodeinImpl = DIImpl
/**
 * DI implementation.
 *
 * Contains almost nothing because the DI object itself contains very few logic.
 * Everything is delegated wither to [container].
 */
internal open class DIImpl internal constructor(private val _container: DIContainerImpl) : DI {

    @Suppress("unused")
    private constructor(builder: DIMainBuilderImpl, runCallbacks: Boolean) : this(DIContainerImpl(builder.containerBuilder, builder.externalSources, builder.fullDescriptionOnError, runCallbacks))

    constructor(allowSilentOverride: Boolean = false, init: DI.MainBuilder.() -> Unit) : this(newBuilder(allowSilentOverride, init), true)

    companion object {
        private fun newBuilder(allowSilentOverride: Boolean = false, init: DI.MainBuilder.() -> Unit) = DIMainBuilderImpl(allowSilentOverride).apply(init)

        fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: DI.MainBuilder.() -> Unit): Pair<DI, () -> Unit> {
            val di = DIImpl(newBuilder(allowSilentOverride, init), false)
            return di to { di._container.initCallbacks?.invoke() ; Unit }
        }
    }

    final override val container: DIContainer by lazy {
        if (_container.initCallbacks != null)
            throw IllegalStateException("DI has not been initialized")
        _container
    }

}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("BindingDIImpl<C,A,T>"), DeprecationLevel.ERROR)
internal typealias BindingKodeinImpl<C,A,T> = BindingDIImpl<C,A,T>

@Suppress("UNCHECKED_CAST")
internal open class BindingDIImpl<out C : Any, out A, out T: Any> internal constructor(
        override val directDI: DirectDI,
        private val _key: DI.Key<C, A, T>,
        override val context: C,
        private val _overrideLevel: Int
) : DirectDI by directDI, BindingDI<C> {
    override fun overriddenFactory(): (Any?) -> Any = container.factory(_key, context, _overrideLevel + 1) as (Any?) -> Any
    override fun overriddenFactoryOrNull(): ((Any?) -> Any)? = container.factoryOrNull(_key, context, _overrideLevel + 1) as ((Any?) -> Any)?
}
