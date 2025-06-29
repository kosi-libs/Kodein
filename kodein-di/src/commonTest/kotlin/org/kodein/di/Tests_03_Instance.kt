package org.kodein.di

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_03_Instance {

    @Test
    fun test_00_InstanceBindingGetInstance() {

        val p = Person()

        val di = DI { bind<Person>() with instance(p) }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test
    fun test_01_InstanceBindingGetProvider() {

        val p = Person()

        val di = DI { bind<Person>() with instance(p) }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }


    @Test
    fun test_03_DirectInstanceBindingGetInstance() {

        val p = Person()

        val di = DI { bind { instance(p) } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test
    fun test_04_DirectInstanceBindingGetProvider() {

        val p = Person()

        val di = DI { bind { instance(p) } }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }

    @Test
    fun test_05_SimpleInstanceBindingGetInstance() {

        val p = Person()

        val di = DI { bindInstance { p } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test
    fun test_06_SimpleInstanceBindingGetProvider() {

        val p = Person()

        val di = DI { bindInstance { p } }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }

    fun unit(@Suppress("UNUSED_PARAMETER") i: Int = 42) {}

    @Test
    fun test_07_BindWithUnit() {
        val di = DI.direct {
            bind<Unit>() with instance(unit())
        }

        assertSame(Unit, di.instance())
    }

    @Test
    fun test_08_DirectBindUnit() {
        val di = DI.direct {
            bind { instance(unit()) }
        }

        assertSame(Unit, di.instance())
    }

    @Test
    fun test_09_KClassBindingWithInstance() {
        val person = Person("Instance")
        val di = DI {
            bind(Person::class) with instance(person)
        }

        val p: Person by di.instance()

        assertSame(person, p)
        assertEquals("Instance", p.name)
    }
}
