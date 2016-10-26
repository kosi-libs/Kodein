package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.Factory
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer
import com.github.salomonbrys.kodein.KodeinWrappedType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

/**
 * Container class where the bindings and their factories are stored.
 *
 * In kodein, every binding is stored as a factory.
 * Providers are special classes of factories that take Unit as parameter.
 *
 * @property _map The map containing all bindings.
 * @property _node See [KodeinContainerImpl.Node]
 */
internal class KodeinContainerImpl private constructor(private val _map: CMap, private val _node: Node? = null) : KodeinContainer {

    /**
     * A cache that is filled each time a key is not found directly into the [_map] but by modifying the key's [Kodein.Key.argType].
     */
    private val _cache = HashMap<Kodein.Key, Factory<*, Any>>()

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

    override val bindings: Map<Kodein.Key, Factory<*, *>> = _map.bindings

    /**
     * The super type of this type.
     *
     * @receiver The type whose super type is needed.
     * @return The super type, or null if this type does not supports it.
     */
    private fun Type.superType(): Type? = when (this) {
        is Class<*> -> this.genericSuperclass
        is ParameterizedType -> this.rawType.superType()
        is KodeinWrappedType -> this.type.superType()
        else -> null
    }

    /**
     * The raw type of this type. Only if *different* from this type.
     *
     * E.g. the raw type of a `Class` (which is already a raw type) is `null`.
     *
     * @receiver The type whose super type is needed.
     * @return The super type, or null if this type does not supports it.
     */
    private fun Type.toRawType(): Type? = when (this) {
        is ParameterizedType -> this.rawType
        is KodeinWrappedType -> this.type.toRawType()
        else -> null
    }

    /**
     * Finds a factory from a key, either in the [_map] or in the [_cache].
     *
     * @param key The key associated to the factory that is requested.
     * @return The factory, or null if non is found in both maps.
     */
    private fun get(key: Kodein.Key) : Factory<*, Any>? {
        _map[key]?.let { return it }
        _cache[key]?.let { return it }
        return null
    }

    /**
     * Recursive function that tries to find a factory according to the provided key.
     *
     * 1. First, it of course tries with the [key] as is.
     * 2. Then, it tries with the [key] with it's [Kodein.Key.argType] set to the raw type if there is one.
     * 3. Then it goes back to 1, with the [key] with it's [Kodein.Key.argType] set to the super type if there is one.
     * 4. If finally a factory is found, it puts it in the cache associated to the original key, so it will be directly found next time.
     *
     * @param key The key to look for
     * @param cache Whether the function needs to cache the result if a result is found (only the original key should be cached).
     * @return The found factory, or null if none is found.
     */
    private fun _findFactoryOrNull(key: Kodein.Key, cache: Boolean) : Factory<*, Any>? {
        get(key)?.let { return it }

        val rawType = key.argType.toRawType()
        if (rawType != null) {
            get(Kodein.Key(key.bind, rawType))?.let {
                if (cache)
                    _cache[key] = it
                return it
            }
        }

        val argSuperType = key.argType.superType()
        if (argSuperType == null || argSuperType == Unit::class.java || argSuperType == Any::class.java)
            return null

        val found = _findFactoryOrNull(Kodein.Key(key.bind, argSuperType), false)
        if (cache && found != null)
            _cache[key] = found
        return found
    }

    override fun factoryOrNull(key: Kodein.Key): ((Any?) -> Any)? {
        val factory = _findFactoryOrNull(key, true) ?: return null
        _node?.check(key)
        @Suppress("UNCHECKED_CAST")
        return { arg -> (factory as Factory<Any?, Any>).getInstance(KodeinImpl(KodeinContainerImpl(_map, Node(key, _node))), key, arg) }
    }
}
