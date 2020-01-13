package org.kodein.di

/**
 * Returns a type token representing the actual type of the given argument.
 *
 * @param T The type, or a parent type of, that the returned TypeToken will represent.
 * @param obj An object whose actual type will be extract.
 */
@Suppress("FunctionName")
expect fun <T: Any> TTOf(obj: T): TypeToken<out T>

/**
 * Returns an **erased** type representation of the given type.
 *
 * - `erased<Whatever>()` -> `TypeToken<Whatever>`
 * - `erased<List<Whatever>>()` -> `TypeToken<List<*>>`
 *
 * @param T The type to represent erased.
 */
expect inline fun <reified T> erased(): TypeToken<T>

/** @suppress */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
expect annotation class Volatile()
