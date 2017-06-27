package com.github.salomonbrys.kodein

import kotlin.reflect.KClass

@PublishedApi
internal class KClassTypeToken<T>(val type: KClass<*>) : TypeToken<T> {

    override fun simpleDispString() = type.simpleName
    override fun fullDispString() = type.qualifiedName

    override fun checkIsReified(disp: Any) {}
    override fun getRawIfGeneric() = null
    override fun getRawIfWildcard() = null
    override fun getSuper() = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is KClassTypeToken<*>) return false

        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}

/**
 * Function used to get a TypeToken representing the provided type **being erased**.
 *
 * @param T The type to get.
 * @return The type object representing `T`.
 */
@Suppress("UNCHECKED_CAST", "UNCHECKED_CAST_TO_NATIVE_INTERFACE")
inline fun <reified T> erased(): TypeToken<T> {
    try {
        return KClassTypeToken(T::class)
    }
    catch (ex: Throwable) {
        throw IllegalArgumentException("Could not get KClass.")
    }
}

/**
 * Gives a [TypeToken] representing the given class.
 */
fun <T: Any> TT(cls: KClass<T>): TypeToken<T> = KClassTypeToken(cls)

/**
 * Gives a [TypeToken] representing the *erased* type of the given object.
 */
fun <T: Any> TTOf(obj: T): TypeToken<out T> = KClassTypeToken(obj::class)
