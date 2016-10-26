package com.github.salomonbrys.kodein

inline fun <reified T : Any> Kodein.Builder.bind(tag: Any? = null, overrides: Boolean? = null) = bindErased<T>(tag, overrides)

infix inline fun <reified T : Any> Kodein.Builder.ConstantBinder.with(value: T) = withErased(value)
