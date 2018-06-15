package org.kodein.di.test

import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.di.erased.*
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

    object test15Scope : Scope<Any?, Nothing?, Any?> {
        private val registry = MultiItemScopeRegistry<Any?>()
        override fun getBindingContext(envContext: Any?) = null
        override fun getRegistry(receiver: Any?, context: Any?) = registry
    }

    // Only the JVM supports precise description
    @Test fun test15_00_BindingsDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with singleton(ref = threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            bind<String>(tag = "scoped") with scoped(test15Scope).singleton { "" }
            constant(tag = "answer") with 42
        }

        val lines = kodein.container.tree.bindings.description().trim().lineSequence().map(String::trim).toList()
        assertEquals(7, lines.size)
        assertTrue("bind<IPerson>() with provider { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"thread-singleton\") with singleton(ref = threadLocal) { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"singleton\") with singleton { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"factory\") with factory { String -> Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"instance\") with instance ( Person )" in lines)
        assertTrue("bind<String>(tag = \"scoped\") with scoped(ErasedJvmTests.test15Scope).singleton { String }" in lines)
        assertTrue("bind<Int>(tag = \"answer\") with instance ( Int )" in lines)
    }

    // Only the JVM supports precise description
    @Test fun test15_01_BindingsFullDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with singleton(ref = threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            bind<String>(tag = "scoped") with scoped(test15Scope).singleton { "" }
            constant(tag = "answer") with 42
        }

        val lines = kodein.container.tree.bindings.fullDescription().trim().lineSequence().map(String::trim).toList()
        assertEquals(7, lines.size)
        assertTrue("bind<org.kodein.di.test.IPerson>() with provider { org.kodein.di.test.Person }" in lines)
        assertTrue("bind<org.kodein.di.test.IPerson>(tag = \"thread-singleton\") with singleton(ref = org.kodein.di.threadLocal) { org.kodein.di.test.Person }" in lines)
        assertTrue("bind<org.kodein.di.test.IPerson>(tag = \"singleton\") with singleton { org.kodein.di.test.Person }" in lines)
        assertTrue("bind<org.kodein.di.test.IPerson>(tag = \"factory\") with factory { kotlin.String -> org.kodein.di.test.Person }" in lines)
        assertTrue("bind<org.kodein.di.test.IPerson>(tag = \"instance\") with instance ( org.kodein.di.test.Person )" in lines)
        assertTrue("bind<kotlin.String>(tag = \"scoped\") with scoped(org.kodein.di.test.ErasedJvmTests.test15Scope).singleton { kotlin.String }" in lines)
        assertTrue("bind<kotlin.Int>(tag = \"answer\") with instance ( kotlin.Int )" in lines)
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

        assertEquals(6, kodein.container.tree.bindings.size)
        assertEquals("provider", kodein.container.tree.bindings[Kodein.Key(AnyToken, UnitToken, generic<IPerson>(), null)]!!.first().binding.factoryName())
        assertEquals("singleton(ref = threadLocal)", kodein.container.tree.bindings[Kodein.Key(AnyToken, UnitToken, generic<IPerson>(), "thread-singleton")]!!.first().binding.factoryName())
        assertEquals("singleton", kodein.container.tree.bindings[Kodein.Key(AnyToken, UnitToken, generic<IPerson>(), "singleton")]!!.first().binding.factoryName())
        assertEquals("factory", kodein.container.tree.bindings[Kodein.Key(AnyToken, generic<String>(), generic<IPerson>(), "factory")]!!.first().binding.factoryName())
        assertEquals("instance", kodein.container.tree.bindings[Kodein.Key(AnyToken, UnitToken, generic<IPerson>(), "instance")]!!.first().binding.factoryName())
        assertEquals("instance", kodein.container.tree.bindings[Kodein.Key(AnyToken, UnitToken, generic<Int>(), "answer")]!!.first().binding.factoryName())
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

        assertEquals("org.kodein.di.test.ErasedJvmTests.Test21_G<*>", erased<Test21_G<*>>().fullDispString())
        assertEquals("org.kodein.di.test.ErasedJvmTests.Test21_G<org.kodein.di.test.ErasedJvmTests.Test21_A>", erasedComp1<Test21_G<Test21_A>, Test21_A>().fullDispString())
        assertEquals("org.kodein.di.test.ErasedJvmTests.Test21_G<org.kodein.di.test.ErasedJvmTests.Test21_B>", erasedComp1<Test21_G<Test21_B>, Test21_B>().fullDispString())
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
        val kodein = Kodein.direct {
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
        }

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

    @Test fun test30_00_AllInstances() {
        val kodein = Kodein {
            bind<Person>() with provider { Person("Salomon") }
            bind<String>() with provider { "Laila" }
        }

        val instances: List<Any> by kodein.allInstances()
        assertTrue(Person("Salomon") in instances)
        assertTrue("Laila" in instances)
    }

    // Only the JVM supports reflection
    @Test fun test31_03_MultipleMultiArgumentsAllFactories() {
        val kodein = Kodein {
            bind<Name>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
            bind<FullName>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
            bind<Name>() with factory { name: String, age: Int -> FullInfos(name, "BRYS", age) }
            bind<String>() with factory { firstName: String, lastName: String -> "Mr $firstName $lastName" }
        }

        val f by kodein.AllFactories<Multi2<String, String>, Name>(Multi2.erased(), erased())
        val df = kodein.direct.AllFactories<Multi2<String, String>, Name>(Multi2.erased(), erased())
        val p by kodein.allProviders<Multi2<String, String>, Name>(arg = M("Salomon", "BRYS"))
        val dp = kodein.direct.allProviders<Multi2<String, String>, Name>(arg = M("Salomon", "BRYS"))
        val i by kodein.allInstances<Multi2<String, String>, Name>(arg = M("Salomon", "BRYS"))
        val di = kodein.direct.allInstances<Multi2<String, String>, Name>(arg = M("Salomon", "BRYS"))

        assertAllEqual(2, f.size, df.size, p.size, dp.size, i.size, di.size)

        val values =
                f.map { it(M("Salomon", "BRYS")) } +
                        df.map { it(M("Salomon", "BRYS")) } +
                        p.map { it() } +
                        dp.map { it() } +
                        i +
                        di

        assertAllEqual(FullName("Salomon", "BRYS"), *values.toTypedArray())
    }

    // Only the JVM supports precise description
    @Test fun test32_01_simpleKeyFullDescription() {
        val key = Kodein.Key(
                contextType = erased<Any>(),
                argType = erased<Unit>(),
                type = erased<String>(),
                tag = null
        )

        assertEquals("bind<kotlin.String>()", key.bindFullDescription)
        assertEquals("bind<kotlin.String>() with ? { ? }", key.fullDescription)
    }

    // Only the JVM supports precise description
    @Test fun test32_03_complexKeyFullDescription() {
        val key = Kodein.Key(
                contextType = erased<String>(),
                argType = erasedComp2<Multi2<String, String>, String, String>(),
                type = erased<IntRange>(),
                tag = "tag"
        )

        assertEquals("bind<kotlin.ranges.IntRange>(tag = \"tag\")", key.bindFullDescription)
        assertEquals("bind<kotlin.ranges.IntRange>(tag = \"tag\") with ?<kotlin.String>().? { org.kodein.di.Multi2<kotlin.String, kotlin.String> -> ? }", key.fullDescription)
    }

    @Test fun test33_00_AllFactories() {
        val kodein = Kodein {
            bind<Name>() with factory { name: String -> Name(name) }
            bind<FullName>() with factory { name: String -> FullName(name, "BRYS") }
            bind<String>() with factory { name: String -> "Mr $name BRYS" }
        }

        val f by kodein.allFactories<String, Name>()
        val df = kodein.direct.allFactories<String, Name>()
        val p by kodein.allProviders<String, Name>(arg = "Salomon")
        val dp = kodein.direct.allProviders<String, Name>(arg = "Salomon")
        val fp by kodein.allProviders<String, Name>(fArg = { "Salomon" })
        val dfp = kodein.direct.allProviders<String, Name>(fArg = { "Salomon" })
        val i by kodein.allInstances<String, Name>(arg = "Salomon")
        val di = kodein.direct.allInstances<String, Name>(arg = "Salomon")
        val fi by kodein.allInstances<String, Name>(fArg = { "Salomon" })

        assertAllEqual(2, f.size, df.size, p.size, dp.size, fp.size, dfp.size, i.size, di.size, fi.size)

        arrayOf(
                f.map { it("Salomon") },
                df.map { it("Salomon") },
                p.map { it() },
                dp.map { it() },
                fp.map { it() },
                dfp.map { it() },
                i,
                di,
                fi
        ).forEach {
            assertTrue(Name("Salomon") in it)
            assertTrue(FullName("Salomon", "BRYS") in it)
        }
    }

    @Test fun test33_01_AllProviders() {
        val kodein = Kodein {
            bind<Name>() with provider { Name("Salomon") }
            bind<FullName>() with provider { FullName("Salomon", "BRYS") }
            bind<String>() with provider { "Mr Salomon BRYS" }
        }

        val providers by kodein.allProviders<IName>()

        assertEquals(2, providers.size)

        val values = providers.map { it() }

        assertTrue(Name("Salomon") in values)
        assertTrue(FullName("Salomon", "BRYS") in values)

        val dProviders = kodein.direct.allProviders<IName>()

        assertEquals(2, providers.size)

        val dValues = dProviders.map { it() }

        assertTrue(Name("Salomon") in dValues)
        assertTrue(FullName("Salomon", "BRYS") in dValues)
    }

    @Test fun test33_02_AllInstances() {
        val kodein = Kodein {
            bind<Name>() with provider { Name("Salomon") }
            bind<FullName>() with provider { FullName("Salomon", "BRYS") }
            bind<String>() with provider { "Mr Salomon BRYS" }
        }

        val values by kodein.allInstances<IName>()

        assertTrue(Name("Salomon") in values)
        assertTrue(FullName("Salomon", "BRYS") in values)

        val dValues = kodein.direct.allInstances<IName>()

        assertTrue(Name("Salomon") in dValues)
        assertTrue(FullName("Salomon", "BRYS") in dValues)
    }

    @Test fun test36_00_subTypeFactory() {
        val kodein = Kodein.direct {
            bind<IName>().subTypes() with { type ->
                when (type.jvmType) {
                    FullName::class.java -> singleton { FullName("Salomon", "BRYS") }
                    Name::class.java -> factory { _: Unit -> Name("Salomon") }
                    else -> throw IllegalStateException()
                }
            }
        }

        assertEquals(FullName::class.java, kodein.instance<FullName>().javaClass)
        assertEquals(FullName("Salomon", "BRYS"), kodein.instance())
        assertEquals<FullName>(kodein.instance(), kodein.instance())

        assertEquals(Name::class.java, kodein.instance<Name>().javaClass)
        assertEquals(Name("Salomon"), kodein.instance())
        assertEquals<Name>(kodein.instance(), kodein.instance())
    }

}
