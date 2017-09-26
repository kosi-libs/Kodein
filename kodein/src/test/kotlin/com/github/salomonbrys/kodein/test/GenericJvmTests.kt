package com.github.salomonbrys.kodein.test

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.bindings.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.util.*
import kotlin.concurrent.thread
import kotlin.test.*


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests {

    @Test fun test00_00_ProviderBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with provider { Person() } }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertNotSame(p1, p2)
    }

    @Test fun test00_01_ProviderBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with provider { Person() } }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertNotSame(p1(), p2())
    }

    @Test fun test00_02_FactoryBindingGetFactory() {

        val kodein = Kodein {
            bind() from factory { name: String -> Person(name) }
        }

        val p1 = kodein.factory<String, Person>()
        val p2 = kodein.factory<String, Person>()

        assertNotSame(p1("Salomon"), p2("Salomon"))
    }

    @Test fun test00_03_FactoryBindingGetProvider() {

        val kodein = Kodein { bind() from factory { name: String -> Person(name) } }

        val f: (String) -> Person = kodein.factory<String, Person>()
        val p: () -> Person = f.toProvider { "Salomon" }

        assertEquals("Salomon", p().name)
    }

    @Test fun test00_04_WithFactoryGetProvider() {

        val kodein = Kodein { bind<Person>() with factory { name: String -> Person(name) } }

        val p: () -> Person = kodein.provider(arg = "Salomon")

        assertEquals("Salomon", p().name)
    }

    @Test fun test00_05_WithFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { name: String -> Person(name) } }

        val p: Person = kodein.instance(arg = "Salomon")

        assertEquals("Salomon", p.name)
    }

    @Test fun test00_06_WithSubFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { p: Name -> Person(p.firstName) } }

        val p: Person = kodein.instance(arg = FullName("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    @Test fun test00_07_WithGenericFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { l: List<*> -> Person(l.first().toString()) } }

        val p: Person = kodein.instance(arg = listOf("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    @Test fun test00_08_WithItfFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { p: IName -> Person(p.firstName) } }

        val p: Person = kodein.instance(arg = FullName("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    @Test fun test00_09_WithTwoItfFactoryGetInstance() {

        val kodein = Kodein {
            bind<Person>() with factory { p: IName -> Person(p.firstName) }
            bind<Person>() with factory { p: IFullName -> Person(p.firstName + " " + p.lastName) }
        }

        val p: Person = kodein.instance(arg = FullInfos("Salomon", "BRYS", 30))

        assertEquals("Salomon BRYS", p.name)
    }

    @Test fun test01_00_SingletonBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertSame(p1, p2)
    }

    @Test fun test01_01_SingletonBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertSame(p1(), p2())
    }

    @Test fun test02_00_ThreadSingletonBindingGetInstance() {
        val kodein = Kodein { bind<Person>() with refSingleton(threadLocal) { Person() } }

        var tp1: Person? = null

        val t = thread {
            tp1 = kodein.Instance(generic())
            val tp2: Person = kodein.Instance(generic())

            assertSame(tp1, tp2)
        }

        val p1: Person = kodein.Instance(generic())
        val p2: Person = kodein.Instance(generic())

        assertSame(p1, p2)

        t.join()

        assertNotSame(p1, tp1)
    }

    @Test fun test02_01_ThreadSingletonBindingGetProvider() {
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

    @Suppress("UNUSED_VALUE")
    @Test fun test02_03_WeakSingletonBinding() {
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


    @Test fun test03_00_InstanceBindingGetInstance() {

        val p = Person()

        val kodein = Kodein { bind() from instance(p) }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test fun test03_01_InstanceBindingGetProvider() {

        val p = Person()

        val kodein = Kodein { bind<Person>() with instance(p) }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }

    @Test fun test04_00_NullBindingGetInstance() {

        val kodein = Kodein {}

        val p = kodein.instanceOrNull<Person>()

        assertNull(p)
    }

    @Test fun test04_01_NullBindingGetProvider() {

        val kodein = Kodein {}

        val p = kodein.providerOrNull<Person>()

        assertNull(p)
    }

    @Test fun test04_02_NullBindingGetFactory() {

        val kodein = Kodein {}

        val p = kodein.factoryOrNull<String, Person>()

        assertNull(p)
    }

    @Test fun test05_00_NamedProviderBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance(tag = "named")

        assertNull(p1.name)
        assertEquals("Salomon", p2.name)
    }

    @Test fun test05_01_NamedProviderBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>(tag = "named")

        assertNull(p1().name)
        assertEquals("Salomon", p2().name)
    }

    @Test fun test06_00_NamedSingletonBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val p1: Person = kodein.instance(tag = "named")
        val p2: Person = kodein.instance(tag = "named")

        assertEquals("Salomon", p1.name)
        assertSame(p1, p2)
    }

    @Test fun test06_01_NamedSingletonBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val p1 = kodein.provider<Person>(tag = "named")
        val p2 = kodein.provider<Person>(tag = "named")

        assertEquals("Salomon", p1().name)
        assertSame(p1(), p2())
    }

    @Test fun test07_00_NamedInstanceBindingGetInstance() {

        val kodein = Kodein {
            bind<Person>() with instance(Person())
            bind<Person>(tag = "named") with instance(Person("Salomon"))
        }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance(tag = "named")
        val p3: Person = kodein.instance(tag = "named")

        assertNull(p1.name)
        assertEquals("Salomon", p2.name)
        assertNotSame(p1, p2)
        assertSame(p2, p3)
    }

    @Test fun test07_01_NamedInstanceBindingGetProvider() {

        val kodein = Kodein {
            bind<Person>() with instance(Person())
            bind<Person>(tag = "named") with instance(Person("Salomon"))
        }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>(tag = "named")
        val p3 = kodein.provider<Person>(tag = "named")

        assertNull(p1().name)
        assertEquals("Salomon", p2().name)
        assertNotSame(p1(), p2())
        assertSame(p2(), p3())
    }

    @Test fun test08_00_ConstantBindingGetInstance() {

        val kodein = Kodein {
            constant(tag = "answer") with 42
        }

        val c: Int = kodein.instance(tag = "answer")

        assertEquals(42, c)
    }

    @Test fun test08_01_ConstantBindingGetProvider() {

        val kodein = Kodein {
            constant(tag = "answer") with 42
        }

        val c = kodein.provider<Int>(tag = "answer")

        assertEquals(42, c())
    }

    @Test fun test08_02_ConstantBindingGetProviderPolymorphic() {

        val kodein = Kodein {
            constant(tag = "salomon") with Person("Salomon") as IPerson
        }

        val p = kodein.instance<IPerson>(tag = "salomon")

        assertEquals(Person("Salomon"), p)
    }

    @Test fun test09_00_DependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(instance()) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance()) }
        }

        assertFailsWith<Kodein.DependencyLoopException> {
            kodein.instance<A>()
        }
    }

    @Test fun test09_01_NoDependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(instance()) }
            bind<A>(tag = "root") with singleton { A(null) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance(tag = "root")) }
        }

        val a = kodein.instance<A>()
        assertNotNull(a.b?.c?.a)
    }

    @Test fun test09_02_TypeNotFound() {

        val kodein = Kodein {}

        assertFailsWith<Kodein.NotFoundException> {
            kodein.instance<Person>()
        }

        assertFailsWith<Kodein.NotFoundException> {
            kodein.instance<FullName>()
        }

        assertFailsWith<Kodein.NotFoundException> {
            kodein.instance<List<*>>()
        }

        assertFailsWith<Kodein.NotFoundException> {
            kodein.instance<List<String>>()
        }
    }

    @Test fun test09_03_NameNotFound() {

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        assertFailsWith<Kodein.NotFoundException> {
            kodein.instance<Person>(tag = "schtroumpf")
        }
    }

    @Test fun test09_04_FactoryIsNotProvider() {

        val kodein = Kodein {
            bind<Person>() with factory { name: String -> Person(name) }
        }

        assertFailsWith<Kodein.NotFoundException> {
            kodein.provider<Person>()
        }
    }

    @Test fun test09_05_ProviderIsNotFactory() {

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
        }

        assertFailsWith<Kodein.NotFoundException> {
            kodein.factory<Int, Person>()
        }
    }

    @Test fun test10_00_TypeErasure() {

        val la = listOf(A(null))
        val lb = listOf(B(null))

        val kodein = Kodein {
            bind<List<A>>() with instance( la )
            bind<List<B>>() with instance( lb )
        }

        assertSame(kodein.instance<List<A>>(), la)
        assertSame(kodein.instance<List<B>>(), lb)
    }

    class PersonLazy(kodein: LazyKodein) {
        val newPerson: () -> Person by kodein.provider()
        val salomon: Person by kodein.instance(tag = "named")
        val factory: (String) -> Person by kodein.factory(tag = "factory")
    }

    @Test fun test11_00_Class() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        val lazied = PersonLazy(LazyKodein(lazy { kodein }))
        assertNotSame(lazied.newPerson(), lazied.newPerson())
        assertEquals("Salomon", lazied.salomon.name)
        assertSame(lazied.salomon, lazied.salomon)
        assertNotSame(lazied.factory("Laila"), lazied.factory("Laila"))
        assertEquals("Laila", lazied.factory("Laila").name)
    }

    @Test fun test12_00_ModuleImport() {

        val personModule = Kodein.Module {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        val kodein = Kodein {
            import(personModule)
        }

        val lazied = PersonLazy(LazyKodein(lazy { kodein }))
        assertNotSame(lazied.newPerson(), lazied.newPerson())
        assertEquals("Salomon", lazied.salomon.name)
        assertSame(lazied.salomon, lazied.salomon)
        assertNotSame(lazied.factory("Laila"), lazied.factory("Laila"))
        assertEquals("Laila", lazied.factory("Laila").name)

        val kodein2 = Kodein {
            import(personModule)
        }

        assertSame(kodein.instance<Person>(tag = "named"), kodein.instance<Person>(tag = "named"))
        assertSame(kodein2.instance<Person>(tag = "named"), kodein2.instance<Person>(tag = "named"))
        assertNotSame(kodein.instance<Person>(tag = "named"), kodein2.instance<Person>(tag = "named"))
    }

    @Test fun test12_01_KodeinExtend() {

        val parent = Kodein {
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val child = Kodein {
            extend(parent)
            bind<Person>() with provider { Person() }
        }

        assertSame(parent.instance<Person>(tag = "named"), child.instance<Person>(tag = "named"))
        assertNull(parent.instanceOrNull<Person>())
        assertNotNull(child.instanceOrNull<Person>())
    }

    @Suppress("unused")
    class Recurs0(val a: RecursA)
    @Suppress("unused")
    class RecursA(val b: RecursB)
    @Suppress("unused")
    class RecursB(val c: RecursC)
    @Suppress("unused")
    class RecursC(val a: RecursA)

    @Test fun test13_00_RecursiveDependencies() {

        val kodein = Kodein {
            bind() from provider { Recurs0(instance()) }
            bind() from provider { RecursA(instance()) }
            bind() from provider { RecursB(instance(tag = "yay")) }
            bind(tag = "yay") from provider { RecursC(instance()) }
        }

        assertFailsWith<Kodein.DependencyLoopException> {
            kodein.instance<Recurs0>()
        }
    }

    class PersonInject {
        val injector = KodeinInjector()
        val newPerson: () -> Person by injector.provider()
        val salomon: Person by injector.instance(tag = "named")
        val factory: (String) -> Person by injector.factory(tag = "factory")
        val provider: () -> Person by injector.provider(tag = "factory", arg = "provided")
        val instance: Person by injector.instance(tag = "factory", arg = "reified")
    }

    @Test fun test14_00_InjectorInjected() {
        val injected = PersonInject()

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        injected.injector.inject(kodein)
        assertNotSame(injected.newPerson(), injected.newPerson())
        assertEquals("Salomon", injected.salomon.name)
        assertSame(injected.salomon, injected.salomon)
        assertNotSame(injected.factory("Laila"), injected.factory("Laila"))
        assertEquals("Laila", injected.factory("Laila").name)
        assertEquals("provided", injected.provider().name)
        assertNotSame(injected.provider(), injected.provider())
        assertEquals("reified", injected.instance.name)
        assertSame(injected.instance, injected.instance)
    }

    @Test fun test14_01_InjectorNotInjected() {
        val injected = PersonInject()

        assertFailsWith<KodeinInjector.UninjectedException> {
            injected.newPerson()
        }
    }

    object test15Scope : AutoScope<Unit> {
        val registry = ScopeRegistry()
        override fun getRegistry(context: Unit) = registry
        override fun getContext() = Unit
    }

    @Test fun test15_00_BindingsDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with refSingleton(threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            bind<String>(tag = "scoped") with scopedSingleton(test15Scope) { "" }
            bind<String>(tag = "auto-scoped") with autoScopedSingleton(test15Scope) { "" }
            constant(tag = "answer") with 42
        }

        val lines = kodein.container.bindings.description.lineSequence().map(String::trim).toList()
        assertEquals(8, lines.size)
        assertTrue("bind<IPerson>() with provider { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"thread-singleton\") with refSingleton(threadLocal) { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"singleton\") with singleton { Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"factory\") with factory { String -> Person }" in lines)
        assertTrue("bind<IPerson>(tag = \"instance\") with instance ( Person )" in lines)
        assertTrue("bind<Int>(tag = \"answer\") with instance ( Int )" in lines)
        assertTrue("bind<String>(tag = \"scoped\") with scopedSingleton(GenericJvmTests.test15Scope) { Unit -> String }" in lines)
        assertTrue("bind<String>(tag = \"auto-scoped\") with autoScopedSingleton(GenericJvmTests.test15Scope) { String }" in lines)
    }

    @Test fun test15_01_BindingsFullDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with refSingleton(threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            bind<String>(tag = "scoped") with scopedSingleton(test15Scope) { "" }
            bind<String>(tag = "auto-scoped") with autoScopedSingleton(test15Scope) { "" }
            constant(tag = "answer") with 42
        }

        val lines = kodein.container.bindings.fullDescription.lineSequence().map(String::trim).toList()
        assertEquals(8, lines.size)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>() with provider { com.github.salomonbrys.kodein.test.Person }" in lines)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>(tag = \"thread-singleton\") with refSingleton(com.github.salomonbrys.kodein.threadLocal) { com.github.salomonbrys.kodein.test.Person }" in lines)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>(tag = \"singleton\") with singleton { com.github.salomonbrys.kodein.test.Person }" in lines)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>(tag = \"factory\") with factory { kotlin.String -> com.github.salomonbrys.kodein.test.Person }" in lines)
        assertTrue("bind<com.github.salomonbrys.kodein.test.IPerson>(tag = \"instance\") with instance ( com.github.salomonbrys.kodein.test.Person )" in lines)
        assertTrue("bind<kotlin.Int>(tag = \"answer\") with instance ( kotlin.Int )" in lines)
        assertTrue("bind<kotlin.String>(tag = \"scoped\") with scopedSingleton(com.github.salomonbrys.kodein.test.GenericJvmTests.test15Scope) { kotlin.Unit -> kotlin.String }" in lines)
        assertTrue("bind<kotlin.String>(tag = \"auto-scoped\") with autoScopedSingleton(com.github.salomonbrys.kodein.test.GenericJvmTests.test15Scope) { kotlin.String }" in lines)
    }

    @Test fun test15_02_RegisteredBindings() {
        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>(tag = "thread-singleton") with refSingleton(threadLocal) { Person("ts") }
            bind<IPerson>(tag = "singleton") with singleton { Person("s") }
            bind<IPerson>(tag = "factory") with factory { name: String -> Person(name) }
            bind<IPerson>(tag = "instance") with instance(Person("i"))
            constant(tag = "answer") with 42
        }

        val UnitToken = generic<Unit>()

        assertEquals(6, kodein.container.bindings.size)
        assertEquals("provider", kodein.container.bindings[Kodein.Key(Kodein.Bind(generic<IPerson>(), null), UnitToken)]?.factoryName())
        assertEquals("refSingleton(threadLocal)", kodein.container.bindings[Kodein.Key(Kodein.Bind(generic<IPerson>(), "thread-singleton"), UnitToken)]?.factoryName())
        assertEquals("singleton", kodein.container.bindings[Kodein.Key(Kodein.Bind(generic<IPerson>(), "singleton"), UnitToken)]?.factoryName())
        assertEquals("factory", kodein.container.bindings[Kodein.Key(Kodein.Bind(generic<IPerson>(), "factory"), generic<String>())]?.factoryName())
        assertEquals("instance", kodein.container.bindings[Kodein.Key(Kodein.Bind(generic<IPerson>(), "instance"), UnitToken)]?.factoryName())
        assertEquals("instance", kodein.container.bindings[Kodein.Key(Kodein.Bind(generic<Int>(), "answer"), UnitToken)]?.factoryName())
    }

    @Test fun test16_01_ScopedSingleton() {

        val myScope = object : Scope<String> {
            val cache = HashMap<String, ScopeRegistry>()
            override fun getRegistry(context: String): ScopeRegistry = cache.getOrPut(context) { ScopeRegistry() }
        }
        val kodein = Kodein {
            bind<Person>() with scopedSingleton(myScope) { Person() }
        }

        val factory = kodein.factory<String, Person>()
        val one = factory("one")
        val two = factory("two")
        assertSame(one, factory("one"))
        assertNotSame(one, factory("two"))
        assertSame(two, factory("two"))

        myScope.cache.remove("one")

        assertNotSame(one, factory("one"))
        assertSame(two, factory("two"))
    }

    @Test fun test16_02_AutoScopedSingleton() {
        val myScope = object : AutoScope<Unit> {
            val registry = ScopeRegistry()
            override fun getRegistry(context: Unit) = registry
            override fun getContext() = Unit
        }

        val kodein = Kodein {
            bind<Person>() with autoScopedSingleton(myScope) { Person() }
        }

        val p = kodein.instance<Person>()
        assertSame(p, kodein.instance<Person>())

        myScope.registry.clear()

        assertNotSame(p, kodein.instance<Person>())
    }

    @Test fun test17_00_ExplicitOverride() {
        val kodein = Kodein {
            bind<String>(tag = "name") with instance("Benjamin")
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        assertEquals("Salomon", kodein.instance<String>(tag = "name"))
    }

    @Test fun test17_01_SilentOverride() {
        val kodein = Kodein(allowSilentOverride = true) {
            bind<String>(tag = "name") with instance("Benjamin")
            bind<String>(tag = "name") with instance("Salomon")
        }

        assertEquals("Salomon", kodein.instance<String>(tag = "name"))
    }

    @Test fun test17_02_SilentOverrideNotAllowed() {
        Kodein {
            bind<String>(tag = "name") with instance("Benjamin")

            assertFailsWith<Kodein.OverridingException> {
                bind<String>(tag = "name") with instance("Salomon")
            }
        }
    }

    @Test fun test17_03_MustNotOverride() {
        Kodein(allowSilentOverride = true) {
            bind<String>(tag = "name") with instance("Benjamin")

            assertFailsWith<Kodein.OverridingException> {
                bind<String>(tag = "name", overrides = false) with instance("Salomon")
            }
        }
    }

    @Test fun test17_04_OverrideWithSuper() {
        val kodein = Kodein(allowSilentOverride = true) {
            bind<String>(tag = "name") with instance("Salomon")
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " BRYS" }
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " the great" } // just kidding!
        }

        assertEquals("Salomon BRYS the great", kodein.instance<String>("name"))
    }

    @Test fun test17_05_DependencyLoopWithOverrides() {

        val kodein = Kodein {
            bind<String>(tag = "name") with singleton { instance<String>(tag = "title") + " Salomon " }
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " BRYS " }
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " of France" }
            bind<String>(tag = "title") with singleton { instance<String>(tag = "name") + " the great" }

        }

        assertFailsWith<Kodein.DependencyLoopException> {
            kodein.instance<String>(tag = "name")
        }
    }

    @Test fun test18_00_ModuleOverride() {
        val module = Kodein.Module {
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        val kodein = Kodein {
            bind<String>(tag = "name") with instance("Benjamin")
            import(module, allowOverride = true)
        }

        assertEquals("Salomon", kodein.instance<String>(tag = "name"))
    }

    @Test fun test18_01_ModuleForbiddenOverride() {
        val module = Kodein.Module {
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        Kodein {
            bind<String>(tag = "name") with instance("Benjamin")

            assertFailsWith<Kodein.OverridingException> {
                import(module)
            }
        }
    }

    @Test fun test18_02_ModuleImportsForbiddenOverride() {
        val subModule = Kodein.Module {
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        val module = Kodein.Module { import(subModule, allowOverride = true) }

        Kodein {
            bind<String>(tag = "name") with instance("Benjamin")

            assertFailsWith<Kodein.OverridingException> {
                import(module)
            }
        }
    }

    @Test fun test19_00_OnReadyCallback() {
        var passed = false
        Kodein {
            constant(tag = "name") with "Salomon"
            bind<Person>() with singleton { Person(instance(tag = "name")) }
            onReady {
                assertEquals("Salomon", instance<Person>().name)
                passed = true
            }
        }
        assertTrue(passed)
    }

    interface FakeLogger { val cls: Class<*> }

    class FakeLoggerImpl(override val cls: Class<*>) : FakeLogger

    class AwareTest(override val kodein: Kodein) : KodeinAware {
        val logger: FakeLogger = instance(arg = javaClass)
    }

    @Test fun test20_00_InjectForClass() {
        val kodein = Kodein {
            bind<FakeLogger>() with factory { cls: Class<*> -> FakeLoggerImpl(cls) }
        }

        val test = AwareTest(kodein)

        assertEquals(AwareTest::class.java, test.logger.cls)
    }

    open class Test21_A
    class Test21_B : Test21_A()
    @Suppress("unused")
    class Test21_G<out T : Test21_A>

    @Test fun test21_00_SimpleDispString() {

        assertEquals("Int", generic<Int>().simpleDispString())

        assertEquals("Array<Char>", generic<Array<Char>>().simpleDispString())

        assertEquals("List<*>", generic<List<*>>().simpleDispString())
        assertEquals("List<out String>", generic<List<String>>().simpleDispString())

        assertEquals("Map<String, *>", generic<Map<String, *>>().simpleDispString())
        assertEquals("Map<String, out String>", generic<Map<String, String>>().simpleDispString())

        assertEquals("GenericJvmTests.Test21_G<*>", generic<Test21_G<*>>().simpleDispString())
        assertEquals("GenericJvmTests.Test21_G<*>", generic<Test21_G<Test21_A>>().simpleDispString())
        assertEquals("GenericJvmTests.Test21_G<out GenericJvmTests.Test21_B>", generic<Test21_G<Test21_B>>().simpleDispString())
    }

    class Test22(kodein: Kodein) {
        val name: String by kodein.lazy.instance(tag = "name")
    }

    @Test fun test22_00_Lazy() {
        val kodein = Kodein {
            constant(tag = "name") with "Salomon"
        }
        val test = Test22(kodein)

        assertEquals("Salomon", test.name)
    }

    @Test fun test23_00_Multiton() {
        val kodein = Kodein { bind() from multiton { name: String -> Person(name) } }

        val p1: Person = kodein.instance(arg = "Salomon")
        val p2: Person = kodein.instance(arg = "Salomon")
        val p3: Person = kodein.instance(arg = "Laila")
        val p4: Person = kodein.instance(arg = "Laila")

        assertSame(p1, p2)
        assertSame(p3, p4)

        assertNotSame(p1, p3)

        assertEquals("Salomon", p1.name)
        assertEquals("Laila", p3.name)
    }

    @Test fun test23_01_threadMultiton() {
        val kodein = Kodein { bind() from refMultiton(threadLocal) { name: String -> Person(name) } }

        var tp1: Person? = null
        var tp3: Person? = null

        val t = thread {
            tp1 = kodein.instance(arg = "Salomon")
            val tp2: Person = kodein.instance(arg = "Salomon")
            tp3 = kodein.instance(arg = "Laila")

            assertSame(tp1, tp2)
            assertNotEquals(tp1, tp3)
        }

        val p1: Person = kodein.instance(arg = "Salomon")
        val p2: Person = kodein.instance(arg = "Salomon")
        val p3: Person = kodein.instance(arg = "Laila")

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

    @Suppress("UNUSED_VALUE")
    @Test fun test23_03_WeakMultiton() {
        val kodein = Kodein { bind() from refMultiton(weakReference) { name: String -> Person(name) } }

        var p1: Person? = kodein.instance(arg = "Salomon")
        var p2: Person? = kodein.instance(arg = "Salomon")
        var p3: Person? = kodein.instance(arg = "Laila")
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

        p1 = kodein.instance(arg = "Salomon")
        p3 = kodein.instance(arg = "Laila")

        assertNotEquals(id1, System.identityHashCode(p1))
        assertNotEquals(id3, System.identityHashCode(p3))
    }

    @Test fun test24_00_Callback() {
        var ready = false

        Kodein {
            onReady {
                ready = true
            }

            assertFalse(ready)
        }

        assertTrue(ready)
    }

    class Wedding(val him: Person, val her: Person)

    @Test fun test25_00_NewInstance() {
        val kodein = Kodein {
            bind<Person>(tag = "Author") with singleton { Person("Salomon") }
            bind<Person>(tag = "Spouse") with singleton { Person("Laila") }
        }

        val wedding = kodein.newInstance { Wedding(instance(tag = "Author"), instance(tag = "Spouse")) }
        assertEquals("Salomon", wedding.him.name)
        assertEquals("Laila", wedding.her.name)
    }

    @Test fun test26_00_MultiSet() {
        val kodein = Kodein {
            bind() from setBinding<Person>()

            bind<Person>().inSet() with singleton { Person("Salomon") }
            bind<Person>().inSet() with provider { Person("Laila") }
        }

        val persons1: Set<Person> = kodein.instance()

        assertTrue(Person("Salomon") in persons1)
        assertTrue(Person("Laila") in persons1)

        val persons2: Set<Person> = kodein.instance()

        val salomon1 = persons1.first { it.name == "Salomon" }
        val salomon2 = persons2.first { it.name == "Salomon" }

        val laila1 = persons1.first { it.name == "Laila" }
        val laila2 = persons2.first { it.name == "Laila" }

        assertSame(salomon1, salomon2)
        assertNotSame(laila1, laila2)
    }

    @Test fun test26_01_MultiMap() {
        val kodein = Kodein {
            bind() from setBinding<PersonEntry>()

            bind<PersonEntry>().inSet() with singleton { "so" to Person("Salomon") }
            bind<PersonEntry>().inSet() with provider { "loulou" to Person("Laila") }
        }

        val persons = kodein.instance<PersonEntries>().toMap()

        assertEquals(Person("Salomon"), persons["so"])
        assertEquals(Person("Laila"), persons["loulou"])
    }

    @Test fun test27_00_ExternalSource() {
        val kodein = Kodein {
            bind(tag = "him") from singleton { Person("Salomon") }

            val laila = Person("Laila")
            container.bindExternalSource { _, _, (bind) ->
                @Suppress("UNUSED_PARAMETER")
                fun _createAnyone(kodein: Kodein, key: Kodein.Key<*, *>, arg: Unit) = Person("Anyone")

                when (bind.type.jvmType) {
                    Person::class.java -> when (bind.tag) {
                        "her" -> bindingBaseProvider { laila }
                        null -> bindingBase(::_createAnyone)
                        else -> null
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

    @Test fun test28_00_ManualTyping() {

        open class Resource
        class SubResource : Resource()

        val resourceClass: Class<out Resource> = SubResource::class.java

        val kodein = Kodein {
            Bind(TT(resourceClass)) with SingletonBinding(TT(resourceClass)) { resourceClass.getConstructor().newInstance() }
        }

        kodein.instance<SubResource>()
    }

}
