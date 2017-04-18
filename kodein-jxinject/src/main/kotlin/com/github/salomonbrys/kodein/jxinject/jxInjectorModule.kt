package com.github.salomonbrys.kodein.jxinject

import com.github.salomonbrys.kodein.*

val jxInjectorModule = Kodein.Module {
    bind() from erasedSingleton { JxInjector(kodein) }
}

val Kodein.jx: JxInjector get() = erasedInstanceOrNull<JxInjector>()
                                  ?: throw IllegalStateException("Did you forget to import(jxInjectorModule)?")

object Jx {
    @JvmStatic
    fun of(kodein: Kodein) = kodein.jx
}
