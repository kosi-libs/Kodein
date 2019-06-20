package org.kodein.di.tornadofx

import javafx.scene.*
import org.kodein.di.*
import tornadofx.*

/**
 * Getting the global [Kodein] container from the [App] parameter if its [KodeinAware]
 */
private fun kodein(getApplication: () -> App) = LazyKodein { (getApplication() as KodeinAware).kodein }

/**
 * Getting a global [Kodein] container from the running [App]
 */
fun Component.kodein() = kodein { app }

/**
 * Unique value to be able to set a [Kodein] container into Node#properties
 */
private const val KODEIN_KEY = "KODEIN_KEY"

/**
 * Installing a [Kodein] container into Node#properties if there is none
 */
fun Node.kodein(init: Kodein.MainBuilder.() -> Unit) = addKodeinProperty(Kodein { init() })

/**
 * Installing a [Kodein] container into Node#properties if there is none
 */
fun Node.addKodeinProperty(kodein: Kodein) {
    if (properties[KODEIN_KEY] != null)
        throw IllegalArgumentException("There is already a Kodein container for the node ${this}")

    properties[KODEIN_KEY] = kodein
}

/**
 * Getting the nearest [Kodein] container in the Node hierarchy,
 * going from parent to parent and retrieve the first [Kodein] container encountered
 *
 * If no [Kodein] container is not found in the hierarchy, we try to retrieve the one from the App if there is one
 */
fun Node.kodein(): LazyKodein = when {
    properties[KODEIN_KEY] != null -> LazyKodein { properties[KODEIN_KEY] as Kodein }
    else -> parent?.kodein() ?: when {
        FX.application is KodeinAware -> LazyKodein { (FX.application as KodeinAware).kodein }
        else -> throw IllegalStateException("No kodein container found for [${this}]")
    }
}