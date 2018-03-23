package org.kodein.di.internal

import org.kodein.di.*

@Suppress("UNCHECKED_CAST")
private val KodeinContext<*>.anyType get() = type as TypeToken<in Any?>

@Suppress("FunctionName")
internal actual class DKodeinImpl actual constructor(container: KodeinContainer, context: KodeinContext<*>, receiver: Any?) : DKodeinBaseImpl(container, context, receiver), DKodein {

    override fun <A, T : Any> AllFactories(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): List<(A) -> T> = container.allFactories(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver)

    override fun <T : Any> AllProviders(type: TypeToken<T>, tag: Any?): List<() -> T> = container.allProviders(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver)

    override fun <A, T : Any> AllProviders(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): List<() -> T> = container.allFactories(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver).map { it.toProvider(arg) }

    override fun <T : Any> AllInstances(type: TypeToken<T>, tag: Any?): List<T> = container.allProviders(Kodein.Key(context.anyType, UnitToken, type, tag), context.value, receiver).map { it.invoke() }

    override fun <A, T : Any> AllInstances(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): List<T> = container.allFactories(Kodein.Key(context.anyType, argType, type, tag), context.value, receiver).map { it.invoke(arg) }
}
