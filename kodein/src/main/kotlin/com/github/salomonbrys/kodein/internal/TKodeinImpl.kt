package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer
import com.github.salomonbrys.kodein.TKodein
import java.lang.reflect.Type

/**
 * Kodein typed API implementation.
 *
 * All methods delegate their business to the [KodeinContainer].
 *
 * @param _container The container to proxy to.
 */
@Suppress("UNCHECKED_CAST", "unused")
class TKodeinImpl(private val _container: KodeinContainer) : TKodein {

    override fun factory(argType: Type, type: Type, tag: Any?): (Any) -> Any = _container.nonNullFactory(Kodein.Key(Kodein.Bind(type, tag), argType))

    override fun factoryOrNull(argType: Type, type: Type, tag: Any?): ((Any) -> Any)? = _container.factoryOrNull(Kodein.Key(Kodein.Bind(type, tag), argType))

    override fun provider(type: Type, tag: Any?): () -> Any = _container.nonNullProvider(Kodein.Bind(type, tag))

    override fun providerOrNull(type: Type, tag: Any?): (() -> Any)? = _container.providerOrNull(Kodein.Bind(type, tag))

    override fun instance(type: Type, tag: Any?): Any = _container.nonNullProvider(Kodein.Bind(type, tag)).invoke()

    override fun instanceOrNull(type: Type, tag: Any?): Any? = _container.providerOrNull(Kodein.Bind(type, tag))?.invoke()
}