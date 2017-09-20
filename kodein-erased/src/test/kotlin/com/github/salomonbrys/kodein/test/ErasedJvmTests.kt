package com.github.salomonbrys.kodein.test

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.bindings.AutoScope
import com.github.salomonbrys.kodein.bindings.ScopeRegistry
import com.github.salomonbrys.kodein.erased.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.concurrent.thread
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests {

    // Only the JVM supports up cast argument searching
    @Test fun test00_6_WithSubFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { p: Name -> Person(p.firstName) } }

        val p: Person = kodein.with(FullName("Salomon", "BRYS")).instance()

        assertEquals("Salomon", p.name)
    }

    // Only the JVM supports threads
    @Test fun test02_0_ThreadSingletonBindingGetInstance() {
        val kodein = Kodein { bind<Person>() with refSingleton(threadLocal) { Person() } }

        var tp1: Person? = null

        val t = thread {
            tp1 = kodein.instance()
            val tp2: Person = kodein.instance()

            assertSame(tp1, tp2)
        }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertSame(p1, p2)

        t.join()

        assertNotSame(p1, tp1)
    }

    // Only the JVM supports threads
    @Test fun test02_1_ThreadSingletonBindingGetProvider() {
        val kodein = Kodein { bind<Person>() with refSingleton(threadLocal) { Person() } }

        var tp1: Person? = null

        val t = thread {
            tp1 = kodein.provider<Person>().invoke()
            val tp2 = kodein.provider<Person>().invoke()

            assertSame(tp1, tp2)
        }

        val p1 = kodein.provider<Person>().invoke()
        val p2 = kodein.provider<Person>().invoke()

        assertSame(p1, p2)

        t.join()

        assertNotSame(p1, tp1)
    }

    // Only the JVM supports weak references
    @Suppress("UNUSED_VALUE")
    @Test fun test02_3_WeakSingletonBinding() {
        val kodein = Kodein { bind<Person>() with refSingleton(weakReference) { Person() } }

        var p1: Person? = kodein.instance()
        var p2: Person? = kodein.instance()
        assertSame(p1, p2)

        val id = System.identityHashCode(p1)

        p1 = null
        p2 = null
        System.gc()

        p1 = kodein.instance()

        assertNotEquals(id, System.identityHashCode(p1))
    }

    object test15Scope : AutoScope<Unit> {
        val registry = ScopeRegistry()
        override fun getRegistry(context: Unit) = registry
        override fun getContext() = Unit
    }

    // Only the JVM supports precise description
    @Test fun test15_0_BindingsDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>("thread-singleton") with refSingleton(threadLocal) { Person("ts") }
            bind<IPerson>("singleton") with singleton { Person("s") }
            bind<IPerson>("factory") with factory { name: String -> Person(name) }
            bind<IPerson>("instance") with instance(Person("i"))
            bind<String>("scoped") with scopedSingleton(test15Scope) { "" }
            bind<String>("auto-scoped") with autoScopedSingleton(test15Scope) { "" }
            constant("answer") with 42
        }

        val lines = kodein.container.bindings.description.lineSequence().map(String::trim).toList()
        assertEquals(8, lines.size)
        assertTrue("bind<IPerson>() with provider { Person }" in lines)
        assertTrue("bind<IPerson>(\"thread-singleton\") with refSingleton(threadLocal) { Person }" in lines)
        assertTrue("bind<IPerson>(\"singleton\") with singleton { Person }" in lines)
        assertTrue("bind<IPerson>(\"factory\") with factory { String -> Person }" in lines)
        assertTrue("bind<IPerson>(\"instance\") with instance ( Person )" in lines)
        assertTrue("bind<Int>(\"answer\") with instance ( Int )" in lines)
        assertTrue("bind<String>(\"scoped\") with scopedSingleton(ErasedJvmTests.test15Scope) { Unit -> String }" in lines)
        assertTrue("bind<String>(\"auto-scoped\") with autoScopedSingleton(ErasedJvmTests.test15Scope) { String }" in lines)
    }

    // Only the JVM supports precise description
    @Test fun test15_1_BindingsFullDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>("thread-singleton") with refSingleton(threadLocal) { Person("ts") }
            bind<IPerson>("singleton") with singleton { Person("s") }
            bind<IPerson>("factory") with factory { name: String -> Person(name) }
            bind<IPerson>("instance") with instance(Person("i"))
            bind<String>("scoped") with scopedSingleton(test15Scope) { "" }
            bind<String>("auto-scoped") with autoScopedSingleton(test15Scope) { "" }
            constant("answer") with 42
        }

        val lines = kodein.container.bindings.fullDescription.lineSequence().map(String::trim).toList()
        assertEquals(8, lines.size)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>() with provider { com.github.salomonbrys.kodein.test.Person }" in lines)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>(\"thread-singleton\") with refSingleton(com.github.salomonbrys.kodein.threadLocal) { com.github.salomonbrys.kodein.test.Person }" in lines)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>(\"singleton\") with singleton { com.github.salomonbrys.kodein.test.Person }" in lines)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>(\"factory\") with factory { kotlin.String -> com.github.salomonbrys.kodein.test.Person }" in lines)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>(\"instance\") with instance ( com.github.salomonbrys.kodein.test.Person )" in lines)
        assertTrue("bind<kotlin.Int>(\"answer\") with instance ( kotlin.Int )" in lines)
        assertTrue("bind<kotlin.String>(\"scoped\") with scopedSingleton(com.github.salomonbrys.kodein.test.ErasedJvmTests.test15Scope) { kotlin.Unit -> kotlin.String }" in lines)
        assertTrue("bind<kotlin.String>(\"auto-scoped\") with autoScopedSingleton(com.github.salomonbrys.kodein.test.ErasedJvmTests.test15Scope) { kotlin.String }" in lines)
    }

    // Only the JVM supports precise description
    @Test fun test15_2_RegisteredBindings() {
        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>("thread-singleton") with refSingleton(threadLocal) { Person("ts") }
            bind<IPerson>("singleton") with singleton { Person("s") }
            bind<IPerson>("factory") with factory { name: String -> Person(name) }
            bind<IPerson>("instance") with instance(Person("i"))
            constant("answer") with 42
        }

        val UnitToken = erased<Unit>()

        assertEquals(6, kodein.container.bindings.size)
        assertEquals("provider", kodein.container.bindings[Kodein.Key(Kodein.Bind(erased<IPerson>(), null), UnitToken)]?.factoryName())
        assertEquals("refSingleton(threadLocal)", kodein.container.bindings[Kodein.Key(Kodein.Bind(erased<IPerson>(), "thread-singleton"), UnitToken)]?.factoryName())
        assertEquals("singleton", kodein.container.bindings[Kodein.Key(Kodein.Bind(erased<IPerson>(), "singleton"), UnitToken)]?.factoryName())
        assertEquals("factory", kodein.container.bindings[Kodein.Key(Kodein.Bind(erased<IPerson>(), "factory"), erased<String>())]?.factoryName())
        assertEquals("instance", kodein.container.bindings[Kodein.Key(Kodein.Bind(erased<IPerson>(), "instance"), UnitToken)]?.factoryName())
        assertEquals("instance", kodein.container.bindings[Kodein.Key(Kodein.Bind(erased<Int>(), "answer"), UnitToken)]?.factoryName())
    }

    open class Test21_A
    class Test21_B : Test21_A()
    @Suppress("unused")
    class Test21_G<out T : Test21_A>

    // Only the JVM supports precise description
    @Test fun test21_0_SimpleDispString() {

        assertEquals("Int", erased<Int>().simpleDispString())

        assertEquals("Array<Char>", erased<Array<Char>>().simpleDispString())

        assertEquals("List<*>", erased<List<*>>().simpleDispString())
        assertEquals("List<String>", erasedComp1<List<String>, String>().simpleDispString())

        assertEquals("Map<String, Any>", erasedComp2<Map<String, *>, String, Any>().simpleDispString())
        assertEquals("Map<String, String>", erasedComp2<Map<String, String>, String, String>().simpleDispString())

        assertEquals("ErasedJvmTests.Test21_G<*>", erased<Test21_G<*>>().simpleDispString())
        assertEquals("ErasedJvmTests.Test21_G<ErasedJvmTests.Test21_A>", erasedComp1<Test21_G<Test21_A>, Test21_A>().simpleDispString())
        assertEquals("ErasedJvmTests.Test21_G<ErasedJvmTests.Test21_B>", erasedComp1<Test21_G<Test21_B>, Test21_B>().simpleDispString())
    }

    // Only the JVM supports precise description
    @Test fun test21_1_FullDispString() {

        assertEquals("kotlin.Int", erased<Int>().fullDispString())

        assertEquals("kotlin.Array<kotlin.Char>", erased<Array<Char>>().fullDispString())

        assertEquals("kotlin.collections.List<*>", erased<List<*>>().fullDispString())
        assertEquals("kotlin.collections.List<kotlin.String>", erasedComp1<List<String>, String>().fullDispString())

        assertEquals("kotlin.collections.Map<kotlin.String, kotlin.Any>", erasedComp2<Map<String, *>, String, Any>().fullDispString())
        assertEquals("kotlin.collections.Map<kotlin.String, kotlin.String>", erasedComp2<Map<String, String>, String, String>().fullDispString())

        assertEquals("com.github.salomonbrys.kodein.test.ErasedJvmTests.Test21_G<*>", erased<Test21_G<*>>().fullDispString())
        assertEquals("com.github.salomonbrys.kodein.test.ErasedJvmTests.Test21_G<com.github.salomonbrys.kodein.test.ErasedJvmTests.Test21_A>", erasedComp1<Test21_G<Test21_A>, Test21_A>().fullDispString())
        assertEquals("com.github.salomonbrys.kodein.test.ErasedJvmTests.Test21_G<com.github.salomonbrys.kodein.test.ErasedJvmTests.Test21_B>", erasedComp1<Test21_G<Test21_B>, Test21_B>().fullDispString())
    }

    // Only the JVM supports threads
    @Test fun test23_1_threadMultiton() {
        val kodein = Kodein { bind() from refMultiton(threadLocal) { name: String -> Person(name) } }

        var tp1: Person? = null
        var tp3: Person? = null

        val t = thread {
            tp1 = kodein.with("Salomon").instance()
            val tp2: Person = kodein.with("Salomon").instance()
            tp3 = kodein.with("Laila").instance()

            assertSame(tp1, tp2)
            assertNotEquals(tp1, tp3)
        }

        val p1: Person = kodein.with("Salomon").instance()
        val p2: Person = kodein.with("Salomon").instance()
        val p3: Person = kodein.with("Laila").instance()

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

    // Only the JVM supports weak references
    @Suppress("UNUSED_VALUE")
    @Test fun test23_2_WeakMultiton() {
        val kodein = Kodein { bind() from refMultiton(weakReference) { name: String -> Person(name) } }

        var p1: Person? = kodein.with("Salomon").instance()
        var p2: Person? = kodein.with("Salomon").instance()
        var p3: Person? = kodein.with("Laila").instance()
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

        p1 = kodein.with("Salomon").instance()
        p3 = kodein.with("Laila").instance()

        assertNotEquals(id1, System.identityHashCode(p1))
        assertNotEquals(id3, System.identityHashCode(p3))
    }
}
