package org.kodein.di.conf

import org.kodein.di.*


private val oneTrueDI = ConfigurableDI()

/**
 * A global One True DI.
 */
@Suppress("unused")
public val DI.Companion.global: ConfigurableDI get() = oneTrueDI

/**
 * A `DIAware` class that needs no implementation because the DI container used will be the [global] One True DI.
 */
public interface DIGlobalAware : DIAware {

    /**
     * The global One True DI.
     */
    override val di: DI get() = DI.global

}
