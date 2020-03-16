package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_07_Error {

    @Test
    fun test_00_DependencyLoop() {

        val kodein = DI {
            bind<A>() with singleton { A(instance()) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance()) }
        }

        val ex = assertFailsWith<DI.DependencyLoopException> {
            kodein.direct.instance<A>()
        }

        assertEquals("""
Dependency recursion:
     bind<A>()
    ╔╩>bind<B>()
    ║  ╚>bind<C>()
    ║    ╚>bind<A>()
    ╚══════╝
            """.trim(), ex.message
        )
    }

    @Test
    fun test_01_DependencyLoopFullDescription() {

        val kodein = DI {
            fullDescriptionOnError = true
            bind<A>() with singleton { A(instance()) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance()) }
        }

        val ex = assertFailsWith<DI.DependencyLoopException> {
            kodein.direct.instance<A>()
        }

        assertEquals("""
Dependency recursion:
     bind<org.kodein.di.test.A>()
    ╔╩>bind<org.kodein.di.test.B>()
    ║  ╚>bind<org.kodein.di.test.C>()
    ║    ╚>bind<org.kodein.di.test.A>()
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

    @Test fun test_02_RecursiveDependencies() {

        val kodein = DI {
            bind() from provider { Recurs0(instance()) }
            bind() from provider { RecursA(instance()) }
            bind() from provider { RecursB(instance(tag = "yay")) }
            bind(tag = "yay") from provider { RecursC(instance()) }
        }

        assertFailsWith<DI.DependencyLoopException> {
            kodein.direct.instance<Recurs0>()
        }
    }

    @Test
    fun test_03_NoDependencyLoop() {

        val kodein = DI {
            bind<A>() with singleton { A(instance()) }
            bind<A>(tag = "root") with singleton { A(null) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance(tag = "root")) }
        }

        val a by kodein.instance<A>()
        assertNotNull(a.b?.c?.a)
    }

    @Test
    fun test_04_TypeNotFound() {

        val kodein = DI.direct {}

        assertFailsWith<DI.NotFoundException> {
            kodein.instance<Person>()
        }

        assertFailsWith<DI.NotFoundException> {
            kodein.instance<FullName>()
        }

        assertFailsWith<DI.NotFoundException> {
            kodein.instance<List<*>>()
        }

        assertFailsWith<DI.NotFoundException> {
            kodein.instance<List<String>>()
        }
    }

    @Test
    fun test_05_TypeNotFoundFullDescription() {

        val kodein = DI.direct {
            fullDescriptionOnError = true
        }

        assertEquals("No binding found for bind<org.kodein.di.test.Person>() with ? { ? }\nRegistered in this DI container:\n", assertFailsWith<DI.NotFoundException> { kodein.instance<Person>() }.message)
    }


    @Test
    fun test_06_NameNotFound() {

        val kodein = DI.direct {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        assertFailsWith<DI.NotFoundException> {
            kodein.instance<Person>(tag = "schtroumpf")
        }
    }

    @Test
    fun test_07_FactoryIsNotProvider() {

        val kodein = DI.direct {
            bind<Person>() with factory { name: String -> Person(name) }
        }

        assertFailsWith<DI.NotFoundException> {
            kodein.provider<Person>()
        }
    }

    @Test
    fun test_08_ProviderIsNotFactory() {

        val kodein = DI.direct {
            bind<Person>() with provider { Person() }
        }

        assertFailsWith<DI.NotFoundException> {
            kodein.factory<Int, Person>()
        }
    }

    @Test
    fun test_09_BindFromUnit() {

        fun unit(@Suppress("UNUSED_PARAMETER") i: Int = 42) {}

        val kodein = DI.direct {
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

        assertSame(Unit, kodein.instance())
    }
}
