package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.type.TypeToken

@Suppress("UNCHECKED_CAST")
private val DIContext<*>.anyType get() = type as TypeToken<in Any>

@Suppress("FunctionName")
internal actual class DirectDIImpl actual constructor(container: DIContainer, context: DIContext<*>) : DirectDIBaseImpl(container, context), DirectDI {

    override fun <A, T : Any> AllFactories(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?): List<(A) -> T> = container.allFactories(DI.Key(context.anyType, argType, type, tag), context)

    override fun <T : Any> AllProviders(type: TypeToken<T>, tag: Any?): List<() -> T> = container.allProviders(DI.Key(context.anyType, TypeToken.Unit, type, tag), context)

    override fun <A, T : Any> AllProviders(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A): List<() -> T> = container.allFactories(DI.Key(context.anyType, argType, type, tag), context).map { it.toProvider(arg) }

    override fun <T : Any> AllInstances(type: TypeToken<T>, tag: Any?): List<T> = container.allProviders(DI.Key(context.anyType, TypeToken.Unit, type, tag), context).map { it.invoke() }

    override fun <A, T : Any> AllInstances(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A): List<T> = container.allFactories(DI.Key(context.anyType, argType, type, tag), context).map { it.invoke(arg) }
}
