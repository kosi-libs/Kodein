package org.kodein.di

import org.kodein.di.bindings.ThreadLocal
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.concurrent.thread
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_01_Thread {

    // Only the JVM supports threads
    @Test
    fun test_00_ThreadSingletonBindingGetInstance() {
        val kodein = DI { bind<Person>() with singleton(ref = ThreadLocal) { Person() } }

        lateinit var tp1: Person

        val t = thread {
            tp1 = kodein.direct.instance()
            val tp2: Person by kodein.instance()

            assertSame(tp1, tp2)
        }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance()

        assertSame(p1, p2)

        t.join()

        assertNotSame(p1, tp1)
    }

    // Only the JVM supports threads
    @Test
    fun test_01_ThreadSingletonBindingGetProvider() {
        val kodein = DI { bind<Person>() with singleton(ref = ThreadLocal) { Person() } }

        lateinit var tp1: Person
        lateinit var tp2: () -> Person

        val t = thread {
            tp1 = kodein.direct.instance()
            tp2 = kodein.direct.provider()

            assertSame(tp1, tp2())
        }

        val p1: () -> Person by kodein.provider()
        val p2: () -> Person by kodein.provider()

        assertSame(p1(), p2())

        t.join()

        assertNotSame(p1(), tp1)
        assertSame(p1(), tp2())
    }

    // Only the JVM supports threads
    @Test
    fun test_02_threadMultiton() {
        val kodein = DI { bind() from multiton(ref = ThreadLocal) { name: String -> Person(name) } }

        lateinit var tp1: Person
        lateinit var tp3: Person

        val t = thread {
            tp1 = kodein.direct.instance(arg = "Salomon")
            val tp2: Person by kodein.instance(arg = "Salomon")
            tp3 = kodein.direct.instance(arg = "Laila")

            assertSame(tp1, tp2)
            assertNotEquals(tp1, tp3)
        }

        val p1: Person by kodein.instance(arg = "Salomon")
        val p2: Person by kodein.instance(arg = "Salomon")
        val p3: Person by kodein.instance(arg = "Laila")

        assertSame(p1, p2)
        assertNotEquals(p1, p3)

        t.join()

        assertNotSame(p1, tp1)
        assertEquals(p1, tp1)
        assertEquals("Salomon", p1.name)
        assertNotSame(p3, tp3)
        assertEquals(p3, tp3)
        assertEquals("Laila", p3.name)
    }

}
