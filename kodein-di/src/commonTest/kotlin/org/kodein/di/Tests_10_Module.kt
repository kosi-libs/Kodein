package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_10_Module {

    class PersonContainer(di: DI) {
        val newPerson: () -> Person by di.provider()
        val salomon: Person by di.instance(tag = "named")
        val factory: (String) -> Person by di.factory(tag = "factory")
    }

    @Test
    fun test_00_ModuleImport() {

        val personModule = DI.Module("test") {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        val di = DI {
            import(personModule)
        }

        val container = PersonContainer(di)
        assertNotSame(container.newPerson(), container.newPerson())
        assertEquals("Salomon", container.salomon.name)
        assertSame(container.salomon, container.salomon)
        assertNotSame(container.factory("Laila"), container.factory("Laila"))
        assertEquals("Laila", container.factory("Laila").name)

        val kodein2 = DI {
            import(personModule)
        }

        assertSame(di.direct.instance<Person>(tag = "named"), di.direct.instance(tag = "named"))
        assertSame(kodein2.direct.instance<Person>(tag = "named"), kodein2.direct.instance(tag = "named"))
        assertNotSame(di.direct.instance<Person>(tag = "named"), kodein2.direct.instance(tag = "named"))
    }

    @Test
    fun test_01_ModuleImportTwice() {

        val module = DI.Module("test") {}

        val ex = assertFailsWith<IllegalStateException> {
            DI {
                import(module)
                import(module)
            }
        }

        assertEquals("Module \"test\" has already been imported!", ex.message)
    }

    @Test
    fun test_02_ModuleOnce() {

        val module = DI.Module("test") {
            bind<String>() with instance("Salomon")
        }

        val di = DI {
            importOnce(module)
            importOnce(module)
        }

        assertEquals("Salomon", di.direct.instance())
    }

    @Test
    fun test_03_ModuleOverExtend() {
        val salomon = "Salomon"
        val module = DI.Module("test") {
            bind<String>() with instance(salomon)
        }

        val container1 = DI {
            importOnce(module)
        }

        val container2 = DI {
            extend(container1)
            importOnce(module)
        }

        val container3 = DI {
            extend(container2)
            importOnce(module)
        }

        assertEquals(salomon, container1.direct.instance())
        assertEquals(salomon, container2.direct.instance())
        assertEquals(salomon, container3.direct.instance())
        assertSame(container1.direct.instance<String>(), container2.direct.instance(), container3.direct.instance())
    }

    @Test
    fun test_03_SimpleBinding_ModuleImport() {

        val personModule = DI.Module("test") {
            bindProvider { Person() }
            bindSingleton(tag = "named") { Person("Salomon") }
            bindFactory(tag = "factory") { name: String -> Person(name) }
        }

        val di = DI {
            import(personModule)
        }

        val container = PersonContainer(di)
        assertNotSame(container.newPerson(), container.newPerson())
        assertEquals("Salomon", container.salomon.name)
        assertSame(container.salomon, container.salomon)
        assertNotSame(container.factory("Laila"), container.factory("Laila"))
        assertEquals("Laila", container.factory("Laila").name)

        val kodein2 = DI {
            import(personModule)
        }

        assertSame(di.direct.instance<Person>(tag = "named"), di.direct.instance(tag = "named"))
        assertSame(kodein2.direct.instance<Person>(tag = "named"), kodein2.direct.instance(tag = "named"))
        assertNotSame(di.direct.instance<Person>(tag = "named"), kodein2.direct.instance(tag = "named"))
    }

    @Test
    fun test_04_SimpleBinding_ModuleOnce() {

        val module = DI.Module("test") {
            bindInstance { "Salomon" }
        }

        val di = DI {
            importOnce(module)
            importOnce(module)
        }

        assertEquals("Salomon", di.direct.instance())
    }

    @Test
    fun test_05_SimpleBinding_ModuleOverExtend() {
        val salomon = "Salomon"
        val module = DI.Module("test") {
            bindInstance { salomon }
        }

        val container1 = DI {
            importOnce(module)
        }

        val container2 = DI {
            extend(container1)
            importOnce(module)
        }

        val container3 = DI {
            extend(container2)
            importOnce(module)
        }

        assertEquals(salomon, container1.direct.instance())
        assertEquals(salomon, container2.direct.instance())
        assertEquals(salomon, container3.direct.instance())
        assertSame(container1.direct.instance<String>(), container2.direct.instance(), container3.direct.instance())
    }

    @Test
    fun test_06_ModuleCreation() {
        val directModule1 = DI.Module("test_1") {}

        assertEquals("test_1", directModule1.name)
        assertFalse(directModule1.allowSilentOverride)
        assertEquals("", directModule1.prefix)

        val directModule2 = DI.Module("test_2", allowSilentOverride = true, prefix = "prefix_2") {}

        assertEquals("test_2", directModule2.name)
        assertTrue(directModule2.allowSilentOverride)
        assertEquals("prefix_2", directModule2.prefix)

        val test_1 by DI.Module {}
        assertEquals("test_1", test_1.name)
        assertFalse(test_1.allowSilentOverride)
        assertEquals("", test_1.prefix)

        val test_2 by DI.Module(allowSilentOverride = true, prefix = "prefix_2") {}

        assertEquals("test_2", test_2.name)
        assertTrue(test_2.allowSilentOverride)
        assertEquals("prefix_2", test_2.prefix)

        val test_3 by DI.Module(name = "test_3_delegate") {}

        assertEquals("test_3_delegate", test_3.name)
        assertFalse(test_3.allowSilentOverride)
        assertEquals("", test_3.prefix)

        assertFailsWith<IllegalStateException> { DI.Module {}.name }
    }
}
