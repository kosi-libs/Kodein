package org.kodein.di.tornadofx

import org.kodein.di.*
import tornadofx.*

/**
 * Extend the global [Kodein] container (from the App)
 */
inline fun Component.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = subKodein(kodein(), allowSilentOverride, copy, init)