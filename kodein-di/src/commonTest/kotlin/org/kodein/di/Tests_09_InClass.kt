package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_09_InClass {

    class PersonContainer(di: DI) {
        val newPerson: () -> Person by di.provider()
        val salomon: Person by di.instance(tag = "named")
        val factory: (String) -> Person by di.factory(tag = "factory")
    }

    @Test
    fun test_00_Class() {
        val di = DI {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        val container = PersonContainer(di)
        assertNotSame(container.newPerson(), container.newPerson())
        assertEquals("Salomon", container.salomon.name)
        assertSame(container.salomon, container.salomon)
        assertNotSame(container.factory("Laila"), container.factory("Laila"))
        assertEquals("Laila", container.factory("Laila").name)
    }

    @Test
    fun test_01_SimpleBinding_Class() {
        val di = DI {
            bindProvider { Person() }
            bindSingleton(tag = "named") { Person("Salomon") }
            bindFactory(tag = "factory") { name: String -> Person(name) }
        }

        val container = PersonContainer(di)
        assertNotSame(container.newPerson(), container.newPerson())
        assertEquals("Salomon", container.salomon.name)
        assertSame(container.salomon, container.salomon)
        assertNotSame(container.factory("Laila"), container.factory("Laila"))
        assertEquals("Laila", container.factory("Laila").name)
    }

}
