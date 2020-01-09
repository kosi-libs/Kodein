package org.kodein.di.ktor.controller

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import org.kodein.di.*
import org.kodein.di.ktor.*
import org.kodein.di.ktor.controller.DIControllerFeature.*

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIControllerFeature"), DeprecationLevel.ERROR)
typealias KodeinControllerFeature = DIControllerFeature

/**
 * Ktor [Feature] that provide a global [DI] container
 * and autowire all the bound [AbstractDIController] by installing the routes
 * that would be accessible from everywhere in the Ktor application
 */
// Deprecated Since 6.4
@Deprecated(message="KodeinController doesn't need to be bound to the Kodein container. " +
        " Consider using the [Route.controller] method (e.g. `Route.controller { KodeinController() }`)" +
        " Will be remove in 7.0")
class DIControllerFeature private constructor() {

    // Implements ApplicationFeature as a companion object.
    companion object Feature : ApplicationFeature<Application, Unit, DIControllerFeature> {

        // Creates a unique key for the feature.
        override val key = AttributeKey<DIControllerFeature>("[DI Controller Registering]")

        // Code to execute when installing the feature.
        override fun install(pipeline: Application, configure: Unit.() -> Unit): DIControllerFeature {
            val application = pipeline

            // Try to retrieve the [DIFeature]
            // if not found throw MissingApplicationFeatureException
            application.feature(DIFeature)
            application.routing {
                /*
                For every binding we check whether or not its assignable to DIController.
                Simply, we are looking for all the [DIController] in the DI context
                */
                val controllerInstances by application.di().AllInstances(erased<DIController>())
                controllerInstances.forEach {
                    it.apply {
                        /*
                        For each of those [DIController]s we install the defined routes to the [Routing] feature
                         */
                        application.log.info("Installing '$this' routes.")
                        installRoutes()
                    }
                }
            }

            return DIControllerFeature()
        }
    }
}
