package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.util.*
import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.di.ktor.DIFeature.*
import org.kodein.type.erased

/**
 * Ktor [Feature] that provide a global [DI] container
 * that would be accessible from everywhere in the Ktor application
 */
public class DIFeature private constructor() {

    /**
     * Configure the [DI] container then put it in the [Application.attributes],
     * thus it would be easily accessible (e.g. [Application.di]
     */
    public fun configureDI(application: Application, diInstance: DI) {
        application.attributes.put(KodeinDIKey, diInstance)
    }

    // Implements ApplicationFeature as a companion object.
    public companion object Feature : ApplicationFeature<Application, DI.MainBuilder, DIFeature> {
        // Creates a unique key for the feature.
        public override val key: AttributeKey<DIFeature> = AttributeKey<DIFeature>("[Global DI Container]")

        // Code to execute when installing the feature.
        public override fun install(pipeline: Application, configure: DI.MainBuilder.() -> Unit): DIFeature {
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
public fun Application.di(configuration: DI.MainBuilder.() -> Unit): DIFeature = install(DIFeature, configuration)