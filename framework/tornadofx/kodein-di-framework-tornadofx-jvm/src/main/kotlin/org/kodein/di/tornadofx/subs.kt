package org.kodein.di.tornadofx

import javafx.scene.*
import org.kodein.di.*
import tornadofx.*

/**
 * Extend the global [DI] container (from the App)
 */
inline fun Component.subDI(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI(kodeinDI(), allowSilentOverride, copy, init)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("subDI(allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun Component.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI(allowSilentOverride, copy, init)

/**
 * Extend the nearest [DI] container, Local (from a parent Node) or Global (from the App)
 */
inline fun Node.subDI(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) {
    val nearestDI = kodeinDI()
    addKodeinDIProperty(subDI(nearestDI, allowSilentOverride, copy, init))
}
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("subDI(allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun Node.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = subDI(allowSilentOverride, copy, init)