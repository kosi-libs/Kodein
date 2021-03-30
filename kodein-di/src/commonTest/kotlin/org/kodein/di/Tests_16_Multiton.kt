package org.kodein.di

import org.kodein.di.bindings.SingleItemScopeRegistry
import org.kodein.di.bindings.UnboundedScope
import org.kodein.di.test.CloseableData
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_16_Multiton {

    @Test
    fun test_00_Multiton() {
        val di = DI { bind { multiton { name: String -> Person(name) } } }

        val p1: Person by di.instance(arg = "Salomon")
        val p2: Person by di.instance(fArg = { "Salomon" })
        val p3: Person by di.instance(arg = "Laila")
        val p4: Person by di.instance(fArg = { "Laila" })

        assertSame(p1, p2)
        assertSame(p3, p4)

        assertNotSame(p1, p3)

        assertEquals("Salomon", p1.name)
        assertEquals("Laila", p3.name)
    }

    @Test
    fun test_01_MultitonWithSingleItemScope() {
        val myScope = UnboundedScope(SingleItemScopeRegistry())

        val di = DI {
            bind<CloseableData>() with scoped(myScope).multiton { name: String -> CloseableData(name) }
        }

        val a: CloseableData by di.instance(arg = "one")
        val b: CloseableData by di.instance(arg = "one")
        assertSame(a, b)
        val c: CloseableData by di.instance(arg = "two")

        assertNotSame(a, c)
        assertTrue(a.closed)
        assertFalse(c.closed)

        val d: CloseableData by di.instance(arg = "one")
        assertNotSame(c, d)
        assertNotSame(a, d)
        assertTrue(c.closed)
        assertFalse(d.closed)
    }

    @Test
    fun test_02_NonSyncedMultiton() {
        val di = DI { bind { multiton(sync = false) { name: String -> Person(name) } }}

        val p1: Person by di.instance(arg = "Salomon")
        val p2: Person by di.instance(fArg = { "Salomon" })
        val p3: Person by di.instance(arg = "Laila")
        val p4: Person by di.instance(fArg = { "Laila" })

        assertSame(p1, p2)
        assertSame(p3, p4)

        assertNotSame(p1, p3)

        assertEquals("Salomon", p1.name)
        assertEquals("Laila", p3.name)
    }

    @Test
    fun test_00_SimpleMultiton() {
        val di = DI { bindMultiton { name: String -> Person(name) } }

        val p1: Person by di.instance(arg = "Salomon")
        val p2: Person by di.instance(fArg = { "Salomon" })
        val p3: Person by di.instance(arg = "Laila")
        val p4: Person by di.instance(fArg = { "Laila" })

        assertSame(p1, p2)
        assertSame(p3, p4)

        assertNotSame(p1, p3)

        assertEquals("Salomon", p1.name)
        assertEquals("Laila", p3.name)
    }

    @Test
    fun test_02_NonSyncedSimpleMultiton() {
        val di = DI { bindMultiton(sync = false) { name: String -> Person(name) } }

        val p1: Person by di.instance(arg = "Salomon")
        val p2: Person by di.instance(fArg = { "Salomon" })
        val p3: Person by di.instance(arg = "Laila")
        val p4: Person by di.instance(fArg = { "Laila" })

        assertSame(p1, p2)
        assertSame(p3, p4)

        assertNotSame(p1, p3)

        assertEquals("Salomon", p1.name)
        assertEquals("Laila", p3.name)
    }

}
