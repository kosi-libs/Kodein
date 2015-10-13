package com.github.salomonbrys.kodein.test

import com.github.salomonbrys.kodein.*
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.lang.reflect.ParameterizedType
import kotlin.concurrent.thread

public data class Person( public val name: String? = null )

public data class A(val b: B?)
public data class B(val c: C?)
public data class C(val a: A?)

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinTests : TestCase() {

    public @Test fun test00_0_ProviderBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with provider { Person() } }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertNotSame(p1, p2)
    }

    public @Test fun test00_1_ProviderBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with provider { Person() } }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertNotSame(p1(), p2())
    }

    public @Test fun test00_2_FactoryBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with factory { name: String -> Person(name) } }

        val p1 = kodein.factory<String, Person>()
        val p2 = kodein.factory<String, Person>()

        assertNotSame(p1("Salomon"), p2("Salomon"))
    }

    public @Test fun test01_0_SingletonBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertSame(p1, p2)
    }

    public @Test fun test01_1_SingletonBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertSame(p1(), p2())
    }

    public @Test fun test02_0_ThreadSingletonBindingGetInstance() {
        val kodein = Kodein { bind<Person>() with threadSingleton { Person() } }

        var tp1: Person? = null;

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

    public @Test fun test02_1_ThreadSingletonBindingGetProvider() {
        val kodein = Kodein { bind<Person>() with threadSingleton { Person() } }

        var tp1: Person? = null;

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

    public @Test fun test03_0_InstanceBindingGetInstance() {

        val p = Person()

        val kodein = Kodein { bind<Person>() with instance(p) }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    public @Test fun test03_1_InstanceBindingGetProvider() {

        val p = Person()

        val kodein = Kodein { bind<Person>() with instance(p) }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }

    public @Test fun test04_0_NullBindingGetInstance() {

        val kodein = Kodein {}

        val p = kodein.instanceOrNull<Person>()

        assertNull(p)
    }

    public @Test fun test04_1_NullBindingGetProvider() {

        val kodein = Kodein {}

        val p = kodein.providerOrNull<Person>()

        assertNull(p)
    }

    public @Test fun test05_0_NamedProviderBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with provider { Person("Salomon") }
        }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance("named")

        assertNull(p1.name)
        assertEquals(p2.name, "Salomon")
    }

    public @Test fun test05_1_NamedProviderBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with provider { Person("Salomon") }
        }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>("named")

        assertNull(p1().name)
        assertEquals(p2().name, "Salomon")
    }

    public @Test fun test06_0_NamedSingletonBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
        }

        val p1: Person = kodein.instance("named")
        val p2: Person = kodein.instance("named")

        assertEquals(p1.name, "Salomon")
        assertSame(p1, p2)
    }

    public @Test fun test06_1_NamedSingletonBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
        }

        val p1 = kodein.provider<Person>("named")
        val p2 = kodein.provider<Person>("named")

        assertEquals(p1().name, "Salomon")
        assertSame(p1(), p2())
    }

    public @Test fun test07_0_NamedInstanceBindingGetInstance() {

        val kodein = Kodein {
            bind<Person>() with instance(Person())
            bind<Person>("named") with instance(Person("Salomon"))
        }

        val p1: Person = kodein.instance()
        val p2: Person = kodein.instance("named")
        val p3: Person = kodein.instance("named")

        assertNull(p1.name)
        assertEquals(p2.name, "Salomon")
        assertNotSame(p1, p2)
        assertSame(p2, p3)
    }

    public @Test fun test07_1_NamedInstanceBindingGetProvider() {

        val kodein = Kodein {
            bind<Person>() with instance(Person())
            bind<Person>("named") with instance(Person("Salomon"))
        }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>("named")
        val p3 = kodein.provider<Person>("named")

        assertNull(p1().name)
        assertEquals(p2().name, "Salomon")
        assertNotSame(p1(), p2())
        assertSame(p2(), p3())
    }

    public @Test fun test08_0_ConstantBindingGetInstance() {

        val kodein = Kodein {
            constant("answer") with 42
        }

        val c: Int = kodein.instance("answer")

        assertEquals(c, 42)
    }

    public @Test fun test08_1_ConstantBindingGetProvider() {

        val kodein = Kodein {
            constant("answer") with 42
        }

        val c = kodein.provider<Int>("answer")

        assertEquals(c(), 42)
    }


    public @Test fun test09_0_DependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(instance()) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance()) }
        }

        assertThrown<Kodein.DependencyLoopException> {
            kodein.instance<A>()
        }
    }

    public @Test fun test09_1_NoDependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(instance()) }
            bind<A>("root") with singleton { A(null) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance("root")) }
        }

        val a = kodein.instance<A>()
        assertNotNull(a.b?.c?.a)
    }

    public @Test fun test09_2_TypeNotFound() {

        val kodein = Kodein {}

        assertThrown<Kodein.NotFoundException> {
            kodein.instance<Person>()
        }
    }

    public @Test fun test09_3_NameNotFound() {

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with provider { Person("Salomon") }
        }

        assertThrown<Kodein.NotFoundException> {
            kodein.instance<Person>("schtroumpf")
        }
    }

    public @Test fun test09_4_FactoryIsNotProvider() {

        val kodein = Kodein {
            bind<Person>() with factory { name: String -> Person(name) }
        }

        assertThrown<Kodein.NotFoundException> {
            kodein.provider<Person>()
        }
    }

    public @Test fun test09_5_ProviderIsNotFactory() {

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
        }

        assertThrown<Kodein.NotFoundException> {
            kodein.factory<Int, Person>()
        }
    }

    public @Test fun test10_0_TypeErasure() {

        val la = listOf(A(null))
        val lb = listOf(B(null))

        val kodein = Kodein {
            bind<List<A>>() with instance( la )
            bind<List<B>>() with instance( lb )
        }

        assertSame(kodein.instance<List<A>>(), la)
        assertSame(kodein.instance<List<B>>(), lb)
    }

    public @Test fun test10_1_ParameterizedTypeWrap() {
        val typeLS1 = KodeinParameterizedType((object : TypeToken<List<String>>() {}).type as ParameterizedType)
        val typeLS2 = KodeinParameterizedType((object : TypeToken<List<String>>() {}).type as ParameterizedType)
        val typeLI = KodeinParameterizedType((object : TypeToken<List<Int>>() {}).type as ParameterizedType)

        assertEquals(typeLS1, typeLS2)
        assertNotEquals(typeLS1, typeLI)
    }

    public class PersonLazy(val kodein: Kodein) {
        val newPerson: () -> Person by kodein.lazyProvider()
        val salomon: Person by kodein.lazyInstance("named")
        val factory: (String) -> Person by kodein.lazyFactory("factory")
    }

    public @Test fun test11_0_Class() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
            bind<Person>("factory") with factory { name: String -> Person(name) }
        }

        val lazied = PersonLazy(kodein)
        assertNotSame(lazied.newPerson(), lazied.newPerson())
        assertEquals(lazied.salomon.name, "Salomon")
        assertSame(lazied.salomon, lazied.salomon)
        assertNotSame(lazied.factory("Laila"), lazied.factory("Laila"))
        assertEquals(lazied.factory("Laila").name, "Laila")
    }

    public @Test fun test12_0_Module() {

        val personModule = Kodein.Module {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
            bind<Person>("factory") with factory { name: String -> Person(name) }
        }

        val kodein = Kodein {
            import(personModule)
        }

        val lazied = PersonLazy(kodein)
        assertNotSame(lazied.newPerson(), lazied.newPerson())
        assertEquals(lazied.salomon.name, "Salomon")
        assertSame(lazied.salomon, lazied.salomon)
        assertNotSame(lazied.factory("Laila"), lazied.factory("Laila"))
        assertEquals(lazied.factory("Laila").name, "Laila")
    }

    class Recurs0(public val a: RecursA)
    class RecursA(public val b: RecursB)
    class RecursB(public val c: RecursC)
    class RecursC(public val a: RecursA)

    public @Test fun test13_0_Recursivedependencies() {

        val kodein = Kodein {
            bind<Recurs0>() with provider { Recurs0(instance()) }
            bind<RecursA>() with provider { RecursA(instance()) }
            bind<RecursA>() with provider { RecursA(instance()) }
            bind<RecursB>() with provider { RecursB(instance("yay")) }
            bind<RecursC>("yay") with provider { RecursC(instance()) }
        }

        assertThrown<Kodein.DependencyLoopException> {
            kodein.instance<Recurs0>()
        }
    }

    public class PersonInject() {
        val injector = KodeinInjector()
        val newPerson: () -> Person by injector.provider()
        val salomon: Person by injector.instance("named")
        val factory: (String) -> Person by injector.factory("factory")
    }

    public @Test fun test14_0_InjectorInjected() {
        val injected = PersonInject()

        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
            bind<Person>("factory") with factory { name: String -> Person(name) }
        }

        injected.injector.inject(kodein);
        assertNotSame(injected.newPerson(), injected.newPerson())
        assertEquals(injected.salomon.name, "Salomon")
        assertSame(injected.salomon, injected.salomon)
        assertNotSame(injected.factory("Laila"), injected.factory("Laila"))
        assertEquals(injected.factory("Laila").name, "Laila")
    }

    public @Test fun test14_1_InjectorNotInjected() {
        val injected = PersonInject()

        assertThrown<KodeinInjector.UninjectedException> {
            injected.newPerson()
        }
    }
}
