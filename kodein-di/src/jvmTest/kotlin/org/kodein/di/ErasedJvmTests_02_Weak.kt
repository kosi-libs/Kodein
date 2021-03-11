package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_02_Weak {

    // Only the JVM supports weak references
    @Suppress("UNUSED_VALUE")
    @Test
    fun test_00_WeakSingletonBinding() {
        val kodein = DI { bind<Person>() with singleton(ref = weakReference) { Person() } }

        fun getId(): Int {
            val p1: Person by kodein.instance()
            val p2: Person by kodein.instance()
            assertSame(p1, p2)

            return System.identityHashCode(p1)
        }
        val id = getId()

        System.gc()

        val p: Person by kodein.instance()
        assertNotEquals(id, System.identityHashCode(p))
    }

    // Only the JVM supports weak references
    @Suppress("UNUSED_VALUE")
    @Test
    fun test_01_WeakMultiton() {
        val kodein = DI {
            bind { multiton(ref = weakReference) { name: String -> Person(name) } }
        }

        var p1: Person? = kodein.direct.instance(arg = "Salomon")
        var p2: Person? = kodein.direct.instance(arg = "Salomon")
        var p3: Person? = kodein.direct.instance(arg = "Laila")
        assertSame(p1, p2)
        assertNotSame(p1, p3)
        assertEquals("Salomon", p1?.name)
        assertEquals("Laila", p3?.name)

        val id1 = System.identityHashCode(p1)
        val id3 = System.identityHashCode(p3)

        p1 = null
        p2 = null
        p3 = null
        System.gc()

        p1 = kodein.direct.instance(arg = "Salomon")
        p3 = kodein.direct.instance(arg = "Laila")

        assertNotEquals(id1, System.identityHashCode(p1))
        assertNotEquals(id3, System.identityHashCode(p3))
    }

}
