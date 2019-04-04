package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import org.kodein.di.*
import org.kodein.di.generic.*
import org.kodein.di.ktor.KodeinControllerFeature.Feature

/**
 * Ktor [Feature] that provide a global [Kodein] container
 * and autowire all the bound [KodeinController] by installing the routes
 * that would be accessible from everywhere in the Ktor application
 */
class KodeinControllerFeature private constructor() {

    /**
     * Configure the [Kodein] container then put it in the [Application.attributes],
     * thus it would be easily accessible (e.g. [Application.kodein]
     */
    fun configureKodein(application: Application, kodeinInstance: Kodein) {
        application.attributes.put(KodeinKey, kodeinInstance)
    }

    // Implements ApplicationFeature as a companion object.
    companion object Feature : ApplicationFeature<Application, Kodein.MainBuilder, KodeinControllerFeature> {
        // Creates a unique key for the feature.
        override val key = AttributeKey<KodeinControllerFeature>("Kodein Controller Container")

        // Code to execute when installing the feature.
        override fun install(pipeline: Application, configure: Kodein.MainBuilder.() -> Unit): KodeinControllerFeature {
            val application = pipeline

            val kodeinInstance = Kodein {
                bind<Application>() with instance(application)
                configure()
            }

            application.routing {
                /*
                For every binding we check whether or not its assignable to KodeinController.
                Simply, we are looking for all the [KodeinController] in the Kodein DI context
                */
                val controllerInstances: List<KodeinController> by kodeinInstance.allInstances()
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

            return KodeinControllerFeature().apply {
                configureKodein(pipeline, kodeinInstance)
            }
        }
    }
}
