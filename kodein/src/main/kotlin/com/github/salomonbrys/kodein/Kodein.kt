package com.github.salomonbrys.kodein

inline fun <reified T : Any> Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null) = bindGeneric<T>(tag, overrides)

infix inline fun <reified T : Any> Kodein.Builder.ConstantBinder.with(value: T) = withGeneric(value)
