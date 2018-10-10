package org.kodein.di.internal

import org.kodein.di.*

@Suppress("UNCHECKED_CAST")
private val KodeinContext<*>.anyType get() = type as TypeToken<in Any?>

@Suppress("FunctionName")
internal abstract class DKodeinBaseImpl protected constructor(override val container: KodeinContainer, val context: KodeinContext<*>, val receiver: Any?) : DKodein {

    override val dkodein: DKodein get() = this

    override val lazy: Kodein get() = KodeinImpl(container as KodeinContainerImpl).On(context = context)

    override fun On(context: KodeinContext<*>, receiver: Any?): DKodein {
        val newContext = if (context == DKodeinBase.SAME_CONTEXT) this.context else context
        val newReceiver = if (receiver == DKodeinBase.SAME_RECEIVER) this.receiver else receiver
        return DKodeinImpl(container, newContext, newReceiver)
    }

    override fun <A, T : Any> Factory(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): (A) -> T = container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)

    override fun <A, T : Any> FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): ((A) -> T)? = container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)

    override fun <T : Any> Provider(type: TypeToken<T>, tag: Any?): () -> T = container.provider(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver)

    override fun <A, T : Any> Provider(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): () -> T = container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver).toProvider(arg)

    override fun <T : Any> ProviderOrNull(type: TypeToken<T>, tag: Any?): (() -> T)? = container.providerOrNull(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver)

    override fun <A, T : Any> ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): (() -> T)? = container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)?.toProvider(arg)

    override fun <T : Any> Instance(type: TypeToken<T>, tag: Any?): T = container.provider(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver).invoke()

    override fun <A, T : Any> Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): T = container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver).invoke(arg)

    override fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any?): T? = container.providerOrNull(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver)?.invoke()

    override fun <A, T : Any> InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): T? = container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)?.invoke(arg)
}

internal expect class DKodeinImpl(container: KodeinContainer, context: KodeinContext<*>, receiver: Any?) : DKodein
