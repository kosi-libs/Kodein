package org.kodein.di.tornadofx

import javafx.scene.*
import org.kodein.di.*
import tornadofx.*

/**
 * Extend the global [Kodein] container (from the App)
 */
inline fun Component.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = subKodein(kodein(), allowSilentOverride, copy, init)

/**
 * Extend the nearest [Kodein] container, Local (from a parent Node) or Global (from the App)
 */
inline fun Node.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) {
    val nearestKodein = kodein()
    addKodeinProperty(subKodein(nearestKodein, allowSilentOverride, copy, init))
}