package org.kodein.internal

import org.kodein.bindings.KodeinBinding
import org.kodein.Kodein

/**
 * Behaves like a `Map<Kodein.Key, Binding<*, Any>>`, but with some extra caching.
 *
 * Each time a new entry is added, the entry is put as-is in the [_map].
 * If the keys's [Kodein.Key.argType] is a `ParameterizedType` that is entirely wildcard, then the [_raws] map is given an entry with it's raw type.
 * For example, when adding a factory with a key's argType being `List<*>`, the same factory will be added to the [_raws] map associated with a slightly different key: argType being the class `List`.
 * However, when adding a factory with a key's argType being `List<String>`, nothing will be added to the [_raws] map.
 */
internal class BindingsMap {
    /**
     * The regular map associating keys to factories
     */
    private val _map  = HashMap<Kodein.Key<*, *>, KodeinBinding<*, *>>()

    // Inverse order !
    private val _overrides  = HashMap<Kodein.Key<*, *>, ArrayList<KodeinBinding<*, *>>>()

    /**
     * A map that contains "raws" version of keys in [_map] whose [Kodein.Key.argType] are `ParameterizedType`.
     */
    private val _raws = HashMap<Kodein.Key<*, *>, KodeinBinding<*, *>>()

    /**
     * Get the factory associated with the given key, either from the regular map, or from the raw pre-cache.
     *
     * @param key The key of the factory to retrieve.
     * @return The factory, or null if none was found.
     */
    operator fun get(key: Kodein.Key<*, *>) : KodeinBinding<*, *>? {
        _map[key]?.let { return it }
        _raws[key]?.let { return it }
        return null
    }

    fun getOverride(key: Kodein.Key<*, *>, overrideLevel: Int) : KodeinBinding<*, *>? {
        _overrides[key]?.let {
            val level = it.size - overrideLevel - 1
            if (level < 0)
                return null
            return it.getOrNull(level)
        }
        return null
    }

    /**
     * Returns whether a key can be found in the [_map].
     *
     * Because [_raws] is implicitly created, it is not looked into.
     *
     * @param key The key to look for.
     * @return Whether or not the key is in the [_map].
     */
    infix operator fun contains(key: Kodein.Key<*, *>) = _map.contains(key)

    /**
     * Adds a binding to the map.
     *
     * Adds the [key] and [binding] as-is into the [_map].
     * If the [key]'s [Kodein.Key.argType] is a `ParameterizedType` that is entirely wildcard, then the [_raws] map is given an entry with it's raw type.
     *
     * @param key The key to add.
     * @param key The associated binding to add.
     */
    operator fun set(key: Kodein.Key<*, *>, binding: KodeinBinding<*, *>) {
        val overridden = _map.put(key, binding)
        if (overridden != null)
            _overrides.getOrPut(key, { ArrayList() }).add(overridden)

        if (key.argType.isWildcard())
            _raws[Kodein.Key(key.bind, key.argType.getRaw())] = binding
    }

    /**
     * Copies all of the mappings from the specified map to this map.
     *
     * @param bindings mappings to be stored in this map.
     */
    fun putAll(bindings: Map<Kodein.Key<*, *>, KodeinBinding<*, *>>) {
        for ((key, factory) in bindings)
            set(key, factory)
    }

    /**
     * Wrapper that makes a map truly immutable, even in Java (Kotlin will throw an exception when trying to mutate).
     */
    private class ImmutableMapView<K : Any, out V : Any>(private val _base: Map<K, V>) : Map<K, V> by _base

    /**
     * An immutable view of the [_map].
     */
    val bindings: Map<Kodein.Key<*, *>, KodeinBinding<*, *>> = ImmutableMapView(_map)

    val overrides: Map<Kodein.Key<*, *>, List<KodeinBinding<*, *>>> = ImmutableMapView(_overrides)
}