package org.kodein.di.resolver

import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.new
import kotlin.test.Test

// TODO: WIP
class CheckKSP {
    val di = DI {
        bindSingleton("DEF") { Bar() }
        bindSingleton { Baz() }
        bindFactory<String, Foo>() { _ -> new(::Foo) }
    }

    @Test fun check_MySecondLibResolver() = di.newMySecondLibResolver().check()
    @Test fun check_MyFirstLibResolver() = di.newMyFirstLibResolver().check()
    @Test fun check_MyAppResolver() = di.newMyAppResolver().check()
}