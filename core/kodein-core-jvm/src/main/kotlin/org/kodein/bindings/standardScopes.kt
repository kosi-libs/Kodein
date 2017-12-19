package org.kodein.bindings

import java.util.*

class WeakContextScope<in T> : Scope<T> {

    private val map = WeakHashMap<T, ScopeRegistry>()

    override fun getRegistry(receiver: Any?, context: T): ScopeRegistry {
        map[context]?.let { return it }
        synchronized(map) {
            return map.getOrPut(context) { MultiItemScopeRegistry() }
        }
    }

}
