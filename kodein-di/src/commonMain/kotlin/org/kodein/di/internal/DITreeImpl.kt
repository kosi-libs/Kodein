package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.type.TypeToken


private sealed class TypeChecker {
    abstract val type: TypeToken<*>
    abstract fun check(other: TypeToken<*>): Boolean

    data class Down(override val type: TypeToken<*>) : TypeChecker() {
        val isAny = (type == TypeToken.Any)
        override fun check(other: TypeToken<*>) = isAny || type.isAssignableFrom(other)
    }

    data class Up(override val type: TypeToken<*>) : TypeChecker() {
        override fun check(other: TypeToken<*>) = other == TypeToken.Any || other.isAssignableFrom(type)
    }
}

private typealias BoundTypeTree = MutableMap<TypeChecker, ContextTypeTree>

private typealias ContextTypeTree = MutableMap<TypeChecker.Down, ArgumentTypeTree>

private typealias ArgumentTypeTree = MutableMap<TypeChecker.Down, TagTree>

private typealias TagTree = MutableMap<Any?, DI.Key<*, *, *>>

internal class DITreeImpl(
        map: Map<DI.Key<*, *, *>, List<DIDefining<*, *, *>>>,
        override val externalSources: List<ExternalSource>,
        override val registeredTranslators: List<ContextTranslator<*, *>>
): DITree {
    private val _cache: MutableMap<DI.Key<*, *, *>, Triple<DI.Key<*, *, *>, List<DIDefinition<*, *, *>>, ContextTranslator<*, *>?>> = newConcurrentMap()
    private val _typeTree: BoundTypeTree = HashMap()

    override val bindings: BindingsMap

    private val translators = ArrayList(registeredTranslators)

    init {
        map.forEach { (key, bindings) ->
            val definitions = bindings.map {
                when (it) {
                    is DIDefinition<*, *, *> -> it
                    else -> DIDefinition(it.binding, it.fromModule, this)
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

    private fun findBySpecs(specs: SearchSpecs): List<Pair<DI.Key<*, *, *>, ContextTranslator<*, *>?>> {
        var bindSeq: Sequence<Map.Entry<TypeChecker, ContextTypeTree>> = _typeTree.asSequence()
        val specsBindType = specs.type
        if (specsBindType != null && specsBindType != TypeToken.Any) {
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

        var tagSeq: Sequence<Triple<Any?, DI.Key<*, *, *>, ContextTranslator<*, *>?>> = argSeq.flatMap { (_, tagTree, translator) -> tagTree.asSequence().map { Triple(it.key, it.value, translator) } } // Get all corresponding tags
        val specsTag = specs.tag
        if (specsTag != SearchSpecs.NoDefinedTag) {
            tagSeq = tagSeq.filter { (tag) -> tag == specsTag } // Filter tags that match this specs tag
        }

        val resultSeq = tagSeq.map { (_, key, translator) -> key to translator } // Get all corresponding queues
        return resultSeq.toList()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any, A, T : Any> find(key: DI.Key<C, A, T>, overrideLevel: Int, all: Boolean): List<Triple<DI.Key<Any, A, T>, DIDefinition<Any, A, T>, ContextTranslator<C, Any>?>> {

        if (!all) {
            _cache[key]?.let { (realKey, list, translator) ->
                val definition = list.getOrNull(overrideLevel) ?: return emptyList()
                return listOf(Triple(realKey as DI.Key<Any, A, T>, definition as DIDefinition<Any, A, T>, translator as ContextTranslator<C, Any>?))
            }

            if (key.contextType != TypeToken.Any) {
                val anyContextKey = key.copy(contextType = TypeToken.Any)
                _cache[anyContextKey]?.let { triple ->
                    val (realKey, list, translator) = triple
                    if (translator != null && translator.contextType != key.contextType)
                        return@let
                    _cache[key] = triple
                    val definition = list.getOrNull(overrideLevel) ?: return emptyList()
                    return listOf(Triple(realKey as DI.Key<Any, A, T>, definition as DIDefinition<Any, A, T>, translator as ContextTranslator<C, Any>?))
                }
            }

            val applicableTranslators = translators.filter { it.contextType == key.contextType } + translators.filter { it.contextType == TypeToken.Any } // Ensure Any translators are at the end of the list.
            for (translator in applicableTranslators) {
                val translatedKey = DI.Key(translator.scopeType, key.argType, key.type, key.tag)
                _cache[translatedKey]?.takeIf { it.third == null }?.let { triple ->
                    if (triple.third != null)
                        return@let
                    _cache[key] = triple.copy(third = translator)
                    val (realKey, list) = triple
                    val definition = list.getOrNull(overrideLevel) ?: return emptyList()
                    return listOf(Triple(realKey as DI.Key<Any, A, T>, definition as DIDefinition<Any, A, T>, translator as ContextTranslator<C, Any>?))
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
            Triple(realKey as DI.Key<Any, A, T>, definition as DIDefinition<Any, A, T>, translator as ContextTranslator<C, Any>?)
        }
    }

    private fun notInMap(result: DI.Key<*, *, *>, request: DI.Key<*, *, *>) = IllegalStateException("Tree returned key ${result.internalDescription} that is not in cache when searching for ${request.internalDescription}.\nKeys in cache:\n${_cache.keys.joinToString("\n") { it.internalDescription }}")

    override fun find(search: SearchSpecs): List<Triple<DI.Key<*, *, *>, List<DIDefinition<*, *, *>>, ContextTranslator<*, *>?>> {
        val result = findBySpecs(search)
        return result.map { (key, translator) -> Triple(key, _cache[key]!!.second, translator) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any, A, T: Any> get(key: DI.Key<C, A, T>) = _cache[key] as Triple<DI.Key<Any, A, T>, List<DIDefinition<Any, A, T>>, ContextTranslator<C, Any>?>?

}
