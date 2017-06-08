package com.github.salomonbrys.kodein

/**
 * An interface that contains a simple Type but is parameterized to enable type safety.
 *
 * @param T The type represented by this object.
 */
interface TypeToken<T> {

    /**
     * The simple (a.k.a. not fully qualified) name of the type represented by this TypeToken.
     */
    fun simpleDispString(): String

    /**
     * The fully qualified name of the type represented by this TypeToken.
     */
    fun fullDispString(): String

    /**
     * Checks that the type represented by this TypeToken is reified. Meaning that it is not or does not reference a `TypeVariable`.
     *
     * @param disp An object to print if the check fails. *For debug print only*.
     * @throws IllegalArgumentException If the type does contain a `TypeVariable`.
     */
    fun checkIsReified(disp: Any)

    /**
     * Returns the raw type of the type represented by this TypeToken if it is generic.
     * If the type is not generic, return null.
     */
    fun getRawIfGeneric(): TypeToken<T>?

    /**
     * Returns the raw type if the type represented by this TypeToken is generic and is entirely wildcard.
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
     * @return the raw type if the type represented by this TypeToken is generic and is entirely wildcard, otherwise null.
     */
    fun getRawIfWildcard(): TypeToken<T>?

    /**
     * Returns the parent type of the type represented by this TypeToken, if any.
     */
    fun getSuper(): TypeToken<in T>?
}

internal val UnitToken = erased<Unit>()
