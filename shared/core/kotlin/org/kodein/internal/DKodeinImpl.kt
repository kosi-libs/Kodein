package org.kodein.internal

import org.kodein.*

internal class DKodeinImpl(override val kodein: Kodein, override val kodeinContext: KodeinContext<*>, override val kodeinReceiver: Any?) : DKodein {

    override fun on(context: KodeinContext<*>, receiver: Any?) = DKodeinImpl(kodein, context, receiver)

    override fun <A, T : Any> Factory(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): (A) -> T = kodein.container.factory(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, kodeinReceiver)

    override fun <A, T : Any> FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): ((A) -> T)? = kodein.container.factoryOrNull(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, kodeinReceiver)

    override fun <T : Any> Provider(type: TypeToken<T>, tag: Any?): () -> T = kodein.container.provider(Kodein.Key(kodeinContext.anyType, UnitToken, type, tag), kodeinContext.value, kodeinReceiver)

    override fun <A, T : Any> Provider(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): () -> T = kodein.container.factory(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, kodeinReceiver).toProvider(arg)

    override fun <T : Any> ProviderOrNull(type: TypeToken<T>, tag: Any?): (() -> T)? = kodein.container.providerOrNull(Kodein.Key(kodeinContext.anyType, UnitToken, type, tag), kodeinContext.value, kodeinReceiver)

    override fun <A, T : Any> ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): (() -> T)? = kodein.container.factoryOrNull(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, kodeinReceiver)?.toProvider(arg)

    override fun <T : Any> Instance(type: TypeToken<T>, tag: Any?): T = kodein.container.provider(Kodein.Key(kodeinContext.anyType, UnitToken, type, tag), kodeinContext.value, kodeinReceiver).invoke()

    override fun <A, T : Any> Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): T = kodein.container.factory(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, kodeinReceiver).invoke(arg)

    override fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any?): T? = kodein.container.providerOrNull(Kodein.Key(kodeinContext.anyType, UnitToken, type, tag), kodeinContext.value, kodeinReceiver)?.invoke()

    override fun <A, T : Any> InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): T? = kodein.container.factoryOrNull(Kodein.Key(kodeinContext.anyType, argType, type, tag), kodeinContext.value, kodeinReceiver)?.invoke(arg)
}
