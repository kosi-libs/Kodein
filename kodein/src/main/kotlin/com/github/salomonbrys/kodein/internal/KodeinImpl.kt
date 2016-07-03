package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer
import java.util.*

class KodeinImpl internal constructor(override val container: KodeinContainer) : Kodein {


    private constructor(builder: Kodein.Builder) : this(KodeinContainerImpl(builder.container)) {
        builder._callbacks.forEach { it() }
    }

    constructor(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit) : this(Kodein.Builder(KodeinContainer.Builder(true, allowSilentOverride),ArrayList(), init))


    override val typed = TKodeinImpl(container)
}