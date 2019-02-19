package org.kodein.di.ktor

import io.ktor.application.ApplicationCall
import io.ktor.sessions.CurrentSession
import io.ktor.sessions.clear
import io.ktor.sessions.get
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import org.kodein.di.bindings.WeakContextScope

//region Session scope
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

/**
 * Clear session instance with type [T] and clear the corresponding [ScopeRegistry]
 * @throws IllegalStateException if no session provider registered for type [T]
 */
inline fun <reified T> CurrentSession.clearSessionScope() {
    val session = get<T>()

    if(session != null && session is KtorSession){
        SessionScope.close(session)
    }

    this.clear<T>()
}
//endregion
//region Request scope
object RequestScope : WeakContextScope<ApplicationCall>()
//endregion