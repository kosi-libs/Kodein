package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_23_Context {

    @Test
    fun test_00_lazyOnContext() {
        val di = DI {
            bind<String>() with contexted<String>().provider { "Salomon $context" }
        }

        var contextRetrieved = false

        val name: String by di.on { contextRetrieved = true ; "BRYS" } .instance()

        assertFalse(contextRetrieved)
        assertEquals("Salomon BRYS", name)
        assertTrue(contextRetrieved)
    }

    class T01(override val di: DI) : DIAware {
        var contextRetrieved = false
        override val diContext = diContext {
            contextRetrieved = true
            "BRYS"
        }
        val name: String by instance()
    }

    @Test
    fun test_01_lazyKContext() {
        val di = DI {
            bind<String>() with contexted<String>().provider { "Salomon $context" }
        }

        val t = T01(di)

        assertFalse(t.contextRetrieved)
        assertEquals("Salomon BRYS", t.name)
        assertTrue(t.contextRetrieved)
    }

    class Test02Ctx
    data class Test02Foo(val bar: Test02Bar)
    data class Test02Bar(val ctx: Test02Ctx?)

    @Test
    fun test_02_singletonEraseContext() {
        val di = DI {
            bind<Test02Foo>(tag = "ok") with singleton { Test02Foo(instance(tag = "ok")) }
            bind<Test02Bar>(tag = "ok") with provider { Test02Bar(null) }

            bind<Test02Foo>(tag = "ko") with singleton { Test02Foo(instance(tag = "ko")) }
            bind<Test02Bar>(tag = "ko") with contexted<Test02Ctx>().provider { Test02Bar(context) }
        }

        di.direct.instance<Test02Foo>(tag = "ok")

        assertFailsWith<DI.NotFoundException> {
            di.direct.instance<Test02Foo>(tag = "ko")
        }
    }

    @Test
    fun test_03_DirectBinding_lazyOnContext() {
        val di = DI {
            bind { contexted<String>().provider { "Salomon $context" } }
        }

        var contextRetrieved = false

        val name: String by di.on { contextRetrieved = true ; "BRYS" } .instance()

        assertFalse(contextRetrieved)
        assertEquals("Salomon BRYS", name)
        assertTrue(contextRetrieved)
    }

    @Test
    fun test_04_DirectBinding_lazyKContext() {
        val di = DI {
            bind { contexted<String>().provider { "Salomon $context" } }
        }

        val t = T01(di)

        assertFalse(t.contextRetrieved)
        assertEquals("Salomon BRYS", t.name)
        assertTrue(t.contextRetrieved)
    }

    @Test
    fun test_05_DirectBinding_singletonEraseContext() {
        val di = DI {
            bindSingleton(tag = "ok") { Test02Foo(instance(tag = "ok")) }
            bindProvider(tag = "ok") { Test02Bar(null) }

            bindSingleton(tag = "ko") { Test02Foo(instance(tag = "ko")) }
            bind(tag = "ko") { contexted<Test02Ctx>().provider { Test02Bar(context) } }
        }

        di.direct.instance<Test02Foo>(tag = "ok")

        assertFailsWith<DI.NotFoundException> {
            di.direct.instance<Test02Foo>(tag = "ko")
        }
    }
}
