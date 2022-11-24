@file:Suppress("UNUSED_PARAMETER")

package org.kodein.di.resolver

class Bar
class Baz
class Foo(b: Bar)

@Resolve
interface MyAppResolver : DIResolver, MyFirstLibResolver {
    @Tag("ABC")
    fun foo(str: String): Foo
}

@Resolve
interface MyFirstLibResolver : DIResolver, MySecondLibResolver {
    @Tag("DEF")
    fun bar(): Bar
}

@Resolve
interface MySecondLibResolver: DIResolver {
    fun baz(): Baz
}