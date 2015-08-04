package com.github.salomonbrys.kodein.test

import assertThrown
import com.github.salomonbrys.kodein.*
import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass
import kotlin.test.expect

public data class Person( public val name: String? = null )

public data class A(val b: B?)
public data class B(val c: C?)
public data class C(val a: A?)

FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinTests : TestCase() {

    public Test fun test00_ProviderBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with { Person() } }

        val p1: Person = kodein()
        val p2: Person = kodein()

        assertNotSame(p1, p2)
    }

    public Test fun test01_ProviderBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with { Person() } }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertNotSame(p1(), p2())
    }


    public Test fun test10_SingletonBindingGetInstance() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1: Person = kodein()
        val p2: Person = kodein()

        assertSame(p1, p2)
    }

    public Test fun test11_SingletonBindingGetProvider() {

        val kodein = Kodein { bind<Person>() with singleton { Person() } }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertSame(p1(), p2())
    }

    public Test fun test20_InstanceBindingGetInstance() {

        val p = Person()

        val kodein = Kodein { bind<Person>() with instance(p) }

        val p1: Person = kodein()
        val p2: Person = kodein()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    public Test fun test21_InstanceBindingGetProvider() {

        val p = Person()

        val kodein = Kodein { bind<Person>() with instance(p) }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }

    public Test fun test30_NullBindingGetInstance() {

        val kodein = Kodein {}

        val p = kodein.instanceOrNull<Person>()

        assertNull(p)
    }

    public Test fun test31_NullBindingGetProvider() {

        val kodein = Kodein {}

        val p = kodein.providerOrNull<Person>()

        assertNull(p)
    }


    public Test fun test40_NamedProviderBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with { Person() }
            bind<Person>("named") with { Person("Salomon") }
        }

        val p1: Person = kodein()
        val p2: Person = kodein("named")

        assertNull(p1.name)
        assertEquals(p2.name, "Salomon")
    }

    public Test fun test41_NamedProviderBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with { Person() }
            bind<Person>("named") with { Person("Salomon") }
        }

        val p1 = kodein.provider<Person>()
        val p2 = kodein.provider<Person>("named")

        assertNull(p1().name)
        assertEquals(p2().name, "Salomon")
    }

    public Test fun test50_NamedSingletonBindingGetInstance() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
        }

        val p1: Person = kodein("named")
        val p2: Person = kodein("named")

        assertEquals(p1.name, "Salomon")
        assertSame(p1, p2)
    }

    public Test fun test51_NamedSingletonBindingGetProvider() {
        val kodein = Kodein {
            bind<Person>() with singleton { Person() }
            bind<Person>("named") with singleton { Person("Salomon") }
        }

        val p1 = kodein.provider<Person>("named")
        val p2 = kodein.provider<Person>("named")

        assertEquals(p1().name, "Salomon")
        assertSame(p1(), p2())
    }

    public Test fun test60_NamedInstanceBindingGetInstance() {

        val kodein = Kodein {
            bind<Person>() with instance(Person())
            bind<Person>("named") with instance(Person("Salomon"))
        }

        val p1: Person = kodein()
        val p2: Person = kodein("named")
        val p3: Person = kodein("named")

        assertNull(p1.name)
        assertEquals(p2.name, "Salomon")
        assertNotSame(p1, p2)
        assertSame(p2, p3)
    }

    public Test fun test61_NamedInstanceBindingGetProvider() {

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

    public Test fun test70_ConstantBindingGetInstance() {

        val kodein = Kodein {
            constant("answer") with 42
        }

        val c: Int = kodein("answer")

        assertEquals(c, 42)
    }

    public Test fun test71_ConstantBindingGetProvider() {

        val kodein = Kodein {
            constant("answer") with 42
        }

        val c = kodein.provider<Int>("answer")

        assertEquals(c(), 42)
    }


    public Test fun test80_DependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(it()) }
            bind<B>() with singleton { B(it()) }
            bind<C>() with singleton { C(it()) }
        }

        assertThrown<Kodein.DependencyLoopException> {
            kodein<A>()
        }
    }

    public Test fun test81_NoDependencyLoop() {

        val kodein = Kodein {
            bind<A>() with singleton { A(it()) }
            bind<A>("root") with singleton { A(null) }
            bind<B>() with singleton { B(it()) }
            bind<C>() with singleton { C(it("root")) }
        }

        val a = kodein<A>()
        assertNotNull(a.b?.c?.a)
    }

    public Test fun test82_TypeNotFound() {

        val kodein = Kodein {}

        assertThrown<IllegalStateException> {
            kodein<Person>()
        }
    }

    public Test fun test83_NameNotFound() {

        val kodein = Kodein {
            bind<Person>() with { Person() }
            bind<Person>("named") with { Person("Salomon") }
        }

        assertThrown<IllegalStateException> {
            kodein<Person>("schtroumpf")
        }
    }

    public Test fun test90_TypeErasure() {

        val la = listOf(A(null))
        val lb = listOf(B(null))

        val kodein = Kodein {
            bind<List<A>>() with instance( la )
            bind<List<B>>() with instance( lb )
        }

        assertSame(kodein<List<A>>(), la)
        assertSame(kodein<List<B>>(), lb)
    }

    public Test fun test91_ParameterizedTypeWrap() {
        val typeLS1 = KodeinParameterizedType((object : TypeToken<List<String>>() {}).type as ParameterizedType)
        val typeLS2 = KodeinParameterizedType((object : TypeToken<List<String>>() {}).type as ParameterizedType)
        val typeLI = KodeinParameterizedType((object : TypeToken<List<Int>>() {}).type as ParameterizedType)

        assertEquals(typeLS1, typeLS2)
        assertNotEquals(typeLS1, typeLI)
    }

}
