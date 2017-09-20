package com.github.salomonbrys.kodein.test

import kotlin.test.assertTrue

fun <T> assertSame(expected: T, actual: T, message: String? = null) =
        assertTrue(expected === actual, message ?: "Expected same <$expected>, actual <$actual>.")

fun <T> assertNotSame(illegal: T, actual: T, message: String? = null) =
        assertTrue(illegal !== actual, message ?: "Illegal same <$illegal>.")
