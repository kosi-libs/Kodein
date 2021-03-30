package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_19_Late {

    class LateInit : DIAware {
        override lateinit var di: DI

        val name: String by instance()
    }

    @Test
    fun test_00_Late() {

        val test = LateInit()

        test.di = DI {
            bind { instance("Salomon") }
        }

        assertEquals("Salomon", test.name)
    }

    @Test
    fun test_01_LateFail() {

        val test = LateInit()

        assertFailsWith<UninitializedPropertyAccessException> { test.name }
    }

    @Test
    fun test_02_LateLocal() {

        val di = LateInitDI()

        val name: String by di.instance()

        di.baseDI = DI {
            bind { instance("Salomon") }
        }

        assertEquals("Salomon", name)
    }

    @Test
    fun test_03_LateLocalFail() {

        val di = LateInitDI()

        val name: String by di.instance()

        assertFailsWith<UninitializedPropertyAccessException> { name.length }
    }

    @Test
    fun test_04_LateLocalTrigger() {

        val trigger = DITrigger()
        val base = LateInitDI()
        val di = base.on(trigger = trigger)

        val name: String by di.instance()

        base.baseDI = DI {
            bind { instance("Salomon") }
        }

        trigger.trigger()

        assertEquals("Salomon", name)
    }

    @Test
    fun test_05_LateLocalTriggerFail() {

        val trigger = DITrigger()
        val base = LateInitDI()
        val di = base.on(trigger = trigger)

        @Suppress("UNUSED_VARIABLE")
        val name: String by di.instance()

        assertFailsWith<UninitializedPropertyAccessException> { trigger.trigger() }
    }

}
