package org.kodein.di.ktor.controller

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import org.kodein.di.*
import org.kodein.di.ktor.*
import org.kodein.di.ktor.controller.KodeinControllerFeature.*

/**
 * Ktor [Feature] that provide a global [Kodein] container
 * and autowire all the bound [AbstractKodeinController] by installing the routes
 * that would be accessible from everywhere in the Ktor application
 */
// Deprecated Since 6.4
@Deprecated(message="KodeinController doesn't need to be bound to the Kodein container. " +
        " Consider using the [Route.controller] method (e.g. `Route.controller { KodeinController() }`)" +
        " Will be remove in 7.0")
class KodeinControllerFeature private constructor() {

    // Implements ApplicationFeature as a companion object.
    companion object Feature : ApplicationFeature<Application, Unit, KodeinControllerFeature> {

        // Creates a unique key for the feature.
        override val key = AttributeKey<KodeinControllerFeature>("[Kodein Controller Registering]")

        // Code to execute when installing the feature.
        override fun install(pipeline: Application, configure: Unit.() -> Unit): KodeinControllerFeature {
            val application = pipeline

            // Try to retrieve the [KodeinFeature]
            // if not found throw MissingApplicationFeatureException
            application.feature(KodeinFeature)
            application.routing {
                /*
                For every binding we check whether or not its assignable to KodeinController.
                Simply, we are looking for all the [KodeinController] in the Kodein DI context
                */
                val controllerInstances by application.kodein().AllInstances(erased<KodeinController>())
                controllerInstances.forEach {
                    it.apply {
                        /*
                        For each of those [KodeinController]s we install the defined routes to the [Routing] feature
                         */
                        application.log.info("Installing '$this' routes.")
                        installRoutes()
                    }
                }
            }

            return KodeinControllerFeature()
        }
    }
}
