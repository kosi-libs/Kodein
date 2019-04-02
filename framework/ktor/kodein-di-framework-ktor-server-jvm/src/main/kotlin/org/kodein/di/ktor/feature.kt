package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*
import org.kodein.di.*
import org.kodein.di.generic.*
import org.kodein.di.ktor.KodeinFeature.Feature

/**
 * Ktor [Feature] that provide a global [Kodein] container
 * that would be accessible from everywhere in the Ktor application
 */
class KodeinFeature private constructor() {

    /**
     * Configure the [Kodein] container then put it in the [Application.attributes],
     * thus it would be easily accessible (e.g. [Application.kodein]
     */
    fun configureKodein(application: Application, kodeinInstance: Kodein) {
        application.attributes.put(KodeinKey, kodeinInstance)
    }

    // Implements ApplicationFeature as a companion object.
    companion object Feature : ApplicationFeature<Application, Kodein.MainBuilder, KodeinFeature> {
        // Creates a unique key for the feature.
        override val key = AttributeKey<KodeinFeature>("Closest Kodein Container")

        // Code to execute when installing the feature.
        override fun install(pipeline: Application, configure: Kodein.MainBuilder.() -> Unit): KodeinFeature {
            val application = pipeline

            val kodeinInstance = Kodein {
                bind<Application>() with instance(application)
                configure()
            }

            application.routing {
                kodeinInstance.container.tree.bindings.asSequence()
                        /*
                        For every binding we check whether or not its assignable to KodeinController.
                        Simply, we are looking for all the [KodeinController] in the Kodein DI context
                        */
                        .filter { KodeinController::class.java.isAssignableFrom(it.key.type.jvmType as Class<*>) }
                        .map { it.key.type }
                        .forEach { type ->
                            /*
                            For each of those [KodeinController]s we install the defined routes to the [Routing] feature
                             */
                            val controller by kodeinInstance.Instance(type)
                            (controller as KodeinController).apply {
                                application.log.info("Installing '$controller' routes.")
                                installRoutes()
                            }
                        }
            }

            return KodeinFeature().apply {
                configureKodein(pipeline, kodeinInstance)
            }
        }
    }
}
