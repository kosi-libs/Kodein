@file:Suppress("DEPRECATION", "unused")

package org.kodein.conf.test

import org.kodein.*
import org.kodein.conf.ConfigurableKodein
import org.kodein.conf.global
import org.kodein.bindings.*
import org.kodein.test.FixMethodOrder
import org.kodein.test.MethodSorters
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinGlobalTests {

    @Test fun test00_00_Configurable() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 42)
        }

        val answer: Int by kodein.Instance(erased(), tag = "answer")

        assertEquals(42, answer)
    }

    @Test fun test01_04_Clear() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 21)
        }

        assertEquals(21, kodein.direct.Instance<Int>(erased(), tag = "answer"))

        kodein.clear()

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 42)
        }

        assertEquals(42, kodein.direct.Instance<Int>(erased(), tag = "answer"))
    }

    @Test fun test01_02_Mutate() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant(tag = "half").With(erased(), 21)
        }

        assertEquals(21, kodein.direct.Instance<Int>(erased(), tag = "half"))

        kodein.addConfig {
            constant(tag = "full").With(erased(), 42)
        }

        assertEquals(21, kodein.direct.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, kodein.direct.Instance<Int>(erased(), tag = "full"))
    }

    @Test fun test01_03_NonMutableClear() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 21)
        }

        assertEquals(21, kodein.direct.Instance<Int>(erased(), tag = "answer"))

        assertFailsWith<IllegalStateException> {
            kodein.clear()
        }
    }

    @Test fun test01_04_NonMutableMutate() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 21)
        }

        assertEquals(21, kodein.direct.Instance<Int>(erased(), tag = "answer"))

        assertFailsWith<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test02_00_mutateConfig() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant(tag = "half").With(erased(), 21)
        }

        assertEquals(21, kodein.direct.Instance<Int>(erased(), tag = "half"))

        kodein.addConfig {
            constant(tag = "full").With(erased(), 42)
        }

        assertEquals(21, kodein.direct.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, kodein.direct.Instance<Int>(erased(), tag = "full"))
    }

    @Test fun test02_01_nonMutableMutateConfig() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "half").With(erased(), 21)
        }

        assertEquals(21, kodein.direct.Instance<Int>(erased(), tag = "half"))

        assertFailsWith<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test04_00_Global() {
        Kodein.global.mutable = true

        Kodein.global.addConfig {
            constant(tag = "half").With(erased(), 21)
        }

        assertEquals(21, Kodein.global.direct.Instance<Int>(erased(), tag = "half"))

        Kodein.global.addConfig {
            constant(tag = "full").With(erased(), 42)
        }

        assertEquals(21, Kodein.global.direct.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, Kodein.global.direct.Instance<Int>(erased(), tag = "full"))
    }

    private object Test05 {
        val kodein = ConfigurableKodein()

        class Loop(@Suppress("UNUSED_PARAMETER") text: String = kodein.direct.Instance(erased(), null))
    }

    @Test fun test05_00_Loop() {
        Test05.kodein.addConfig {
            bind() from Singleton(NoScope(), AnyToken, erased()) { "test" }
            bind() from EagerSingleton(containerBuilder, erased()) { Test05.Loop() }
        }

        Test05.kodein.getOrConstruct()
    }

    @Test fun test06_00_Callback() {
        val kodein = ConfigurableKodein()

        var ready = false

        kodein.addConfig {
            bind() from Singleton(NoScope(), AnyToken, erased()) { "test" }

            onReady {
                ready = true
            }

            assertFalse(ready)
        }

        assertFalse(ready)

        val value by kodein.Instance<String>(erased(), null)

        assertFalse(ready)

        assertEquals("test", value)

        assertTrue(ready)
    }

}
