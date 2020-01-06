package org.kodein.di.internal

import org.kodein.di.*

@Suppress("UNCHECKED_CAST")
@Deprecated(DEPRECATE_7X)
private val KodeinContext<*>.anyType get() = type as TypeToken<in Any?>

@Suppress("FunctionName")
@Deprecated(DEPRECATE_7X)
internal actual class DKodeinImpl actual constructor(container: KodeinContainer, context: KodeinContext<*>) : DKodeinBaseImpl(container, context), DKodein {

    override fun <A, T : Any> AllFactories(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): List<(A) -> T> = container.allFactories(Kodein.Key(context.anyType, argType, type, tag), context.value)

    override fun <T : Any> AllProviders(type: TypeToken<T>, tag: Any?): List<() -> T> = container.allProviders(Kodein.Key(context.anyType, UnitToken, type, tag), context.value)

    override fun <A, T : Any> AllProviders(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): List<() -> T> = container.allFactories(Kodein.Key(context.anyType, argType, type, tag), context.value).map { it.toProvider(arg) }

    override fun <T : Any> AllInstances(type: TypeToken<T>, tag: Any?): List<T> = container.allProviders(Kodein.Key(context.anyType, UnitToken, type, tag), context.value).map { it.invoke() }

    override fun <A, T : Any> AllInstances(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): List<T> = container.allFactories(Kodein.Key(context.anyType, argType, type, tag), context.value).map { it.invoke(arg) }
}
