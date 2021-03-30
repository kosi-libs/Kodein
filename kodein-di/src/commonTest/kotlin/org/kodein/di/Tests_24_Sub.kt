package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_24_Sub {

    @Test fun test_00_SubDIOverrideDefaultCopy() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = subDI(root) {
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar by sub.instance<Bar>()
        val rootBar by root.instance<Bar>()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)

        val subFoo by sub.instance<Foo>()
        val rootFoo by root.instance<Foo>()

        assertNotEquals(subFoo, rootFoo)
    }

    @Test fun test_01_SubDIOverrideCopyAll() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = subDI(root, copy = Copy.All, init = {
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        })

        val subBar by sub.instance<Bar>()
        val rootBar by root.instance<Bar>()

        assertNotSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)

        val subFoo by sub.instance<Foo>()
        val rootFoo by root.instance<Foo>()

        assertNotSame(subFoo, rootFoo)
    }

    @Test fun test_02_SubDIAllowedSilentOverride() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = subDI(root, allowSilentOverride = true, init = {
            bind<Foo>() with provider { Foo("subFoo") }
        })

        val subBar by sub.instance<Bar>()
        val rootBar by root.instance<Bar>()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)

        val subFoo by sub.instance<Foo>()
        val rootFoo by root.instance<Foo>()

        assertNotSame(subFoo, rootFoo)
    }

    @Test fun test_03_SubDINotAllowedSilentOverride() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        assertFailsWith(DI.OverridingException::class) {
            subDI(root) {
                bind<Foo>() with provider { Foo("subFoo") }
            }.direct
        }
    }

    @Test fun test_04_SimpleBinding_SubDIOverrideDefaultCopy() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI {
            bindProvider { Foo("rootFoo") }
            bindSingleton { Bar(instance()) }
        }

        val sub = subDI(root) {
            bindProvider(overrides = true) { Foo("subFoo") }
        }

        val subBar by sub.instance<Bar>()
        val rootBar by root.instance<Bar>()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)

        val subFoo by sub.instance<Foo>()
        val rootFoo by root.instance<Foo>()

        assertNotEquals(subFoo, rootFoo)
    }

    @Test fun test_05_SimpleBinding_SubDIOverrideCopyAll() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI {
            bindProvider { Foo("rootFoo") }
            bindSingleton { Bar(instance()) }
        }

        val sub = subDI(root, copy = Copy.All, init = {
            bindProvider(overrides = true) { Foo("subFoo") }
        })

        val subBar by sub.instance<Bar>()
        val rootBar by root.instance<Bar>()

        assertNotSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)

        val subFoo by sub.instance<Foo>()
        val rootFoo by root.instance<Foo>()

        assertNotSame(subFoo, rootFoo)
    }

    @Test fun test_06_SimpleBinding_SubDIAllowedSilentOverride() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI {
            bindProvider { Foo("rootFoo") }
            bindSingleton { Bar(instance()) }
        }

        val sub = subDI(root, allowSilentOverride = true, init = {
            bindProvider { Foo("subFoo") }
        })

        val subBar by sub.instance<Bar>()
        val rootBar by root.instance<Bar>()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)

        val subFoo by sub.instance<Foo>()
        val rootFoo by root.instance<Foo>()

        assertNotSame(subFoo, rootFoo)
    }

    @Test fun test_07_SimpleBinding_SubDINotAllowedSilentOverride() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI {
            bindProvider { Foo("rootFoo") }
            bindSingleton { Bar(instance()) }
        }

        assertFailsWith(DI.OverridingException::class) {
            subDI(root) {
                bindProvider { Foo("subFoo") }
            }.direct
        }
    }
}
