package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.Factory
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer

/**
 * Container class where the bindings and their factories are stored.
 *
 * In kodein, every binding is stored as a factory.
 * Providers are special classes of factories that take Unit as parameter.
 *
 * @property _map The map containing all bindings.
 * @property _node See [KodeinContainerImpl.Node]
 */
class KodeinContainerImpl private constructor(private val _map: Map<Kodein.Key, Factory<*, Any>>, private val _node: Node? = null) : KodeinContainer {

    /**
     * Class used to check for recursive dependencies, represents a node in the dependency tree.
     *
     * Each factory, in their Factory@getInstance methods receives a Kodein instance to enable transient dependency.
     * However, it is not the same kodein instance as the one used to get the main dependency.
     * Each time a transient dependency is needed, a new Kodein instance is constructed, with a new Node that has
     * the current Node as it's parent.
     * This allows, at each step, to walk up the node tree and check if the requested key has not yet been requested.
     * If the same key exists twice in the tree, it means that it has, and that there's a dependency recursion.
     *
     * @property _key The key of this node, meaning that this key has been looked for once.
     * @property _parent The parent node, meaning the parent lookup that needed this key.
     */
    private class Node(private val _key: Kodein.Key, private val _parent: Node?) {

        /**
         * Check that given key does **not** exist in the node tree or throws an exception if it does.
         *
         * @throws Kodein.DependencyLoopException if the key exists in the dependency tree.
         */
        internal fun check(search: Kodein.Key) {
            if (!_check(search))
                throw Kodein.DependencyLoopException("Dependency recursion:\n" + tree(search) + "\n       ╚═> ${search.bind}")
        }

        /**
         * Recursive function that walks up the node tree to check if a specific key can be found.
         *
         * @return whether the given key exists in the tree.
         */
        private fun _check(search: Kodein.Key): Boolean {
            return if (_key == search) false else (_parent?._check(search) ?: true)
        }

        /**
         * @return The current transitive dependency tree as a string.
         */
        private fun tree(first: Kodein.Key): String {
            if (first == _key)
                return "       ╔═> ${_key.bind}"
            else
                return "${_parent?.tree(first)}\n       ╠─> ${_key.bind}"
        }
    }

    /**
     * "Main" constructor that uses the bindings map configured by a [KodeinContainer.Builder].
     */
    internal constructor(builder: KodeinContainer.Builder) : this(builder._map)

    /**
     * Wrapper that makes a map truly immutable, even in Java (Kotlin will throw an exception when trying to mutate).
     */
    private class ImmutableMapWrapper<K : Any, out V : Any>(private val _base: Map<K, V>) : Map<K, V> by _base

    override val bindings: Map<Kodein.Key, Factory<*, *>> = ImmutableMapWrapper(_map)

    override fun factoryOrNull(key: Kodein.Key): ((Any?) -> Any)? {
        val factory = _map[key] ?: return null
        _node?.check(key)
        @Suppress("UNCHECKED_CAST")
        return { arg -> (factory as Factory<Any?, Any>).getInstance(KodeinImpl(KodeinContainerImpl(_map, Node(key, _node))), key, arg) }
    }
}
