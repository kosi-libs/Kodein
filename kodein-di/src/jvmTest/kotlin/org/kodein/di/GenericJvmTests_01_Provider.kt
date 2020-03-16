package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.Test
import kotlin.test.assertNotSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_01_Provider {

    @Test
    fun test_00_ProviderBindingGetInstance() {

        val kodein = DI { bind<Person>() with provider { Person() } }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance()

        assertNotSame(p1, p2)
    }

    @Test
    fun test_01_ProviderBindingGetProvider() {

        val kodein = DI { bind<Person>() with provider { Person() } }

        val p1 by kodein.provider<Person>()
        val p2 by kodein.provider<Person>()

        assertNotSame(p1(), p2())
    }

}
