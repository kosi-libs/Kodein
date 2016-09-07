package com.github.salomonbrys.kodein.conf.test

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.factory
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.test.FullName
import com.github.salomonbrys.kodein.test.Name
import com.github.salomonbrys.kodein.test.assertThrown
import com.github.salomonbrys.kodein.with
import junit.framework.TestCase
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinGlobalTests : TestCase() {

    @Test fun test00_0_Configurable() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer") with 42
        }

        val answer: Int = kodein.instance("answer")

        assertEquals(42, answer)
    }

    @Test fun test01_0_Clear() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("answer") with 21
        }

        assertEquals(21, kodein.instance<Int>("answer"))

        kodein.clear()

        kodein.addConfig {
            constant("answer") with 42
        }

        assertEquals(42, kodein.instance<Int>("answer"))
    }

    @Test fun test01_1_Mutate() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("half") with 21
        }

        assertEquals(21, kodein.instance<Int>("half"))

        kodein.addConfig {
            constant("full") with 42
        }

        assertEquals(21, kodein.instance<Int>("half"))
        assertEquals(42, kodein.instance<Int>("full"))
    }

    @Test fun test01_2_NonMutableClear() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer") with 21
        }

        assertEquals(21, kodein.instance<Int>("answer"))

        assertThrown<IllegalStateException> {
            kodein.clear()
        }
    }

    @Test fun test01_3_NonMutableMutate() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer") with 21
        }

        assertEquals(21, kodein.instance<Int>("answer"))

        assertThrown<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test02_0_mutateConfig() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("half") with 21
        }

        assertEquals(21, kodein.instance<Int>("half"))

        kodein.addConfig {
            constant("full") with 42
        }

        assertEquals(21, kodein.instance<Int>("half"))
        assertEquals(42, kodein.instance<Int>("full"))
    }

    @Test fun test02_1_nonMutableMutateConfig() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("half") with 21
        }

        assertEquals(21, kodein.instance<Int>("half"))

        assertThrown<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test03_0_ChildOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            bind<String>() with factory { n: Name -> n.firstName }
        }

        assertEquals("Salomon", kodein.with(FullName("Salomon", "BRYS")).instance<String>())

        kodein.addConfig {
            bind<String>() with factory { n: FullName -> n.firstName + " " + n.lastName }
        }

        assertEquals("Salomon BRYS", kodein.with(FullName("Salomon", "BRYS")).instance<String>())
    }

    @Test fun test03_1_GenericOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            bind<String>() with factory { l: List<*> -> l.first().toString() + " *" }
        }

        assertEquals("Salomon *", kodein.with(listOf("Salomon", "BRYS")).instance<String>())

        kodein.addConfig {
            bind<String>() with factory { l: List<String> -> l[0].toString() + " " + l[1].toString() }
        }

        assertEquals("Salomon BRYS", kodein.with(listOf("Salomon", "BRYS")).instance<String>())
        assertEquals("42 *", kodein.with(listOf(42)).instance<String>())
    }

    @Test fun test04_0_global() {
        Kodein.global.mutable = true

        Kodein.global.addConfig {
            constant("half") with 21
        }

        assertEquals(21, Kodein.global.instance<Int>("half"))

        Kodein.global.addConfig {
            constant("full") with 42
        }

        assertEquals(21, Kodein.global.instance<Int>("half"))
        assertEquals(42, Kodein.global.instance<Int>("full"))
    }

}
