package org.kodein.di.ktor

import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry

/**
 * Interface that will help leverage the use of Kodein in the Ktor [Sessions] context
 */
interface KtorSession {
    fun getSessionId(): Any
}

/**
 * Kodein scope that will provide singletons according to a specific [KtorSession]
 */
object SessionScope : Scope<KtorSession> {

    private val mapRegistry = HashMap<Any, ScopeRegistry>()

    /**
     * Reclaim the right [ScopeRegistry] regarding to the given [KtorSession]
     * This will help maintaining and retrieving singletons linked with the [KtorSession]
     */
    override fun getRegistry(context: KtorSession): ScopeRegistry {
        return synchronized(mapRegistry) {
            mapRegistry[context.getSessionId()] ?: run {
                val scopeRegistry = StandardScopeRegistry()
                mapRegistry[context.getSessionId()] = scopeRegistry
                scopeRegistry
            }
        }
    }

    /**
     * Remove amd close the [ScopeRegistry] linked to the [KtorSession]
     * The linked singletons won't be retrievable anymore
     *
     * This is usually called when closing / expiring the session
     */
    fun close(session: KtorSession) {
        synchronized(mapRegistry) {
            val scopeRegistry = mapRegistry[session]
            if (scopeRegistry != null) {
                mapRegistry.remove(session.getSessionId())
                scopeRegistry.clear()
            }
        }
    }
}
