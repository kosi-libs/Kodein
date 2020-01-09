package org.kodein.di.conf

import org.kodein.di.*


private val oneTrueKodein = ConfigurableKodein()

/**
 * A global One True Kodein.
 */
@Suppress("unused")
val Kodein.Companion.global: ConfigurableKodein get() = oneTrueKodein

/**
 * A `KodeinAware` class that needs no implementation because the kodein used will be the [global] One True Kodein.
 */
@Deprecated(DEPRECATE_7X)
interface KodeinGlobalAware : KodeinAware {

    /**
     * The global One True Kodein.
     */
    override val kodein: Kodein get() = Kodein.global

}
