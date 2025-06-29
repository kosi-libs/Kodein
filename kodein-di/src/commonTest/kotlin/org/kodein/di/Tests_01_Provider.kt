package org.kodein.di

import kotlin.test.Test
import kotlin.test.assertNotSame
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_01_Provider {

    @Test
    fun test_00_ProviderBindingGetInstance() {

        val di = DI { bind<Person>() with provider { Person() } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertNotSame(p1, p2)
    }

    @Test
    fun test_01_ProviderBindingGetProvider() {

        val di = DI { bind<Person>() with provider { Person() } }

        val p1 by di.provider<Person>()
        val p2 by di.provider<Person>()

        assertNotSame(p1(), p2())
    }


    @Test
    fun test_03_DirectProviderBindingGetInstance() {

        val di = DI { bind { provider { Person() } } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertNotSame(p1, p2)
    }

    @Test
    fun test_04_DirectProviderBindingGetProvider() {

        val di = DI { bind { provider { Person() } } }

        val p1 by di.provider<Person>()
        val p2 by di.provider<Person>()

        assertNotSame(p1(), p2())
    }

    @Test
    fun test_05_SimpleProviderBindingGetInstance() {

        val di = DI { bindProvider { Person() } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertNotSame(p1, p2)
    }

    @Test
    fun test_06_SimpleProviderBindingGetProvider() {

        val di = DI { bindProvider { Person() } }

        val p1 by di.provider<Person>()
        val p2 by di.provider<Person>()

        assertNotSame(p1(), p2())
    }

    @Test
    fun test_07_KClassBindingWithProvider() {
        val di = DI {
            bind(Person::class) with provider { Person() }
        }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertNotSame(p1, p2)
    }

}
