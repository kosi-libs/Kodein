package org.kodein.di.erased

import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DITrigger
import org.kodein.di.LateInitDI
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedTests_19_Late {

    class LateInit : DIAware {
        override lateinit var di: DI

        val name: String by instance()
    }

    @Test
    fun test_00_Late() {

        val test = LateInit()

        test.di = DI {
            bind() from instance("Salomon")
        }

        assertEquals("Salomon", test.name)
    }

    @Test
    fun test_01_LateFail() {

        val test = LateInit()

        assertFails { test.name }
    }

    @Test
    fun test_02_LateLocal() {

        val kodein = LateInitDI()

        val name: String by kodein.instance()

        kodein.baseDI = DI {
            bind() from instance("Salomon")
        }

        assertEquals("Salomon", name)
    }

    @Test
    fun test_03_LateLocalFail() {

        val kodein = LateInitDI()

        val name: String by kodein.instance()

        assertFails { name.length }
    }

    @Test
    fun test_04_LateLocalTrigger() {

        val trigger = DITrigger()
        val base = LateInitDI()
        val kodein = base.on(trigger = trigger)

        val name: String by kodein.instance()

        base.baseDI = DI {
            bind() from instance("Salomon")
        }

        trigger.trigger()

        assertEquals("Salomon", name)
    }

    @Test
    fun test_05_LateLocalTriggerFail() {

        val trigger = DITrigger()
        val base = LateInitDI()
        val kodein = base.on(trigger = trigger)

        @Suppress("UNUSED_VARIABLE")
        val name: String by kodein.instance()

        assertFails { trigger.trigger() }
    }
    
}
