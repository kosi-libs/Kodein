package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_07_Error {

    @Test
    fun test_00_DependencyLoop() {

        val di = DI {
            bind<A>() with singleton { A(instance()) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance()) }
        }

        val ex = assertFailsWith<DI.DependencyLoopException> {
            di.direct.instance<A>()
        }

        assertEquals("""
Dependency recursion:
     bind<A>()
    ╔╩>bind<B>()
    ║  ╚>bind<C>()
    ║    ╚>bind<A>()
    ╚══════╝
        """.trim(), ex.message?.trim()
        )
    }

    @Suppress("unused")
    class Recurs0(val a: RecursA)
    @Suppress("unused")
    class RecursA(val b: RecursB)
    @Suppress("unused")
    class RecursB(val c: RecursC)
    @Suppress("unused")
    class RecursC(val a: RecursA)

    @Test fun test_01_DependencyLoopInClass() {

        val di = DI {
            bind() from provider { Recurs0(instance()) }
            bind() from provider { RecursA(instance()) }
            bind() from provider { RecursB(instance(tag = "yay")) }
            bind(tag = "yay") from provider { RecursC(instance()) }
        }

        assertFailsWith<DI.DependencyLoopException> {
            di.direct.instance<Recurs0>()
        }
    }


    @Test
    fun test_02_NoDependencyLoop() {

        val di = DI {
            bind<A>() with singleton { A(instance()) }
            bind<A>(tag = "root") with singleton { A(null) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance(tag = "root")) }
        }

        val a by di.instance<A>()
        assertNotNull(a.b?.c?.a)
    }

    @Test
    fun test_03_TypeNotFound() {

        val di = DI.direct {}

        assertEquals("No binding found for bind<Person>() with ? { ? }\nRegistered in this DI container:\n", assertFailsWith<DI.NotFoundException> { di.instance<Person>() }.message)

        assertFailsWith<DI.NotFoundException> { di.instance<FullName>() }

        assertFailsWith<DI.NotFoundException> { di.instance<List<*>>() }

        assertFailsWith<DI.NotFoundException> { di.instance<List<String>>() }
    }

    @Test
    fun test_04_NameNotFound() {

        val di = DI.direct {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        assertFailsWith<DI.NotFoundException> {
            di.instance<Person>(tag = "schtroumpf")
        }
    }

    @Test
    fun test_05_FactoryIsNotProvider() {

        val di = DI.direct {
            bind<Person>() with factory { name: String -> Person(name) }
        }

        assertFailsWith<DI.NotFoundException> {
            di.provider<Person>()
        }
    }

    @Test
    fun test_06_ProviderIsNotFactory() {

        val di = DI.direct {
            bind<Person>() with provider { Person() }
        }

        assertFailsWith<DI.NotFoundException> {
            di.factory<Int, Person>()
        }
    }

    @Test
    fun test_07_BindFromUnit() {

        fun unit(@Suppress("UNUSED_PARAMETER") i: Int = 42) {}

        val di = DI.direct {
            assertFailsWith<IllegalArgumentException> {
                bind() from factory { i: Int -> unit(i) }
            }
            assertFailsWith<IllegalArgumentException> {
                bind() from provider { unit() }
            }
            assertFailsWith<IllegalArgumentException> {
                bind() from instance(Unit)
            }
            assertFailsWith<IllegalArgumentException> {
                bind() from singleton { unit() }
            }

            bind<Unit>() with instance(unit())
        }

        assertSame(Unit, di.instance())
    }
}
