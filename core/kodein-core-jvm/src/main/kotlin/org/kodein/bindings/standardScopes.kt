package org.kodein.bindings

import java.util.*

class WeakContextScope<C> : Scope<C, C> {

    private val map = WeakHashMap<C, ScopeRegistry>()

    override fun getBindingContext(envContext: C) = envContext

    override fun getRegistry(receiver: Any?, envContext: C, bindContext: C): ScopeRegistry {
        map[bindContext]?.let { return it }
        synchronized(map) {
            return map.getOrPut(bindContext) { MultiItemScopeRegistry() }
        }
    }

}
