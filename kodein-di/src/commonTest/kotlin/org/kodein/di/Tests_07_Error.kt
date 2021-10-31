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
     bind<A>
    ╔╩>bind<B>
    ║  ╚>bind<C>
    ║    ╚>bind<A>
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
            bind { provider { Recurs0(instance()) } }
            bind { provider { RecursA(instance()) } }
            bind { provider { RecursB(instance(tag = "yay")) } }
            bind(tag = "yay") { provider { RecursC(instance()) } }
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
    fun test_03_TypeNotFound_NoTree() {

        val di = DI.direct {}

        assertEquals("No binding found for Person", assertFailsWith<DI.NotFoundException> { di.instance<Person>() }.message)

        assertFailsWith<DI.NotFoundException> { di.instance<FullName>() }

        assertFailsWith<DI.NotFoundException> { di.instance<List<*>>() }

        assertFailsWith<DI.NotFoundException> { di.instance<List<String>>() }
    }

    @Test
    fun test_04_TypeNotFound_FullTree() {

        val di = DI.direct { fullContainerTreeOnError = true }

        assertEquals("No binding found for Person\nRegistered in this DI container:\n", assertFailsWith<DI.NotFoundException> { di.instance<Person>() }.message)

        assertFailsWith<DI.NotFoundException> { di.instance<FullName>() }

        assertFailsWith<DI.NotFoundException> { di.instance<List<*>>() }

        assertFailsWith<DI.NotFoundException> { di.instance<List<String>>() }
    }

    @Test
    fun test_05_NameNotFound() {

        val di = DI.direct {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        assertFailsWith<DI.NotFoundException> {
            di.instance<Person>(tag = "schtroumpf")
        }
    }

    @Test
    fun test_06_FactoryIsNotProvider() {

        val di = DI.direct {
            bind<Person>() with factory { name: String -> Person(name) }
        }

        assertFailsWith<DI.NotFoundException> {
            di.provider<Person>()
        }
    }

    @Test
    fun test_07_ProviderIsNotFactory() {

        val di = DI.direct {
            bind<Person>() with provider { Person() }
        }

        assertFailsWith<DI.NotFoundException> {
            di.factory<Int, Person>()
        }
    }

    @Test
    fun test_08_SimpleBinding_DependencyLoop() {

        val di = DI {
            bindSingleton { A(instance()) } 
            bindSingleton { B(instance()) } 
            bindSingleton { C(instance()) } 
        }

        val ex = assertFailsWith<DI.DependencyLoopException> {
            di.direct.instance<A>()
        }

        assertEquals("""
Dependency recursion:
     bind<A>
    ╔╩>bind<B>
    ║  ╚>bind<C>
    ║    ╚>bind<A>
    ╚══════╝
        """.trim(), ex.message?.trim()
        )
    }

    @Test
    fun test_09_SimpleBinding_NoDependencyLoop() {

        val di = DI {
            bindSingleton { A(instance()) }
            bindSingleton(tag = "root") { A(null) }
            bindSingleton { B(instance()) }
            bindSingleton { C(instance(tag = "root")) }
        }

        val a by di.instance<A>()
        assertNotNull(a.b?.c?.a)
    }

    @Test
    fun test_10_SimpleBinding_NameNotFound() {

        val di = DI.direct {
            bindProvider { Person() }
            bindProvider(tag = "named") { Person("Salomon") }
        }

        assertFailsWith<DI.NotFoundException> {
            di.instance<Person>(tag = "schtroumpf")
        }
    }

    @Test
    fun test_11_SimpleBinding_FactoryIsNotProvider() {

        val di = DI.direct {
            bindFactory { name: String -> Person(name) }
        }

        assertFailsWith<DI.NotFoundException> {
            di.provider<Person>()
        }
    }

    @Test
    fun test_12_SimpleBinding_ProviderIsNotFactory() {

        val di = DI.direct {
            bindProvider { Person() }
        }

        assertFailsWith<DI.NotFoundException> {
            di.factory<Int, Person>()
        }
    }
}
