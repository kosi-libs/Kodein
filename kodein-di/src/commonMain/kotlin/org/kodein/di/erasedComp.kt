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
public inline fun <reified T : Any> erasedSet(): TypeToken<Set<T>> = erasedComp(Set::class, generic<T>()) as TypeToken<Set<T>>

/**
 * Creates a [CompositeTypeToken] that defines a `List<T>`.
 *
 * @param T The parameter type of the List.
 */
public inline fun <reified T : Any> erasedList(): TypeToken<List<T>> = erasedComp(List::class, generic<T>()) as TypeToken<List<T>>

/**
 * Creates a [CompositeTypeToken] that defines a `Map<K, V>`.
 *
 * @param K The key parameter type of the Map.
 * @param V The value parameter type of the Map.
 */
public inline fun <reified K : Any, reified V : Any> erasedMap(): TypeToken<Map<K, V>> = erasedComp(Map::class, generic<K>(), generic<V>()) as TypeToken<Map<K, V>>
