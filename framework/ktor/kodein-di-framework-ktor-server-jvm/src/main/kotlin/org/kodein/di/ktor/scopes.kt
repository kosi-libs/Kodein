package org.kodein.di.ktor

import io.ktor.server.application.*
import io.ktor.server.routing.RoutingCall
import io.ktor.server.sessions.*
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import org.kodein.di.bindings.WeakContextScope

//region Session scope
/**
 * Interface that will help leverage the use of DI in the Ktor [Sessions] context
 */
public interface KodeinDISession {
    public fun getSessionId(): Any
}

/**
 * DI scope that will provide singletons according to a specific [KodeinDISession]
 */
public object SessionScope : Scope<KodeinDISession> {

    private val mapRegistry = HashMap<Any, ScopeRegistry>()

    /**
     * Reclaim the right [ScopeRegistry] regarding to the given [KodeinDISession]
     * This will help maintaining and retrieving singletons linked with the [KodeinDISession]
     */
    public override fun getRegistry(context: KodeinDISession): ScopeRegistry {
        return synchronized(mapRegistry) {
            mapRegistry[context.getSessionId()] ?: run {
                val scopeRegistry = StandardScopeRegistry()
                mapRegistry[context.getSessionId()] = scopeRegistry
                scopeRegistry
            }
        }
    }

    /**
     * Remove amd close the [ScopeRegistry] linked to the [KodeinDISession]
     * The linked singletons won't be retrievable anymore
     *
     * This is usually called when closing / expiring the session
     */
    public fun close(session: KodeinDISession) {
        synchronized(mapRegistry) {
            val scopeRegistry = mapRegistry[session.getSessionId()]
            if (scopeRegistry != null) {
                mapRegistry.remove(session.getSessionId())
                scopeRegistry.clear()
            }
        }
    }
}

/**
 * Clear session instance with type [T] and clear the corresponding [ScopeRegistry]
 * @throws IllegalStateException if no session provider registered for type [T]
 */
public inline fun <reified T : Any> CurrentSession.clearSessionScope() {
    val session = get<T>()

    if(session != null && session is KodeinDISession){
        SessionScope.close(session)
    }

    this.clear<T>()
}
//endregion
//region Request scope
public object CallScope : WeakContextScope<ApplicationCall>() {
    override fun getRegistry(context: ApplicationCall): ScopeRegistry {
        val actualContext = when (context) {
            is RoutingCall -> context.pipelineCall
            else -> context
        }
        return super.getRegistry(actualContext)
    }
}
//endregion