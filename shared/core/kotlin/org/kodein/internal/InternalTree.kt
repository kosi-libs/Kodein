package org.kodein.internal

import org.kodein.*
import org.kodein.bindings.KodeinBinding
import java.util.*


private typealias BoundTypeTree = MutableMap<TypeToken<*>, ContextTypeTree>

private typealias ContextTypeTree = MutableMap<TypeToken<*>, ArgumentTypeTree>

private typealias ArgumentTypeTree = MutableMap<TypeToken<*>, TagTree>

private typealias TagTree = MutableMap<Any?, Kodein.Key<*, *, *>>

internal class InternalTree(
        private val _map: BindingsMap
) {
    private val _cache: MutableMap<Kodein.Key<*, *, *>, List<KodeinBinding<*, *, *>>> = HashMap()
    private val _typeTree: BoundTypeTree = HashMap()

    init {
        _map.forEach { key, bindings ->
            _cache.putIfAbsent(key, bindings)

            val contextTree = _typeTree.getOrPut(key.type) { HashMap() }
            val argumentTree = contextTree.getOrPut(key.contextType) { HashMap() }
            val tagTree = argumentTree.getOrPut(key.argType) { HashMap() }
            tagTree.put(key.tag, key)
        }
    }

    private fun _find(key: Kodein.Key<*, *, *>): List<Kodein.Key<*, *, *>> {
//        val it0 = _typeTree.asIterable()
//        val it1 = it0.filter { (boundType) -> key.type.isAssignableFrom(boundType) }
//        val it2 = it1.flatMap { (_, contextTree) -> contextTree.asIterable() }
//        val it3 = it2.filter { (contextType) -> contextType.isAssignableFrom(key.contextType) }
//        val it4 = it3.flatMap { (_, argumentTree) -> argumentTree.asIterable() }
//        val it5 = it4.filter { (argType) -> argType.isAssignableFrom(key.argType) }
//        val it6 = it5.flatMap { (_, tagTree) -> tagTree.asIterable() }
//        val it7 = it6.filter { (tag) -> tag == key.tag }
//        val it8 = it7.map { (_, overrideQueue) -> overrideQueue }
//        val list = it8.toList()


        return _typeTree
                .asSequence()
                .filter { (boundType) -> key.type.isAssignableFrom(boundType) }             // Filter keys that are for sub-types of this key type...
                .flatMap { (_, contextTree) -> contextTree.asSequence() }                   // ...Get all corresponding context types
                .filter { (contextType) -> contextType.isAssignableFrom(key.contextType) }  // Filter context types that are super-types of this key context type...
                .flatMap { (_, argumentTree) -> argumentTree.asSequence() }                 // ...Get all corresponding argument types
                .filter { (argType) -> argType.isAssignableFrom(key.argType) }              // Filter argument types that are super-types of this key argument type...
                .flatMap { (_, tagTree) -> tagTree.asSequence() }                           // ...Get all corresponding tags
                .filter { (tag) -> tag == key.tag }                                         // Filter tags that match this key tag...
                .map { (_, overrideQueue) -> overrideQueue }                                // ...Get all corresponding queues
                .toList()
    }

    data class Result<C, A, T: Any>(
            val found: KodeinBinding<C, A, T>? = null,
            val potentials: List<Kodein.Key<C, A, T>>? = null
    )

    @Suppress("UNCHECKED_CAST")
    fun <C, A, T : Any> find(key: Kodein.Key<C, A, T>, overrideLevel: Int): Result<C, A, T> {

        _cache[key]?.let { return Result(found = it.getOrNull(overrideLevel) as KodeinBinding<C, A, T>?) }

        if (key.contextType != AnyToken) {
            val anyContextKey = key.copy(contextType = AnyToken)
            _cache[anyContextKey]?.let {
                _cache[key] = it
                return Result(found = it.getOrNull(overrideLevel) as KodeinBinding<C, A, T>?)
            }
        }

        val entries = _find(key)
        if (entries.isEmpty()) {
            return Result()
        }

        if (entries.size > 1) {
            return Result(potentials = entries.map { realKey -> realKey as Kodein.Key<C, A, T> })
        }

        val realKey = entries.first()
        val queue = _map[realKey] ?: throw IllegalStateException("The tree contains a key that is not in the map.")
        _cache[key] = queue

        return Result(found = queue.getOrNull(overrideLevel) as KodeinBinding<C, A, T>?)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <C, A, T: Any> get(key: Kodein.Key<C, A, T>) = _cache[key] as List<KodeinBinding<C, A, T>>

    inner class BindingsMapView : BindingsMap by _map

    val map: BindingsMap get() = BindingsMapView()
}
