package org.kodein.di.tornadofx

import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.type.jvmType
import tornadofx.*
import tornadofx.Scope
import kotlin.reflect.*

/**
 * Installs a TornadoFX source to the DI container
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Suppress("UNCHECKED_CAST")
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public fun DI.MainBuilder.installTornadoSource() {
    externalSources += ExternalSource { key ->
        val elementType = key.type.getRaw().jvmType as Class<*>?

        // Check if the elementType is injectable by TornadoFX di container (infine if its a Component)
        if (elementType != null &&
                Component::class.java.isAssignableFrom(elementType)) {
            val componentType = elementType.kotlin as KClass<Component>
            val scope = this.context

            // If there is a context it must be of type Scope
            // then we try to find the right Component for the given Scope
            if (Scope::class.java.isAssignableFrom(scope::class.java))
                externalFactory { find(type = componentType, scope = scope as Scope) }
            else
                externalFactory { find(componentType) } // The context is null or not of type Scope

        } else null // its not a Component
    }
}
