package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.util.*
import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.di.ktor.DIFeature.*

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIFeature"), DeprecationLevel.ERROR)
typealias KodeinFeature = DIFeature
/**
 * Ktor [Feature] that provide a global [DI] container
 * that would be accessible from everywhere in the Ktor application
 */
class DIFeature private constructor() {

    /**
     * Configure the [DI] container then put it in the [Application.attributes],
     * thus it would be easily accessible (e.g. [Application.di]
     */
    fun configureDI(application: Application, diInstance: DI) {
        application.attributes.put(KodeinDIKey, diInstance)
    }

    // Implements ApplicationFeature as a companion object.
    companion object Feature : ApplicationFeature<Application, DI.MainBuilder, DIFeature> {
        // Creates a unique key for the feature.
        override val key = AttributeKey<DIFeature>("[Global DI Container]")

        // Code to execute when installing the feature.
        override fun install(pipeline: Application, configure: DI.MainBuilder.() -> Unit): DIFeature {
            val application = pipeline

            val applicationToken = erased<Application>()

            val diInstance = DI {
                Bind<Application>(erased()) with InstanceBinding(applicationToken, application)
                configure()
            }

            return DIFeature().apply {
                configureDI(pipeline, diInstance)
            }
        }
    }
}

/**
 * Gets or installs a [DIFeature] feature for the this [Application] and runs a [configuration] script on it
 */
fun Application.di(configuration: DI.MainBuilder.() -> Unit) = install(DIFeature, configuration)
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("di(configuration)"), DeprecationLevel.ERROR)
fun Application.kodein(configuration: DI.MainBuilder.() -> Unit) = di(configuration)