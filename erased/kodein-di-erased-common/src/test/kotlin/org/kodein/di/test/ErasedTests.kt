@file:Suppress("DEPRECATION", "unused")

package org.kodein.di.test

import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.di.erased.*
import kotlin.reflect.KClass
import kotlin.test.*


@Suppress("ClassName")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
        val dp: () -> Person = kodein.direct.provider(arg = "Salomon")

        assertAllEqual("Salomon", p().name, dp().name)

        val fp: () -> Person by kodein.provider(fArg = { "Salomon" })
        val dfp: () -> Person = kodein.direct.provider(fArg = { "Salomon" })

        assertAllEqual("Salomon", fp().name, dfp().name)
    }

    @Test fun test00_04_WithFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { name: String -> Person(name) } }

        val p: Person by kodein.instance(arg = "Salomon")

        assertEquals("Salomon", p.name)

        val fp: Person by kodein.instance(fArg = { "Salomon" })

        assertEquals("Salomon", fp.name)
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

    @Test fun test00_09_WithFactoryLambdaArgument() {
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

    @Test fun test04_00_NullBindingProviderAndInstance() {

        val kodein = Kodein {}

        val i: Person? by kodein.instanceOrNull()
        val di: Person? = kodein.direct.instanceOrNull()
        val p: (() -> Person)? by kodein.providerOrNull()
        val dp: (() -> Person)? = kodein.direct.providerOrNull()

        assertAllNull(i, di, p, dp)
    }

    @Test fun test04_01_NullBindingGetFactory() {

        val kodein = Kodein {}

        val f: ((String) -> Person)? by kodein.factoryOrNull()
        val df: ((String) -> Person)? = kodein.direct.factoryOrNull()
        val p: (() -> Person)? by kodein.providerOrNull(arg = "Salomon")
        val dp: (() -> Person)? = kodein.direct.providerOrNull(arg = "Salomon")
        val fp: (() -> Person)? by kodein.providerOrNull(fArg = { "Salomon" })
        val dfp: (() -> Person)? = kodein.direct.providerOrNull(fArg = { "Salomon" })
        val i: Person? by kodein.instanceOrNull(arg = "Salomon")
        val di: Person? = kodein.direct.instanceOrNull(arg = "Salomon")
        val fi: Person? by kodein.instanceOrNull(fArg = { "Salomon" })

        assertAllNull(f, df, p, dp, fp, dfp, i, di, fi)
    }

    @Test fun test04_02_NonNullBindingProviderAndInstance() {

        val kodein = Kodein {
            bind<String>() with provider { "Salomon" }
        }

        val i: String? by kodein.instanceOrNull()
        val di: String? = kodein.direct.instanceOrNull()
        val p: (() -> String)? by kodein.providerOrNull()
        val dp: (() -> String)? = kodein.direct.providerOrNull()

        assertAllNotNull(i, di, p, dp)
        assertAllEqual("Salomon", i!!, di!!, p!!(), dp!!())
    }

    @Test fun test04_03_NonNullBindingGetFactory() {

        val kodein = Kodein {
            bind<String>() with factory { name: String -> "$name BRYS" }
        }

        val f: ((String) -> String)? by kodein.factoryOrNull()
        val df: ((String) -> String)? = kodein.direct.factoryOrNull()
        val p: (() -> String)? by kodein.providerOrNull(arg = "Salomon")
        val dp: (() -> String)? = kodein.direct.providerOrNull(arg = "Salomon")
        val fp: (() -> String)? by kodein.providerOrNull(fArg = { "Salomon" })
        val dfp: (() -> String)? = kodein.direct.providerOrNull(fArg = { "Salomon" })
        val i: String? by kodein.instanceOrNull(arg = "Salomon")
        val di: String? = kodein.direct.instanceOrNull(arg = "Salomon")
        val fi: String? by kodein.instanceOrNull(fArg = { "Salomon" })

        assertAllNotNull(f, df, p, dp, fp, dfp, i, di, fi)

        assertAllEqual("Salomon BRYS", f!!.invoke("Salomon"), df!!.invoke("Salomon"), p!!(), dp!!(), fp!!(), dfp!!(), i!!, di!!, fi!!)
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

        val c: () -> Int by kodein.provider(tag = "answer")

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

        val ex = assertFailsWith<Kodein.DependencyLoopException> {
            kodein.direct.instance<A>()
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

        val kodein = Kodein.direct {}

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

        val kodein = Kodein.direct {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with provider { Person("Salomon") }
        }

        assertFailsWith<Kodein.NotFoundException> {
            kodein.instance<Person>(tag = "schtroumpf")
        }
    }

    @Test fun test09_04_FactoryIsNotProvider() {

        val kodein = Kodein.direct {
            bind<Person>() with factory { name: String -> Person(name) }
        }

        assertFailsWith<Kodein.NotFoundException> {
            kodein.provider<Person>()
        }
    }

    @Test fun test09_05_ProviderIsNotFactory() {

        val kodein = Kodein.direct {
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
            Bind(erasedComp1<List<A>, A>()) with instance(la)
            Bind(erasedComp1<List<B>, B>()) with instance(lb)
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

        val personModule = Kodein.Module("test") {
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

        assertSame(kodein.direct.instance<Person>(tag = "named"), kodein.direct.instance(tag = "named"))
        assertSame(kodein2.direct.instance<Person>(tag = "named"), kodein2.direct.instance(tag = "named"))
        assertNotSame(kodein.direct.instance<Person>(tag = "named"), kodein2.direct.instance(tag = "named"))
    }

    @Test fun test12_01_ModuleImportTwice() {

        val module = Kodein.Module("test") {}

        val ex = assertFailsWith<IllegalStateException> {
            val kodein = Kodein {
                import(module)
                import(module)
            }
        }

        assertEquals("Module \"test\" has already been imported!", ex.message)
    }

    @Test fun test12_02_ModuleOnce() {

        val module = Kodein.Module("test") {
            bind<String>() with instance("Salomon")
        }

        val kodein = Kodein {
            importOnce(module)
            importOnce(module)
        }

        assertEquals("Salomon", kodein.direct.instance())
    }

    @Test fun test12_01_KodeinExtend() {

        val parent = Kodein {
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
        }

        val child = Kodein {
            extend(parent)
            bind<Person>() with provider { Person() }
        }

        assertSame(parent.direct.instance<Person>(tag = "named"), child.direct.instance(tag = "named"))
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

        val root = Kodein.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with provider { Bar(instance()) }
        }

        val sub = Kodein.direct {
            extend(root, allowOverride = true, copy = Copy.None)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("rootFoo", subBar.foo.name)
    }

    @Test fun test12_04_KodeinExtendOverriddenInstanceCopy() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with provider { Bar(instance()) }
        }

        val sub = Kodein.direct {
            extend(root, allowOverride = true, copy = Copy.All)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)
    }

    @Test fun test12_05_KodeinExtendOverriddenSingletonSame() {

        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = Kodein.direct {
            extend(root, allowOverride = true)
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
    }

    @Test fun test12_06_KodeinExtendOverriddenSingletonCopy() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = Kodein.direct {
            extend(root, allowOverride = true, copy = Copy {
                copy all binding<Bar>()
            })
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertNotSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
        assertEquals("subFoo", subBar.foo.name)
    }

    @Test fun test12_07_KodeinExtendCopyAllBut() {
        data class Foo(val name: String)
        data class Bar(val foo: Foo)

        val root = Kodein.direct {
            bind<Foo>() with provider { Foo("rootFoo") }
            bind<Bar>() with singleton { Bar(instance()) }
        }

        val sub = Kodein.direct {
            extend(root, allowOverride = true, copy = Copy.allBut {
                ignore all binding<Bar>()
            })
            bind<Foo>(overrides = true) with provider { Foo("subFoo") }
        }

        val subBar : Bar = sub.instance()
        val rootBar : Bar = root.instance()

        assertSame(rootBar, subBar)
        assertEquals("rootFoo", rootBar.foo.name)
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
        @Suppress("unused")
        val person: Person by instance()
    }

    @Test fun test14_01_CreatedAtInjection() {
        var created = false
        val kodein = Kodein {
            bind<Person>() with singleton { created = true; Person() }
        }

        val container = Test14_01(kodein)

        assertFalse(created)
        container.kodeinTrigger.trigger()
        assertTrue(created)
    }

    @Test fun test16_00_AnyScopeSingleton() {
        val registry = MultiItemScopeRegistry<Any?>()
        val myScope = object : Scope<Any?, Nothing?, Any?> {
            override fun getBindingContext(envContext: Any?): Nothing? = null
            override fun getRegistry(receiver: Any?, context: Any?) = registry
        }
        val kodein = Kodein {
            bind<Person>() with scoped(myScope).singleton { Person() }
        }

        assertTrue(registry.isEmpty())

        val person: Person by kodein.instance()
        assertSame(person, kodein.direct.instance())

        assertFalse(registry.isEmpty())

        registry.clear()

        assertTrue(registry.isEmpty())

        assertNotSame(person, kodein.direct.instance())
    }

    @Test fun test16_01_ScopeSingleton() {

        val registries = mapOf("a" to SingleItemScopeRegistry<Any?>(), "b" to SingleItemScopeRegistry<Any?>())
        val myScope = object : SimpleScope<String, Any?> {
            override fun getRegistry(receiver: Any?, context: String) = registries[context]!!
        }
        val kodein = Kodein {
            bind<Person>() with scoped(myScope).singleton { Person() }
        }

        assertTrue(registries["a"]!!.isEmpty())

        val a: Person by kodein.on(context = "a").instance()
        val b: Person by kodein.on(context = "b").instance()
        assertNotSame(a, b)
        assertSame(a, kodein.direct.on(context = "a").instance())
        assertSame(b, kodein.direct.on(context = "b").instance())

        assertFalse(registries["a"]!!.isEmpty())

        registries.values.forEach { it.clear() }

        assertTrue(registries["a"]!!.isEmpty())

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

    class CloseableData(val name: String? = null) : ScopeCloseable {
        var closed = false
            private set

        override fun close() {
            closed = true
        }
    }

    @Test fun test16_03_ScopeColeableSingleton() {

        val myScope = BasicScope(SingleItemScopeRegistry())

        val kodein = Kodein {
            bind<CloseableData>() with scoped(myScope).singleton { CloseableData() }
        }

        val a: CloseableData by kodein.instance()
        val b: CloseableData by kodein.instance()
        assertSame(a, b)
        myScope.registry.clear()
        val c: CloseableData by kodein.instance()

        assertNotSame(a, c)
        assertTrue(a.closed)
        assertFalse(c.closed)
    }

    @Test fun test17_00_ExplicitOverride() {
        val kodein = Kodein {
            bind<String>(tag = "name") with instance("Benjamin")
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        assertEquals("Salomon", kodein.direct.instance(tag = "name"))
    }

    @Test fun test17_01_SilentOverride() {
        val kodein = Kodein(allowSilentOverride = true) {
            bind<String>(tag = "name") with instance("Benjamin")
            bind<String>(tag = "name") with instance("Salomon")
        }

        assertEquals("Salomon", kodein.direct.instance(tag = "name"))
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

        assertEquals("Salomon BRYS of France", kodein.direct.instance("name"))
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
        val module = Kodein.Module("test") {
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        val kodein = Kodein {
            bind<String>(tag = "name") with instance("Benjamin")
            import(module, allowOverride = true)
        }

        assertEquals("Salomon", kodein.direct.instance(tag = "name"))
    }

    @Test fun test18_01_ModuleForbiddenOverride() {
        val module = Kodein.Module("test") {
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
        val subModule = Kodein.Module("test1") {
            bind<String>(tag = "name", overrides = true) with instance("Salomon")
        }

        val module = Kodein.Module("test2") { import(subModule, allowOverride = true) }

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
        val p2: Person by kodein.instance(fArg = { "Salomon" })
        val p3: Person by kodein.instance(arg = "Laila")
        val p4: Person by kodein.instance(fArg = { "Laila" })

        assertSame(p1, p2)
        assertSame(p3, p4)

        assertNotSame(p1, p3)

        assertEquals("Salomon", p1.name)
        assertEquals("Laila", p3.name)
    }

    @Test fun test23_03_MultitonWithSingleItemScope() {
        val myScope = BasicScope(SingleItemScopeRegistry())

        val kodein = Kodein {
            bind<CloseableData>() with scoped(myScope).multiton { name: String -> CloseableData(name) }
        }

        val a: CloseableData by kodein.instance(arg = "one")
        val b: CloseableData by kodein.instance(arg = "one")
        assertSame(a, b)
        val c: CloseableData by kodein.instance(arg = "two")

        assertNotSame(a, c)
        assertTrue(a.closed)
        assertFalse(c.closed)

        val d: CloseableData by kodein.instance(arg = "one")
        assertNotSame(c, d)
        assertNotSame(a, d)
        assertTrue(c.closed)
        assertFalse(d.closed)
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

    @Test fun test25_01_DirectNewInstance() {
        val kodein = Kodein.direct {
            bind<Person>(tag = "Author") with singleton { Person("Salomon") }
            bind<Person>(tag = "Spouse") with singleton { Person("Laila") }
        }

        val wedding = kodein.newInstance { Wedding(instance(tag = "Author"), instance(tag = "Spouse")) }
        assertEquals("Salomon", wedding.him.name)
        assertEquals("Laila", wedding.her.name)
    }

    @Test fun test26_00_MultiSet() {
        val kodein = Kodein {
            bind() from setBinding<IPerson>()

            bind<IPerson>().inSet() with singleton { Person("Salomon") }
            bind<IPerson>().inSet() with provider { Person("Laila") }

            Bind<List<IPerson>>(erasedList()) with provider { Instance<Set<IPerson>>(erasedSet(), null).toList() }
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

        val list: List<IPerson> by kodein.Instance(erasedList())
        assertEquals(persons1.toList(), list)
    }

    @Test fun test26_01_MultiMap() {
        val kodein = Kodein {
            bind() from setBinding<PersonEntry>()

            bind<PersonEntry>().inSet() with singleton { "so" to Person("Salomon") }
            bind<PersonEntry>().inSet() with provider { "loulou" to Person("Laila") }

            Bind<Map<String, Person>>(erasedMap()) with provider { Instance<PersonEntries>(erasedSet(), null).toMap() }
        }

        val persons: Map<String, Person> = kodein.direct.Instance(erasedMap(), null)

        assertEquals(Person("Salomon"), persons["so"])
        assertEquals(Person("Laila"), persons["loulou"])
    }

    class Test29 : KodeinAware {
        override lateinit var kodein: Kodein

        val name: String by instance()
    }

    @Test fun test29_00_Late() {

        val test = Test29()

        test.kodein = Kodein {
            bind() from instance("Salomon")
        }

        assertEquals("Salomon", test.name)
    }

    @Test fun test29_01_LateFail() {

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

    @Test fun test31_00_MultiArgumentsFactory() {
        val kodein = Kodein {
            bind<FullName>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
        }

        val i: FullName by kodein.instance(arg = M("Salomon", "BRYS"))
        val ni: FullName? by kodein.instanceOrNull(arg = M("Salomon", 42))
        val nni: FullName? by kodein.instanceOrNull(arg = M("Salomon", "BRYS"))
        val di: FullName = kodein.direct.instance(arg = M("Salomon", "BRYS"))
        val dni: FullName? = kodein.direct.instanceOrNull(arg = M("Salomon", 42))
        val dnni: FullName? = kodein.direct.instanceOrNull(arg = M("Salomon", "BRYS"))
        val p: () -> FullName by kodein.provider(arg = M("Salomon", "BRYS"))
        val np: (() -> FullName)? by kodein.providerOrNull(arg = M("Salomon", 42))
        val nnp: (() -> FullName)? by kodein.providerOrNull(arg = M("Salomon", "BRYS"))
        val dp: () -> FullName = kodein.direct.provider(arg = M("Salomon", "BRYS"))
        val dnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = M("Salomon", 42))
        val dnnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = M("Salomon", "BRYS"))

        assertAllNull(ni, dni, np, dnp)
        assertAllNotNull(nni, dnni, nnp, dnnp)

        assertAllEqual(FullName("Salomon", "BRYS"), i, nni!!, di, dnni, p(), nnp!!(), dp(), dnnp!!())
    }

    @Test fun test31_01_MultiArgumentsMultiton() {
        val kodein = Kodein {
            bind<FullName>() with multiton { firstName: String, lastName: String -> FullName(firstName, lastName) }
        }

        val i: FullName by kodein.instance(arg = M("Salomon", "BRYS"))
        val ni: FullName? by kodein.instanceOrNull(arg = M("Salomon", 42))
        val nni: FullName? by kodein.instanceOrNull(arg = M("Salomon", "BRYS"))
        val di: FullName = kodein.direct.instance(arg = M("Salomon", "BRYS"))
        val dni: FullName? = kodein.direct.instanceOrNull(arg = M("Salomon", 42))
        val dnni: FullName? = kodein.direct.instanceOrNull(arg = M("Salomon", "BRYS"))
        val p: () -> FullName by kodein.provider(arg = M("Salomon", "BRYS"))
        val np: (() -> FullName)? by kodein.providerOrNull(arg = M("Salomon", 42))
        val nnp: (() -> FullName)? by kodein.providerOrNull(arg = M("Salomon", "BRYS"))
        val dp: () -> FullName = kodein.direct.provider(arg = M("Salomon", "BRYS"))
        val dnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = M("Salomon", 42))
        val dnnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = M("Salomon", "BRYS"))

        assertAllNull(ni, dni, np, dnp)
        assertAllNotNull(nni, dnni, nnp, dnnp)

        assertAllEqual(FullName("Salomon", "BRYS"), i, nni!!, di, dnni, p(), nnp!!(), dp(), dnnp!!())
    }

    @Test fun test31_02_MultiArgumentsFactoryBadType() {
        val kodein = Kodein {
            bind<FullName>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
        }

        assertFailsWith<Kodein.NotFoundException> {
            @Suppress("UNUSED_VARIABLE")
            val fullName: FullName = kodein.direct.instance(arg = M("Salomon", 42))
        }
    }

    @Test fun test31_04_BigMultiArgumentsFactories() {
        val kodein = Kodein {
            bind<String>() with factory { a: String -> "Mr $a" }
            bind<String>() with factory { a: String, b: String -> "Mr $a $b" }
            bind<String>() with factory { a: String, b: String, c: String -> "Mr $a $b of $c" }
            bind<String>() with factory { a: String, b: String, c: String, d: String -> "Mr $a $b of $c, $d" }
            bind<String>() with factory { a: String, b: String, c: String, d: String, e: String -> "Mr $a $b of $c, $d in $e" }
        }

        assertEquals("Mr Salomon", kodein.direct.instance(arg = "Salomon"))
        assertEquals("Mr Salomon BRYS", kodein.direct.instance(arg = M("Salomon", "BRYS")))
        assertEquals("Mr Salomon BRYS of Paris", kodein.direct.instance(arg = M("Salomon", "BRYS", "Paris")))
        assertEquals("Mr Salomon BRYS of Paris, France", kodein.direct.instance(arg = M("Salomon", "BRYS", "Paris", "France")))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", kodein.direct.instance(arg = M("Salomon", "BRYS", "Paris", "France", "Europe")))
    }

    @Test fun test31_05_MultiArgumentsFactoryFunction() {
        val kodein = Kodein {
            bind<String>() with factory { a: String -> "Mr $a" }
            bind<String>() with factory { a: String, b: String -> "Mr $a $b" }
            bind<String>() with factory { a: String, b: String, c: String -> "Mr $a $b of $c" }
            bind<String>() with factory { a: String, b: String, c: String, d: String -> "Mr $a $b of $c, $d" }
            bind<String>() with factory { a: String, b: String, c: String, d: String, e: String -> "Mr $a $b of $c, $d in $e" }
        }

        val f1: (String) -> String by kodein.factory()
        val f2: (String, String) -> String by kodein.factory2()
        val f3: (String, String, String) -> String by kodein.factory3()
        val f4: (String, String, String, String) -> String by kodein.factory4()
        val f5: (String, String, String, String, String) -> String by kodein.factory5()
        val fn1: ((Int) -> String)? by kodein.factoryOrNull()
        val fn2: ((Int, Int) -> String)? by kodein.factory2OrNull()
        val fn3: ((Int, Int, Int) -> String)? by kodein.factory3OrNull()
        val fn4: ((Int, Int, Int, Int) -> String)? by kodein.factory4OrNull()
        val fn5: ((Int, Int, Int, Int, Int) -> String)? by kodein.factory5OrNull()

        assertEquals("Mr Salomon", f1("Salomon"))
        assertEquals("Mr Salomon BRYS", f2("Salomon", "BRYS"))
        assertEquals("Mr Salomon BRYS of Paris", f3("Salomon", "BRYS", "Paris"))
        assertEquals("Mr Salomon BRYS of Paris, France", f4("Salomon", "BRYS", "Paris", "France"))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", f5("Salomon", "BRYS", "Paris", "France", "Europe"))
        assertNull(fn1)
        assertNull(fn2)
        assertNull(fn3)
        assertNull(fn4)
        assertNull(fn5)
    }

    @Test fun test31_06_MultiArgumentsFactoryDirectFunction() {
        val kodein = Kodein.direct {
            bind<String>() with factory { a: String -> "Mr $a" }
            bind<String>() with factory { a: String, b: String -> "Mr $a $b" }
            bind<String>() with factory { a: String, b: String, c: String -> "Mr $a $b of $c" }
            bind<String>() with factory { a: String, b: String, c: String, d: String -> "Mr $a $b of $c, $d" }
            bind<String>() with factory { a: String, b: String, c: String, d: String, e: String -> "Mr $a $b of $c, $d in $e" }
        }

        val f1: (String) -> String = kodein.factory()
        val f2: (String, String) -> String = kodein.factory2()
        val f3: (String, String, String) -> String = kodein.factory3()
        val f4: (String, String, String, String) -> String = kodein.factory4()
        val f5: (String, String, String, String, String) -> String = kodein.factory5()
        val fn1: ((Int) -> String)? = kodein.factoryOrNull()
        val fn2: ((Int, Int) -> String)? = kodein.factory2OrNull()
        val fn3: ((Int, Int, Int) -> String)? = kodein.factory3OrNull()
        val fn4: ((Int, Int, Int, Int) -> String)? = kodein.factory4OrNull()
        val fn5: ((Int, Int, Int, Int, Int) -> String)? = kodein.factory5OrNull()

        assertEquals("Mr Salomon", f1("Salomon"))
        assertEquals("Mr Salomon BRYS", f2("Salomon", "BRYS"))
        assertEquals("Mr Salomon BRYS of Paris", f3("Salomon", "BRYS", "Paris"))
        assertEquals("Mr Salomon BRYS of Paris, France", f4("Salomon", "BRYS", "Paris", "France"))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", f5("Salomon", "BRYS", "Paris", "France", "Europe"))
        assertNull(fn1)
        assertNull(fn2)
        assertNull(fn3)
        assertNull(fn4)
        assertNull(fn5)
    }

    @Test fun test32_00_SimpleKeySimpleDescription() {
        val key = Kodein.Key(
                contextType = erased<Any>(),
                argType = erased<Unit>(),
                type = erased<String>(),
                tag = null
        )

        assertEquals("bind<String>()", key.bindDescription)
        assertEquals("bind<String>() with ? { ? }", key.description)
    }

    @Test fun test32_02_ComplexKeySimpleDescription() {
        val key = Kodein.Key(
                contextType = erased<String>(),
                argType = erasedComp2<Multi2<String, String>, String, String>(),
                type = erased<IntRange>(),
                tag = "tag"
        )

        assertEquals("bind<IntRange>(tag = \"tag\")", key.bindDescription)
        assertEquals("bind<IntRange>(tag = \"tag\") with ?<String>().? { Multi2<String, String> -> ? }", key.description)
    }

    class Test_34(override val kodein: Kodein) : KodeinAware {
        val name: String by instance()
    }

    class Test_34_D(dkodein: DKodein) : DKodeinAware {
        override val dkodein: DKodein = dkodein.on(receiver = this)
        val name: String = instance()
    }

    @Test fun test34_00_Receiver() {
        val kodein = Kodein {
            bind<String>() with provider { if (receiver == null) "null" else receiver!!::class.simpleName!! }
        }

        val test = Test_34(kodein)
        assertEquals("Test_34", test.name)

        val testD = Test_34_D(kodein.direct)
        assertEquals("Test_34_D", testD.name)
    }

    @Test fun test35_00_SearchTagged() {
        val kodein = Kodein {
            bind<String>(tag = "foo") with provider { "String-foo" }
            bind<String>(tag = "bar") with provider { "String-bar" }
            bind<Int>(tag = "foo") with provider { 42 }
            bind<Int>(tag = "bar") with provider { 21 }
        }

        val bindings = kodein.container.tree.findAllBindings {
            +tag("foo")
        }

        assertEquals(2, bindings.size)

        val values = bindings.map { (key, _) ->
            @Suppress("UNCHECKED_CAST")
            kodein.container.factory(key as Kodein.Key<Any?, Any?, Any>, null, null).invoke(Unit)
        }

        assertTrue("String-foo" in values)
        assertTrue(42 in values)
    }

    @Test fun test35_01_SearchArgument() {
        val kodein = Kodein {
            bind<String>() with provider { "String-foo" }
            bind<String>() with factory { name: String -> "String-$name" }
            bind<Int>() with provider { 42 }
            bind<Int>() with factory { i: Int -> 21 + i }
        }

        val bindings = kodein.container.tree.findAllBindings {
            +argument<Unit>()
        }

        assertEquals(2, bindings.size)

        val values = bindings.map { (key, _) ->
            @Suppress("UNCHECKED_CAST")
            kodein.container.factory(key as Kodein.Key<Any?, Unit, Any>, null, null).invoke(Unit)
        }

        assertTrue("String-foo" in values)
        assertTrue(42 in values)
    }

    @Test fun test35_02_SearchContext() {
        val kodein = Kodein {
            bind<String>() with provider { "String-foo" }
            bind<String>() with contexted<String>().provider { "String-$context" }
            bind<Int>() with provider { 42 }
            bind<Int>() with contexted<String>().provider { 21 + context.length }
        }

        val bindings = kodein.container.tree.findAllBindings {
            +context<Any?>()
        }

        assertEquals(2, bindings.size)

        val values = bindings.map { (key, _) ->
            @Suppress("UNCHECKED_CAST")
            kodein.container.factory(key as Kodein.Key<Any?, Any?, Any>, null, null).invoke(Unit)
        }

        assertTrue("String-foo" in values)
        assertTrue(42 in values)
    }

}
