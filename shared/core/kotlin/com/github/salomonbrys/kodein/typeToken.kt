package com.github.salomonbrys.kodein

/**
 * An interface that contains a simple Type but is parameterized to enable type safety.
 *
 * @param T The type represented by this object.
 */
interface TypeToken<T> {
    fun simpleDispString(): String
    fun fullDispString(): String

    /**
     * Checks that a type is reified. Meaning that it is not or does not reference a [TypeVariable].
     *
     * @param disp An object to print if the check fails. *For debug print only*.
     * @param type The type to check.
     * @throws IllegalArgumentException If the type does contain a [TypeVariable].
     */
    fun checkIsReified(disp: Any)

    fun getRawIfGeneric(): TypeToken<T>?

    /**
     * Returns the raw type if this type is generic and is entirely wildcard.
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
     * @return the raw type if this type is generic and is entirely wildcard, otherwise null.
     */
    fun getRawIfWildcard(): TypeToken<T>?

    fun getSuper(): TypeToken<in T>?
}

val UnitToken = erased<Unit>()
