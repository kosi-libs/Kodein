package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertNotSame
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
internal class Tests_26_New {

    class A
    class B(val a: A)

    @Test
    fun test_00_new_singleton() {
        val di = DI {
            bind { singleton { new(::A) } }
            bind { singleton { new(::B) } }
        }

        val a: A by di.instance()
        val b: B by di.instance()

        assertSame(a, b.a)
    }

    @Test
    fun test_01_new_provider() {
        val di = DI {
            bind { provider { new(::A) } }
            bind { singleton { new(::B) } }
        }

        val a: A by di.instance()
        val b: B by di.instance()

        assertNotSame(a, b.a)
    }

    @Test
    fun test_02_new_instance() {
        val di = DI {
            bind { singleton { new(::A) } }
        }

        val a: A by di.instance()
        val b: B by di.newInstance { new(::B) }

        assertSame(a, b.a)
    }

    @Test
    fun test_03_singleton_of() {
        val di = DI {
            bindSingletonOf(::A)
            bindSingletonOf(::B)
        }

        val a: A by di.instance()
        val b: B by di.instance()

        assertSame(a, b.a)
    }

    @Test
    fun test_03_new_provider_of() {
        val di = DI {
            bindProviderOf(::A)
            bindProviderOf(::B)
        }

        val a: A by di.instance()
        val b: B by di.instance()

        assertNotSame(a, b.a)
    }
}
