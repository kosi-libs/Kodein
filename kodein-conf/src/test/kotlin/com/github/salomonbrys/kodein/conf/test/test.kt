package com.github.salomonbrys.kodein.conf.test

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.bindings.*
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

        assertThrown<IllegalStateException> {
            kodein.clear()
        }
    }

    @Test fun test01_3_NonMutableMutate() {
        val kodein = ConfigurableKodein()

        kodein.addConfig {
            constant("answer").With(erased(), 21)
        }

        assertEquals(21, kodein.Instance<Int>(erased(), tag = "answer"))

        assertThrown<IllegalStateException> {
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

        assertThrown<IllegalStateException> {
            kodein.addConfig {}
        }
    }

    @Test fun test03_0_ChildOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(erased(), erased()) { n: Name -> n.firstName }
        }

        assertEquals("Salomon", kodein.With(erased(), FullName ("Salomon", "BRYS")).Instance<String>(erased()))

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(erased(), erased()) { n: FullName -> n.firstName + " " + n.lastName }
        }

        assertEquals("Salomon BRYS", kodein.With(erased(), FullName("Salomon", "BRYS")).Instance<String>(erased()))
    }

    @Test fun test03_1_GenericOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(erased(), erased()) { l: List<*> -> l.first().toString() + " *" }
        }

        assertEquals("Salomon *", kodein.With(generic(), listOf("Salomon", "BRYS")).Instance<String>(erased()))

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(generic(), erased()) { l: List<String> -> l[0] + " " + l[1] }
        }

        assertEquals("Salomon BRYS", kodein.With(generic(), listOf("Salomon", "BRYS")).Instance<String>(erased()))
        assertEquals("42 *", kodein.With(generic(), listOf(42)).Instance<String>(erased()))
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

    @Suppress("EXPERIMENTAL_FEATURE_WARNING")
    @Test fun test07_0_coroutine() {
        val kodein = ConfigurableKodein(mutable = true)
        kodein.addConfig {
            constant("lastName").With(erased(), "BRYS_2")

            bind("names") from SequenceBinding<String>(erased()) {
                yieldAll(buildSequence {
                    yield("Benjamin " + Instance<String>(erased(), "lastName"))
                    yield("Maroussia " + Instance<String>(erased(), "lastName"))
                })
                yield("Salomon " + Instance<String>(erased(), "lastName"))
            }
        }

        assertEquals("Benjamin BRYS_2", kodein.Instance<String>(erased(), tag = "names"))

        kodein.addConfig { constant("lastName", overrides = true).With(erased(), "BRYS_1") }

        assertEquals("Maroussia BRYS_1", kodein.Instance<String>(erased(), tag = "names"))

        kodein.addConfig { constant("lastName", overrides = true).With(erased(), "BRYS_0") }

        assertEquals("Salomon BRYS_0", kodein.Instance<String>(erased(), tag = "names"))
        assertEquals("Salomon BRYS_0", kodein.Instance<String>(erased(), tag = "names"))
    }

}
