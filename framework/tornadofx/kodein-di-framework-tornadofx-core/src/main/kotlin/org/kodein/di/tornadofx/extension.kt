package org.kodein.di.tornadofx

import org.kodein.di.*
import org.kodein.di.bindings.*
import tornadofx.*
import tornadofx.Scope
import kotlin.reflect.*

@Suppress("UNCHECKED_CAST")
val tornadoFXExternalSource = Kodein {
    externalSource = ExternalSource { key ->
        val elementType = key.type.jvmType as Class<*>
        val contextType = key.argType.jvmType as Class<*>

        // Check if the elementType is injectable by TornadoFX di container (infine if its a Component)
        if (Component::class.java.isAssignableFrom(elementType)) {
            val componentType = elementType.kotlin as KClass<Component>

            when (contextType) {
                // If there is no context argument, we try to find the right Component
                Unit::class.java -> externalFactory { find(componentType) }
                else ->
                    // If there is a context argument it must be of type Scope
                    // then we try to find the right Component for the given Scope
                    if (Scope::class.java.isAssignableFrom(contextType))
                        externalFactory { find(type = componentType, scope = it as Scope) }
                    else null // The context is not of type Scope
            }
        } else null // its not a Component
    }
}