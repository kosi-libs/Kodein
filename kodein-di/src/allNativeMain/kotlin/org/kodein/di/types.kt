package org.kodein.di

import kotlin.reflect.KClass

@PublishedApi
internal class NativeTypeToken<T>(type: KClass<*>) : AbstractKClassTypeToken<T>(type) {

    override fun simpleErasedDispString(): String = type.simpleName ?: "{Unknown}"

    override fun fullErasedDispString() = type.qualifiedName ?: "{Unknown}"

}

/**
 * Function used to get a TypeToken representing the provided type **being erased**.
 *
 * @param T The type to get.
 * @return The type object representing `T`.
 */
@Suppress("UNCHECKED_CAST", "UNCHECKED_CAST_TO_NATIVE_INTERFACE")
actual inline fun <reified T : Any> erased(): TypeToken<T> = NativeTypeToken(T::class)

/**
 * Gives a [TypeToken] representing the given class.
 */
fun <T: Any> TT(cls: KClass<T>): TypeToken<T> = NativeTypeToken(cls)

/**
 * Gives a [TypeToken] representing the *erased* type of the given object.
 */
actual fun <T: Any> TTOf(obj: T): TypeToken<out T> = NativeTypeToken(obj::class)
