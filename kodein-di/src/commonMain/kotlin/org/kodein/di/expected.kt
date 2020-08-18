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
@Deprecated("Has been reimplemented in Kodein-Type", ReplaceWith("erasedOf(obj)", "org.kodein.type"))
public fun <T: Any> TTOf(obj: T): TypeToken<out T> = erasedOf(obj)

/** @suppress */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
public expect annotation class Volatile()
