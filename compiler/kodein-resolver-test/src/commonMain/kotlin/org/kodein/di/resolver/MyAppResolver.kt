@file:Suppress("UNUSED_PARAMETER")

package org.kodein.di.resolver

class Bar
class Baz
class Foo(b: Bar)

@DIResolver
interface MyAppResolver : DIChecker, MyFirstLibResolver {
    @Tag("ABC")
    fun foo(str: String): Foo
}

@DIResolver
interface MyFirstLibResolver : DIChecker, MySecondLibResolver {
    @Tag("DEF")
    fun bar(): Bar
}

@DIResolver
interface MySecondLibResolver: DIChecker {
    fun baz(): Baz
}