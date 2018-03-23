package org.kodein.di

import org.kodein.di.bindings.ExternalSource

/**
 * Tree where the bindings and their factories are sorted & stored.
 */
interface KodeinTree {

    /**
     * An immutable view of the bindings map. *For inspection & debug*.
     */
    val bindings: BindingsMap

    /**
     * The external source that will be queried if no bindings are found for a given request.
     */
    val externalSource: ExternalSource?

    /**
     * Finds all keys and definitions that match the given key.
     *
     * If a "perfect" match is found (a definition that exactly matches the given key), only that definition is returned, unless [all] is true.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param overrideLevel 0 if looking for regular bindings, 1 or more if looking for bindings that have been overridden.
     * @return A list of keys and their definition.
     */
    fun <C, A, T : Any> find(key: Kodein.Key<C, A, T>, overrideLevel: Int = 0, all: Boolean = false): List<Pair<Kodein.Key<C, A, T>, KodeinDefinition<C, A, T>>>

    /**
     * Finds all keys and definitions that match the given specs.
     *
     * @return A list of keys and their definition.
     */
    fun find(search: SearchSpecs): List<Pair<Kodein.Key<*, *, *>, List<KodeinDefinition<*, *, *>>>>

    /**
     * Gets a List of definition for an exact key representing a binding and all its overrides.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The exact key to look for.
     * @return The binding and all it's overrides, or null if this key is not registered.
     */
    operator fun <C, A, T: Any> get(key: Kodein.Key<C, A, T>): List<KodeinDefinition<C, A, T>>?

}