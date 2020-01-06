package org.kodein.di.internal

import org.kodein.di.*

@Suppress("UNCHECKED_CAST")
@Deprecated(DEPRECATE_7X)
private inline val KodeinContext<*>.anyType get() = type as TypeToken<in Any?>


@Suppress("FunctionName")
@Deprecated(DEPRECATE_7X)
internal abstract class DKodeinBaseImpl protected constructor(override val container: KodeinContainer, val context: KodeinContext<*>) : DKodein {

    override val dkodein: DKodein get() = this

    override val lazy: Kodein get() = KodeinImpl(container as KodeinContainerImpl).On(context = context)

    override fun On(context: KodeinContext<*>): DKodein = DKodeinImpl(container, context)

    override fun <A, T : Any> Factory(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): (A) -> T = container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value)

    override fun <A, T : Any> FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): ((A) -> T)? = container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value)

    override fun <T : Any> Provider(type: TypeToken<T>, tag: Any?): () -> T = container.provider(Kodein.Key(context.anyType, UnitToken, type, tag), context.value)

    override fun <A, T : Any> Provider(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): () -> T = container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value).toProvider(arg)

    override fun <T : Any> ProviderOrNull(type: TypeToken<T>, tag: Any?): (() -> T)? = container.providerOrNull(Kodein.Key(context.anyType, UnitToken, type, tag), context.value)

    override fun <A, T : Any> ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): (() -> T)? = container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value)?.toProvider(arg)

    override fun <T : Any> Instance(type: TypeToken<T>, tag: Any?): T = container.provider(Kodein.Key(context.anyType, UnitToken, type, tag), context.value).invoke()

    override fun <A, T : Any> Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): T = container.factory(Kodein.Key(context.anyType, argType, type, tag), context.value).invoke(arg)

    override fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any?): T? = container.providerOrNull(Kodein.Key(context.anyType, UnitToken, type, tag), context.value)?.invoke()

    override fun <A, T : Any> InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): T? = container.factoryOrNull(Kodein.Key(context.anyType, argType, type, tag), context.value)?.invoke(arg)
}
@Deprecated(DEPRECATE_7X)
internal expect class DKodeinImpl(container: KodeinContainer, context: KodeinContext<*>) : DKodein
