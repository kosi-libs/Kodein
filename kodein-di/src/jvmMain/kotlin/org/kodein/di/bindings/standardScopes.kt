package org.kodein.di.bindings

import java.util.*

/**
 * Scope that map [scope registries][ScopeRegistry] associated to weak contexts.
 *
 * In essence, the context is weak, and for a given context, its registry will be GC'd when it is itself GC'd.
 */
public open class WeakContextScope<in C>(public val newRepo: () -> ScopeRegistry = { StandardScopeRegistry() }) : Scope<C> {

    private val map = WeakHashMap<C, ScopeRegistry>()

    override fun getRegistry(context: C): ScopeRegistry {
        map[context]?.let { return it }
        synchronized(map) {
            return map.getOrPut(context) { newRepo() }
        }
    }

    public companion object Of : WeakContextScope<Any>() {
        @Suppress("UNCHECKED_CAST")
        public fun <T> of(): Scope<T> = this as Scope<T>
    }
}
