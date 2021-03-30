package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_11_Extend {

    @Test
    fun test_00_DIExtend() {

        val parent = DI {
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val child = DI {
            extend(parent)
            bind<Person>() with provider { Person() }
        }

        assertSame(parent.direct.instance<Person>(tag = "named"), child.direct.instance(tag = "named"))
        assertNull(parent.direct.instanceOrNull<Person>())
        assertNotNull(child.direct.instanceOrNull<Person>())
    }

    @Test
    fun test_01_DIExtendOverride() {

        val parent = DI {
            bind<String>() with singleton { "parent" }
        }

        val child = DI {
            extend(parent)
            bind<String>(overrides = true) with singleton { "child" }
        }

        assertEquals("parent", parent.direct.instance())
        assertEquals("child", child.direct.instance())
    }

    @Test
    fun test_02_DIExtendOverriddenInstance() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with provider { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true, copy = Copy.None)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)
    }

    @Test
    fun test_03_DIExtendOverriddenInstanceCopy() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with provider { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true, copy = Copy.All)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)
    }

    @Test
    fun test_04_DIExtendOverriddenSingletonSame() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
    }

    @Test
    fun test_05_DIExtendOverriddenSingletonCopy() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true, copy = Copy {
                copy all binding<Bar>()
            })
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertNotSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)
    }

    @Test
    fun test_06_SimpleBinding_DIExtendCopyAllBut() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bindProvider { Foo("rootFoo") }
            bindSingleton { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true, copy = Copy.allBut {
                ignore all binding<Bar>()
            })
            bindProvider(overrides = true) { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
    }

    @Test
    fun test_07_SimpleBinding_DIExtend() {

        val parent = DI {
            bindSingleton(tag = "named") { Person("Salomon") }
        }

        val child = DI {
            extend(parent)
            bindProvider { Person() }
        }

        assertSame(parent.direct.instance<Person>(tag = "named"), child.direct.instance(tag = "named"))
        assertNull(parent.direct.instanceOrNull<Person>())
        assertNotNull(child.direct.instanceOrNull<Person>())
    }

    @Test
    fun test_08_SimpleBinding_DIExtendOverride() {

        val parent = DI {
            bindSingleton { "parent" }
        }

        val child = DI {
            extend(parent)
            bindSingleton(overrides = true) { "child" }
        }

        assertEquals("parent", parent.direct.instance())
        assertEquals("child", child.direct.instance())
    }

    @Test
    fun test_09_SimpleBinding_DIExtendOverriddenInstance() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bindProvider { Foo("rootFoo") }
            bindProvider { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true, copy = Copy.None)
            bindProvider(overrides = true) { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)
    }

    @Test
    fun test_10_SimpleBinding_DIExtendOverriddenInstanceCopy() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bindProvider { Foo("rootFoo") }
            bindProvider { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true, copy = Copy.All)
            bindProvider(overrides = true) { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)
    }

    @Test
    fun test_11_SimpleBinding_DIExtendOverriddenSingletonSame() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bindProvider { Foo("rootFoo") }
            bindSingleton { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true)
            bindProvider(overrides = true) { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
    }

    @Test
    fun test_12_SimpleBinding_DIExtendOverriddenSingletonCopy() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bindProvider { Foo("rootFoo") }
            bindSingleton { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true, copy = Copy {
                copy all binding<Bar>()
            })
            bindProvider(overrides = true) { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertNotSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)
    }

    @Test
    fun test_13_SimpleBinding_DIExtendCopyAllBut() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = DI.direct {
            bindProvider { Foo("rootFoo") }
            bindSingleton { Bar(instance()) }
        }

        val sub = DI.direct {
            extend(root, allowOverride = true, copy = Copy.allBut {
                ignore all binding<Bar>()
            })
            bindProvider(overrides = true) { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
    }

}
