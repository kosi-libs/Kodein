package org.kodein.conf

import org.kodein.Kodein
import org.kodein.KodeinAware


/**
 * A global One True Kodein.
 */
private var _global = ConfigurableKodein()

/**
 * A global One True Kodein.
 */
@Suppress("unused")
val Kodein.Companion.global: ConfigurableKodein get() = _global

/**
 * A `KodeinAware` class that needs no implementation because the kodein used will be the [global] One True Kodein.
 */
interface KodeinGlobalAware : KodeinAware {

    /**
     * The global One True Kodein.
     */
    override val kodein: Kodein get() = Kodein.global

}
