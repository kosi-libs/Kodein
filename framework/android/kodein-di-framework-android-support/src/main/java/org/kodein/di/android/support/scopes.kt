package org.kodein.di.android.support

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.kodein.di.bindings.*
import org.kodein.di.internal.synchronizedIfNull
import java.util.HashMap

public open class AndroidLifecycleScope private constructor(private val newRegistry: () -> ScopeRegistry) : Scope<LifecycleOwner> {

    public companion object multiItem: AndroidLifecycleScope(::StandardScopeRegistry)

    public object singleItem: AndroidLifecycleScope(::SingleItemScopeRegistry)

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
