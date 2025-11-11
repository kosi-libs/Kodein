@file:Suppress("deprecation")

package org.kodein.di

import org.kodein.di.test.*
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
            bind<IPerson> { singleton { Person("Salomon") } }

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

    @Test
    fun test_16_DslMarker_enforces_correct_receiver_scope() {
        // This test demonstrates the fix for issue #478:
        // The @DIDsl marker prevents calling methods on outer receivers (DI.Builder)
        // when inside inner DSL contexts (SetBinder).
        //
        // Before the fix, code like this would compile but fail:
        //   inBindSet<IPerson> { bindSingleton { Person("Wrong") } }
        // where bindSingleton would incorrectly call DI.Builder.bindSingleton
        // instead of SetBinder's method (which didn't exist).
        //
        // After the fix, only the correct SetBinder methods are in scope.

        val di = DI {
            bindSet<IPerson>()

            inBindSet<IPerson> {
                // These calls are now restricted to SetBinder's scope only.
                // Attempting to call DI.Builder methods here will cause a compilation error.
                add { singleton { Person("Salomon") } }
                add { provider { Person("Laila") } }
            }
        }

        val persons: Set<IPerson> by di.instance()

        assertTrue(Person("Salomon") in persons)
        assertTrue(Person("Laila") in persons)
        assertEquals(2, persons.size)
    }

    @Test
    fun test_17_MultiSet_with_add_convenience_methods() {
        // Test the add* convenience methods for cleaner syntax
        val di = DI {
            bindSet<IPerson> {
                addSingleton { Person("Salomon") }  // Instead of: add { singleton { Person("Salomon") } }
                addProvider { Person("Laila") }     // Instead of: add { provider { Person("Laila") } }
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

        // Singleton should be the same instance
        assertSame(salomon1, salomon2)
        // Provider should create new instances
        assertNotSame(laila1, laila2)

        val list: List<IPerson> by di.instance()
        assertEquals(persons1.toList(), list)
    }

    @Test
    fun test_18_MultiSet_with_addInstance() {
        val existingPerson = Person("Romain")

        val di = DI {
            bindSet<IPerson> {
                addInstance(existingPerson)  // Instead of: add { instance(existingPerson) }
                addSingleton { Person("Salomon") }
            }
        }

        val persons: Set<IPerson> by di.instance()

        assertTrue(existingPerson in persons)
        assertTrue(Person("Salomon") in persons)
        assertEquals(2, persons.size)

        // Instance should be the exact same object
        assertSame(existingPerson, persons.first { it.name == "Romain" })
    }

    @Test
    fun test_19_MultiSetWithArg_with_add_convenience_methods() {
        // Test addFactory and addMultiton convenience methods
        val di = DI {
            bindArgSet<String, IPerson> {
                addFactory { lastName: String -> Person("Salomon $lastName") }  // Instead of: add { factory { ... } }
                addMultiton { lastName: String -> Person("Laila $lastName") }   // Instead of: add { multiton { ... } }
            }
        }

        val persons1: Set<IPerson> by di.instance(arg = "BRYS")
        assertTrue(Person("Salomon BRYS") in persons1)
        assertTrue(Person("Laila BRYS") in persons1)

        val persons2: Set<IPerson> by di.instance(arg = "BRYS")

        val salomon1 = persons1.first { it.name == "Salomon BRYS" }
        val salomon2 = persons2.first { it.name == "Salomon BRYS" }

        val laila1 = persons1.first { it.name == "Laila BRYS" }
        val laila2 = persons2.first { it.name == "Laila BRYS" }

        // Factory should create new instances
        assertNotSame(salomon1, salomon2)
        // Multiton should cache per argument
        assertSame(laila1, laila2)
    }
}
