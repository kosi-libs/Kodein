package org.kodein.test

import org.kodein.*
import org.kodein.bindings.ExternalSource
import org.kodein.bindings.NoScope
import org.kodein.bindings.Singleton
import org.kodein.bindings.externalFactory
import org.kodein.erased.*
import kotlin.concurrent.thread
import kotlin.test.*


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests {

    // Only the JVM supports up-casting
    @Test fun test00_05_WithSubFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { p: Name -> Person(p.firstName) } }

        val p: Person by kodein.instance(arg = FullName("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    // Only the JVM supports up-casting
    @Test fun test00_07_WithItfFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { p: IName -> Person(p.firstName) } }

        val p: Person by kodein.instance(arg = FullName("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }


    // Only the JVM supports threads
    @Test fun test02_00_ThreadSingletonBindingGetInstance() {
        val kodein = Kodein { bind<Person>() with singleton(ref = threadLocal) { Person() } }

        var tp1: Person? = null

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
    @Test fun test02_01_ThreadSingletonBindingGetProvider() {
        val kodein = Kodein { bind<Person>() with singleton(ref = threadLocal) { Person() } }

        /*lateinit*/ var tp1: Person? = null
        /*lateinit*/ var tp2: () -> Person = { throw IllegalStateException() }

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

    // Only the JVM supports weak references
    @Suppress("UNUSED_VALUE")
    @Test fun test02_03_WeakSingletonBinding() {
        val kodein = Kodein { bind<Person>() with singleton(ref = weakReference) { Person() } }

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

//    object test15Scope : AutoScope<Unit> {
//        val registry = ScopeRegistry()
//        override fun getRegistry(context: Unit) = registry
//        override fun getContext() = Unit
//    }

    // Only the JVM supports precise description
    @Test fun test15_00_BindingsDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with singleton(ref = threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
//            bind<String>(tag = "scoped") with scopedSingleton(test15Scope) { "" }
//            bind<String>(tag = "auto-scoped") with autoScopedSingleton(test15Scope) { "" }
            constant(tag = "answer") with 42
        }

        val lines = kodein.container.bindings.description().trim().lineSequence().map(String::trim).toList()
        assertEquals(6, lines.size)
        assertTrue("bind<IPerson>() with provider { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"thread-singleton\") with singleton(ref = threadLocal) { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"singleton\") with singleton { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"factory\") with factory { String -> Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"instance\") with instance ( Person )" in lines)
        assertTrue("bind<Int>(tag = \"answer\") with instance ( Int )" in lines)
//        assertTrue("bind<String>(tag = \"scoped\") with scopedSingleton(ErasedJvmTests.test15Scope) { Unit -> String }" in lines)
//        assertTrue("bind<String>(tag = \"auto-scoped\") with autoScopedSingleton(ErasedJvmTests.test15Scope) { String }" in lines)
    }

    // Only the JVM supports precise description
    @Test fun test15_01_BindingsFullDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with singleton(ref = threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
//            bind<String>(tag = "scoped") with scopedSingleton(test15Scope) { "" }
//            bind<String>(tag = "auto-scoped") with autoScopedSingleton(test15Scope) { "" }
            constant(tag = "answer") with 42
        }

        val lines = kodein.container.bindings.fullDescription().trim().lineSequence().map(String::trim).toList()
        assertEquals(6, lines.size)
        assertTrue("bind<org.kodein.test.IPerson>() with provider { org.kodein.test.Person }" in lines)
        assertTrue("bind<org.kodein.test.IPerson>(tag = \"thread-singleton\") with singleton(ref = org.kodein.threadLocal) { org.kodein.test.Person }" in lines)
        assertTrue("bind<org.kodein.test.IPerson>(tag = \"singleton\") with singleton { org.kodein.test.Person }" in lines)
        assertTrue("bind<org.kodein.test.IPerson>(tag = \"factory\") with factory { kotlin.String -> org.kodein.test.Person }" in lines)
        assertTrue("bind<org.kodein.test.IPerson>(tag = \"instance\") with instance ( org.kodein.test.Person )" in lines)
        assertTrue("bind<kotlin.Int>(tag = \"answer\") with instance ( kotlin.Int )" in lines)
//        assertTrue("bind<kotlin.String>(tag = \"scoped\") with scopedSingleton(ErasedJvmTests.test15Scope) { kotlin.Unit -> kotlin.String }" in lines)
//        assertTrue("bind<kotlin.String>(tag = \"auto-scoped\") with autoScopedSingleton(ErasedJvmTests.test15Scope) { kotlin.String }" in lines)
    }

    // Only the JVM supports precise description
    @Test fun test15_02_RegisteredBindings() {
        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with singleton(ref = threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            constant(tag = "answer") with 42
        }

        val UnitToken = erased<Unit>()

        assertEquals(6, kodein.container.bindings.size)
        assertEquals("provider", kodein.container.bindings[Kodein.Key(AnyToken, UnitToken, generic<IPerson>(), null)]!!.first().binding.factoryName())
        assertEquals("singleton(ref = threadLocal)", kodein.container.bindings[Kodein.Key(AnyToken, UnitToken, generic<IPerson>(), "thread-singleton")]!!.first().binding.factoryName())
        assertEquals("singleton", kodein.container.bindings[Kodein.Key(AnyToken, UnitToken, generic<IPerson>(), "singleton")]!!.first().binding.factoryName())
        assertEquals("factory", kodein.container.bindings[Kodein.Key(AnyToken, generic<String>(), generic<IPerson>(), "factory")]!!.first().binding.factoryName())
        assertEquals("instance", kodein.container.bindings[Kodein.Key(AnyToken, UnitToken, generic<IPerson>(), "instance")]!!.first().binding.factoryName())
        assertEquals("instance", kodein.container.bindings[Kodein.Key(AnyToken, UnitToken, generic<Int>(), "answer")]!!.first().binding.factoryName())
    }

    open class Test21_A
    class Test21_B : Test21_A()
    @Suppress("unused")
    class Test21_G<out T : Test21_A>

    // Only the JVM supports precise description
    @Test fun test21_00_SimpleDispString() {

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
    @Test fun test21_01_FullDispString() {

        assertEquals("kotlin.Int", erased<Int>().fullDispString())

        assertEquals("kotlin.Array<kotlin.Char>", erased<Array<Char>>().fullDispString())

        assertEquals("kotlin.collections.List<*>", erased<List<*>>().fullDispString())
        assertEquals("kotlin.collections.List<kotlin.String>", erasedComp1<List<String>, String>().fullDispString())

        assertEquals("kotlin.collections.Map<kotlin.String, kotlin.Any>", erasedComp2<Map<String, *>, String, Any>().fullDispString())
        assertEquals("kotlin.collections.Map<kotlin.String, kotlin.String>", erasedComp2<Map<String, String>, String, String>().fullDispString())

        assertEquals("org.kodein.test.ErasedJvmTests.Test21_G<*>", erased<Test21_G<*>>().fullDispString())
        assertEquals("org.kodein.test.ErasedJvmTests.Test21_G<org.kodein.test.ErasedJvmTests.Test21_A>", erasedComp1<Test21_G<Test21_A>, Test21_A>().fullDispString())
        assertEquals("org.kodein.test.ErasedJvmTests.Test21_G<org.kodein.test.ErasedJvmTests.Test21_B>", erasedComp1<Test21_G<Test21_B>, Test21_B>().fullDispString())
    }

    // Only the JVM supports threads
    @Test fun test23_01_threadMultiton() {
        val kodein = Kodein { bind() from multiton(ref = threadLocal) { name: String -> Person(name) } }

        var tp1: Person? = null
        var tp3: Person? = null

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

    // Only the JVM supports weak references
    @Suppress("UNUSED_VALUE")
    @Test fun test23_02_WeakMultiton() {
        val kodein = Kodein { bind() from multiton(ref = weakReference) { name: String -> Person(name) } }

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

    // Only the JVM supports class.java
    @Test fun test27_00_ExternalSource() {
        val kodein = Kodein {
            bind(tag = "him") from singleton { Person("Salomon") }

            val laila = Person("Laila")
            externalSource = ExternalSource { key ->
                @Suppress("UNUSED_PARAMETER")
                when (key.type.jvmType) {
                    Person::class.java -> {
                        when (key.tag) {
                            "her" -> externalFactory { laila }
                            null -> externalFactory { Person("Anyone") }
                            else -> null
                        }
                    }
                    else -> null
                }
            }
        } .direct

        assertNotNull(kodein.instanceOrNull<Person>())

        assertNull(kodein.instanceOrNull<Person>(tag = "no-one"))

        assertNotNull(kodein.instanceOrNull<Person>(tag = "him"))
        assertSame(kodein.instanceOrNull<Person>(tag = "him"), kodein.instanceOrNull<Person>(tag = "him"))

        assertNotNull(kodein.instanceOrNull<Person>(tag = "her"))
        assertSame(kodein.instanceOrNull<Person>(tag = "her"), kodein.instanceOrNull<Person>(tag = "her"))

        assertNotSame(kodein.instanceOrNull<Person>(), kodein.instanceOrNull<Person>())
        assertEquals(kodein.instanceOrNull<Person>(), kodein.instanceOrNull<Person>())
    }

    // Only the JVM supports class.java
    @Test fun test28_00_ManualTyping() {

        open class Resource
        class SubResource : Resource()

        val resourceClass: Class<out Resource> = SubResource::class.java

        val kodein = Kodein {
            Bind(TT(resourceClass)) with Singleton(NoScope(), AnyToken, TT(resourceClass)) { resourceClass.getConstructor().newInstance() }
        }

        kodein.instance<SubResource>()
    }


}
