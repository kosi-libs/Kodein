package org.kodein.di.resolver

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.new
import org.kodein.di.singleton
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

class CheckKSP {
    private val di: DI = DI {
        bindSingleton { Bar() }
        bind { singleton { Baz() } }
        bindFactory("ABC") { _: String -> new(::Foo) }
    }

    @Test
    fun check_DIResolver() {
        val resolver = di.newMyAppResolver()
        resolver.check()
        assertNotSame(
            illegal = resolver.foo("HELLO"),
            actual = resolver.foo("ROMAIN")
        )
    }

    private val appResolver: MyAppResolver = DI.ofMyAppResolver {
        bindSingletonOf(::Bar)
        bind { singleton { new(::Baz) } }
        bindFactory("ABC") { _: String -> Foo(bar()) }
    }

    @Test
    fun check_AppResolver() {
        appResolver.check()
        assertNotSame(
            illegal = appResolver.foo("HELLO"),
            actual = appResolver.foo("ROMAIN")
        )
    }

    @Test
    fun check_MyFirstLibResolver() = di.newMyFirstLibResolver().check()

    @Test
    fun check_MySecondLibResolver() = di.newMySecondLibResolver().check()

    @Test
    fun toString_MyAppResolver() {
        assertEquals(
            """MyAppResolver manages the following binding:
                        | bind<Foo>(tag = "ABC", arg = String)
                        | bind<Bar>()
                        | bind<Baz>()""".trimMargin(), di.newMyAppResolver().toString()
        )
    }
}
