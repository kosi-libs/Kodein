package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.Test
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_02_Singleton {
    @Test
    fun test_00_SingletonBindingGetInstance() {

        val di = DI { bind<Person>() with singleton { Person() } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p2)
    }

    @Test
    fun test_01_SingletonBindingGetProvider() {

        val di = DI { bind<Person>() with singleton { Person() } }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p2())
    }

    @Test
    fun test_02_NonSyncedSingletonBindingGetInstance() {

        val di = DI { bind<Person>() with singleton(sync = false) { Person() } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p2)
    }

}
