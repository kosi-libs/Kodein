package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.Factory
import com.github.salomonbrys.kodein.Kodein
import java.util.*

/**
 * Container class where the bindings and their factories are stored.
 * This is an internal class, it is public and contains public methods to allow inlining from the Kodein class methods.
 * However, this class should not be considered public and should only be used by Kodein's internals
 *
 * In kodein, every binding is stored as a factory (that's why a scope is a function creating a factory).
 * Providers are special classes of factories that take Unit as parameter.
 */
class KodeinContainer private constructor(
        private val _map: Map<Kodein.Key, Factory<*, Any>>,
        private val _node: Node? = null
) {

    /**
     * Class used to check for recursive dependencies
     *
     * Each factory, in their Factory@getInstance methods receive a Kodein instance to enable transient dependency.
     * However, it is not the same kodein instance as the one used to get the main dependency.
     * Each time a transient dependency is needed, a new Kodein instance is constructed, with a new Node that has
     * the current Node as it's parent.
     * This allows, at each step, to walk up the node tree and check if the requested key has not yet been requested.
     * If the same key exists twice in the tree, it means that it has, and that there's a dependency recursion.
     */
    private class Node(private val _key: Kodein.Key, private val _parent: Node?) {
        internal fun check(search: Kodein.Key) {
            if (!_check(search))
                throw Kodein.DependencyLoopException("Dependency recursion:\n" + tree(search) + "\n       ╚═> ${search.bind}")
        }

        private fun _check(search: Kodein.Key): Boolean {
            return if (_key == search) false else (_parent?._check(search) ?: true)
        }

        private fun tree(first: Kodein.Key): String {
            if (first == _key)
                return "       ╔═> ${_key.bind}"
            else
                return "${_parent?.tree(first)}\n       ╠─> ${_key.bind}"
        }
    }

    /**
     * Allows for the building of a Kodein object by defining bindings
     */
    internal class Builder internal constructor() {

        internal val _map: MutableMap<Kodein.Key, Factory<*, Any>> = HashMap()

        internal fun bind(key: Kodein.Key, factory: Factory<*, Any>, mustOverride: Boolean?) {
            if (mustOverride != null) {
                if (mustOverride && key !in _map)
                    throw Kodein.OverridingException("Binding must override an existing binding. Key: $key")
                if (!mustOverride && key in _map)
                    throw Kodein.OverridingException("Binding must not override an existing binding. Key: $key")
            }
            _map[key] = factory
        }

        internal fun extend(container: KodeinContainer, allowOverride: Boolean) {
            if (allowOverride)
                _map.putAll(container._map)
            else
                container._map.forEach { bind(it.key, it.value, false) }
        }
    }

    internal constructor(builder: Builder) : this(builder._map)

    /**
     * This is for debug. It allows to print all binded keys.
     */
    val registeredBindings: Map<Kodein.Bind, String> get() = _map.mapKeys { it.key.bind } .mapValues { it.value.scopeName }

    val bindingsDescription: String get() = registeredBindings.map { "        ${it.key.toString()} with ${it.value}" }.joinToString("\n")

    fun notFoundException(reason: String): Kodein.NotFoundException
            = Kodein.NotFoundException("$reason\nRegistered in Kodein:\n" + bindingsDescription)

    /**
     * All Kodein getter methods, whether it's instance(), provider() or factory() eventually ends up calling this
     * function.
     */
    fun factoryOrNull(key: Kodein.Key): ((Any?) -> Any)? {
        val factory = _map[key] ?: return null
        _node?.check(key)
        @Suppress("UNCHECKED_CAST")
        return { arg -> (factory as Factory<Any?, Any>).getInstance(Kodein(KodeinContainer(_map, Node(key, _node))), arg) }
    }

    fun nonNullFactory(key: Kodein.Key): ((Any?) -> Any)
            = factoryOrNull(key) ?: throw notFoundException("No factory found for $key")

    fun providerOrNull(bind: Kodein.Bind): (() -> Any)? {
        val factory = factoryOrNull(Kodein.Key(bind, Unit::class.java)) ?: return null
        return { factory(Unit) }
    }

    fun nonNullProvider(bind: Kodein.Bind): (() -> Any)
            = providerOrNull(bind) ?: throw notFoundException("No provider found for $bind")
}
