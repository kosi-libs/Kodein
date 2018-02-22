@file:Suppress("DEPRECATION", "unused")

package org.kodein.test

import org.kodein.*
import org.kodein.bindings.MultiItemScopeRegistry
import org.kodein.bindings.Scope
import org.kodein.bindings.SimpleScope
import org.kodein.bindings.SingleItemScopeRegistry
import org.kodein.erased.*
import kotlin.reflect.KClass
import kotlin.test.*


//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedTests {

    @Test fun test00_00_ProviderBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with provider { Person() } }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance()

        assertNotSame(p1, p2)
    }

    @Test fun test00_01_ProviderBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with provider { Person() } }

        val p1 by kodein.provider<Person>()
        val p2 by kodein.provider<Person>()

        assertNotSame(p1(), p2())
    }

    @Test fun test00_02_FactoryBindingGetFactory() {

        val kodein = Kodein {
            bind() from factory { name: String -> Person(name) }
        }

        val p1: (String) -> Person by kodein.factory()
        val p2: (String) -> Person by kodein.factory()

        assertNotSame(p1("Salomon"), p2("Salomon"))
    }

    @Test fun test00_03_WithFactoryGetProvider() {

        val kodein = Kodein { bind<Person>() with factory { name: String -> Person(name) } }

        val p: () -> Person by kodein.provider(arg = "Salomon")

        assertEquals("Salomon", p().name)
    }

    @Test fun test00_04_WithFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { name: String -> Person(name) } }

        val p: Person by kodein.instance(arg = "Salomon")

        assertEquals("Salomon", p.name)
    }

    @Test fun test00_06_WithGenericFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { l: List<*> -> Person(l.first().toString()) } }

        val p: Person by kodein.instance(arg = listOf("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    @Test fun test00_08_WithTwoItfFactoryGetInstance() {

        val kodein = Kodein {
            bind<Person>() with factory { p: IName -> Person(p.firstName) }
            bind<Person>() with factory { p: IFullName -> Person(p.firstName + " " + p.lastName) }
        }

        val p: Person by kodein.instance(arg = FullInfos("Salomon", "BRYS", 30))

        assertFailsWith<Kodein.NotFoundException> { p.name }
    }

    @Test fun test00_09_withFactoryLambdaArgument() {
        val kodein = Kodein {
            bind<() -> Unit>() with factory { f: () -> Unit -> f }
        }

        var passed = false
        val f = { passed = true }

        val run: () -> Unit by kodein.instance(arg = f)
        run.invoke()

        assertTrue(passed)
    }

    @Test fun test01_00_SingletonBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance()

        assertSame(p1, p2)
    }

    @Test fun test01_01_SingletonBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1: () -> Person by kodein.provider()
        val p2: () -> Person by kodein.provider()

        assertSame(p1(), p2())
    }

    @Test fun test03_00_InstanceBindingGetInstance() {

        val p = Person()

        val kodein = Kodein { bind() from instance(p) }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test fun test03_01_InstanceBindingGetProvider() {

        val p = Person()

        val kodein = Kodein { bind<Person>() with instance(p) }

        val p1: () -> Person by kodein.provider()
        val p2: () -> Person by kodein.provider()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }

    @Test fun test04_00_NullBindingGetInstance() {

        val kodein = Kodein {}

        val p: Person? by kodein.instanceOrNull()

        assertNull(p)
    }

    @Test fun test04_01_NullBindingGetProvider() {

        val kodein = Kodein {}

        val p: (() -> Person)? by kodein.providerOrNull()

        assertNull(p)
    }

    @Test fun test04_02_NullBindingGetFactory() {

        val kodein = Kodein {}

        val p: ((String) -> Person)? by kodein.factoryOrNull()

        assertNull(p)
    }

    @Test fun test05_00_NamedProviderBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance(tag = "named")

        assertNull(p1.name)
        assertEquals("Salomon", p2.name)
    }

    @Test fun test05_01_NamedProviderBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        val p1: () -> Person by kodein.provider()
        val p2: () -> Person by kodein.provider(tag = "named")

        assertNull(p1().name)
        assertEquals("Salomon", p2().name)
    }

    @Test fun test06_00_NamedSingletonBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val p1: Person by kodein.instance(tag = "named")
        val p2: Person by kodein.instance(tag = "named")

        assertEquals("Salomon", p1.name)
        assertSame(p1, p2)
    }

    @Test fun test06_01_NamedSingletonBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val p1: () -> Person by kodein.provider(tag = "named")
        val p2: () -> Person by kodein.provider(tag = "named")

        assertEquals("Salomon", p1().name)
        assertSame(p1(), p2())
    }

    @Test fun test07_00_NamedInstanceBindingGetInstance() {

        val kodein = Kodein {
            bind<Person>() with instance(Person())
            bind<Person>(tag = "named") with instance(Person("Salomon"))
        }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance(tag = "named")
        val p3: Person by kodein.instance(tag = "named")

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

        val p1: () -> Person by kodein.provider()
        val p2: () -> Person by kodein.provider(tag = "named")
        val p3: () -> Person by kodein.provider(tag = "named")

        assertNull(p1().name)
        assertEquals("Salomon", p2().name)
        assertNotSame(p1(), p2())
        assertSame(p2(), p3())
    }

    @Test fun test08_00_ConstantBindingGetInstance() {

        val kodein = Kodein {
            constant(tag = "answer") with 42
        }

        val c: Int by kodein.instance(tag = "answer")

        assertEquals(42, c)
    }

    @Test fun test08_01_ConstantBindingGetProvider() {

        val kodein = Kodein {
            constant(tag = "answer") with 42
        }

        val c: () -> Int by kodein.provider<Int>(tag = "answer")

        assertEquals(42, c())
    }

    @Test fun test08_02_ConstantBindingGetProviderPolymorphic() {

        val kodein = Kodein {
            constant(tag = "salomon") with Person("Salomon") as IPerson
        }

        val p: IPerson by kodein.instance(tag = "salomon")

        assertEquals(Person("Salomon"), p)
    }

    @Test fun test09_00_DependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(instance()) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance()) }
        }

        assertFailsWith<Kodein.DependencyLoopException> {
            kodein.direct.instance<A>()
        }
    }

    @Test fun test09_01_NoDependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(instance()) }
            bind<A>(tag = "root") with singleton { A(null) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance(tag = "root")) }
        }

        val a by kodein.instance<A>()
        assertNotNull(a.b?.c?.a)
    }

    @Test fun test09_02_TypeNotFound() {

        val kodein = Kodein {} .direct

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
        } .direct

        assertFailsWith<Kodein.NotFoundException> {
            kodein.instance<Person>(tag = "schtroumpf")
        }
    }

    @Test fun test09_04_FactoryIsNotProvider() {

        val kodein = Kodein {
            bind<Person>() with factory { name: String -> Person(name) }
        } .direct

        assertFailsWith<Kodein.NotFoundException> {
            kodein.provider<Person>()
        }
    }

    @Test fun test09_05_ProviderIsNotFactory() {

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
        } .direct

        assertFailsWith<Kodein.NotFoundException> {
            kodein.factory<Int, Person>()
        }
    }

    @Test fun test10_00_TypeErasure() {

        val la = listOf(A(null))
        val lb = listOf(B(null))

        val kodein = Kodein {
            Bind(erasedComp1<List<A>, A>()) with instance( la )
            Bind(erasedComp1<List<B>, B>()) with instance( lb )
        }

        assertSame(kodein.direct.Instance(erasedComp1<List<A>, A>(), null), la)
        assertSame(kodein.direct.Instance(erasedComp1<List<B>, B>(), null), lb)
    }

    class PersonContainer(kodein: Kodein) {
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

        val container = PersonContainer(kodein)
        assertNotSame(container.newPerson(), container.newPerson())
        assertEquals("Salomon", container.salomon.name)
        assertSame(container.salomon, container.salomon)
        assertNotSame(container.factory("Laila"), container.factory("Laila"))
        assertEquals("Laila", container.factory("Laila").name)
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

        val container = PersonContainer(kodein)
        assertNotSame(container.newPerson(), container.newPerson())
        assertEquals("Salomon", container.salomon.name)
        assertSame(container.salomon, container.salomon)
        assertNotSame(container.factory("Laila"), container.factory("Laila"))
        assertEquals("Laila", container.factory("Laila").name)

        val kodein2 = Kodein {
            import(personModule)
        }

        assertSame(kodein.direct.instance<Person>(tag = "named"), kodein.direct.instance<Person>(tag = "named"))
        assertSame(kodein2.direct.instance<Person>(tag = "named"), kodein2.direct.instance<Person>(tag = "named"))
        assertNotSame(kodein.direct.instance<Person>(tag = "named"), kodein2.direct.instance<Person>(tag = "named"))
    }

    @Test fun test12_01_KodeinExtend() {

        val parent = Kodein {
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val child = Kodein {
            extend(parent)
            bind<Person>() with provider { Person() }
        }

        assertSame(parent.direct.instance<Person>(tag = "named"), child.direct.instance<Person>(tag = "named"))
        assertNull(parent.direct.instanceOrNull<Person>())
        assertNotNull(child.direct.instanceOrNull<Person>())
    }

    @Test fun test12_02_KodeinExtendOverride() {

        val parent = Kodein {
            bind<String>() with singleton { "parent" }
        }

        val child = Kodein {
            extend(parent)
            bind<String>(overrides = true) with singleton { "child" }
        }

        assertEquals("parent", parent.direct.instance())
        assertEquals("child", child.direct.instance())
    }

    @Test fun test12_03_KodeinExtendOverriddenInstance() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with provider { Bar(instance()) }
        }.direct

        val sub = Kodein {
            extend(root, allowOverride = true, copy = Copy.None)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }.direct

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)
    }

    @Test fun test12_04_KodeinExtendOverriddenInstanceCopy() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with provider { Bar(instance()) }
        }.direct

        val sub = Kodein {
            extend(root, allowOverride = true, copy = Copy.All)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }.direct

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)
    }

    @Test fun test12_05_KodeinExtendOverriddenSingletonSame() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }.direct

        val sub = Kodein {
            extend(root, allowOverride = true)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }.direct

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
    }

    @Test fun test12_06_KodeinExtendOverriddenSingletonCopy() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }.direct

        val sub = Kodein {
            extend(root, allowOverride = true, copy = Copy {
                copy all binding<Bar>()
            })
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }.direct

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertNotSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)
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
            kodein.direct.instance<Recurs0>()
        }
    }

    class Test14_00(override val kodein: Kodein): KodeinAware {
        override val kodeinTrigger = KodeinTrigger()
        val newPerson: () -> Person by provider()
        val salomon: Person by instance(tag = "named")
        val pFactory: (String) -> Person by factory(tag = "factory")
        val pProvider: () -> Person by provider(tag = "factory", arg = "provided")
        val instance: Person by instance(tag = "factory", arg = "reified")
    }

    @Test fun test14_00_InjectorInjected() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        val injected = Test14_00(kodein)

        injected.kodeinTrigger.trigger()
        assertNotSame(injected.newPerson(), injected.newPerson())
        assertEquals("Salomon", injected.salomon.name)
        assertSame(injected.salomon, injected.salomon)
        assertNotSame(injected.pFactory("Laila"), injected.pFactory("Laila"))
        assertEquals("Laila", injected.pFactory("Laila").name)
        assertEquals("provided", injected.pProvider().name)
        assertNotSame(injected.pProvider(), injected.pProvider())
        assertEquals("reified", injected.instance.name)
        assertSame(injected.instance, injected.instance)
    }

    class Test14_01(override val kodein: Kodein): KodeinAware {
        override val kodeinTrigger = KodeinTrigger()
        val person: Person by instance()
    }

    @Test fun test14_01_CreatedAtInjection() {
        var created = false
        val kodein = Kodein {
            bind<Person>() with singleton { created = true ; Person() }
        }

        val container = Test14_01(kodein)

        assertFalse(created)
        container.kodeinTrigger.trigger()
        assertTrue(created)
    }

    @Test fun test16_00_AnyScopeSingleton() {
        val registry = MultiItemScopeRegistry()
        val myScope = object : Scope<Any?, Nothing?> {
            override fun getBindingContext(envContext: Any?) = null
            override fun getRegistry(receiver: Any?, envContext: Any?, bindContext: Nothing?) = registry
        }
        val kodein = Kodein {
            bind<Person>() with scoped(myScope).singleton { Person() }
        }

        val person: Person by kodein.instance()
        assertSame(person, kodein.direct.instance())

        registry.clear()

        assertNotSame(person, kodein.direct.instance())
    }

    @Test fun test16_01_ScopeSingleton() {

        val registries = mapOf("a" to SingleItemScopeRegistry(), "b" to SingleItemScopeRegistry())
        val myScope = object : SimpleScope<String> {
            override fun getRegistry(receiver: Any?, context: String) = registries[context]!!
        }
        val kodein = Kodein {
            bind<Person>() with scoped(myScope).singleton { Person() }
        }

        val a: Person by kodein.on(context = "a").instance()
        val b: Person by kodein.on(context = "b").instance()
        assertNotSame(a, b)
        assertSame(a, kodein.direct.on(context = "a").instance())
        assertSame(b, kodein.direct.on(context = "b").instance())

        registries.values.forEach { it.clear() }

        assertNotSame(a, kodein.direct.on(context = "a").instance())
        assertNotSame(b, kodein.direct.on(context = "b").instance())
    }

    @Test fun test16_02_ScopeIgnoredSingleton() {

        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
        }

        val a: Person by kodein.on(context = "a").instance()
        val b: Person by kodein.on(context = "b").instance()
        assertSame(a, b)
    }

    @Test fun test17_00_ExplicitOverride() {
        val kodein = Kodein {
            bind<String>(tag = "name") with instance("Benjamin")
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        assertEquals("Salomon", kodein.direct.instance<String>(tag = "name"))
    }

    @Test fun test17_01_SilentOverride() {
        val kodein = Kodein(allowSilentOverride = true) {
            bind<String>(tag = "name") with instance("Benjamin")
            bind<String>(tag = "name") with instance("Salomon")
        }

        assertEquals("Salomon", kodein.direct.instance<String>(tag = "name"))
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
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " of France" }
        }

        assertEquals("Salomon BRYS of France", kodein.direct.instance<String>("name"))
    }

    @Test fun test17_05_DependencyLoopWithOverrides() {

        val kodein = Kodein {
            bind<String>(tag = "name") with singleton { instance<String>(tag = "title") + " Salomon " }
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " BRYS " }
            bind<String>(tag = "name", overrides = true) with singleton { (overriddenInstance() as String) + " of France" }
            bind<String>(tag = "title") with singleton { instance<String>(tag = "name") + " the great" }

        }

        assertFailsWith<Kodein.DependencyLoopException> {
            @Suppress("UNUSED_VARIABLE")
            kodein.direct.instance<String>(tag = "name")
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

        assertEquals("Salomon", kodein.direct.instance<String>(tag = "name"))
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

    interface FakeLogger { val cls: KClass<*> }

    class FakeLoggerImpl(override val cls: KClass<*>) : FakeLogger

    class AwareTest(override val kodein: Kodein) : KodeinAware {
        val logger: FakeLogger by instance(arg = this::class)
    }

    @Test fun test20_00_InjectForClass() {
        val kodein = Kodein {
            bind<FakeLogger>() with factory { cls: KClass<*> -> FakeLoggerImpl(cls) }
        }

        val test = AwareTest(kodein)

        assertEquals(AwareTest::class, test.logger.cls)
    }

    class Test22(kodein: Kodein) {
        val name: String by kodein.instance(tag = "name")
    }

    @Test fun test22_00_Now() {
        val kodein = Kodein {
            constant(tag = "name") with "Salomon"
        }
        val test = Test22(kodein)

        assertEquals("Salomon", test.name)
    }

    @Test fun test23_00_Multiton() {
        val kodein = Kodein { bind() from multiton { name: String -> Person(name) } }

        val p1: Person by kodein.instance(arg = "Salomon")
        val p2: Person by kodein.instance(arg = "Salomon")
        val p3: Person by kodein.instance(arg = "Laila")
        val p4: Person by kodein.instance(arg = "Laila")

        assertSame(p1, p2)
        assertSame(p3, p4)

        assertNotSame(p1, p3)

        assertEquals("Salomon", p1.name)
        assertEquals("Laila", p3.name)
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

        val wedding by kodein.newInstance { Wedding(instance(tag = "Author"), instance(tag = "Spouse")) }
        assertEquals("Salomon", wedding.him.name)
        assertEquals("Laila", wedding.her.name)
    }

    @Test fun test26_00_MultiSet() {
        val kodein = Kodein {
            bind() from setBinding<IPerson>()

            bind<IPerson>().inSet() with singleton { Person("Salomon") }
            bind<IPerson>().inSet() with provider { Person("Laila") }
        }

        val persons1: Set<IPerson> by kodein.Instance(erasedSet())

        assertTrue(Person("Salomon") in persons1)
        assertTrue(Person("Laila") in persons1)

        val persons2: Set<IPerson> by kodein.Instance(erasedSet())

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

        val persons = kodein.direct.Instance<PersonEntries>(erasedSet(), null).toMap()

        assertEquals(Person("Salomon"), persons["so"])
        assertEquals(Person("Laila"), persons["loulou"])
    }

    class Test29 : KodeinAware {
        override lateinit var kodein: Kodein

        val name: String by instance()
    }

    @Test fun fun29_00_Late() {

        val test = Test29()

        test.kodein = Kodein {
            bind() from instance("Salomon")
        }

        assertEquals("Salomon", test.name)
    }

    @Test fun fun29_01_LateFail() {

        val test = Test29()

        assertFails { test.name }
    }

    @Test fun test29_02_LateLocal() {

        val kodein = LateInitKodein()

        val name: String by kodein.instance()

        kodein.baseKodein = Kodein {
            bind() from instance("Salomon")
        }

        assertEquals("Salomon", name)
    }

    @Test fun test29_03_LateLocalFail() {

        val kodein = LateInitKodein()

        val name: String by kodein.instance()

        assertFails { name.length }
    }

    @Test fun test29_04_LateLocalTrigger() {

        val trigger = KodeinTrigger()
        val base = LateInitKodein()
        val kodein = base.on(trigger = trigger)

        val name: String by kodein.instance()

        base.baseKodein = Kodein {
            bind() from instance("Salomon")
        }

        trigger.trigger()

        assertEquals("Salomon", name)
    }

    @Test fun test29_05_LateLocalTriggerFail() {

        val trigger = KodeinTrigger()
        val base = LateInitKodein()
        val kodein = base.on(trigger = trigger)

        @Suppress("UNUSED_VARIABLE")
        val name: String by kodein.instance()

        assertFails { trigger.trigger() }
    }

    @Test fun test30_00_allInstances() {
        val kodein = Kodein {
            bind<Person>() with provider { Person("Salomon") }
            bind<String>() with provider { "Laila" }
        }

        val instances: List<Any> by kodein.allInstances()
        assertTrue(Person("Salomon") in instances)
        assertTrue("Laila" in instances)
    }

}
