package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_14_Override {

    @Test
    fun test_00_ExplicitOverride() {
        val di = DI {
            bind<String>(tag = "name") with instance("Benjamin")
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        assertEquals("Salomon", di.direct.instance(tag = "name"))
    }

    @Test
    fun test_01_SilentOverride() {
        val di = DI(allowSilentOverride = true) {
            bind<String>(tag = "name") with instance("Benjamin")
            bind<String>(tag = "name") with instance("Salomon")
        }

        assertEquals("Salomon", di.direct.instance(tag = "name"))
    }

    @Test
    fun test_02_SilentOverrideNotAllowed() {
        DI {
            bind<String>(tag = "name") with instance("Benjamin")

            assertFailsWith<DI.OverridingException> {
                bind<String>(tag = "name") with instance("Salomon")
            }
        }
    }

    @Test
    fun test_03_MustNotOverride() {
        DI(allowSilentOverride = true) {
            bind<String>(tag = "name") with instance("Benjamin")

            assertFailsWith<DI.OverridingException> {
                bind<String>(tag = "name", overrides = false) with instance("Salomon")
            }
        }
    }

    @Test
    fun test_04_OverrideWithSuper() {
        val di = DI(allowSilentOverride = true) {
            bind<String>(tag = "name") with instance("Salomon")
            bind<String>(tag = "name", overrides = true) with scopedSingleton { (overriddenInstance() as String) + " BRYS" }
            bind<String>(tag = "name", overrides = true) with scopedSingleton { (overriddenInstance() as String) + " of France" }
        }

        assertEquals("Salomon BRYS of France", di.direct.instance("name"))
    }

    @Test
    fun test_05_DependencyLoopWithOverrides() {

        val di = DI {
            bind<String>(tag = "name") with scopedSingleton { instance<String>(tag = "title") + " Salomon " }
            bind<String>(tag = "name", overrides = true) with scopedSingleton { (overriddenInstance() as String) + " BRYS " }
            bind<String>(tag = "name", overrides = true) with scopedSingleton { (overriddenInstance() as String) + " of France" }
            bind<String>(tag = "title") with scopedSingleton { instance<String>(tag = "name") + " the great" }

        }

        assertFailsWith<DI.DependencyLoopException> {
            @Suppress("UNUSED_VARIABLE")
            di.direct.instance<String>(tag = "name")
        }
    }

    @Test
    fun test_06_ModuleOverride() {
        val module = DI.Module("test") {
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        val di = DI {
            bind<String>(tag = "name") with instance("Benjamin")
            import(module, allowOverride = true)
        }

        assertEquals("Salomon", di.direct.instance(tag = "name"))
    }

    @Test
    fun test_07_ModuleForbiddenOverride() {
        val module = DI.Module("test") {
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        DI {
            bind<String>(tag = "name") with instance("Benjamin")

            assertFailsWith<DI.OverridingException> {
                import(module)
            }
        }
    }

    @Test
    fun test_08_ModuleImportsForbiddenOverride() {
        val subModule = DI.Module("test1") {
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        val module = DI.Module("test2") { import(subModule, allowOverride = true) }

        DI {
            bind<String>(tag = "name") with instance("Benjamin")

            assertFailsWith<DI.OverridingException> {
                import(module)
            }
        }
    }


}
