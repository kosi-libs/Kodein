package org.kodein.di.ktor

/**
 * Interface that will help leverage the use of Kodein in the Ktor [Sessions] context
 */
interface KtorSession {
    fun getSessionId(): Any
}
