@file:Suppress("DEPRECATION", "unused")

package org.kodein.di.conf.test

import org.kodein.di.Kodein
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.conf.KodeinGlobalAware
import org.kodein.di.conf.global
import org.kodein.di.direct
import org.kodein.di.erased.*
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.FullName
import org.kodein.di.test.MethodSorters
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinConfTests {

    @Test fun test00_00_Configurable() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer") with 42
        }

        assertTrue(kodein.canConfigure)

        val answer: Int by kodein.instance(tag = "answer")

        assertEquals(42, answer)

        assertFalse(kodein.canConfigure)
    }

    @Test fun test01_04_Clear() {
        val kodein = ConfigurableKodein(true)

        kodein.addImport(Kodein.Module {
            constant(tag = "answer") with 21
        })

        assertEquals(21, kodein.direct.instance(tag = "answer"))

        kodein.clear()

        kodein.addConfig {
            constant(tag = "answer") with 42
        }

        assertEquals(42, kodein.direct.instance(tag = "answer"))
    }

    @Test fun test01_02_Mutate() {
        val kodein = ConfigurableKodein(true)

        kodein.addExtend(Kodein {
            constant(tag = "half") with 21
        })

        assertEquals(21, kodein.direct.instance(tag = "half"))

        kodein.addConfig {
            constant(tag = "full") with 42
        }

        assertEquals(21, kodein.direct.instance(tag = "half"))
        assertEquals(42, kodein.direct.instance(tag = "full"))
    }

    @Test fun test01_03_NonMutableClear() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer") with 21
        }

        assertEquals(21, kodein.direct.instance(tag = "answer"))

        assertFailsWith<IllegalStateException> {
            kodein.clear()
        }
    }

    @Test fun test01_04_NonMutableMutate() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer") with 21
        }

        assertEquals(21, kodein.direct.instance(tag = "answer"))

        assertFailsWith<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test02_00_mutateConfig() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant(tag = "half") with 21
        }

        assertEquals(21, kodein.direct.instance(tag = "half"))

        kodein.addConfig {
            constant(tag = "full") with 42
        }

        assertEquals(21, kodein.direct.instance(tag = "half"))
        assertEquals(42, kodein.direct.instance(tag = "full"))
    }

    @Test fun test02_01_nonMutableMutateConfig() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "half") with 21
        }

        assertEquals(21, kodein.direct.instance(tag = "half"))

        assertFailsWith<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test
    fun test03_00_ChildOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            bind<String>() with factory { n: FullName -> n.firstName }
        }

        assertEquals("Salomon", kodein.direct.factory<FullName, String>().invoke(FullName("Salomon", "BRYS")))

        kodein.addConfig {
            bind<String>(overrides = true) with factory { n: FullName -> n.firstName + " " + n.lastName }
        }

        assertEquals("Salomon BRYS", kodein.direct.factory<FullName, String>().invoke(FullName("Salomon", "BRYS")))
    }

    class Test04 : KodeinGlobalAware {
        val answer: Int by instance(tag = "full")
    }

    @Test fun test04_00_Global() {
        Kodein.global.mutable = true

        Kodein.global.addConfig {
            constant(tag = "half") with 21
        }

        assertEquals(21, Kodein.global.direct.instance(tag = "half"))

        Kodein.global.addConfig {
            constant(tag = "full") with 42
        }

        assertEquals(21, Kodein.global.direct.instance(tag = "half"))
        assertEquals(42, Test04().answer)
    }

    private object Test05 {
        val kodein = ConfigurableKodein()

        class Loop(@Suppress("UNUSED_PARAMETER") text: String = kodein.direct.instance())
    }

    @Test fun test05_00_Loop() {
        Test05.kodein.addConfig {
            bind() from singleton { "test" }
            bind() from eagerSingleton { Test05.Loop() }
        }

        Test05.kodein.getOrConstruct()
    }

    @Test fun test06_00_Callback() {
        val kodein = ConfigurableKodein()

        var ready = false

        kodein.addConfig {
            bind() from singleton { "test" }

            onReady {
                ready = true
            }

            assertFalse(ready)
        }

        assertFalse(ready)

        val value: String by kodein.instance()

        assertFalse(ready)

        assertEquals("test", value)

        assertTrue(ready)
    }


}
