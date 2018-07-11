package org.kodein.di.android.x

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.ScopeRepositoryType
import org.kodein.di.bindings.SimpleScope
import org.kodein.di.bindings.newScopeRegistry
import org.kodein.di.internal.synchronizedIfNull
import java.util.*

open class AndroidLifecycleScope private constructor(private val repositoryType: ScopeRepositoryType) : SimpleScope<Any?, Any?> {

    companion object multiItem: AndroidLifecycleScope(ScopeRepositoryType.MULTI_ITEM)

    object singleItem: AndroidLifecycleScope(ScopeRepositoryType.SINGLE_ITEM)

    private val map = HashMap<LifecycleOwner, ScopeRegistry<in Any?>>()

    override fun getRegistry(receiver: Any?, context: Any?): ScopeRegistry<in Any?> {
        val owner = (context as? LifecycleOwner) ?: (receiver as LifecycleOwner) ?: throw IllegalStateException("Either the receiver or the context must be LifecycleOwner")
        return synchronizedIfNull(
                lock = map,
                predicate = { map[owner] },
                ifNotNull = { it },
                ifNull = {
                    val registry = newScopeRegistry<Any?>(repositoryType)
                    map[owner] = registry
                    owner.lifecycle.addObserver(object : LifecycleObserver {
                        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                        fun onDestroy() {
                            owner.lifecycle.removeObserver(this)
                            registry.clear()
                            map.remove(owner)
                        }
                    })
                    registry
                }
        )
    }
}