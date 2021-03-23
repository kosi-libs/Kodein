package org.kodein.di

import org.kodein.di.test.*
import org.kodein.type.generic
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_18_MultiBindings {

    @Test
    fun test_00_MultiSet() {
        val di = DI {
            bindSet<IPerson>()

            bind<IPerson>().inSet() with singleton { Person("Salomon") }
            bind<IPerson>().inSet() with provider { Person("Laila") }

            bind<List<IPerson>>() with provider { instance<Set<IPerson>>().toList() }
        }

        val persons1: Set<IPerson> by di.instance()

        assertTrue(Person("Salomon") in persons1)
        assertTrue(Person("Laila") in persons1)

        val persons2: Set<IPerson> by di.instance()

        val salomon1 = persons1.first { it.name == "Salomon" }
        val salomon2 = persons2.first { it.name == "Salomon" }

        val laila1 = persons1.first { it.name == "Laila" }
        val laila2 = persons2.first { it.name == "Laila" }

        assertSame(salomon1, salomon2)
        assertNotSame(laila1, laila2)

        val list: List<IPerson> by di.instance()
        assertEquals(persons1.toList(), list)
    }

    @Test
    fun test_01_MultiMap() {
        val di = DI {
            bindSet<PersonEntry>()

            bind<PersonEntry>().inSet() with singleton { "so" to Person("Salomon") }
            bind<PersonEntry>().inSet() with provider { "loulou" to Person("Laila") }

            bind<Map<String, Person>>() with provider { instance<PersonEntries>().toMap() }
        }

        val persons: Map<String, Person> = di.direct.instance()

        assertEquals(Person("Salomon"), persons["so"])
        assertEquals(Person("Laila"), persons["loulou"])
    }

    @Test
    fun test_02_MultiSetWithArg() {
        val di = DI {
            bindArgSet<String, IPerson>()
            bind<IPerson>().inSet() with multiton { lastName: String -> Person("Salomon $lastName") }
            bind<IPerson>().inSet() with factory { lastName: String -> Person("Laila $lastName") }
        }

        val persons: Set<IPerson> by di.instance(arg = "BRYS")
        assertTrue(Person("Salomon BRYS") in persons)
        assertTrue(Person("Laila BRYS") in persons)
    }

    @Test
    fun test_03_SimpleMultiSet() {
        val di = DI {
            bindSet<IPerson>()

            inSet<IPerson> { singleton { Person("Salomon") } }
            inSet<IPerson> { provider { Person("Laila") } }

            bind<List<IPerson>>() with provider { instance<Set<IPerson>>().toList() }
        }

        val persons1: Set<IPerson> by di.instance()

        assertTrue(Person("Salomon") in persons1)
        assertTrue(Person("Laila") in persons1)

        val persons2: Set<IPerson> by di.instance()

        val salomon1 = persons1.first { it.name == "Salomon" }
        val salomon2 = persons2.first { it.name == "Salomon" }

        val laila1 = persons1.first { it.name == "Laila" }
        val laila2 = persons2.first { it.name == "Laila" }

        assertSame(salomon1, salomon2)
        assertNotSame(laila1, laila2)

        val list: List<IPerson> by di.instance()
        assertEquals(persons1.toList(), list)
    }

    @Test
    fun test_04_SimpleMultiMap() {
        val di = DI {
            bindSet<PersonEntry>()

            inSet { singleton { "so" to Person("Salomon") } }
            inSet { provider { "loulou" to Person("Laila") } }

            bind<Map<String, Person>>() with provider { instance<PersonEntries>().toMap() }
        }

        val persons: Map<String, Person> = di.direct.instance()

        assertEquals(Person("Salomon"), persons["so"])
        assertEquals(Person("Laila"), persons["loulou"])
    }

    @Test
    fun test_05_SimpleMultiSetWithArg() {
        val di = DI {
            bindArgSet<String, IPerson>()
            inSet<IPerson> { factory { lastName: String -> Person("Salomon $lastName") } }
            inSet<IPerson> { factory { lastName: String -> Person("Laila $lastName") } }
        }

        val persons: Set<IPerson> by di.instance(arg = "BRYS")
        assertTrue(Person("Salomon BRYS") in persons)
        assertTrue(Person("Laila BRYS") in persons)
    }

}
