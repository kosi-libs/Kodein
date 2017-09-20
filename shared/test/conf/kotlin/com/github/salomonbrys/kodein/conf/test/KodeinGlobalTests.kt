@file:Suppress("DEPRECATION", "unused")

package com.github.salomonbrys.kodein.conf.test

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.bindings.EagerSingletonBinding
import com.github.salomonbrys.kodein.bindings.FactoryBinding
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

    @Test fun test00_0_Configurable() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer").With(erased(), 42)
        }

        val answer: Int = kodein.Instance(erased(), tag = "answer")

        assertEquals(42, answer)
    }

    @Test fun test01_0_Clear() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("answer").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "answer"))

        kodein.clear()

        kodein.addConfig {
            constant("answer").With(erased(), 42)
        }

        assertEquals(42, kodein.Instance<Int>(erased(), tag = "answer"))
    }

    @Test fun test01_1_Mutate() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("half").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))

        kodein.addConfig {
            constant("full").With(erased(), 42)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, kodein.Instance<Int>(erased(), tag = "full"))
    }

    @Test fun test01_2_NonMutableClear() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "answer"))

        assertFailsWith<IllegalStateException> {
            kodein.clear()
        }
    }

    @Test fun test01_3_NonMutableMutate() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "answer"))

        assertFailsWith<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test02_0_mutateConfig() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("half").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))

        kodein.addConfig {
            constant("full").With(erased(), 42)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, kodein.Instance<Int>(erased(), tag = "full"))
    }

    @Test fun test02_1_nonMutableMutateConfig() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("half").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "half"))

        assertFailsWith<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test04_0_Global() {
        Kodein.global.mutable = true

        Kodein.global.addConfig {
            constant("half").With(erased(), 21)
        }

        assertEquals(21, Kodein.global.Instance<Int>(erased(), tag = "half"))

        Kodein.global.addConfig {
            constant("full").With(erased(), 42)
        }

        assertEquals(21, Kodein.global.Instance<Int>(erased(), tag = "half"))
        assertEquals(42, Kodein.global.Instance<Int>(erased(), tag = "full"))
    }

    object Test05_0 {
        val kodein = ConfigurableKodein()

        class Loop(@Suppress("UNUSED_PARAMETER") text: String = kodein.Instance(erased()))
    }

    @Test fun test05_0_Loop() {
        Test05_0.kodein.addConfig {
            bind() from SingletonBinding(erased()) { "test" }
            bind() from EagerSingletonBinding(this, erased()) { Test05_0.Loop() }
        }

        Test05_0.kodein.getOrConstruct()
    }

    @Test fun test06_0_Callback() {
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
