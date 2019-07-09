package org.kodein.di.generic

import org.kodein.di.*
import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_10_Module {

    class PersonContainer(kodein: Kodein) {
        val newPerson: () -> Person by kodein.provider()
        val salomon: Person by kodein.instance(tag = "named")
        val factory: (String) -> Person by kodein.factory(tag = "factory")
    }

    @Test
    fun test_00_ModuleImport() {

        val personModule = Kodein.Module("test") {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        val kodein = Kodein {
            import(personModule)
        }

        val container = PersonContainer(kodein)
        assertNotSame(container.newPerson(), container.newPerson())
        assertEquals("Salomon", container.salomon.name)
        assertSame(container.salomon, container.salomon)
        assertNotSame(container.factory("Laila"), container.factory("Laila"))
        assertEquals("Laila", container.factory("Laila").name)

        val kodein2 = Kodein {
            import(personModule)
        }

        assertSame(kodein.direct.instance(tag = "named"), kodein.direct.instance<Person>(tag = "named"))
        assertSame(kodein2.direct.instance(tag = "named"), kodein2.direct.instance<Person>(tag = "named"))
        assertNotSame(kodein.direct.instance(tag = "named"), kodein2.direct.instance<Person>(tag = "named"))
    }

    @Test
    fun test_01_ModuleImportTwice() {

        val module = Kodein.Module("test") {}

        val ex = assertFailsWith<IllegalStateException> {
            Kodein {
                import(module)
                import(module)
            }
        }

        assertEquals("Module \"test\" has already been imported!", ex.message)
    }

    @Test
    fun test_02_ModuleOnce() {

        val module = Kodein.Module("test") {
            bind<String>() with instance("Salomon")
        }

        val kodein = Kodein {
            importOnce(module)
            importOnce(module)
        }

        assertEquals("Salomon", kodein.direct.instance())
    }

    @Test
    fun test_03_ModuleOverExtend() {
        val salomon = "Salomon"
        val module = Kodein.Module("test") {
            bind<String>() with instance(salomon)
        }

        val container1 = Kodein {
            importOnce(module)
        }

        val container2 = Kodein {
            extend(container1)
            importOnce(module)
        }

        val container3 = Kodein {
            extend(container2)
            importOnce(module)
        }

        assertEquals(salomon, container1.direct.instance())
        assertEquals(salomon, container2.direct.instance())
        assertEquals(salomon, container3.direct.instance())
        assertSame(container1.direct.instance<String>(), container2.direct.instance(), container3.direct.instance())
    }
}
