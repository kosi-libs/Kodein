package org.kodein.di.erased

import org.kodein.di.AnyToken
import org.kodein.di.Kodein
import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.erased

inline fun <reified C, reified S> contextTranslator(crossinline t: (C) -> S) = object : ContextTranslator<C, S>(erased(), erased()) {
    override fun translate(ctx: C): S = t(ctx)
}

inline fun <reified C, reified S> Kodein.Builder.registerContextTranslator(crossinline t: (C) -> S) = RegisterContextTranslator(contextTranslator(t))

inline fun <reified S> contextFinder(crossinline t: () -> S) = object : ContextTranslator<Any?, S>(AnyToken, erased()) {
    override fun translate(ctx: Any?): S = t()
}

inline fun <reified S> Kodein.Builder.registerContextFinder(crossinline t: () -> S) = RegisterContextTranslator(contextFinder(t))
