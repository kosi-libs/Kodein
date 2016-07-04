package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer
import java.util.*

/**
 * Kodein implementation.
 *
 * Contains almost nothing because the Kodein object itself contains very few logic.
 * Everything is delegated wither to [typed] or to [container].
 */
class KodeinImpl internal constructor(override val container: KodeinContainer) : Kodein {

    /**
     * Creates a Kodein object with a [Kodein.Builder]'s internal.
     *
     * - Uses the [KodeinContainer.Builder] to create the [container].
     * - Calls all callbacks registered in [Kodein.Builder._callbacks].
     */
    @Suppress("unused")
    private constructor(builder: Kodein.Builder) : this(KodeinContainerImpl(builder.container)) {
        builder._callbacks.forEach { it() }
    }

    /**
     * "Main" constructor.
     */
    constructor(allowSilentOverride: Boolean = false, init: Kodein.Builder.() -> Unit) : this(Kodein.Builder(KodeinContainer.Builder(true, allowSilentOverride),ArrayList(), init))

    override val typed = TKodeinImpl(container)
}