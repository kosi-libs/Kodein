package org.kodein.di

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.kodein.di.bindings.overriddenInstanceOf
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person


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
            bindSingleton<String>(
                tag = "name",
                overrides = true,
            ) {
                val s = overriddenInstance()
                "$s BRYS"
            }
            bindSingleton<String>(
                tag = "name",
                overrides = true,
            ) {
                val s = overriddenInstance()
                "$s of France"
            }
        }

        assertEquals("Salomon BRYS of France", di.direct.instance("name"))
    }

    @Test
    fun test_04c_OverrideWithSuper_ClassCastException() {
        val di = DI(allowSilentOverride = true) {
            bind<Int>() with instance(20)
            bindSingleton<Int>(overrides = true) {
                val s: String = overriddenInstanceOf()
                22 + s.toInt()
            }
        }

        assertFailsWith<ClassCastException> { di.direct.instance<Int>() }
    }

    @Test
    fun test_05_DependencyLoopWithOverrides() {

        val di = DI {
            bind<String>(tag = "name") with singleton { instance<String>(tag = "title") + " Salomon " }
            bindSingleton<String>(
                tag = "name",
                overrides = true,
            ) { (overriddenInstance() as String) + " BRYS " }
            bindSingleton<String>(
                tag = "name",
                overrides = true,
            ) { (overriddenInstance() as String) + " of France" }
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
            bindSingleton(
                tag = "name",
                overrides = true,
            )  { (overriddenInstance() as String) + " BRYS" }
            bindSingleton(
                tag = "name",
                overrides = true,
            )  { (overriddenInstance() as String) + " of France" }
        }

        assertEquals("Salomon BRYS of France", di.direct.instance("name"))
    }

    @Test
    fun test_18_DirectBinding_DependencyLoopWithOverrides() {

        val di = DI {
            bind(tag = "name") { singleton { instance<String>(tag = "title") + " Salomon " } }
            bindSingleton(
                tag = "name",
                overrides = true,
            )  { (overriddenInstance() as String) + " BRYS " }
            bindSingleton(
                tag = "name",
                overrides = true,
            )  { (overriddenInstance() as String) + " of France" }
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

    @Test
    fun test_22_KClassBindingWithOverrides() {
        val di = DI {
            bind(Person::class) with singleton { Person("First") }
            bind(Person::class, overrides = true) with singleton { Person("Second") }
        }

        val p: Person by di.instance()

        assertEquals("Second", p.name)
    }


    private inline fun <reified T : String> uppercase(t: T): String = t.toString().uppercase()

    @Test
    fun test_23_OverrideWithSuper_Reified() {
        val di = DI(allowSilentOverride = true) {
            bind<String>() with instance("Salomon")
            bindSingleton {
                uppercase<String>(overriddenInstanceOf())
            }
            bindSingleton {
                val s: String = overriddenInstanceOf()
                "$s of France"
            }
        }

        assertEquals("SALOMON of France", di.direct.instance())
    }

}
