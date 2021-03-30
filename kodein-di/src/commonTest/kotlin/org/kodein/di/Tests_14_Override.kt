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
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " BRYS" }
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " of France" }
        }

        assertEquals("Salomon BRYS of France", di.direct.instance("name"))
    }

    @Test
    fun test_05_DependencyLoopWithOverrides() {

        val di = DI {
            bind<String>(tag = "name") with singleton { instance<String>(tag = "title") + " Salomon " }
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " BRYS " }
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " of France" }
            bind<String>(tag = "title") with singleton { instance<String>(tag = "name") + " the great" }

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

    @Test
    fun test_09_SimpleBinding_ExplicitOverride() {
        val di = DI {
            bindInstance(tag = "name") { "Benjamin" }
            bindInstance(tag = "name", overrides = true) { "Salomon" }
        }

        assertEquals("Salomon", di.direct.instance(tag = "name"))
    }

    @Test
    fun test_10_SimpleBinding_SilentOverride() {
        val di = DI(allowSilentOverride = true) {
            bindInstance(tag = "name") { "Benjamin" }
            bindInstance(tag = "name") { "Salomon" }
        }

        assertEquals("Salomon", di.direct.instance(tag = "name"))
    }

    @Test
    fun test_11_SimpleBinding_SilentOverrideNotAllowed() {
        DI {
            bindInstance(tag = "name") { "Benjamin" }

            assertFailsWith<DI.OverridingException> {
                bindInstance(tag = "name") { "Salomon" }
            }
        }
    }

    @Test
    fun test_12_SimpleBinding_MustNotOverride() {
        DI(allowSilentOverride = true) {
            bindInstance(tag = "name") { "Benjamin" }

            assertFailsWith<DI.OverridingException> {
                bindInstance(tag = "name", overrides = false) { "Salomon" }
            }
        }
    }

    @Test
    fun test_13_SimpleBinding__ExplicitOverride() {
        val di = DI {
            bindInstance(tag = "name") { "Benjamin" }
            bindInstance(tag = "name", overrides = true) { "Salomon" }
        }

        assertEquals("Salomon", di.direct.instance(tag = "name"))
    }

    @Test
    fun test_14_SimpleBinding_SilentOverride() {
        val di = DI(allowSilentOverride = true) {
            bindInstance(tag = "name") { "Benjamin" }
            bindInstance(tag = "name") { "Salomon" }
        }

        assertEquals("Salomon", di.direct.instance(tag = "name"))
    }

    @Test
    fun test_15_SimpleBinding_SilentOverrideNotAllowed() {
        DI {
            bindInstance(tag = "name") { "Benjamin" }

            assertFailsWith<DI.OverridingException> {
                bindInstance(tag = "name") { "Salomon" }
            }
        }
    }

    @Test
    fun test_16_SimpleBinding_MustNotOverride() {
        DI(allowSilentOverride = true) {
            bindInstance(tag = "name") { "Benjamin" }

            assertFailsWith<DI.OverridingException> {
                bindInstance(tag = "name", overrides = false) { "Salomon" }
            }
        }
    }

    @Test
    fun test_17_SimpleBinding_OverrideWithSuper() {
        val di = DI(allowSilentOverride = true) {
            bindInstance(tag = "name") { "Salomon" }
            bind(tag = "name", overrides = true) { singleton { (overriddenInstance() as String) + " BRYS" } }
            bind(tag = "name", overrides = true) { singleton { (overriddenInstance() as String) + " of France" } }
        }

        assertEquals("Salomon BRYS of France", di.direct.instance("name"))
    }

    @Test
    fun test_18_DirectBinding_DependencyLoopWithOverrides() {

        val di = DI {
            bind(tag = "name") { singleton { instance<String>(tag = "title") + " Salomon " } }
            bind(tag = "name", overrides = true) { singleton { (overriddenInstance() as String) + " BRYS " } }
            bind(tag = "name", overrides = true) { singleton { (overriddenInstance() as String) + " of France" } }
            bind(tag = "title") { singleton { instance<String>(tag = "name") + " the great" } }

        }

        assertFailsWith<DI.DependencyLoopException> {
            @Suppress("UNUSED_VARIABLE")
            di.direct.instance<String>(tag = "name")
        }
    }

    @Test
    fun test_19_SimpleBinding_ModuleOverride() {
        val module = DI.Module("test") {
            bindInstance(tag = "name", overrides = true) { "Salomon" }
        }

        val di = DI {
            bindInstance(tag = "name") { "Benjamin" }
            import(module, allowOverride = true)
        }

        assertEquals("Salomon", di.direct.instance(tag = "name"))
    }

    @Test
    fun test_20_SimpleBinding_ModuleForbiddenOverride() {
        val module = DI.Module("test") {
            bindInstance(tag = "name", overrides = true) { "Salomon" }
        }

        DI {
            bindInstance(tag = "name") { "Benjamin" }

            assertFailsWith<DI.OverridingException> {
                import(module)
            }
        }
    }

    @Test
    fun test_21_SimpleBinding_ModuleImportsForbiddenOverride() {
        val subModule = DI.Module("test1") {
            bindInstance(tag = "name", overrides = true) { "Salomon" }
        }

        val module = DI.Module("test2") { import(subModule, allowOverride = true) }

        DI {
            bindInstance(tag = "name") { "Benjamin" }

            assertFailsWith<DI.OverridingException> {
                import(module)
            }
        }
    }
}
