package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_05_Named {

    @Test
    fun test_00_NamedProviderBindingGetInstance() {
        val kodein = DI {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance(tag = "named")

        assertNull(p1.name)
        assertEquals("Salomon", p2.name)
    }

    @Test
    fun test_01_NamedProviderBindingGetProvider() {
        val kodein = DI {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        val p1: () -> Person by kodein.provider()
        val p2: () -> Person by kodein.provider(tag = "named")

        assertNull(p1().name)
        assertEquals("Salomon", p2().name)
    }

    @Test fun test_02_NamedSingletonBindingGetInstance() {
        val kodein = DI {
            bind<Person>() with singleton { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val p1: Person by kodein.instance(tag = "named")
        val named: Person by kodein.named.instance()

        assertEquals("Salomon", p1.name)
        assertSame(p1, named)
    }

    @Test fun test_03_NamedSingletonBindingGetProvider() {
        val kodein = DI {
            bind<Person>() with singleton { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val p1: () -> Person by kodein.provider(tag = "named")
        val named: () -> Person by kodein.named.provider()

        assertEquals("Salomon", p1().name)
        assertSame(p1(), named())
    }

    @Test fun test_04_NamedInstanceBindingGetInstance() {

        val kodein = DI {
            bind<Person>() with instance(Person())
            bind<Person>(tag = "named") with instance(Person("Salomon"))
        }

        val p1: Person by kodein.instance()
        val named: Person by kodein.named.instance()
        val p3: Person by kodein.instance(tag = "named")

        assertNull(p1.name)
        assertEquals("Salomon", named.name)
        assertNotSame(p1, named)
        assertSame(named, p3)
    }

    @Test fun test_05_NamedInstanceBindingGetProvider() {

        val kodein = DI {
            bind<Person>() with instance(Person())
            bind<Person>(tag = "named") with instance(Person("Salomon"))
        }

        val p1: () -> Person by kodein.provider()
        val named: () -> Person by kodein.named.provider()
        val p3: () -> Person by kodein.provider(tag = "named")

        assertNull(p1().name)
        assertEquals("Salomon", named().name)
        assertNotSame(p1(), named())
        assertSame(named(), p3())
    }

}
