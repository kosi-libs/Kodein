package org.kodein.di.erased

import org.kodein.di.AnyToken
import org.kodein.di.Kodein
import org.kodein.di.TypeToken
import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.erased

// Kotlin/Native produces a NullPointerException when using `= object : ContextTranslator {`
@PublishedApi
internal class EContextTranslator<C, S>(val t: (C) -> S, contextType: TypeToken<in C>, scopeType: TypeToken<in S>) : ContextTranslator<C, S>(contextType, scopeType) {
    override fun translate(ctx: C): S = t(ctx)
}

inline fun <reified C, reified S> contextTranslator(noinline t: (C) -> S): ContextTranslator<C, S> = EContextTranslator(t, erased(), erased())

inline fun <reified C, reified S> Kodein.Builder.registerContextTranslator(noinline t: (C) -> S) = RegisterContextTranslator(contextTranslator(t))

// Kotlin/Native produces a NullPointerException when using `= object : ContextTranslator {`
@PublishedApi
internal class EContextFinder<S>(val t: () -> S, scopeType: TypeToken<in S>) : ContextTranslator<Any?, S>(AnyToken, scopeType) {
    override fun translate(ctx: Any?): S = t()
}

inline fun <reified S> contextFinder(noinline t: () -> S) : ContextTranslator<Any?, S> = EContextFinder(t, erased())

inline fun <reified S> Kodein.Builder.registerContextFinder(noinline t: () -> S) = RegisterContextTranslator(contextFinder(t))
