package com.github.salomonbrys.kodein

import kotlin.reflect.KClass

class JSTypeToken<T>(val type: JsClass<*>) : TypeToken<T> {

    override fun simpleDispString() = type.name
    override fun fullDispString() = type.name

    override fun checkIsReified(disp: Any) {}
    override fun getRawIfGeneric() = null
    override fun getRawIfWildcard() = null
    override fun getSuper() = null

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

@Suppress("UNCHECKED_CAST", "UNCHECKED_CAST_TO_NATIVE_INTERFACE")
inline fun <reified T> erased(): TypeToken<T> {
    try {
        return JSTypeToken((T::class as KClass<*>).js as JsClass<*>)
    }
    catch (ex: Throwable) {
        throw IllegalArgumentException("Could not get KClass. Note that Kotlin does NOT support reflection over primitives.")
    }
}

fun <T: Any> TT(cls: JsClass<T>): TypeToken<T> = JSTypeToken(cls)

fun <T: Any> TTOf(obj: T): TypeToken<out T> = JSTypeToken(obj::class.js)
