package org.kodein.di.erased

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.LateInitKodein
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedTests_19_Late {

    class LateInit : KodeinAware {
        override lateinit var kodein: Kodein

        val name: String by instance()
    }

    @Test
    fun test_00_Late() {

        val test = LateInit()

        test.kodein = Kodein {
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

        val kodein = LateInitKodein()

        val name: String by kodein.instance()

        kodein.baseKodein = Kodein {
            bind() from instance("Salomon")
        }

        assertEquals("Salomon", name)
    }

    @Test
    fun test_03_LateLocalFail() {

        val kodein = LateInitKodein()

        val name: String by kodein.instance()

        assertFails { name.length }
    }

    @Test
    fun test_04_LateLocalTrigger() {

        val trigger = KodeinTrigger()
        val base = LateInitKodein()
        val kodein = base.on(trigger = trigger)

        val name: String by kodein.instance()

        base.baseKodein = Kodein {
            bind() from instance("Salomon")
        }

        trigger.trigger()

        assertEquals("Salomon", name)
    }

    @Test
    fun test_05_LateLocalTriggerFail() {

        val trigger = KodeinTrigger()
        val base = LateInitKodein()
        val kodein = base.on(trigger = trigger)

        @Suppress("UNUSED_VARIABLE")
        val name: String by kodein.instance()

        assertFails { trigger.trigger() }
    }
    
}
