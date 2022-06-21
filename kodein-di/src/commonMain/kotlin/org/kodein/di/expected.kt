package org.kodein.di

import org.kodein.type.TypeToken
import org.kodein.type.erasedOf
import kotlin.reflect.KClass

/** @suppress */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
public expect annotation class Volatile()
