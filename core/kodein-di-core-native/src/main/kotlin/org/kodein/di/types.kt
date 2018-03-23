package org.kodein.di

import kotlin.reflect.KClass

@PublishedApi
internal class NativeTypeToken<T>(val type: KClass<*>) : TypeToken<T> {

    override fun simpleDispString() = type.simpleName ?: "{Unknown}"
    override fun simpleErasedDispString() = type.simpleName ?: "{Unknown}"
    override fun fullDispString() = type.qualifiedName ?: "{Unknown}"
    override fun fullErasedDispString() = type.qualifiedName ?: "{Unknown}"

    override fun checkIsReified(disp: Any) {}
    override fun getRaw() = this
    override fun isGeneric() = false
    override fun isWildcard() = false
    override fun getSuper() = emptyList<TypeToken<*>>()
    override fun getGenericParameters() = emptyArray<TypeToken<*>>()
    override fun isAssignableFrom(typeToken: TypeToken<*>): Boolean {
        if (this == typeToken)
            return true
        if (type == Any::class)
            return true
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NativeTypeToken<*>) return false

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
actual inline fun <reified T> erased(): TypeToken<T> = NativeTypeToken(T::class)

/**
 * Gives a [TypeToken] representing the given class.
 */
fun <T: Any> TT(cls: KClass<T>): TypeToken<T> = NativeTypeToken(cls)

/**
 * Gives a [TypeToken] representing the *erased* type of the given object.
 */
actual fun <T: Any> TTOf(obj: T): TypeToken<out T> = NativeTypeToken(obj::class)
