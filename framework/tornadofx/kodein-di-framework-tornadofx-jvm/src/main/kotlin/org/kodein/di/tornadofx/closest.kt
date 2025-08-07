package org.kodein.di.tornadofx

import javafx.scene.*
import org.kodein.di.*
import tornadofx.*

/**
 * Getting the global [DI] container from the [App] parameter if its [DIAware]
 */
private fun kodeinDI(getApplication: () -> App) = LazyDI { (getApplication() as DIAware).di }


/**
 * Getting a global [DI] container from the running [App]
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public fun Component.kodeinDI(): LazyDI = kodeinDI { app }

/**
 * Alias to `di`
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public fun Component.closestKodeinDI(): LazyDI = kodeinDI()

/**
 * Unique value to be able to set a [DI] container into Node#properties
 */
private const val KODEIN_DI_KEY: String = "KODEIN_DI_KEY"

/**
 * Installing a [DI] container into Node#properties if there is none
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public fun Node.kodeinDI(init: DI.MainBuilder.() -> Unit): Unit = addKodeinDIProperty(DI { init() })

/**
 * Alias to `di`
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public fun Node.closestKodeinDI(init: DI.MainBuilder.() -> Unit): Unit = kodeinDI(init)

/**
 * Installing a [DI] container into Node#properties if there is none
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public fun Node.addKodeinDIProperty(di: DI) {
    if (properties[KODEIN_DI_KEY] != null)
        throw IllegalArgumentException("There is already a DI container for the node ${this}")

    properties[KODEIN_DI_KEY] = di
}

/**
 * Getting the nearest [DI] container in the Node hierarchy,
 * going from parent to parent and retrieve the first [DI] container encountered
 *
 * If no [DI] container is not found in the hierarchy, we try to retrieve the one from the App if there is one
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public fun Node.kodeinDI(): LazyDI = when {
    properties[KODEIN_DI_KEY] != null -> LazyDI { properties[KODEIN_DI_KEY] as DI }
    else -> parent?.kodeinDI() ?: when {
        FX.application is DIAware -> LazyDI { (FX.application as DIAware).di }
        else -> throw IllegalStateException("No DI container found for [${this}]")
    }
}

/**
 * Alias to `di`
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public fun Node.closestKodeinDI(): LazyDI = kodeinDI()
