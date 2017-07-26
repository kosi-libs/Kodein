package com.github.salomonbrys.kodein

import java.util.*

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

    fun getRaw(): TypeToken<T>

    fun isGeneric(): Boolean

    /**
     * Returns whether the type represented by this TypeToken is generic and is entirely wildcard.
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
     * @return whether the type represented by this TypeToken is generic and is entirely wildcard, otherwise null.
     */
    fun isWildcard(): Boolean

    /**
     * Returns the parent type of the type represented by this TypeToken, if any.
     */
    fun getSuper(): TypeToken<in T>?
}

class CompositeTypeToken<T>(val main: TypeToken<T>, vararg val params: TypeToken<*>) : TypeToken<T> {

    init {
        if (params.isEmpty())
            throw IllegalStateException("CompositeTypeToken must be given at least one type parameter")
    }

    override fun simpleDispString() = "${main.simpleDispString()}<${params.joinToString(", ")}>"

    override fun fullDispString() = "${main.fullDispString()}<${params.joinToString(", ")}>"

    override fun checkIsReified(disp: Any) {
        main.checkIsReified(disp)
        params.forEach { it.checkIsReified(disp) }
    }

    override fun getRaw() = main.getRaw()

    override fun isGeneric() = true

    override fun isWildcard() = false

    override fun getSuper() = main.getSuper()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is CompositeTypeToken<*>) return false

        return main == other.main && Arrays.equals(params, other.params)
    }

    override fun hashCode() = 31 * main.hashCode() + Arrays.hashCode(params)
}

internal val UnitToken = erased<Unit>()
