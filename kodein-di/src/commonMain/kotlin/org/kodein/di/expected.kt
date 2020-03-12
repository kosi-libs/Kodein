package org.kodein.di

import org.kodein.type.TypeToken
import org.kodein.type.erasedOf
import kotlin.reflect.KClass

/**
 * Returns a type token representing the actual type of the given argument.
 *
 * @param T The type, or a parent type of, that the returned TypeToken will represent.
 * @param obj An object whose actual type will be extract.
 */
@Suppress("FunctionName")
fun <T: Any> TTOf(obj: T): TypeToken<out T> = erasedOf(obj)

/**
 * Gives a [TypeToken] representing the given class.
 */
fun <T: Any> TT(cls: KClass<T>): TypeToken<T> = org.kodein.type.erased(cls)

/**
 * Returns an **erased** type representation of the given type.
 *
 * - `erased<Whatever>()` -> `TypeToken<Whatever>`
 * - `erased<List<Whatever>>()` -> `TypeToken<List<*>>`
 *
 * @param T The type to represent erased.
 */
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("generic()", "org.kodein.type"))
inline fun <reified T : Any> erased(): TypeToken<T> = org.kodein.type.generic()

/** @suppress */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
expect annotation class Volatile()
