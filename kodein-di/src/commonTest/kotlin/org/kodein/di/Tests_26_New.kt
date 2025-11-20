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
    
    class C(val a: A, val x: Int, val y: String)

    @Test
    fun test_04_new_with_named_args_all_manual() {
        val di = DI {
            bindSingleton { new(::A) }
            bindSingleton { new(::C, a1 = A(), a2 = 42, a3 = "test") }
        }

        val c: C by di.instance()
        val a: A by di.instance()

        assertNotSame(a, c.a)
        kotlin.test.assertEquals(42, c.x)
        kotlin.test.assertEquals("test", c.y)
    }

    @Test
    fun test_05_new_with_named_args_partial() {
        val di = DI {
            bindSingleton { new(::A) }
            bindSingleton { new(::C, a2 = 99, a3 = "partial") }
        }

        val c: C by di.instance()
        val a: A by di.instance()

        assertSame(a, c.a)
        kotlin.test.assertEquals(99, c.x)
        kotlin.test.assertEquals("partial", c.y)
    }

    @Test
    fun test_06_new_with_named_args_skip_middle() {
        val di = DI {
            bindSingleton { new(::A) }
            bindSingleton { new(::C, a1 = A(), a3 = "skip") }
        }

       
        kotlin.test.assertFailsWith<DI.NotFoundException> {
            val c: C by di.instance()
            c.x
        }
    }

    class D(val x: Int, val y: Int, val z: Int, val w: Int)

    @Test
    fun test_07_new_with_multiple_named_args() {
        val di = DI {
            bindSingleton { new(::D, a1 = 1, a2 = 2, a3 = 3, a4 = 4) }
        }

        val d: D by di.instance()
        kotlin.test.assertEquals(1, d.x)
        kotlin.test.assertEquals(2, d.y)
        kotlin.test.assertEquals(3, d.z)
        kotlin.test.assertEquals(4, d.w)
    }

    @Test
    fun test_08_new_with_named_args_and_bindings() {
        val di = DI {
            bindConstant("x") { 100 }
            bindConstant("y") { 200 }
            bindConstant("z") { 300 }
            bindConstant("w") { 400 }
            bindSingleton { new(::D, a2 = 222, a4 = 444) }
        }

       
        kotlin.test.assertFailsWith<DI.NotFoundException> {
            val d: D by di.instance()
            d.x
        }
    }

    class E(val a: A, val b: B)

    @Test
    fun test_09_new_with_mixed_dependencies() {
        val di = DI {
            bind { singleton { new(::A) } }
            bind { singleton { new(::B) } }
            bind { singleton { new(::E, a2 = B(A())) } }
        }

        val e: E by di.instance()
        val a: A by di.instance()
        val b: B by di.instance()

        assertSame(a, e.a)
        assertNotSame(b, e.b)
    }

    @Test
    fun test_10_new_with_provider_and_named_args() {
        val di = DI {
            bindSingleton { new(::A) }
            bindProvider { new(::C, a2 = 10, a3 = "provider") }
        }

        val c1: C by di.instance()
        val c2: C by di.instance()

        assertNotSame(c1, c2)
        kotlin.test.assertEquals(10, c1.x)
        kotlin.test.assertEquals(10, c2.x)
        kotlin.test.assertEquals("provider", c1.y)
        kotlin.test.assertEquals("provider", c2.y)
    }
}
