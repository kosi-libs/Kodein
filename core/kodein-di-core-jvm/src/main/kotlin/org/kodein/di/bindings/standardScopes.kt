package org.kodein.di.bindings

import java.util.*

/**
 * Scope that map [scope registries][ScopeRegistry] associated to weak contexts.
 *
 * In essence, the context is weak, and for a given context, its registry will be GC'd when it is itself GC'd.
 */
open class WeakContextScope<C, in A>(val newRepo: () -> ScopeRegistry<in A>) : SimpleScope<C, A> {

    private val map = WeakHashMap<C, ScopeRegistry<in A>>()

    override fun getRegistry(receiver: Any?, context: C): ScopeRegistry<in A> {
        map[context]?.let { return it }
        synchronized(map) {
            return map.getOrPut(context) { newRepo() }
        }
    }

}
