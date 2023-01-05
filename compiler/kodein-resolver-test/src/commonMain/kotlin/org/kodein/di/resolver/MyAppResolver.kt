@file:Suppress("UNUSED_PARAMETER")

package org.kodein.di.resolver

class Bar
class Baz
class Foo(b: Bar)

@Resolved
interface MyAppResolver : DIResolver, MyFirstLibResolver {
    @Tag("ABC")
    fun foo(str: String): Foo
}

@Resolved
interface MyFirstLibResolver : DIResolver, MySecondLibResolver {
    fun bar(): Bar
}

@Resolved
interface MySecondLibResolver: DIResolver {
    fun baz(): Baz
}