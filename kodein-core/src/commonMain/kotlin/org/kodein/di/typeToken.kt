package org.kodein.di

/**
 * An interface that contains a simple Type but is parameterized to enable type safety.
 *
 * @param T The type represented by this object.
 */
interface TypeToken<T> {

    /**
     * @return The simple (a.k.a. not fully qualified) name of the type represented by this TypeToken.
     */
    fun simpleDispString(): String

    /**
     * @return The simple (a.k.a. not fully qualified) erased (a.k.a without generic parameters) name of the type represented by this TypeToken.
     */
    fun simpleErasedDispString(): String

    /**
     * @return The fully qualified name of the type represented by this TypeToken.
     */
    fun fullDispString(): String

    /**
     * @return The fully qualified erased (a.k.a without generic parameters) name of the type represented by this TypeToken.
     */
    fun fullErasedDispString(): String

    /**
     * Checks that the type represented by this TypeToken is reified. Meaning that it is not or does not reference a `TypeVariable`.
     *
     * @param disp An object to print if the check fails. *For debug print only*.
     * @throws IllegalArgumentException If the type does contain a `TypeVariable`.
     */
    fun checkIsReified(disp: Any)

    /**
     * @return the raw type represented by this type.
     *   If this type is not generic, than it's raw type is itself.
     */
    fun getRaw(): TypeToken<T>?

    /**
     * @return Whether the type represented by this TypeToken is generic.
     */
    fun isGeneric(): Boolean

    /**
     * @return A list of generic parameters (empty if this types does not have generic parameters).
     */
    fun getGenericParameters(): Array<out TypeToken<*>>

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
     * @return Whether the type represented by this TypeToken is generic and is entirely wildcard, otherwise null.
     */
    fun isWildcard(): Boolean

    /**
     * Returns the parent type of the type represented by this TypeToken, if any.
     */
    fun getSuper(): List<TypeToken<*>>

    /**
     * Determines if the type represented by this type object is either the same as, or is a superclass or superinterface of, the type represented by the specified type parameter.
     */
    fun isAssignableFrom(typeToken: TypeToken<*>): Boolean {
        if (this == typeToken)
            return true

        val raw = getRaw()
        if (raw != null && raw == typeToken.getRaw()) {
            val thisParams = getGenericParameters()
            if (thisParams.isEmpty())
                return true
            val tokenParams = typeToken.getGenericParameters()
            thisParams.forEachIndexed { index, thisParam ->
                val tokenParam = tokenParams[index]
                if (!thisParam.isAssignableFrom(tokenParam))
                    return false
            }
            return true
        }

        return typeToken.getSuper().any { isAssignableFrom(it) }
    }
}

/**
 * A composite type token represents a generic class in an erased manner.
 *
 * For example, the type `Map<String, List<String>>` can be represented as:
 *
 * ```
 * CompositeTypeToken(erased<Map<*, *>>(), erased<String>(), CompositeTypeToken(erased<List<*>(), erased<String>()))
 * ```
 *
 * Note that you should rather use the [erasedComp1], [erasedComp2] or [erasedComp3] functions to create a composite type token.
 *
 * @param T The main type represented by this type token.
 * @property main The main type represented by this type token.
 * @property params The type parameters of the main type.
 */
class CompositeTypeToken<T>(val main: TypeToken<T>, vararg val params: TypeToken<*>) : TypeToken<T> {

    init {
        if (params.isEmpty())
            throw IllegalStateException("CompositeTypeToken must be given at least one type parameter")
    }

    override fun simpleDispString() = "${main.simpleErasedDispString()}<${params.joinToString(", ") { it.simpleDispString() }}>"

    override fun simpleErasedDispString() = main.simpleErasedDispString()

    override fun fullDispString() = "${main.fullErasedDispString()}<${params.joinToString(", ")  { it.fullDispString() }}>"

    override fun fullErasedDispString() = main.fullErasedDispString()

    override fun checkIsReified(disp: Any) {
        main.checkIsReified(disp)
        params.forEach { it.checkIsReified(disp) }
    }

    override fun getRaw() = main.getRaw()

    override fun isGeneric() = true

    override fun isWildcard() = false

    override fun getSuper() = main.getSuper()

    override fun getGenericParameters() = params

    /** @suppress */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (other !is CompositeTypeToken<*>) return false

        return main == other.main && params.contentEquals(other.params)
    }

    /** @suppress */
    override fun hashCode() = 31 * main.hashCode() + params.contentHashCode()
}

/**
 * A simple type token that represents the type `Unit`.
 */
val UnitToken = erased<Unit>()

/**
 * A simple type token that represents the type `Any?`.
 */
val AnyToken = erased<Any?>()
