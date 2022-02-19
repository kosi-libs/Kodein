package org.kodein.di.conf

import org.kodein.di.*
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.FullName
import org.kodein.di.test.MethodSorters
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ConfTests {

    @Test
    fun test_00_Configurable() {
        val di = ConfigurableDI()

        di.addConfig {
            constant(tag = "answer") with 42
        }

        assertTrue(di.canConfigure)

        val answer: Int by di.instance(tag = "answer")

        assertEquals(42, answer)

        assertFalse(di.canConfigure)
    }

    @Test
    fun test_01_Clear() {
        val di = ConfigurableDI(true)

        di.addImport(DI.Module("myModule") {
            constant(tag = "answer") with 21
        })

        assertEquals(21, di.direct.instance(tag = "answer"))

        di.clear()

        di.addConfig {
            constant(tag = "answer") with 42
        }

        assertEquals(42, di.direct.instance(tag = "answer"))
    }

    @Test
    fun test_02_Mutate() {
        val di = ConfigurableDI(true)

        di.addExtend(DI {
            constant(tag = "half") with 21
        })

        assertEquals(21, di.direct.instance(tag = "half"))

        di.addConfig {
            constant(tag = "full") with 42
        }

        assertEquals(21, di.direct.instance(tag = "half"))
        assertEquals(42, di.direct.instance(tag = "full"))
    }

    @Test
    fun test_03_NonMutableClear() {
        val di = ConfigurableDI()

        di.addConfig {
            constant(tag = "answer") with 21
        }

        assertEquals(21, di.direct.instance(tag = "answer"))

        assertFailsWith<IllegalStateException> {
            di.clear()
        }
    }

    @Test
    fun test_04_NonMutableMutate() {
        val di = ConfigurableDI()

        di.addConfig {
            constant(tag = "answer") with 21
        }

        assertEquals(21, di.direct.instance(tag = "answer"))

        assertFailsWith<IllegalStateException> {
            di.addConfig {}
        }
    }

    @Test
    fun test_05_mutateConfig() {
        val di = ConfigurableDI(true)

        di.addConfig {
            constant(tag = "half") with 21
        }

        assertEquals(21, di.direct.instance(tag = "half"))

        di.addConfig {
            constant(tag = "full") with 42
        }

        assertEquals(21, di.direct.instance(tag = "half"))
        assertEquals(42, di.direct.instance(tag = "full"))
    }

    @Test
    fun test_06_nonMutableMutateConfig() {
        val di = ConfigurableDI()

        di.addConfig {
            constant(tag = "half") with 21
        }

        assertEquals(21, di.direct.instance(tag = "half"))

        assertFailsWith<IllegalStateException> {
            di.addConfig {}
        }
    }

    @Test
    fun test_07_ChildOverride() {
        val di = ConfigurableDI(true)

        di.addConfig {
            bind<String>() with factory { n: FullName -> n.firstName }
        }

        assertEquals("Salomon", di.direct.factory<FullName, String>().invoke(FullName("Salomon", "BRYS")))

        di.addConfig {
            bind<String>(overrides = true) with factory { n: FullName -> n.firstName + " " + n.lastName }
        }

        assertEquals("Salomon BRYS", di.direct.factory<FullName, String>().invoke(FullName("Salomon", "BRYS")))
    }

    class T08 : DIGlobalAware {
        val answer: Int by instance(tag = "full")
    }

    @Test
    fun test_08_Global() {
        DI.global.mutable = true

        DI.global.addConfig {
            constant(tag = "half") with 21
        }

        assertEquals(21, DI.global.direct.instance(tag = "half"))

        DI.global.addConfig {
            constant(tag = "full") with 42
        }

        assertEquals(21, DI.global.direct.instance(tag = "half"))
        assertEquals(42, T08().answer)
    }

    @Test
    fun test_09_Callback() {
        val di = ConfigurableDI()

        var ready = false

        di.addConfig {
            bind { singleton { "test" } }

            onReady {
                ready = true
            }

            assertFalse(ready)
        }

        assertFalse(ready)

        val value: String by di.instance()

        assertFalse(ready)

        assertEquals("test", value)

        assertTrue(ready)
    }

    @Test
    fun test_10_config_chaining() {
        val di = ConfigurableDI(true)

        di
            .addConfig {
                constant(tag = "half") with 21
            }.addConfig {
                constant(tag = "full") with 42
            }

        assertEquals(21, di.direct.instance(tag = "half"))
        assertEquals(42, di.direct.instance(tag = "full"))
    }
}
