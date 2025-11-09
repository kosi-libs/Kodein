package org.kodein.di

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Tests_28_ParameterizedNewJvm {

    private open class A
    private class B(val p: A)
    private class S() : A()

    @Test
    fun test_02_new_subclass() {
        val di = DI {
            bind { singleton { A() } }
            bind { factory { p: A -> new(p, ::B) } }
        }

        val subclass = S()
        val b: B by di.instance(arg = subclass)

        assertEquals(b.p, subclass, "injected parameter ${b.p} is not the expected subclass")
    }
}
