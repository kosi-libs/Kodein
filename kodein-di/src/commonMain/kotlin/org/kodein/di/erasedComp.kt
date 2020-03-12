package org.kodein.di

import org.kodein.type.erasedComp
import org.kodein.type.generic

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Set<String>`, use `erasedComp1<Set<String>, String>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("erasedComp()", "org.kodein.type"), DeprecationLevel.ERROR)
inline fun <reified T : Any, reified A1 : Any> erasedComp1() = Any()

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Map<Int, String>`, use `erasedComp2<Map<Int, String>, Int, String>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The first type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("erasedComp()", "org.kodein.type"), DeprecationLevel.ERROR)
inline fun <reified T : Any, reified A1 : Any, reified A2 : Any> erasedComp2() = Any()

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 * @param A3 The third type parameter of the main type.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("erasedComp()", "org.kodein.type"), DeprecationLevel.ERROR)
inline fun <reified T : Any, reified A1 : Any, reified A2 : Any, reified A3 : Any> erasedComp3() = Any()

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 * @param A3 The third type parameter of the main type.
 * @param A4 The fourth type parameter of the main type.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("erasedComp()", "org.kodein.type"), DeprecationLevel.ERROR)
inline fun <reified T : Any, reified A1 : Any, reified A2 : Any, reified A3 : Any, reified A4 : Any> erasedComp4() = Any()

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Five. Generic. Freaking. Parameters!
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 * @param A3 The third type parameter of the main type.
 * @param A4 The fourth type parameter of the main type.
 * @param A5 The fifth type parameter of the main type.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("erasedComp()", "org.kodein.type"), DeprecationLevel.ERROR)
inline fun <reified T : Any, reified A1 : Any, reified A2 : Any, reified A3 : Any, reified A4 : Any, reified A5 : Any> erasedComp5() = Any()

/**
 * Creates a [CompositeTypeToken] that defines a `Set<T>`.
 *
 * @param T The parameter type of the Set.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("erasedComp(Set::class, generic<T>())", "org.kodein.type"))
inline fun <reified T : Any> erasedSet() = erasedComp(Set::class, generic<T>())

/**
 * Creates a [CompositeTypeToken] that defines a `List<T>`.
 *
 * @param T The parameter type of the List.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("erasedComp(List::class, generic<T>())", "org.kodein.type"))
inline fun <reified T : Any> erasedList() = erasedComp(List::class, generic<T>())

/**
 * Creates a [CompositeTypeToken] that defines a `Map<K, V>`.
 *
 * @param K The key parameter type of the Map.
 * @param V The value parameter type of the Map.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("erasedComp(Map::class, generic<K>(), generic<V>())", "org.kodein.type"))
inline fun <reified K : Any, reified V : Any> erasedMap() = erasedComp(Map::class, generic<K>(), generic<V>())
