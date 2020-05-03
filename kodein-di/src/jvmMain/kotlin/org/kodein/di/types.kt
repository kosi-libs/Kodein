package org.kodein.di

import org.kodein.type.TypeToken
import org.kodein.type.typeToken
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Class used to get a generic type at runtime.
 *
 * @param T The type to extract.
 * @see generic
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X)
abstract class TypeReference<T> {

    /**
     * Generic type, unwrapped.
     */
    val superType: Type = (javaClass.genericSuperclass as? ParameterizedType ?: throw RuntimeException("Invalid TypeToken; must specify type parameters")).actualTypeArguments[0]
}

/**
 * Function used to get a generic type at runtime.
 *
 * @param T The type to get.
 * @return The type object representing `T`.
 */
@Suppress("UNCHECKED_CAST")
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("org.kodein.type.generic<T>()"))
inline fun <reified T : Any> generic(): TypeToken<T> = org.kodein.type.generic<T>()

/**
 * Gives a [TypeToken] representing the given `Class`.
 */
fun <T> TT(cls: Class<T>): TypeToken<T> = org.kodein.type.erased(cls)

/**
 * Gives a [TypeToken] representing the given type.
 */
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("typeToken(type)", "org.kodein.type"))
fun TT(type: Type): TypeToken<*> = typeToken(type)

/**
 * Gives a [TypeToken] representing the given [TypeReference].
 */
@Suppress("UNCHECKED_CAST")
@Deprecated(DEPRECATED_ERASED_GENERIC_7X, ReplaceWith("typeToken(ref.superType)", "org.kodein.type"))
fun <T> TT(ref: TypeReference<T>): TypeToken<T> = typeToken(ref.superType) as TypeToken<T>