package org.kodein.di

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
    override fun getSuper() = emptyList<TypeToken<*>>()
    override fun getGenericParameters() = emptyArray<TypeToken<*>>()
    override fun isAssignableFrom(typeToken: TypeToken<*>): Boolean {
        if (this == typeToken)
            return true
        if (type == Any::class.js)
            return true
        return false
    }

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
actual inline fun <reified T> erased(): TypeToken<T> {
    try {
        @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
        return JSTypeToken((T::class as KClass<*>).js as JsClass<*>)
    }
    catch (ex: Throwable) {
        throw IllegalArgumentException("Could not get KClass. Note that Kotlin does NOT support reflection over primitives.")
    }
}

/**
 * Gives a [TypeToken] representing the given `Class`.
 */
fun <T: Any> TT(cls: JsClass<T>): TypeToken<T> = JSTypeToken(cls)

/**
 * Gives a [TypeToken] representing the given `KClass`.
 */
fun <T: Any> TT(cls: KClass<T>): TypeToken<T> = TT(cls.js)

actual fun <T: Any> TTOf(obj: T): TypeToken<out T> = JSTypeToken(obj::class.js)
