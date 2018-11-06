package org.kodein.di.generic

import org.kodein.di.Kodein
import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.bindings.SimpleAutoContextTranslator
import org.kodein.di.bindings.SimpleContextTranslator
import org.kodein.di.generic

inline fun <reified C, reified S> contextTranslator(noinline t: (C) -> S): ContextTranslator<C, S> = SimpleContextTranslator(generic(), generic(), t)

inline fun <reified C, reified S> Kodein.Builder.registerContextTranslator(noinline t: (C) -> S) = RegisterContextTranslator(contextTranslator(t))

inline fun <reified S> contextFinder(noinline t: () -> S) : ContextTranslator<Any?, S> = SimpleAutoContextTranslator(generic(), t)

inline fun <reified S> Kodein.Builder.registerContextFinder(noinline t: () -> S) = RegisterContextTranslator(contextFinder(t))
