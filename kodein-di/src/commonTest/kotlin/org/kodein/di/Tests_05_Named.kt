package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_05_Named {

    @Test fun test_00_NamedProviderBindingGetInstance() {
        val di = DI {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        val p1: Person by di.instance()
        val p2: Person by di.instance(tag = "named")

        assertNull(p1.name)
        assertEquals("Salomon", p2.name)
    }

    @Test fun test_01_NamedProviderBindingGetProvider() {
        val di = DI {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider(tag = "named")

        assertNull(p1().name)
        assertEquals("Salomon", p2().name)
    }

    @Test fun test_02_NamedSingletonBindingGetInstance() {
        val di = DI {
            bind<Person>() with scopedSingleton { Person() }
            bind<Person>(tag = "named") with scopedSingleton { Person("Salomon") }
        }

        val p1: Person by di.instance(tag = "named")
        val named: Person by di.named.instance()

        assertEquals("Salomon", p1.name)
        assertSame(p1, named)
    }

    @Test fun test_03_NamedSingletonBindingGetProvider() {
        val di = DI {
            bind<Person>() with scopedSingleton { Person() }
            bind<Person>(tag = "named") with scopedSingleton { Person("Salomon") }
        }

        val p1: () -> Person by di.provider(tag = "named")
        val named: () -> Person by di.named.provider()

        assertEquals("Salomon", p1().name)
        assertSame(p1(), named())
    }

    @Test fun test_04_NamedInstanceBindingGetInstance() {

        val di = DI {
            bind<Person>() with instance(Person())
            bind<Person>(tag = "named") with instance(Person("Salomon"))
        }

        val p1: Person by di.instance()
        val named: Person by di.named.instance()
        val p3: Person by di.instance(tag = "named")

        assertNull(p1.name)
        assertEquals("Salomon", named.name)
        assertNotSame(p1, named)
        assertSame(named, p3)
    }

    @Test fun test_05_NamedInstanceBindingGetProvider() {

        val di = DI {
            bind<Person>() with instance(Person())
            bind<Person>(tag = "named") with instance(Person("Salomon"))
        }

        val p1: () -> Person by di.provider()
        val named: () -> Person by di.named.provider()
        val p3: () -> Person by di.provider(tag = "named")

        assertNull(p1().name)
        assertEquals("Salomon", named().name)
        assertNotSame(p1(), named())
        assertSame(named(), p3())
    }

}
