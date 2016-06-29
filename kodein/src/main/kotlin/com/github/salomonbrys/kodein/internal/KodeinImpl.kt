package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer
import com.github.salomonbrys.kodein.internal.TKodeinImpl
import java.util.*

class KodeinImpl internal constructor(override val container: KodeinContainer) : Kodein {


    private constructor(builder: Kodein.Builder) : this(KodeinContainerImpl(builder._builder)) {
        builder._callbacks.forEach { it() }
    }

    constructor(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit) : this(Kodein.Builder(Kodein.Builder.OverrideMode.get(true, allowSilentOverride), KodeinContainer.Builder(), ArrayList(), init))


    override val typed = TKodeinImpl(container)
}