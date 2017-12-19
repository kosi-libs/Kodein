package org.kodein.internal

import org.kodein.*

internal class DKodeinImpl(override val kodein: Kodein, val context: KodeinContext<*>, val receiver: Any?) : DKodein {

    override fun on(context: KodeinContext<*>, receiver: Any?): DKodein {
        val newContext = if (context == DKodein.SAME_CONTEXT) this.context else context
        val newReceiver = if (receiver == DKodein.SAME_RECEIVER) this.receiver else receiver
        return DKodeinImpl(kodein, newContext, newReceiver)
    }

    override fun <A, T : Any> Factory(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): (A) -> T = kodein.container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)

    override fun <A, T : Any> FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): ((A) -> T)? = kodein.container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)

    override fun <T : Any> Provider(type: TypeToken<T>, tag: Any?): () -> T = kodein.container.provider(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver)

    override fun <A, T : Any> Provider(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): () -> T = kodein.container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver).toProvider(arg)

    override fun <T : Any> ProviderOrNull(type: TypeToken<T>, tag: Any?): (() -> T)? = kodein.container.providerOrNull(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver)

    override fun <A, T : Any> ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): (() -> T)? = kodein.container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)?.toProvider(arg)

    override fun <T : Any> Instance(type: TypeToken<T>, tag: Any?): T = kodein.container.provider(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver).invoke()

    override fun <A, T : Any> Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): T = kodein.container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver).invoke(arg)

    override fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any?): T? = kodein.container.providerOrNull(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver)?.invoke()

    override fun <A, T : Any> InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): T? = kodein.container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)?.invoke(arg)
}
