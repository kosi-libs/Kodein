package com.github.salomonbrys.kodein.conf.test

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.conf.global
import junit.framework.TestCase
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.coroutines.experimental.buildSequence

private open class Name(val firstName: String) {
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is Name) return false
        if (firstName != other.firstName) return false
        return true
    }

    override fun hashCode(): Int{
        return firstName.hashCode()
    }
}

private class FullName(firstName: String, val lastName: String) : Name(firstName) {
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is FullName) return false
        if (!super.equals(other)) return false
        if (lastName != other.lastName) return false
        return true
    }

    override fun hashCode(): Int{
        return 31 * super.hashCode() + lastName.hashCode()
    }
}


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinGlobalTests : TestCase() {

    @Test fun test00_0_Configurable() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer") withErased 42
        }

        val answer: Int = kodein.erasedInstance("answer")

        assertEquals(42, answer)
    }

    @Test fun test01_0_Clear() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("answer") withErased 21
        }

        assertEquals(21, kodein.erasedInstance<Int>("answer"))

        kodein.clear()

        kodein.addConfig {
            constant("answer") withErased 42
        }

        assertEquals(42, kodein.erasedInstance<Int>("answer"))
    }

    @Test fun test01_1_Mutate() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("half") withErased 21
        }

        assertEquals(21, kodein.erasedInstance<Int>("half"))

        kodein.addConfig {
            constant("full") withErased 42
        }

        assertEquals(21, kodein.erasedInstance<Int>("half"))
        assertEquals(42, kodein.erasedInstance<Int>("full"))
    }

    @Test fun test01_2_NonMutableClear() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer") withErased 21
        }

        assertEquals(21, kodein.erasedInstance<Int>("answer"))

        assertThrown<IllegalStateException> {
            kodein.clear()
        }
    }

    @Test fun test01_3_NonMutableMutate() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer") withErased 21
        }

        assertEquals(21, kodein.erasedInstance<Int>("answer"))

        assertThrown<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test02_0_mutateConfig() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            constant("half") withErased 21
        }

        assertEquals(21, kodein.erasedInstance<Int>("half"))

        kodein.addConfig {
            constant("full") withErased 42
        }

        assertEquals(21, kodein.erasedInstance<Int>("half"))
        assertEquals(42, kodein.erasedInstance<Int>("full"))
    }

    @Test fun test02_1_nonMutableMutateConfig() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("half") withErased 21
        }

        assertEquals(21, kodein.erasedInstance<Int>("half"))

        assertThrown<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test03_0_ChildOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            bindErased<String>() with erasedFactory { n: Name -> n.firstName }
        }

        assertEquals("Salomon", kodein.withErased(FullName("Salomon", "BRYS")).erasedInstance())

        kodein.addConfig {
            bindErased<String>() with erasedFactory { n: FullName -> n.firstName + " " + n.lastName }
        }

        assertEquals("Salomon BRYS", kodein.withErased(FullName("Salomon", "BRYS")).erasedInstance())
    }

    @Test fun test03_1_GenericOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            bindErased<String>() with erasedFactory { l: List<*> -> l.first().toString() + " *" }
        }

        assertEquals("Salomon *", kodein.withGeneric(listOf("Salomon", "BRYS")).erasedInstance())

        kodein.addConfig {
            bindErased<String>() with genericFactory { l: List<String> -> l[0] + " " + l[1] }
        }

        assertEquals("Salomon BRYS", kodein.withGeneric(listOf("Salomon", "BRYS")).erasedInstance())
        assertEquals("42 *", kodein.withGeneric(listOf(42)).erasedInstance())
    }

    @Test fun test04_0_Global() {
        Kodein.global.mutable = true

        Kodein.global.addConfig {
            constant("half") withErased 21
        }

        assertEquals(21, Kodein.global.erasedInstance<Int>("half"))

        Kodein.global.addConfig {
            constant("full") withErased 42
        }

        assertEquals(21, Kodein.global.erasedInstance<Int>("half"))
        assertEquals(42, Kodein.global.erasedInstance<Int>("full"))
    }

    object Test05_0 {
        val kodein = ConfigurableKodein()

        class Loop(@Suppress("UNUSED_PARAMETER") text: String = kodein.erasedInstance())
    }

    @Test fun test05_0_Loop() {
        Test05_0.kodein.addConfig {
            bind() from erasedSingleton { "test" }
            bind() from erasedEagerSingleton { Test05_0.Loop() }
        }

        Test05_0.kodein.getOrConstruct()
    }

    @Test fun test06_0_Callback() {
        val kodein = ConfigurableKodein()

        var ready = false

        kodein.addConfig {
            onReady {
                bind() from erasedSingleton { "test" }
                ready = true
            }

            assertFalse(ready)
        }

        kodein.erasedInstance<String>()

        assertTrue(ready)
    }

    @Suppress("EXPERIMENTAL_FEATURE_WARNING")
    @Test fun test07_0_coroutine() {
        val kodein = ConfigurableKodein(mutable = true)
        kodein.addConfig {
            constant("lastName") withErased "BRYS_2"

            bind("names") from erasedSequence {
                yieldAll(buildSequence {
                    yield("Benjamin " + erasedInstance<String>("lastName"))
                    yield("Maroussia " + erasedInstance<String>("lastName"))
                })
                yield("Salomon " + erasedInstance<String>("lastName"))
            }
        }

        assertEquals("Benjamin BRYS_2", kodein.erasedInstance<String>("names"))

        kodein.addConfig { constant("lastName", overrides = true) withErased "BRYS_1" }

        assertEquals("Maroussia BRYS_1", kodein.erasedInstance<String>("names"))

        kodein.addConfig { constant("lastName", overrides = true) withErased "BRYS_0" }

        assertEquals("Salomon BRYS_0", kodein.erasedInstance<String>("names"))
        assertEquals("Salomon BRYS_0", kodein.erasedInstance<String>("names"))
    }

}
