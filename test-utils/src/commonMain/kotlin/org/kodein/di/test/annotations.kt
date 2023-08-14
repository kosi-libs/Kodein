// https://youtrack.jetbrains.com/issue/KT-61573
@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.kodein.di.test

public expect enum class MethodSorters {
    DEFAULT,
    NAME_ASCENDING
}

public expect annotation class FixMethodOrder(
        val value: MethodSorters //= MethodSorters.DEFAULT
)
