@file:Suppress("UNCHECKED_CAST")

package org.kodein.di

import org.kodein.type.TypeToken
import org.kodein.type.erasedComp
import org.kodein.type.generic

/**
 * Creates a [CompositeTypeToken] that defines a `Set<T>`.
 *
 * @param T The parameter type of the Set.
 */
inline fun <reified T : Any> erasedSet() = erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>

/**
 * Creates a [CompositeTypeToken] that defines a `List<T>`.
 *
 * @param T The parameter type of the List.
 */
inline fun <reified T : Any> erasedList() = erasedComp(List::class, generic<T>()) as TypeToken<List<T>>

/**
 * Creates a [CompositeTypeToken] that defines a `Map<K, V>`.
 *
 * @param K The key parameter type of the Map.
 * @param V The value parameter type of the Map.
 */
inline fun <reified K : Any, reified V : Any> erasedMap() = erasedComp(Map::class, generic<K>(), generic<V>()) as TypeToken<Map<K, V>>
