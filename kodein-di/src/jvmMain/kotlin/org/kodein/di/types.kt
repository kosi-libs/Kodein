package org.kodein.di

import org.kodein.type.TypeToken
import org.kodein.type.typeToken
import java.lang.reflect.*
import kotlin.reflect.KClass

/**
 * Class used to get a generic type at runtime.
 *
 * @param T The type to extract.
 * @see generic
 */
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
@Deprecated(DEPRECATED_KODEIN_7X)
inline fun <reified T : Any> generic(): TypeToken<T> = org.kodein.type.generic<T>()

/**
 * Gives a [TypeToken] representing the given `Class`.
 */
fun <T> TT(cls: Class<T>): TypeToken<T> = org.kodein.type.erased(cls)

/**
 * Gives a [TypeToken] representing the given type.
 */
fun TT(type: Type): TypeToken<*> = typeToken(type)

/**
 * Gives a [TypeToken] representing the given [TypeReference].
 */
@Suppress("UNCHECKED_CAST")
fun <T> TT(ref: TypeReference<T>): TypeToken<T> = TT(ref.superType) as TypeToken<T>