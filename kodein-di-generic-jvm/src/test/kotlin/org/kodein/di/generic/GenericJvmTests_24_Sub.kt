package org.kodein.di.generic

import org.kodein.di.*
import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_24_Sub {

    @Test fun test_00_SubKodeinOverrideDefaultCopy() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = subKodein(root) {
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

    @Test fun test_01_SubKodeinOverrideCopyAll() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = subKodein(root, copy = Copy.All) {
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar by sub.instance<Bar>()
        val rootBar by root.instance<Bar>()

        assertNotSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)

        val subFoo by sub.instance<Foo>()
        val rootFoo by root.instance<Foo>()

        assertNotSame(subFoo, rootFoo)
    }

    @Test fun test_02_SubKodeinAllowedSilentOverride() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = subKodein(root, allowSilentOverride = true) {
            bind<Foo>() with provider { Foo("subFoo") }
        }

        val subBar by sub.instance<Bar>()
        val rootBar by root.instance<Bar>()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)

        val subFoo by sub.instance<Foo>()
        val rootFoo by root.instance<Foo>()

        assertNotSame(subFoo, rootFoo)
    }

    @Test fun test_03_SubKodeinNotAllowedSilentOverride() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        assertFailsWith(Kodein.OverridingException::class) {
            subKodein(root) {
                bind<Foo>() with provider { Foo("subFoo") }
            }.direct
        }
    }
}
