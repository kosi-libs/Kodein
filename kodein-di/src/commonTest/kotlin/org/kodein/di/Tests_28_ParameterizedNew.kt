package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
internal class Tests_28_ParameterizedNew {

    private open class A(val i: Int = 0)
    private class B(val p: A)

    @Test
    fun test_00_new_1param() {
        val di = DI {
            bind { singleton { A() } }
            bind { factory { p: A -> new(p, ::B) } }
        }

        val arg = A()
        val b: B by di.instance(arg = arg)

        assertEquals(b.p, arg, "expected parameter, but injected instance")
    }

    private class C(val p1: A, val p2: A)

    @Test
    fun test_01_new_2param() {
        val di = DI {
            bind { singleton { A() } }
            bind { factory { p: A -> new(p, ::C) } }
        }

        val injected by di.instance<A>()

        val param = A()
        val c: C by di.instance(arg = param)

        assertEquals(c.p1, param, "expected parameter, but injected instance")
        assertEquals(c.p2, injected, "expected instance, but injected parameter")
    }

    @Test
    fun test_03_new_2param_no_injected() {
        val di = DI {
            //            bind { singleton { A() } } // on purpose
            bind { factory { p: A -> new(p, ::C) } }
        }

        assertFailsWith<DI.NotFoundException> {
            val param = A()
            val c: C by di.instance(arg = param) // first param succeeds, second fails
            println("p1=${c.p1} p2=${c.p2}")
        }
    }

    private class Param

    @Suppress("unused")
    private class D(val inj1: A, val inj2: B)

    @Test
    fun test_03_new_nousage() {
        val di = DI {
            bind { singleton { A() } }
            bind { singleton { new(::B) } }
            bind { factory { p: Param -> new(p, ::D) } }
        }

        val param = Param() // injected class does not use the param
        assertFailsWith<DI.UnusedParameterException> {
            val d: D by di.instance(arg = param)
            println("injected D=$d")
        }
    }
}
