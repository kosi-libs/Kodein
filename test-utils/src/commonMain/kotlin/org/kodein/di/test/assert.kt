package org.kodein.di.test

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@Suppress("NOTHING_TO_INLINE")
public inline fun assertAllNull(vararg values: Any?, message: String? = null): Unit = values.forEach { assertNull(it, message) }

@Suppress("NOTHING_TO_INLINE")
public inline fun assertAllNotNull(vararg values: Any?, message: String? = null): Unit = values.forEach { assertNotNull(it, message) }

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> assertAllEqual(expected: T, vararg values: T, message: String? = null): Unit = values.forEachIndexed { index, actual -> assertEquals(expected, actual, message ?: "Item $index: Expected <$expected>, actual <$actual>.") }
