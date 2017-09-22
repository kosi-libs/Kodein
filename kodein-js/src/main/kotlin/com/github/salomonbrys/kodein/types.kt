package com.github.salomonbrys.kodein

import kotlin.reflect.KClass

@PublishedApi
internal class JSTypeToken<T>(val type: JsClass<*>) : TypeToken<T> {

    override fun simpleDispString() = type.name
    override fun simpleErasedDispString() = type.name
    override fun fullDispString() = type.name
    override fun fullErasedDispString() = type.name

    override fun checkIsReified(disp: Any) {}
    override fun getRaw() = this
    override fun isGeneric() = false
    override fun isWildcard() = false
    override fun getSuper() = null
    override fun getInterfaces() = emptyList<TypeToken<*>>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JSTypeToken<*>) return false

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
        return JSTypeToken((T::class as KClass<*>).js as JsClass<*>)
    }
    catch (ex: Throwable) {
        throw IllegalArgumentException("Could not get KClass. Note that Kotlin does NOT support reflection over primitives.")
    }
}

/**
 * Gives a [TypeToken] representing the given class.
 */
fun <T: Any> TT(cls: JsClass<T>): TypeToken<T> = JSTypeToken(cls)

fun <T: Any> TT(cls: KClass<T>): TypeToken<T> = TT(cls.js)

/**
 * Gives a [TypeToken] representing the *erased* type of the given object.
 */
fun <T: Any> TTOf(obj: T): TypeToken<out T> = JSTypeToken(obj::class.js)
