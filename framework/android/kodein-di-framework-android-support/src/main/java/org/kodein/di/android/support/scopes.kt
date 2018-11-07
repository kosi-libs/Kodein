package org.kodein.di.android.support

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import org.kodein.di.bindings.*
import org.kodein.di.internal.synchronizedIfNull
import java.util.HashMap

open class AndroidLifecycleScope private constructor(private val newRegistry: () -> ScopeRegistry) : Scope<LifecycleOwner> {

    companion object multiItem: AndroidLifecycleScope(::StandardScopeRegistry)

    object singleItem: AndroidLifecycleScope(::SingleItemScopeRegistry)

    private val map = HashMap<LifecycleOwner, ScopeRegistry>()

    override fun getRegistry(context: LifecycleOwner): ScopeRegistry {
        return synchronizedIfNull(
                lock = map,
                predicate = { map[context] },
                ifNotNull = { it },
                ifNull = {
                    val registry = newRegistry()
                    map[context] = registry
                    context.lifecycle.addObserver(object : LifecycleObserver {
                        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                        fun onDestroy() {
                            context.lifecycle.removeObserver(this)
                            registry.clear()
                            map.remove(context)
                        }
                    })
                    registry
                }
        )
    }
}
