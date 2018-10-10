package org.kodein.di.test

actual enum class MethodSorters {
    DEFAULT,
    NAME_ASCENDING
}

actual annotation class FixMethodOrder(
        actual val value: MethodSorters = MethodSorters.DEFAULT
)
