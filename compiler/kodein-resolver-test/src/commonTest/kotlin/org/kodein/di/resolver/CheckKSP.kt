package org.kodein.di.resolver

import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.new
import kotlin.test.Test
import kotlin.test.assertEquals

// TODO: WIP
class CheckKSP {
    private val di = DI {
        bindSingleton("DEF") { Bar() }
        bindSingleton { Baz() }
        bindFactory<Bar, Foo>("ABC") { _ -> new(::Foo) }
    }

    @Test fun check_MySecondLibResolver() = di.newMySecondLibResolver().check()
    @Test fun check_MyFirstLibResolver() = di.newMyFirstLibResolver().check()
    @Test fun check_MyAppResolver() = di.newMyAppResolver().check()
    @Test fun toString_MyAppResolver() {
        assertEquals("""MyAppResolver manages the following binding:
                        | bind<Foo>(tag = "ABC", arg = Bar)
                        | bind<Bar>(tag = "DEF")
                        | bind<Baz>()""".trimMargin(),  di.newMyAppResolver().toString())
    }
}