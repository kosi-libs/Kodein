@file:Suppress("DEPRECATION", "unused")

package com.github.salomonbrys.kodein.conf.test

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.bindings.EagerSingletonBinding
import com.github.salomonbrys.kodein.bindings.SingletonBinding
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.conf.global
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinGlobalTests {

    @Test fun test00_00_Configurable() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 42)
        }

        val answer: Int = kodein.Instance(erased(), tag = "answer")

        assertEquals(42, answer)
    }

    @Test fun test01_04_Clear() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "answer"))

        kodein.clear()

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 42)
        }

        assertEquals(42, kodein.Instance<Int>(erased(), tag = "answer"))
    }

    @Test fun test01_02_Mutate() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant(tag = "half").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))

        kodein.addConfig {
            constant(tag = "full").With(erased(), 42)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, kodein.Instance<Int>(erased(), tag = "full"))
    }

    @Test fun test01_03_NonMutableClear() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "answer"))

        assertFailsWith<IllegalStateException> {
            kodein.clear()
        }
    }

    @Test fun test01_04_NonMutableMutate() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "answer").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "answer"))

        assertFailsWith<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test02_00_mutateConfig() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant(tag = "half").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))

        kodein.addConfig {
            constant(tag = "full").With(erased(), 42)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, kodein.Instance<Int>(erased(), tag = "full"))
    }

    @Test fun test02_01_nonMutableMutateConfig() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant(tag = "half").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))

        assertFailsWith<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test04_00_Global() {
        Kodein.global.mutable = true

        Kodein.global.addConfig {
            constant(tag = "half").With(erased(), 21)
        }

        assertEquals(21, Kodein.global.Instance<Int>(erased(), tag = "half"))

        Kodein.global.addConfig {
            constant(tag = "full").With(erased(), 42)
        }

        assertEquals(21, Kodein.global.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, Kodein.global.Instance<Int>(erased(), tag = "full"))
    }

    private object Test05 {
        val kodein = ConfigurableKodein()

        class Loop(@Suppress("UNUSED_PARAMETER") text: String = kodein.Instance(erased()))
    }

    @Test fun test05_00_Loop() {
        Test05.kodein.addConfig {
            bind() from SingletonBinding(erased()) { "test" }
            bind() from EagerSingletonBinding(this, erased()) { Test05.Loop() }
        }

        Test05.kodein.getOrConstruct()
    }

    @Test fun test06_00_Callback() {
        val kodein = ConfigurableKodein()

        var ready = false

        kodein.addConfig {
            onReady {
                bind() from SingletonBinding(erased()) { "test" }
                ready = true
            }

            assertFalse(ready)
        }

        kodein.Instance<String>(erased())

        assertTrue(ready)
    }

}
