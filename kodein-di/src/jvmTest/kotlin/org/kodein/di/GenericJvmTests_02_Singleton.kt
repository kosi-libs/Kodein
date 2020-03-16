package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.Test
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_02_Singleton {

    @Test
    fun test_00_SingletonBindingGetInstance() {

        val kodein = DI { bind<Person>() with singleton { Person() } }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance()

        assertSame(p1, p2)
    }

    @Test
    fun test_01_SingletonBindingGetProvider() {

        val kodein = DI { bind<Person>() with singleton { Person() } }

        val p1: () -> Person by kodein.provider()
        val p2: () -> Person by kodein.provider()

        assertSame(p1(), p2())
    }

    @Test
    fun test_02_NonSyncedSingletonBindingGetInstance() {

        val kodein = DI { bind<Person>() with singleton(sync = false) { Person() } }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance()

        assertSame(p1, p2)
    }


}
