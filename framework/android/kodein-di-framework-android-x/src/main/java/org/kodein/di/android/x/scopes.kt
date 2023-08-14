package org.kodein.di.android.x

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import org.kodein.di.bindings.*
import org.kodein.di.internal.synchronizedIfNull
import java.util.*

public open class AndroidLifecycleScope private constructor(
    private val newRegistry: () -> ScopeRegistry
) : Scope<LifecycleOwner>, DefaultLifecycleObserver {

    public companion object multiItem : AndroidLifecycleScope(::StandardScopeRegistry)

    public object singleItem : AndroidLifecycleScope(::SingleItemScopeRegistry)

    private val map = HashMap<LifecycleOwner, ScopeRegistry>()

    override fun getRegistry(context: LifecycleOwner): ScopeRegistry {
        return synchronizedIfNull(
            lock = map,
                predicate = { map[context] },
                ifNotNull = { it },
                ifNull = {
                    val registry = newRegistry()
                    map[context] = registry
                    context.lifecycle.addObserver(
                        LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_DESTROY) {
                                context.lifecycle.removeObserver(this)
                                registry.clear()
                                map.remove(context)
                            }
                        }
                    )
                    registry
                }
        )
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }
}
