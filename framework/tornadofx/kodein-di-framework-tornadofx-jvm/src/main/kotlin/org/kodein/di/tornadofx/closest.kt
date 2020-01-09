package org.kodein.di.tornadofx

import javafx.scene.*
import org.kodein.di.*
import tornadofx.*

/**
 * Getting the global [DI] container from the [App] parameter if its [DIAware]
 */
private fun kodeinDI(getApplication: () -> App) = LazyDI { (getApplication() as DIAware).di }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di(getApplication)"), DeprecationLevel.ERROR)
private fun kodein(getApplication: () -> App) = kodeinDI(getApplication)


/**
 * Getting a global [DI] container from the running [App]
 */
fun Component.kodeinDI() = kodeinDI { app }
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("kodeinDI()"), DeprecationLevel.ERROR)
fun Component.kodein() = kodeinDI()

/**
 * Alias to `di`
 */
fun Component.closestKodeinDI() = kodeinDI()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestKodeinDI()"), DeprecationLevel.ERROR)
fun Component.closestKodein() = kodeinDI()

/**
 * Unique value to be able to set a [DI] container into Node#properties
 */
private const val KODEIN_DI_KEY = "KODEIN_DI_KEY"
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("KODEIN_DI_KEY"), DeprecationLevel.ERROR)
private const val KODEIN_KEY = "KODEIN_DI_KEY"

/**
 * Installing a [DI] container into Node#properties if there is none
 */
fun Node.kodeinDI(init: DI.MainBuilder.() -> Unit) = addKodeinDIProperty(DI { init() })
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("kodeinDI(init)"), DeprecationLevel.ERROR)
fun Node.kodein(init: DI.MainBuilder.() -> Unit) = kodeinDI(init)

/**
 * Alias to `di`
 */
fun Node.closestKodeinDI(init: DI.MainBuilder.() -> Unit) = kodeinDI(init)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestKodeinDI(init)"), DeprecationLevel.ERROR)
fun Node.closestKodein(init: DI.MainBuilder.() -> Unit) = kodeinDI(init)

/**
 * Installing a [DI] container into Node#properties if there is none
 */
fun Node.addKodeinDIProperty(di: DI) {
    if (properties[KODEIN_DI_KEY] != null)
        throw IllegalArgumentException("There is already a DI container for the node ${this}")

    properties[KODEIN_DI_KEY] = di
}
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("addKodeinDIProperty(di)"), DeprecationLevel.ERROR)
fun Node.addKodeinProperty(di: DI) = addKodeinDIProperty(di)

/**
 * Getting the nearest [DI] container in the Node hierarchy,
 * going from parent to parent and retrieve the first [DI] container encountered
 *
 * If no [DI] container is not found in the hierarchy, we try to retrieve the one from the App if there is one
 */
fun Node.kodeinDI(): LazyDI = when {
    properties[KODEIN_DI_KEY] != null -> LazyDI { properties[KODEIN_DI_KEY] as DI }
    else -> parent?.kodeinDI() ?: when {
        FX.application is DIAware -> LazyDI { (FX.application as DIAware).di }
        else -> throw IllegalStateException("No DI container found for [${this}]")
    }
}
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("kodeinDI()"), DeprecationLevel.ERROR)
fun Node.kodein(): LazyDI = kodeinDI()

/**
 * Alias to `di`
 */
fun Node.closestKodeinDI() = kodeinDI()
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("closestKodeinDI"), DeprecationLevel.ERROR)
fun Node.closestKodein() = kodeinDI()
