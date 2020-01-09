package org.kodein.di.conf

import org.kodein.di.*


private val oneTrueDI = ConfigurableDI()

/**
 * A global One True DI.
 */
@Suppress("unused")
val DI.Companion.global: ConfigurableDI get() = oneTrueDI

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIGlobalAware"), DeprecationLevel.ERROR)
typealias KodeinGlobalAware = DIGlobalAware
/**
 * A `DIAware` class that needs no implementation because the DI container used will be the [global] One True DI.
 */
interface DIGlobalAware : DIAware {

    /**
     * The global One True DI.
     */
    override val di: DI get() = DI.global

}
