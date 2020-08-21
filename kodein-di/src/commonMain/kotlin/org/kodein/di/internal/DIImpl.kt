package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.di.bindings.BindingDI
import org.kodein.type.TypeToken

/**
 * DI implementation.
 *
 * Contains almost nothing because the DI object itself contains very few logic.
 * Everything is delegated wither to [container].
 */
internal open class DIImpl internal constructor(private val internalContainer: DIContainerImpl) : DI {

    @Suppress("unused")
    private constructor(builder: DIMainBuilderImpl, runCallbacks: Boolean) : this(DIContainerImpl(builder.containerBuilder, builder.externalSources, builder.fullDescriptionOnError, runCallbacks))

    constructor(allowSilentOverride: Boolean = false, init: DI.MainBuilder.() -> Unit) : this(newBuilder(allowSilentOverride, init), true)

    companion object {
        private fun newBuilder(allowSilentOverride: Boolean = false, init: DI.MainBuilder.() -> Unit) = DIMainBuilderImpl(allowSilentOverride).apply(init)

        fun withDelayedCallbacks(allowSilentOverride: Boolean = false, init: DI.MainBuilder.() -> Unit): Pair<DI, () -> Unit> {
            val di = DIImpl(newBuilder(allowSilentOverride, init), false)
            return di to { di.internalContainer.initCallbacks?.invoke() ; Unit }
        }
    }

    final override val container: DIContainer get() {
        if (internalContainer.initCallbacks != null)
            throw IllegalStateException("DI has not been initialized")
        return internalContainer
    }

}

@Suppress("UNCHECKED_CAST")
internal open class BindingDIImpl<out C : Any, out A, out T: Any> internal constructor(
        override val directDI: DirectDI,
        private val key: DI.Key<C, A, T>,
        private val diContext: DIContext<C>,
        private val overrideLevel: Int
) : DirectDI by directDI, BindingDI<C> {

    override fun overriddenFactory(): (Any?) -> Any = container.factory(key, diContext, overrideLevel + 1) as (Any?) -> Any
    override fun overriddenFactoryOrNull(): ((Any?) -> Any)? = container.factoryOrNull(key, diContext, overrideLevel + 1) as ((Any?) -> Any)?

    override val context: C get() = diContext.value(key.contextType) as C

    override fun noGlobalContext(): BindingDI<C> {
        if (diContext != DIContext.Any && key.contextType == TypeToken.Any) {
            return BindingDIImpl(directDI, key, DIContext.Any as DIContext<C>, overrideLevel)
        }
        return this
    }
}
