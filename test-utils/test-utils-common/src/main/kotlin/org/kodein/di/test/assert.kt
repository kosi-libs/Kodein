package org.kodein.di.test

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

fun <T> assertSame(expected: T, actual: T, message: String? = null) =
        assertTrue(expected === actual, message ?: "Expected same <$expected>, actual <$actual>.")

fun <T> assertNotSame(illegal: T, actual: T, message: String? = null) =
        assertTrue(illegal !== actual, message ?: "Illegal same <$illegal>.")

@Suppress("NOTHING_TO_INLINE")
inline fun assertAllNull(vararg values: Any?, message: String? = null) = values.forEach { assertNull(it, message) }

@Suppress("NOTHING_TO_INLINE")
inline fun assertAllNotNull(vararg values: Any?, message: String? = null) = values.forEach { assertNotNull(it, message) }

@Suppress("NOTHING_TO_INLINE")
inline fun <T> assertAllEqual(expected: T, vararg values: T, message: String? = null) = values.forEachIndexed { index, actual -> assertEquals(expected, actual, message ?: "Item $index: Expected <$expected>, actual <$actual>.") }
