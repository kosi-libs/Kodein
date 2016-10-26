package com.github.salomonbrys.kodein.internal

import com.github.salomonbrys.kodein.Factory
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinWrappedType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.WildcardType
import java.util.*

/**
 * Behaves like a `Map<Kodein.Key, Factory<*, Any>>`, but with some extra caching.
 *
 * Each time a new entry is added, the entry is put as-is in the [_map].
 * If the keys's [Kodein.Key.argType] is a `ParameterizedType` that is entirely wildcard, then the [_raws] map is given an entry with it's raw type.
 * For example, when adding a factory with a key's argType being `List<*>`, the same factory will be added to the [_raws] map associated with a slightly different key: argType being the class `List`.
 * However, when adding a factory with a key's argType being `List<String>`, nothing will be added to the [_raws] map.
 */
internal class CMap {
    /**
     * The regular map associating keys to factories
     */
    private val _map  = HashMap<Kodein.Key, Factory<*, Any>>()

    /**
     * A map that contains "raws" version of keys in [_map] whose [Kodein.Key.argType] are `ParameterizedType`.
     */
    private val _raws = HashMap<Kodein.Key, Factory<*, Any>>()

    /**
     * Get the factory associated with the given key, either from the regular map, or from the raw pre-cache.
     *
     * @param key The key of the factory to retrieve.
     * @return The factory, or null if none was found.
     */
    operator fun get(key: Kodein.Key) : Factory<*, Any>? {
        _map[key]?.let { return it }
        _raws[key]?.let { return it }
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
    infix operator fun contains(key: Kodein.Key) = _map.contains(key)

    /**
     * Returns whether the ParameterizedType is entirely wildcard.
     *
     * Examples:
     *
     * - `List<*>`: true
     * - `List<String>`: false
     * - `Map<*, *>`: true
     * - `Map<String, *>`: false
     * - `Map<*, String>`: false
     * - `Map<String, String>`: very false!
     *
     * @receiver The wildcard to test
     * @return whether or not the ParameterizedType is entirely wildcard.
     */
    private fun ParameterizedType.isWildcard() : Boolean {
        var hasWildCard = false
        var hasSpecific = false

        val cls = this.rawType as Class<*>
        cls.typeParameters.forEachIndexed { i, variable ->
            val argument = actualTypeArguments[i]

            if (argument is WildcardType && variable.bounds.any { it in argument.upperBounds })
                hasWildCard = true
            else
                hasSpecific = true
        }

        return hasWildCard && !hasSpecific
    }

    /**
     * Adds a factory to the map.
     *
     * Adds the [key] and [factory] as-is into the [_map].
     * If the [key]'s [Kodein.Key.argType] is a `ParameterizedType` that is entirely wildcard, then the [_raws] map is given an entry with it's raw type.
     *
     * @param key The key to add.
     * @param key The associated factory to add.
     */
    operator fun set(key: Kodein.Key, factory: Factory<*, Any>) {
        _map[key] = factory

        val realArgType = if (key.argType is KodeinWrappedType) key.argType.type else key.argType
        if (realArgType is ParameterizedType && realArgType.isWildcard())
            _raws[Kodein.Key(key.bind, realArgType.rawType)] = factory
    }

    /**
     * Copies all of the mappings from the specified map to this map.
     *
     * @param bindings mappings to be stored in this map.
     */
    fun putAll(bindings: Map<Kodein.Key, Factory<*, *>>) {
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
    val bindings: Map<Kodein.Key, Factory<*, *>> = ImmutableMapView(_map)
}