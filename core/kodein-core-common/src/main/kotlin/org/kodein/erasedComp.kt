package org.kodein

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Set<String>`, use `erasedComp1<Set<String>, String>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 */
//inline fun <reified T, reified A1> erasedComp1() = CompositeTypeToken(erased<T>(), erased<A1>())
inline fun <reified T, reified A1> erasedComp1(): CompositeTypeToken<T> {
    val paramsList = ArrayList<TypeToken<*>>(1)
    paramsList.add(erased<A1>())
    return CompositeTypeToken(erased(), paramsList.toTypedArray())
}

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Map<Int, String>`, use `erasedComp2<Map<Int, String>, Int, String>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The first type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 */
inline fun <reified T, reified A1, reified A2> erasedComp2(): CompositeTypeToken<T> {
    val paramsList = ArrayList<TypeToken<*>>(2)
    paramsList.add(erased<A1>())
    paramsList.add(erased<A2>())
    return CompositeTypeToken(erased(), paramsList.toTypedArray())
}

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Triple<Int, String, Int>`, use `erasedComp3<Triple<Int, String, Int>, Int, String, Int>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 * @param A3 The third type parameter of the main type.
 */
inline fun <reified T, reified A1, reified A2, reified A3> erasedComp3(): CompositeTypeToken<T> {
    val paramsList = ArrayList<TypeToken<*>>(3)
    paramsList.add(erased<A1>())
    paramsList.add(erased<A2>())
    paramsList.add(erased<A3>())
    return CompositeTypeToken(erased(), paramsList.toTypedArray())
}

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Triple<Int, String, Int>`, use `erasedComp3<Triple<Int, String, Int>, Int, String, Int>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 * @param A3 The third type parameter of the main type.
 * @param A3 The fourth type parameter of the main type.
 */
inline fun <reified T, reified A1, reified A2, reified A3, reified A4> erasedComp4(): CompositeTypeToken<T> {
    val paramsList = ArrayList<TypeToken<*>>(4)
    paramsList.add(erased<A1>())
    paramsList.add(erased<A2>())
    paramsList.add(erased<A3>())
    paramsList.add(erased<A4>())
    return CompositeTypeToken(erased(), paramsList.toTypedArray())
}

/**
 * Creates a [CompositeTypeToken] for an erased generic type.
 *
 * Example: to create an erased type token representing `Triple<Int, String, Int>`, use `erasedComp3<Triple<Int, String, Int>, Int, String, Int>()`.
 *
 * @param T The main type represented by this type token.
 * @param A1 The type parameter of the main type.
 * @param A2 The second type parameter of the main type.
 * @param A3 The third type parameter of the main type.
 * @param A3 The fourth type parameter of the main type.
 */
inline fun <reified T, reified A1, reified A2, reified A3, reified A4, reified A5> erasedComp5(): CompositeTypeToken<T> {
    val paramsList = ArrayList<TypeToken<*>>(5)
    paramsList.add(erased<A1>())
    paramsList.add(erased<A2>())
    paramsList.add(erased<A3>())
    paramsList.add(erased<A4>())
    paramsList.add(erased<A5>())
    return CompositeTypeToken(erased(), paramsList.toTypedArray())
}

/**
 * Creates a [CompositeTypeToken] that defines a `Set<T>`.
 *
 * @param T The parameter type of the set.
 */
inline fun <reified T> erasedSet() = erasedComp1<Set<T>, T>()
