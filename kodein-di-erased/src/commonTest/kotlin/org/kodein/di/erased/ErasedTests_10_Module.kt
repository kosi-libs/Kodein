package org.kodein.di.erased

import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedTests_10_Module {

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

        assertSame(kodein.direct.instance<Person>(tag = "named"), kodein.direct.instance(tag = "named"))
        assertSame(kodein2.direct.instance<Person>(tag = "named"), kodein2.direct.instance(tag = "named"))
        assertNotSame(kodein.direct.instance<Person>(tag = "named"), kodein2.direct.instance(tag = "named"))
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

}
