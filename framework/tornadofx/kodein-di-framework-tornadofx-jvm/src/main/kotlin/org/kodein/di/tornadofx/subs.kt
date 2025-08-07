package org.kodein.di.tornadofx

import javafx.scene.*
import org.kodein.di.*
import tornadofx.*

/**
 * Extend the global [DI] container (from the App)
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public inline fun Component.subDI(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit): LazyDI = subDI(kodeinDI(), allowSilentOverride, copy, init)

/**
 * Extend the nearest [DI] container, Local (from a parent Node) or Global (from the App)
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public inline fun Node.subDI(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) {
    val nearestDI = kodeinDI()
    addKodeinDIProperty(subDI(nearestDI, allowSilentOverride, copy, init))
}
