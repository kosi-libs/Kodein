package org.kodein.di.generic

import org.kodein.di.DI
import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.bindings.SimpleAutoContextTranslator
import org.kodein.di.bindings.SimpleContextTranslator
import org.kodein.di.generic

inline fun <reified C : Any, reified S : Any> contextTranslator(noinline t: (C) -> S): ContextTranslator<C, S> = SimpleContextTranslator(org.kodein.type.generic(), org.kodein.type.generic(), t)

inline fun <reified C : Any, reified S : Any> DI.Builder.registerContextTranslator(noinline t: (C) -> S) = RegisterContextTranslator(contextTranslator(t))

inline fun <reified S : Any> contextFinder(noinline t: () -> S) : ContextTranslator<Any, S> = SimpleAutoContextTranslator(org.kodein.type.generic(), t)

inline fun <reified S : Any> DI.Builder.registerContextFinder(noinline t: () -> S) = RegisterContextTranslator(contextFinder(t))
