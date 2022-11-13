@file:Suppress("deprecation")

package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_18_MultiBindings {

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
    fun test_01_MultiSet_with_builder() {
        val di = DI {
            bindSet<IPerson> {
                add { singleton { Person("Salomon") } }
                add { provider { Person("Laila") } }
            }

            bind { provider { instance<Set<IPerson>>().toList() } }
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
    fun test_02_MultiSet_with_modules() {
        val m1 by DI.Module {
            bindSet<IPerson>()
            inBindSet<IPerson> {
                add { singleton { Person("Salomon") } }
            }
        }
        val m2 by DI.Module {
            addInBindSet<IPerson> { provider { Person("Laila") } }
        }

        val di = DI {
            importAll(m1, m2)
            bind { provider { instance<Set<IPerson>>().toList() } }
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
    fun test_03_MultiMap() {
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
    fun test_04_MultiMap_with_builder() {
        val di = DI {
            bindSet {
                add { singleton { "so" to Person("Salomon") } }
                add { provider { "loulou" to Person("Laila") }}
            }

            bind<Map<String, Person>>() with provider { instance<PersonEntries>().toMap() }
        }

        val persons: Map<String, Person> = di.direct.instance()

        assertEquals(Person("Salomon"), persons["so"])
        assertEquals(Person("Laila"), persons["loulou"])
    }

    @Test
    fun test_05_MultiSetWithArg() {
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
    fun test_06_MultiSetWithArg_with_builder() {
        val di = DI {
            bindArgSet<String, IPerson> {
                add {  multiton { lastName: String -> Person("Salomon $lastName") } }
                add { factory { lastName: String -> Person("Laila $lastName") } }
            }
        }

        val persons: Set<IPerson> by di.instance(arg = "BRYS")
        assertTrue(Person("Salomon BRYS") in persons)
        assertTrue(Person("Laila BRYS") in persons)
    }

    @Test
    fun test_07_SimpleMultiSet() {
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
    fun test_08_SimpleMultiSet_with_builder() {
        val di = DI {
            bindSet<IPerson> {
                add { singleton { Person("Salomon") } }
                add { provider { Person("Laila") } }
            }

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
    fun test_09_SimpleMultiMap() {
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
    fun test_10_SimpleMultiMap_with_builder() {
        val di = DI {
            bindSet {
                add { singleton { "so" to Person("Salomon") } }
                add { provider { "loulou" to Person("Laila") } }
            }

            bind<Map<String, Person>>() with provider { instance<PersonEntries>().toMap() }
        }

        val persons: Map<String, Person> = di.direct.instance()

        assertEquals(Person("Salomon"), persons["so"])
        assertEquals(Person("Laila"), persons["loulou"])
    }

    @Test
    fun test_11_SimpleMultiSetWithArg() {
        val di = DI {
            bindArgSet<String, IPerson>()
            inSet<IPerson> { factory { lastName: String -> Person("Salomon $lastName") } }
            inSet<IPerson> { factory { lastName: String -> Person("Laila $lastName") } }
        }

        val persons: Set<IPerson> by di.instance(arg = "BRYS")
        assertTrue(Person("Salomon BRYS") in persons)
        assertTrue(Person("Laila BRYS") in persons)
    }

    @Test
    fun test_12_SimpleMultiSetWithArg_with_builder() {
        val di = DI {
            bindArgSet<String, IPerson> {
                add { factory { lastName: String -> Person("Salomon $lastName") } }
                add { factory { lastName: String -> Person("Laila $lastName") } }
            }
        }

        val persons: Set<IPerson> by di.instance(arg = "BRYS")
        assertTrue(Person("Salomon BRYS") in persons)
        assertTrue(Person("Laila BRYS") in persons)
    }

    @Test
    fun test_13_MultiSet_with_delegate() {
        val di = DI {
            bind { singleton { Person("Salomon") } }

            bindSet<IPerson> {
                add { singleton { Person("Laila") } }
                add { singleton { instance() } }
            }
        }

        val persons: Set<IPerson> by di.instance()
        val salomon: IPerson by di.instance()

        assertTrue(Person("Salomon") in persons)
        assertTrue(Person("Laila") in persons)

        assertSame(persons.last(), salomon)
    }

    @Test
    fun test_14_MultiSet_with_tag() {
        val di = DI {
            bindSet<IPerson> {
                add { singleton { Person("Laila") } }
                bind("ANOTHER_TAG") { singleton { Person("Salomon") } }
            }
        }

        val persons: Set<IPerson> by di.instance()
        val salomon: Person by di.instance("ANOTHER_TAG")

        assertTrue(Person("Salomon") in persons)
        assertTrue(Person("Laila") in persons)

        assertSame(persons.last(), salomon)
    }

    @Test
    fun test_15_MultiSet_with_overrides() {
        val di = DI {
            bind { singleton { Person("Romain") } }

            bindSet<IPerson> {
                add { singleton { Person("Laila") } }
                bind(overrides = true) { singleton { Person("Salomon") } }
            }
        }

        val persons: Set<IPerson> by di.instance()
        val salomon: Person by di.instance()

        assertTrue(Person("Romain") !in persons)
        assertTrue(Person("Salomon") in persons)
        assertTrue(Person("Laila") in persons)

        assertSame(persons.last(), salomon)
    }
}
