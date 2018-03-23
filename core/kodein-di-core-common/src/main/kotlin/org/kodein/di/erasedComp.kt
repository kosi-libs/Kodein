package org.kodein.di

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Set<String>`, use `erasedComp1<Set<String>, String>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 */
inline fun <reified T, reified A1> erasedComp1() = CompositeTypeToken(erased<T>(), erased<A1>())

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Map<Int, String>`, use `erasedComp2<Map<Int, String>, Int, String>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The first type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 */
inline fun <reified T, reified A1, reified A2> erasedComp2() = CompositeTypeToken(erased<T>(), erased<A1>(), erased<A2>())

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 * @param A3 The third type parameter of the main type.
 */
inline fun <reified T, reified A1, reified A2, reified A3> erasedComp3() = CompositeTypeToken(erased<T>(), erased<A1>(), erased<A2>(), erased<A3>())

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 * @param A3 The third type parameter of the main type.
 * @param A4 The fourth type parameter of the main type.
 */
inline fun <reified T, reified A1, reified A2, reified A3, reified A4> erasedComp4() = CompositeTypeToken(erased<T>(), erased<A1>(), erased<A2>(), erased<A3>(), erased<A4>())

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
inline fun <reified T, reified A1, reified A2, reified A3, reified A4, reified A5> erasedComp5() = CompositeTypeToken(erased<T>(), erased<A1>(), erased<A2>(), erased<A3>(), erased<A4>(), erased<A5>())

/**
 * Creates a [CompositeTypeToken] that defines a `Set<T>`.
 *
 * @param T The parameter type of the Set.
 */
inline fun <reified T> erasedSet() = erasedComp1<Set<T>, T>()

/**
 * Creates a [CompositeTypeToken] that defines a `List<T>`.
 *
 * @param T The parameter type of the List.
 */
inline fun <reified T> erasedList() = erasedComp1<List<T>, T>()

/**
 * Creates a [CompositeTypeToken] that defines a `Map<K, V>`.
 *
 * @param K The key parameter type of the Map.
 * @param V The value parameter type of the Map.
 */
inline fun <reified K, reified V> erasedMap() = erasedComp2<Map<K, V>, K, V>()
