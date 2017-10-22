package org.kodein.internal

import org.kodein.DKodein
import org.kodein.Kodein
import org.kodein.TypeToken

internal class DKodeinImpl(override val kodein: Kodein, private val _receiver: Any?) : DKodein {

    override fun on(receiver: Any?) = DKodeinImpl(kodein, receiver)

    override fun <A, T : Any> Factory(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any?): (A) -> T = kodein.container.factory(Kodein.Key(Kodein.Bind(type, tag), argType), _receiver)

    override fun <A, T : Any> FactoryOrNull(argType: TypeToken<out A>, type: TypeToken<T>, tag: Any?): ((A) -> T)? = kodein.container.factoryOrNull(Kodein.Key(Kodein.Bind(type, tag), argType), _receiver)

    override fun <T : Any> Provider(type: TypeToken<T>, tag: Any?): () -> T = kodein.container.provider(Kodein.Bind(type, tag), _receiver)

    override fun <T : Any> ProviderOrNull(type: TypeToken<T>, tag: Any?): (() -> T)? = kodein.container.providerOrNull(Kodein.Bind(type, tag), _receiver)

    override fun <T : Any> Instance(type: TypeToken<T>, tag: Any?): T = kodein.container.provider(Kodein.Bind(type, tag), _receiver).invoke()

    override fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any?): T? = kodein.container.providerOrNull(Kodein.Bind(type, tag), _receiver)?.invoke()
}
