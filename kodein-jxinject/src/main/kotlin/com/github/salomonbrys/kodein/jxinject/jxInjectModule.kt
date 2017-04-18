package com.github.salomonbrys.kodein.jxinject

import com.github.salomonbrys.kodein.*

val jxInjectModule = Kodein.Module {
    bind() from erasedSingleton { KodeinJavaxInjector(kodein) }
}

val Kodein.jx: KodeinJavaxInjector get() = erasedInstanceOrNull<KodeinJavaxInjector>()
                                           ?: throw IllegalStateException("Did you forget to import(jxInjectModule)?")

object Jx {
    @JvmStatic
    fun of(kodein: Kodein) = kodein.jx
}
