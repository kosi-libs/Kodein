package com.github.salomonbrys.kodein.test

import com.github.salomonbrys.kodein.*
import junit.framework.TestCase
import org.junit.Assert.assertNotEquals
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.lang.reflect.ParameterizedType
import java.util.*
import kotlin.concurrent.thread

interface IPerson { val name: String? }

data class Person(override val name: String? = null ) : IPerson

data class A(val b: B?)
data class B(val c: C?)
data class C(val a: A?)

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinTests : TestCase() {

    @Test fun test00_0_ProviderBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with provider { Person() } }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertNotSame(p1, p2)
    }

    @Test fun test00_1_ProviderBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with provider { Person() } }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertNotSame(p1(), p2())
    }

    @Test fun test00_2_FactoryBindingGetFactory() {

        val kodein = Kodein { bind() from factory { name: String -> Person(name) } }

        val p1 = kodein.factory<String, Person>()
        val p2 = kodein.factory<String, Person>()

        assertNotSame(p1("Salomon"), p2("Salomon"))
    }

    @Test fun test00_3_FactoryBindingGetProvider() {

        val kodein = Kodein { bind() from factory { name: String -> Person(name) } }

        val p = kodein.factory<String, Person>().toProvider { "Salomon" }

        assertEquals("Salomon", p().name)
    }

    @Test fun test00_4_WithFactoryGetProvider() {

        val kodein = Kodein { bind<Person>() with factory { name: String -> Person(name) } }

        val p: () -> Person = kodein.with("Salomon").provider()

        assertEquals("Salomon", p().name)
    }

    @Test fun test00_5_WithFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { name: String -> Person(name) } }

        val p: Person = kodein.with("Salomon").instance()

        assertEquals("Salomon", p.name)
    }

    @Test fun test01_0_SingletonBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertSame(p1, p2)
    }

    @Test fun test01_1_SingletonBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertSame(p1(), p2())
    }

    @Test fun test02_0_ThreadSingletonBindingGetInstance() {
        val kodein = Kodein { bind<Person>() with threadSingleton { Person() } }

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

    @Test fun test02_1_ThreadSingletonBindingGetProvider() {
        val kodein = Kodein { bind<Person>() with threadSingleton { Person() } }

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

    @Test fun test03_0_InstanceBindingGetInstance() {

        val p = Person()

        val kodein = Kodein { bind() from instance(p) }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test fun test03_1_InstanceBindingGetProvider() {

        val p = Person()

        val kodein = Kodein { bind<Person>() with instance(p) }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }

    @Test fun test04_0_NullBindingGetInstance() {

        val kodein = Kodein {}

        val p = kodein.instanceOrNull<Person>()

        assertNull(p)
    }

    @Test fun test04_1_NullBindingGetProvider() {

        val kodein = Kodein {}

        val p = kodein.providerOrNull<Person>()

        assertNull(p)
    }

    @Test fun test04_2_NullBindingGetFactory() {

        val kodein = Kodein {}

        val p = kodein.factoryOrNull<String, Person>()

        assertNull(p)
    }

    @Test fun test05_0_NamedProviderBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with provider { Person("Salomon") }
        }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance("named")

        assertNull(p1.name)
        assertEquals("Salomon", p2.name)
    }

    @Test fun test05_1_NamedProviderBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with provider { Person("Salomon") }
        }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>("named")

        assertNull(p1().name)
        assertEquals("Salomon", p2().name)
    }

    @Test fun test06_0_NamedSingletonBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
        }

        val p1: Person = kodein.instance("named")
        val p2: Person = kodein.instance("named")

        assertEquals("Salomon", p1.name)
        assertSame(p1, p2)
    }

    @Test fun test06_1_NamedSingletonBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
        }

        val p1 = kodein.provider<Person>("named")
        val p2 = kodein.provider<Person>("named")

        assertEquals("Salomon", p1().name)
        assertSame(p1(), p2())
    }

    @Test fun test07_0_NamedInstanceBindingGetInstance() {

        val kodein = Kodein {
            bind<Person>() with instance(Person())
            bind<Person>("named") with instance(Person("Salomon"))
        }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance("named")
        val p3: Person = kodein.instance("named")

        assertNull(p1.name)
        assertEquals("Salomon", p2.name)
        assertNotSame(p1, p2)
        assertSame(p2, p3)
    }

    @Test fun test07_1_NamedInstanceBindingGetProvider() {

        val kodein = Kodein {
            bind<Person>() with instance(Person())
            bind<Person>("named") with instance(Person("Salomon"))
        }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>("named")
        val p3 = kodein.provider<Person>("named")

        assertNull(p1().name)
        assertEquals("Salomon", p2().name)
        assertNotSame(p1(), p2())
        assertSame(p2(), p3())
    }

    @Test fun test08_0_ConstantBindingGetInstance() {

        val kodein = Kodein {
            constant("answer") with 42
        }

        val c: Int = kodein.instance("answer")

        assertEquals(42, c)
    }

    @Test fun test08_1_ConstantBindingGetProvider() {

        val kodein = Kodein {
            constant("answer") with 42
        }

        val c = kodein.provider<Int>("answer")

        assertEquals(42, c())
    }

    @Test fun test08_2_ConstantBindingGetProviderPolymorphic() {

        val kodein = Kodein {
            constant("salomon") with Person("Salomon") as IPerson
        }

        val p = kodein.instance<IPerson>("salomon")

        assertEquals(Person("Salomon"), p)
    }

    @Test fun test09_0_DependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(instance()) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance()) }
        }

        assertThrown<Kodein.DependencyLoopException> {
            kodein.instance<A>()
        }
    }

    @Test fun test09_1_NoDependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(instance()) }
            bind<A>("root") with singleton { A(null) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance("root")) }
        }

        val a = kodein.instance<A>()
        assertNotNull(a.b?.c?.a)
    }

    @Test fun test09_2_TypeNotFound() {

        val kodein = Kodein {}

        assertThrown<Kodein.NotFoundException> {
            kodein.instance<Person>()
        }
    }

    @Test fun test09_3_NameNotFound() {

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with provider { Person("Salomon") }
        }

        assertThrown<Kodein.NotFoundException> {
            kodein.instance<Person>("schtroumpf")
        }
    }

    @Test fun test09_4_FactoryIsNotProvider() {

        val kodein = Kodein {
            bind<Person>() with factory { name: String -> Person(name) }
        }

        assertThrown<Kodein.NotFoundException> {
            kodein.provider<Person>()
        }
    }

    @Test fun test09_5_ProviderIsNotFactory() {

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
        }

        assertThrown<Kodein.NotFoundException> {
            kodein.factory<Int, Person>()
        }
    }

    @Test fun test10_0_TypeErasure() {

        val la = listOf(A(null))
        val lb = listOf(B(null))

        val kodein = Kodein {
            bind<List<A>>() with instance( la )
            bind<List<B>>() with instance( lb )
        }

        assertSame(kodein.instance<List<A>>(), la)
        assertSame(kodein.instance<List<B>>(), lb)
    }

    @Test fun test10_1_ParameterizedTypeWrap() {
        val typeLS1 = KodeinWrappedType((object : TypeReference<List<String>>() {}).trueType as ParameterizedType)
        val typeLS2 = KodeinWrappedType((object : TypeReference<List<String>>() {}).trueType as ParameterizedType)
        val typeLI = KodeinWrappedType((object : TypeReference<List<Int>>() {}).trueType as ParameterizedType)

        assertEquals(typeLS1, typeLS2)
        assertNotEquals(typeLS1, typeLI)
    }

    class PersonLazy(kodein: LazyKodein) {
        val newPerson: () -> Person by kodein.provider()
        val salomon: Person by kodein.instance("named")
        val factory: (String) -> Person by kodein.factory("factory")
    }

    @Test fun test11_0_Class() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
            bind<Person>("factory") with factory { name: String -> Person(name) }
        }

        val lazied = PersonLazy(LazyKodein(lazy { kodein }))
        assertNotSame(lazied.newPerson(), lazied.newPerson())
        assertEquals("Salomon", lazied.salomon.name)
        assertSame(lazied.salomon, lazied.salomon)
        assertNotSame(lazied.factory("Laila"), lazied.factory("Laila"))
        assertEquals("Laila", lazied.factory("Laila").name)
    }

    @Test fun test12_0_ModuleImport() {

        val personModule = Kodein.Module {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
            bind<Person>("factory") with factory { name: String -> Person(name) }
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

        assertSame(kodein.instance<Person>("named"), kodein.instance<Person>("named"))
        assertSame(kodein2.instance<Person>("named"), kodein2.instance<Person>("named"))
        assertNotSame(kodein.instance<Person>("named"), kodein2.instance<Person>("named"))
    }

    @Test fun test12_1_KodeinExtend() {

        val parent = Kodein {
            bind<Person>("named") with singleton { Person("Salomon") }
        }

        val child = Kodein {
            extend(parent)
            bind<Person>() with provider { Person() }
        }

        assertSame(parent.instance<Person>("named"), child.instance<Person>("named"))
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

    @Test fun test13_0_Recursivedependencies() {

        val kodein = Kodein {
            bind() from provider { Recurs0(instance()) }
            bind() from provider { RecursA(instance()) }
            bind() from provider { RecursB(instance("yay")) }
            bind("yay") from provider { RecursC(instance()) }
        }

        assertThrown<Kodein.DependencyLoopException> {
            kodein.instance<Recurs0>()
        }
    }

    class PersonInject() {
        val injector = KodeinInjector()
        val newPerson: () -> Person by injector.provider()
        val salomon: Person by injector.instance("named")
        val factory: (String) -> Person by injector.factory("factory")
        val provider: () -> Person by injector.with("provided").provider("factory")
        val instance: Person by injector.with("reified").instance("factory")
    }

    @Test fun test14_0_InjectorInjected() {
        val injected = PersonInject()

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
            bind<Person>("factory") with factory { name: String -> Person(name) }
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

    @Test fun test14_1_InjectorNotInjected() {
        val injected = PersonInject()

        assertThrown<KodeinInjector.UninjectedException> {
            injected.newPerson()
        }
    }

    object test15Scope : AutoScope<Unit> {
        val registry = ScopeRegistry()
        override fun getRegistry(context: Unit) = registry
        override fun getContext() = Unit
    }

    @Test fun test15_0_BindingsDescription() {

        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>("thread-singleton") with threadSingleton { Person("ts") }
            bind<IPerson>("singleton") with singleton { Person("s") }
            bind<IPerson>("factory") with factory { name: String -> Person(name) }
            bind<IPerson>("instance") with instance(Person("i"))
            bind<String>("scoped") with scopedSingleton(test15Scope) { "" }
            bind<String>("auto-scoped") with autoScopedSingleton(test15Scope) { "" }
            constant("answer") with 42
        }

        val lines = kodein.container.bindings.description.lineSequence().map { it.trim() }.toList()
        assertEquals(8, lines.size)
        assertTrue("bind<IPerson>() with provider { Person }" in lines)
        assertTrue("bind<IPerson>(\"thread-singleton\") with threadSingleton { Person }" in lines)
        assertTrue("bind<IPerson>(\"singleton\") with singleton { Person }" in lines)
        assertTrue("bind<IPerson>(\"factory\") with factory { String -> Person }" in lines)
        assertTrue("bind<IPerson>(\"instance\") with instance ( Person )" in lines)
        assertTrue("bind<Int>(\"answer\") with instance ( Int )" in lines)
        assertTrue("bind<String>(\"scoped\") with scopedSingleton(KodeinTests.test15Scope) { Unit -> String }" in lines)
        assertTrue("bind<String>(\"auto-scoped\") with autoScopedSingleton(KodeinTests.test15Scope) { String }" in lines)
        println(lines.joinToString("\n"))
    }

    @Test fun test15_1_RegisteredBindings() {
        val kodein = Kodein {
            bind<IPerson>() with provider { Person() }
            bind<IPerson>("thread-singleton") with threadSingleton { Person("ts") }
            bind<IPerson>("singleton") with singleton { Person("s") }
            bind<IPerson>("factory") with factory { name: String -> Person(name) }
            bind<IPerson>("instance") with instance(Person("i"))
            constant("answer") with 42
        }

        assertEquals(6, kodein.container.bindings.size)
        assertEquals("provider", kodein.container.bindings[Kodein.Key(Kodein.Bind(IPerson::class.java, null), Unit::class.java)]?.factoryName)
        assertEquals("threadSingleton", kodein.container.bindings[Kodein.Key(Kodein.Bind(IPerson::class.java, "thread-singleton"), Unit::class.java)]?.factoryName)
        assertEquals("singleton", kodein.container.bindings[Kodein.Key(Kodein.Bind(IPerson::class.java, "singleton"), Unit::class.java)]?.factoryName)
        assertEquals("factory", kodein.container.bindings[Kodein.Key(Kodein.Bind(IPerson::class.java, "factory"), String::class.java)]?.factoryName)
        assertEquals("instance", kodein.container.bindings[Kodein.Key(Kodein.Bind(IPerson::class.java, "instance"), Unit::class.java)]?.factoryName)
        assertEquals("instance", kodein.container.bindings[Kodein.Key(Kodein.Bind(Int::class.javaObjectType, "answer"), Unit::class.java)]?.factoryName)
    }

    @Test fun test16_1_ScopedSingleton() {

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

    @Test fun test16_2_AutoScopedSingleton() {
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

    @Test fun test17_0_ExplicitOverride() {
        val kodein = Kodein {
            bind<String>("name") with instance("Benjamin")
            bind<String>("name", overrides = true) with instance("Salomon")
        }

        assertEquals("Salomon", kodein.instance<String>("name"))
    }

    @Test fun test17_1_SilentOverride() {
        val kodein = Kodein(allowSilentOverride = true) {
            bind<String>("name") with instance("Benjamin")
            bind<String>("name") with instance("Salomon")
        }

        assertEquals("Salomon", kodein.instance<String>("name"))
    }

    @Test fun test17_2_SilentOverrideNotAllowed() {
        Kodein() {
            bind<String>("name") with instance("Benjamin")

            assertThrown<Kodein.OverridingException> {
                bind<String>("name") with instance("Salomon")
            }
        }
    }

    @Test fun test17_3_MustNotOverride() {
        Kodein(allowSilentOverride = true) {
            bind<String>("name") with instance("Benjamin")

            assertThrown<Kodein.OverridingException> {
                bind<String>("name", overrides = false) with instance("Salomon")
            }
        }
    }

    @Test fun test18_0_ModuleOverride() {
        val module = Kodein.Module {
            bind<String>("name", overrides = true) with instance("Salomon")
        }

        val kodein = Kodein {
            bind<String>("name") with instance("Benjamin")
            import(module, allowOverride = true)
        }

        assertEquals("Salomon", kodein.instance<String>("name"))
    }

    @Test fun test18_1_ModuleForbiddenOverride() {
        val module = Kodein.Module {
            bind<String>("name", overrides = true) with instance("Salomon")
        }

        Kodein {
            bind<String>("name") with instance("Benjamin")

            assertThrown<Kodein.OverridingException> {
                import(module)
            }
        }
    }

    @Test fun test18_2_ModuleImportsForbiddenOverride() {
        val subModule = Kodein.Module {
            bind<String>("name", overrides = true) with instance("Salomon")
        }

        val module = Kodein.Module { import(subModule, allowOverride = true) }

        Kodein {
            bind<String>("name") with instance("Benjamin")

            assertThrown<Kodein.OverridingException> {
                import(module)
            }
        }
    }

    @Test fun test19_0_onReadyCallback() {
        var passed = false
        Kodein {
            constant("name") with "Salomon"
            bind<Person>() with singleton { Person(instance("name")) }
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
        val logger: FakeLogger = withClass().instance()
    }

    @Test fun test20_0_injectForClass() {
        val kodein = Kodein {
            bind<FakeLogger>() with factory { cls: Class<*> -> FakeLoggerImpl(cls) }
        }

        val test = AwareTest(kodein)

        assertEquals(AwareTest::class.java, test.logger.cls)
    }
}
