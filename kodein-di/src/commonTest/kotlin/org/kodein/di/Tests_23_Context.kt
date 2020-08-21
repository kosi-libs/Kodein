package org.kodein.di

import org.kodein.di.bindings.Clearable
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import org.kodein.di.bindings.Strong
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
        override val diContext = diContext(Strong) {
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

    @Test
    fun test_02_ClearableContext() {
        val di = DI {
            bind<String>() with provider { "Salomon $context" }
        }

        val context = Clearable("foo-bar")

        val result: String by di.onRef(context).instance()

        context.clear()

        assertEquals("Salomon NoContext", result)
    }

    class T03(override val di: DI): DIAware {
        val scopeRegistry = StandardScopeRegistry()

        val global: String by instance(tag = "global")
        val local: String by instance(tag = "local")

        override fun toString(): String = "T03"
    }

    @Test
    fun test_03_NoGlobalContext() {
        val T03Scope = Scope<T03> { it.scopeRegistry }

        val di = DI {
            bind<String>(tag = "global") with singleton { "Salomon $context" }
            bind<String>(tag = "local") with scoped(T03Scope).singleton { "Salomon $context" }
        }

        val t = T03(di)

        t.global
        t.local

        assertEquals("Salomon NoContext", t.global)
        assertEquals("Salomon T03", t.local)
    }

}
