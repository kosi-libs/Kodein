package org.kodein.di

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person

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

    @Test
    fun test_03_DirectSingletonBindingGetInstance() {

        val di = DI { bind { singleton { Person() } } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p2)
    }

    @Test
    fun test_04_DirectSingletonBindingGetProvider() {

        val di = DI { bind { singleton { Person() } } }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p2())
    }

    @Test
    fun test_05_SimpleSingletonBindingGetInstance() {

        val di = DI { bindSingleton { Person() } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p2)
    }

    @Test
    fun test_06_SimpleSingletonBindingGetProvider() {

        val di = DI { bindSingleton { Person() } }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p2())
    }

    @Test
    fun test_07_KClassBindingWithSingleton() {
        val di = DI {
            bind(Person::class) with singleton { Person() }
        }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p2)
    }

    @Test
    fun test_08_KClassBindingWithTaggedSingleton() {
        val di = DI {
            bind(Person::class, tag = "person") with singleton { Person("Tagged") }
        }

        val p1: Person by di.instance(tag = "person")
        val p2: Person by di.instance(tag = "person")

        assertSame(p1, p2)
        assertEquals("Tagged", p1.name)
    }

}
