package org.kodein

import org.kodein.bindings.ExternalSource

interface KodeinTree {

    /**
     * An immutable view of the bindings map. *For inspection & debug*.
     */
    val bindings: BindingsMap

    val externalSource: ExternalSource?

    fun <C, A, T : Any> find(key: Kodein.Key<C, A, T>, overrideLevel: Int = 0): List<Pair<Kodein.Key<C, A, T>, KodeinDefinition<C, A, T>>>

    fun find(search: SearchSpecs): List<Pair<Kodein.Key<*, *, *>, List<KodeinDefinition<*, *, *>>>>

    operator fun <C, A, T: Any> get(key: Kodein.Key<C, A, T>): List<KodeinDefinition<C, A, T>>?

}