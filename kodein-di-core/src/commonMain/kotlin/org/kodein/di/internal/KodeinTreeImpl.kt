package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.di.bindings.*


private sealed class TypeChecker {
    abstract val type: TypeToken<*>
    abstract fun check(other: TypeToken<*>): Boolean

    data class Down(override val type: TypeToken<*>) : TypeChecker() {
        val isAny = (type == AnyToken)
        override fun check(other: TypeToken<*>) = isAny || type.isAssignableFrom(other)
    }

    data class Up(override val type: TypeToken<*>) : TypeChecker() {
        override fun check(other: TypeToken<*>) = other == AnyToken || other.isAssignableFrom(type)
    }
}

private typealias BoundTypeTree = MutableMap<TypeChecker, ContextTypeTree>

private typealias ContextTypeTree = MutableMap<TypeChecker.Down, ArgumentTypeTree>

private typealias ArgumentTypeTree = MutableMap<TypeChecker.Down, TagTree>

private typealias TagTree = MutableMap<Any?, Kodein.Key<*, *, *>>

internal class KodeinTreeImpl(
        map: Map<Kodein.Key<*, *, *>, List<KodeinDefining<*, *, *>>>,
        override val externalSources: List<ExternalSource>,
        override val registeredTranslators: List<ContextTranslator<*, *>>
): KodeinTree {
    private val _cache: MutableMap<Kodein.Key<*, *, *>, Triple<Kodein.Key<*, *, *>, List<KodeinDefinition<*, *, *>>, ContextTranslator<*, *>?>> = newConcurrentMap()
    private val _typeTree: BoundTypeTree = HashMap()

    override val bindings: BindingsMap

    private val translators = ArrayList(registeredTranslators)

    init {
        map.forEach { (key, bindings) ->
            val definitions = bindings.map {
                when (it) {
                    is KodeinDefinition<*, *, *> -> it
                    else -> KodeinDefinition(it.binding, it.fromModule, this)
                }
            }
            _cache[key] = Triple(key, definitions, null)

            val typeChecker = if (bindings.first().binding.supportSubTypes) TypeChecker.Down(key.type) else TypeChecker.Up(key.type)
            val contextTree = _typeTree.getOrPut(typeChecker) { HashMap() }
            val argumentTree = contextTree.getOrPut(TypeChecker.Down(key.contextType)) { HashMap() }
            val tagTree = argumentTree.getOrPut(TypeChecker.Down(key.argType)) { HashMap() }
            tagTree[key.tag] = key
        }
        bindings = HashMap(_cache.mapValues { it.value.second })

        while (true) {
            val added = ArrayList<ContextTranslator<*, *>>()
            for (src in translators) {
                for (dst in translators) {
                    if (dst.contextType.isAssignableFrom(src.scopeType) && src.contextType != dst.scopeType) {
                        if (translators.none { it.contextType == src.contextType && it.scopeType == dst.scopeType })
                            @Suppress("UNCHECKED_CAST")
                            added += CompositeContextTranslator(src as ContextTranslator<Any, Any>, dst as ContextTranslator<Any, Any>)
                    }
                }
            }
            translators += added
            if (added.isEmpty())
                break
        }
    }

    private fun findBySpecs(specs: SearchSpecs): List<Pair<Kodein.Key<*, *, *>, ContextTranslator<*, *>?>> {
        var bindSeq: Sequence<Map.Entry<TypeChecker, ContextTypeTree>> = _typeTree.asSequence()
        val specsBindType = specs.type
        if (specsBindType != null && specsBindType != AnyToken) {
            bindSeq = bindSeq.filter { (bindType) -> bindType.check(specsBindType) } // Filter keys that are for sub-types of this specs bind type
        }

        var contextSeq: Sequence<Triple<TypeChecker.Down, ArgumentTypeTree, ContextTranslator<*, *>?>> = bindSeq.flatMap { (_, contextTree) -> contextTree.asSequence().map { Triple(it.key, it.value, null) } } // Get all matched context types
        val specsContextType = specs.contextType
        if (specsContextType != null) {
            contextSeq = contextSeq.mapNotNull { triple ->
                val (contextType) = triple
                if (contextType.check(specsContextType)) // Filter context types that are super-types of this specs context type
                    triple
                else { // Or context types that can be correctly translated
                    val translator = translators.firstOrNull { it.contextType.isAssignableFrom(specsContextType) && contextType.check(it.scopeType) }
                    if (translator != null)
                        triple.copy(third = translator)
                    else
                        null
                }
            }
        }

        var argSeq: Sequence<Triple<TypeChecker.Down, TagTree, ContextTranslator<*, *>?>> = contextSeq.flatMap { (_, argumentTree, translator) -> argumentTree.asSequence().map { Triple(it.key, it.value, translator) } } // Get all corresponding argument types
        val specsArgType = specs.argType
        if (specsArgType != null) {
            argSeq = argSeq.filter { (argType) -> argType.check(specsArgType) } // Filter argument types that are super-types of this specs argument type
        }

        var tagSeq: Sequence<Triple<Any?, Kodein.Key<*, *, *>, ContextTranslator<*, *>?>> = argSeq.flatMap { (_, tagTree, translator) -> tagTree.asSequence().map { Triple(it.key, it.value, translator) } } // Get all corresponding tags
        val specsTag = specs.tag
        if (specsTag != SearchSpecs.NoDefinedTag) {
            tagSeq = tagSeq.filter { (tag) -> tag == specsTag } // Filter tags that match this specs tag
        }

        val resultSeq = tagSeq.map { (_, key, translator) -> key to translator } // Get all corresponding queues
        return resultSeq.toList()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C, A, T : Any> find(key: Kodein.Key<C, A, T>, overrideLevel: Int, all: Boolean): List<Triple<Kodein.Key<Any, A, T>, KodeinDefinition<Any, A, T>, ContextTranslator<C, Any>?>> {

        if (!all) {
            _cache[key]?.let { (realKey, list, translator) ->
                val definition = list.getOrNull(overrideLevel) ?: return emptyList()
                return listOf(Triple(realKey as Kodein.Key<Any, A, T>, definition as KodeinDefinition<Any, A, T>, translator as ContextTranslator<C, Any>?))
            }

            if (key.contextType != AnyToken) {
                val anyContextKey = key.copy(contextType = AnyToken)
                _cache[anyContextKey]?.let { triple ->
                    val (realKey, list, translator) = triple
                    if (translator != null && translator.contextType != key.contextType)
                        return@let
                    _cache[key] = triple
                    val definition = list.getOrNull(overrideLevel) ?: return emptyList()
                    return listOf(Triple(realKey as Kodein.Key<Any, A, T>, definition as KodeinDefinition<Any, A, T>, translator as ContextTranslator<C, Any>?))
                }
            }

            val applicableTranslators = translators.filter { it.contextType == key.contextType } + translators.filter { it.contextType == AnyToken } // Ensure Any translators are at the end of the list.
            for (translator in applicableTranslators) {
                val translatedKey = Kodein.Key(translator.scopeType, key.argType, key.type, key.tag)
                _cache[translatedKey]?.takeIf { it.third == null }?.let { triple ->
                    if (triple.third != null)
                        return@let
                    _cache[key] = triple.copy(third = translator)
                    val (realKey, list) = triple
                    val definition = list.getOrNull(overrideLevel) ?: return emptyList()
                    return listOf(Triple(realKey as Kodein.Key<Any, A, T>, definition as KodeinDefinition<Any, A, T>, translator as ContextTranslator<C, Any>?))
                }
            }
        }

        val result = findBySpecs(SearchSpecs(key.contextType, key.argType, key.type, key.tag))
        if (result.size == 1) {
            val (realKey, translator) = result.first()
            _cache[key] = _cache[realKey]?.copy(third = translator) ?: throw notInMap(realKey, key)
        }

        return result.mapNotNull { (realKey, translator) ->
            val (_, definitions, _) = _cache[realKey] ?: throw notInMap(realKey, key)
            val definition = definitions.getOrNull(overrideLevel) ?: return@mapNotNull null
            Triple(realKey as Kodein.Key<Any, A, T>, definition as KodeinDefinition<Any, A, T>, translator as ContextTranslator<C, Any>?)
        }
    }

    private fun notInMap(result: Kodein.Key<*, *, *>, request: Kodein.Key<*, *, *>) = IllegalStateException("Tree returned key ${result.internalDescription} that is not in cache when searching for ${request.internalDescription}.\nKeys in cache:\n${_cache.keys.joinToString("\n") { it.internalDescription }}")

    @Suppress("UNCHECKED_CAST")
    override fun find(search: SearchSpecs): List<Triple<Kodein.Key<*, *, *>, List<KodeinDefinition<*, *, *>>, ContextTranslator<*, *>?>> {
        val result = findBySpecs(search)
        @Suppress("UselessCallOnCollection")
        return result.map { (key, translator) -> Triple(key, _cache[key]!!.second, translator) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C, A, T: Any> get(key: Kodein.Key<C, A, T>) = _cache[key] as Triple<Kodein.Key<Any, A, T>, List<KodeinDefinition<Any, A, T>>, ContextTranslator<C, Any>?>?

}
