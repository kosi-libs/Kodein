package org.kodein.di

import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.bindings.ExternalSource

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DITree"), DeprecationLevel.ERROR)
typealias KodeinTree = DITree

/**
 * Tree where the bindings and their factories are sorted & stored.
 */
interface DITree {

    /**
     * An immutable view of the bindings map. *For inspection & debug*.
     */
    val bindings: BindingsMap

    val registeredTranslators: List<ContextTranslator<*, *>>

    /**
     * The external sources that will be queried if no bindings are found for a given request.
     */
    val externalSources: List<ExternalSource>


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
    fun <C : Any, A, T : Any> find(key: DI.Key<C, A, T>, overrideLevel: Int = 0, all: Boolean = false): List<Triple<DI.Key<Any, A, T>, DIDefinition<Any, A, T>, ContextTranslator<C, Any>?>>

    /**
     * Finds all keys and definitions that match the given specs.
     *
     * @return A list of keys and their definition.
     */
    fun find(search: SearchSpecs): List<Triple<DI.Key<*, *, *>, List<DIDefinition<*, *, *>>, ContextTranslator<*, *>?>>

    /**
     * Gets a List of definition for an exact key representing a binding and all its overrides.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The exact key to look for.
     * @return The binding and all it's overrides, or null if this key is not registered.
     */
    operator fun <C : Any, A, T: Any> get(key: DI.Key<C, A, T>): Triple<DI.Key<Any, A, T>, List<DIDefinition<Any, A, T>>, ContextTranslator<C, Any>?>?

}