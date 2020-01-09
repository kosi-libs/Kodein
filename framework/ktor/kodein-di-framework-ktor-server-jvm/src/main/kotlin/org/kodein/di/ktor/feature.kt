package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.util.*
import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.di.ktor.KodeinFeature.*

/**
 * Ktor [Feature] that provide a global [Kodein] container
 * that would be accessible from everywhere in the Ktor application
 */
@Deprecated(DEPRECATE_7X)
class KodeinFeature private constructor() {

    /**
     * Configure the [Kodein] container then put it in the [Application.attributes],
     * thus it would be easily accessible (e.g. [Application.kodein]
     */
    @Deprecated(DEPRECATE_7X)
    fun configureKodein(application: Application, kodeinInstance: Kodein) {
        application.attributes.put(KodeinKey, kodeinInstance)
    }

    // Implements ApplicationFeature as a companion object.
    companion object Feature : ApplicationFeature<Application, Kodein.MainBuilder, KodeinFeature> {
        // Creates a unique key for the feature.
        override val key = AttributeKey<KodeinFeature>("[Global Kodein Container]")

        // Code to execute when installing the feature.
        override fun install(pipeline: Application, configure: Kodein.MainBuilder.() -> Unit): KodeinFeature {
            val application = pipeline

            val applicationToken = erased<Application>()

            val kodeinInstance = Kodein {
                Bind<Application>(erased()) with InstanceBinding(applicationToken, application)
                configure()
            }

            return KodeinFeature().apply {
                configureKodein(pipeline, kodeinInstance)
            }
        }
    }
}

/**
 * Gets or installs a [KodeinFeature] feature for the this [Application] and runs a [configuration] script on it
 */
@Deprecated(DEPRECATE_7X)
fun Application.kodein(configuration: Kodein.MainBuilder.() -> Unit) = install(KodeinFeature, configuration)