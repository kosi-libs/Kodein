package org.kodein.di.tornadofx

import org.kodein.di.*
import tornadofx.*

/**
 * Getting the global [Kodein] container from the [App] parameter if its [KodeinAware]
 */
private fun kodein(getApplication: () -> App) = LazyKodein { (getApplication() as KodeinAware).kodein}

/**
 * Getting a global [Kodein] container from the running [App]
 */
fun Component.kodein() = kodein { app }