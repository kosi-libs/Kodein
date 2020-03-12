package org.kodein.di.generic

import org.kodein.di.*
import org.kodein.di.bindings.UnboundedScope
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.IPerson
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_21_Description {

    @Test
    fun test_00_simpleKeySimpleDescription() {
        val key = DI.Key(
                contextType = generic<Any>(),
                argType = generic<Unit>(),
                type = generic<String>(),
                tag = null
        )

        assertEquals("bind<String>()", key.bindDescription)
        assertEquals("bind<String>() with ? { ? }", key.description)
    }

    @Test
    fun test_01_simpleKeyFullDescription() {
        val key = DI.Key(
                contextType = generic<Any>(),
                argType = generic<Unit>(),
                type = generic<String>(),
                tag = null
        )

        assertEquals("bind<kotlin.String>()", key.bindFullDescription)
        assertEquals("bind<kotlin.String>() with ? { ? }", key.fullDescription)
    }

    private data class MultiArgs(val s1: String, val s2: String)

    @Test
    fun test_02_complexKeySimpleDescription() {
        val key = DI.Key(
                contextType = generic<String>(),
                argType = generic<MultiArgs>(),
                type = generic<IntRange>(),
                tag = "tag"
        )

        assertEquals("bind<IntRange>(tag = \"tag\")", key.bindDescription)
        assertEquals("bind<IntRange>(tag = \"tag\") with ?<String>().? { GenericJvmTests_21_Description.MultiArgs -> ? }", key.description)
    }

    @Test
    fun test_03_complexKeyFullDescription() {
        val key = DI.Key(
                contextType = generic<String>(),
                argType = generic<MultiArgs>(),
                type = generic<IntRange>(),
                tag = "tag"
        )

        assertEquals("bind<kotlin.ranges.IntRange>(tag = \"tag\")", key.bindFullDescription)
        assertEquals("bind<kotlin.ranges.IntRange>(tag = \"tag\") with ?<kotlin.String>().? { org.kodein.di.generic.GenericJvmTests_21_Description.MultiArgs -> ? }", key.fullDescription)
    }

    object TestScope : UnboundedScope()

    @Test
    fun test_04_BindingsDescription() {

        val kodein = DI {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with singleton(ref = threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            bind<String>(tag = "scoped") with scoped(TestScope).singleton { "" }
            constant(tag = "answer") with 42
        }

        val lines = kodein.container.tree.bindings.description().trim().lineSequence().map(String::trim).toList()
        assertEquals(7, lines.size)
        assertTrue("bind<IPerson>() with provider { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"thread-singleton\") with singleton(ref = threadLocal) { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"singleton\") with singleton { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"factory\") with factory { String -> Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"instance\") with instance ( Person )" in lines)
        assertTrue("bind<String>(tag = \"scoped\") with scoped(GenericJvmTests_21_Description.TestScope).singleton { String }" in lines)
        assertTrue("bind<Int>(tag = \"answer\") with instance ( Int )" in lines)
    }

    @Test
    fun test_05_BindingsFullDescription() {

        val kodein = DI {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with singleton(ref = threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            bind<String>(tag = "scoped") with scoped(TestScope).singleton { "" }
            constant(tag = "answer") with 42
        }

        val lines = kodein.container.tree.bindings.fullDescription().trim().lineSequence().map(String::trim).toList()
        assertEquals(7, lines.size)
        assertTrue("bind<org.kodein.di.test.IPerson>() with provider { org.kodein.di.test.Person }" in lines)
        assertTrue("bind<org.kodein.di.test.IPerson>(tag = \"thread-singleton\") with singleton(ref = org.kodein.di.threadLocal) { org.kodein.di.test.Person }" in lines)
        assertTrue("bind<org.kodein.di.test.IPerson>(tag = \"singleton\") with singleton { org.kodein.di.test.Person }" in lines)
        assertTrue("bind<org.kodein.di.test.IPerson>(tag = \"factory\") with factory { kotlin.String -> org.kodein.di.test.Person }" in lines)
        assertTrue("bind<org.kodein.di.test.IPerson>(tag = \"instance\") with instance ( org.kodein.di.test.Person )" in lines)
        assertTrue("bind<kotlin.String>(tag = \"scoped\") with scoped(org.kodein.di.generic.GenericJvmTests_21_Description.TestScope).singleton { kotlin.String }" in lines)
        assertTrue("bind<kotlin.Int>(tag = \"answer\") with instance ( kotlin.Int )" in lines)
    }

    @Test
    fun test_06_RegisteredBindings() {
        val kodein = DI {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with singleton(ref = threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            constant(tag = "answer") with 42
        }

        assertEquals(6, kodein.container.tree.bindings.size)
        assertEquals("provider", kodein.container.tree.bindings[DI.Key(TypeToken.Any, TypeToken.Unit, generic<IPerson>(), null)]!!.first().binding.factoryName())
        assertEquals("singleton(ref = threadLocal)", kodein.container.tree.bindings[DI.Key(TypeToken.Any, TypeToken.Unit, generic<IPerson>(), "thread-singleton")]!!.first().binding.factoryName())
        assertEquals("singleton", kodein.container.tree.bindings[DI.Key(TypeToken.Any, TypeToken.Unit, generic<IPerson>(), "singleton")]!!.first().binding.factoryName())
        assertEquals("factory", kodein.container.tree.bindings[DI.Key(TypeToken.Any, generic<String>(), generic<IPerson>(), "factory")]!!.first().binding.factoryName())
        assertEquals("instance", kodein.container.tree.bindings[DI.Key(TypeToken.Any, TypeToken.Unit, generic<IPerson>(), "instance")]!!.first().binding.factoryName())
        assertEquals("instance", kodein.container.tree.bindings[DI.Key(TypeToken.Any, TypeToken.Unit, generic<Int>(), "answer")]!!.first().binding.factoryName())
    }

    open class A
    class B : A()
    @Suppress("unused")
    class G<out T : A>

    @Test
    fun test_07_SimpleDispString() {
        assertEquals("Int", generic<Int>().simpleDispString())

        assertEquals("Array<Char>", generic<Array<Char>>().simpleDispString())

        assertEquals("List<*>", generic<List<*>>().simpleDispString())
        assertEquals("List<out String>", generic<List<String>>().simpleDispString())

        assertEquals("Map<String, *>", generic<Map<String, *>>().simpleDispString())
        assertEquals("Map<String, out String>", generic<Map<String, String>>().simpleDispString())

        assertEquals("GenericJvmTests_21_Description.G<*>", generic<G<*>>().simpleDispString())
        assertEquals("GenericJvmTests_21_Description.G<*>", generic<G<A>>().simpleDispString())
        assertEquals("GenericJvmTests_21_Description.G<out GenericJvmTests_21_Description.B>", generic<G<B>>().simpleDispString())
    }

    @Test
    fun test_08_FullDispString() {
        assertEquals("kotlin.Int", generic<Int>().fullDispString())

        assertEquals("kotlin.Array<kotlin.Char>", generic<Array<Char>>().fullDispString())

        assertEquals("kotlin.collections.List<*>", generic<List<*>>().fullDispString())
        assertEquals("kotlin.collections.List<out kotlin.String>", generic<List<String>>().fullDispString())

        assertEquals("kotlin.collections.Map<kotlin.String, *>", generic<Map<String, *>>().fullDispString())
        assertEquals("kotlin.collections.Map<kotlin.String, out kotlin.String>", generic<Map<String, String>>().fullDispString())

        assertEquals("org.kodein.di.generic.GenericJvmTests_21_Description.G<*>", generic<G<*>>().fullDispString())
        assertEquals("org.kodein.di.generic.GenericJvmTests_21_Description.G<*>", generic<G<A>>().fullDispString())
        assertEquals("org.kodein.di.generic.GenericJvmTests_21_Description.G<out org.kodein.di.generic.GenericJvmTests_21_Description.B>", generic<G<B>>().fullDispString())
    }

}
