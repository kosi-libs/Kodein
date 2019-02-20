package org.kodein.di.ktor

import io.ktor.application.Application
import io.ktor.application.ApplicationFeature
import io.ktor.util.AttributeKey
import org.kodein.di.Kodein
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
    fun configureKodein(application: Application, configure: Kodein.MainBuilder.() -> Unit ) {
        val kodeinInstance = Kodein { configure() }
        application.attributes.put(KodeinKey, kodeinInstance)
    }

    // Implements ApplicationFeature as a companion object.
    companion object Feature : ApplicationFeature<Application, Kodein.MainBuilder, KodeinFeature> {
        // Creates a unique key for the feature.
        override val key = AttributeKey<KodeinFeature>("Closest Kodein Container")

        // Code to execute when installing the feature.
        override fun install(pipeline: Application, configure: Kodein.MainBuilder.() -> Unit): KodeinFeature {
            return KodeinFeature().apply {
                configureKodein(pipeline, configure)
            }
        }
    }
}
