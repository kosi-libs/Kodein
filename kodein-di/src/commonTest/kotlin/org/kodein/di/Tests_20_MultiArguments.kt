package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_20_MultiArguments {

    private data class Person(val firstName: String, val lastName: String)
    private data class MultiArgElement(val a1: String = "", val a2: String = "", val a3: String = "", val a4: String = "", val a5: String = "") {
        override fun toString(): String {
            fun concat(prefix: String = "", value: String): String = if (value.isNotEmpty()) "$prefix $value" else ""
            return "Mr $a1" +
                    concat(value = a2) +
                    concat(" of", a3) +
                    concat(",", a4) +
                    concat(" in", a5)
        }
    }

    @Test
    fun test_00_MultiArgumentsFactory() {
        val kodein = DI {
            bind<FullName>() with factory { p: Person -> FullName(p.firstName, p.lastName) }
        }

        val i: FullName by kodein.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val ni: FullName? by kodein.instanceOrNull(arg = Person(name = "Salomon"))
        val nni: FullName? by kodein.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val di: FullName = kodein.direct.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dni: FullName? = kodein.direct.instanceOrNull(arg = Person(name = "Salomon"))
        val dnni: FullName? = kodein.direct.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val p: () -> FullName by kodein.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val np: (() -> FullName)? by kodein.providerOrNull(arg = Person(name = "Salomon"))
        val nnp: (() -> FullName)? by kodein.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dp: () -> FullName = kodein.direct.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(name = "Salomon"))
        val dnnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))

        assertAllNull(ni, dni, np, dnp)
        assertAllNotNull(nni, dnni, nnp, dnnp)

        assertAllEqual(FullName("Salomon", "BRYS"), i, nni!!, di, dnni, p(), nnp!!(), dp(), dnnp!!())
    }

    @Test
    fun test_01_MultiArgumentsMultiton() {
        val kodein = DI {
            bind<FullName>() with multiton { p: Person -> FullName(p.firstName, p.lastName) }
        }

        val i: FullName by kodein.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val ni: FullName? by kodein.instanceOrNull(arg = Person(name = "Salomon"))
        val nni: FullName? by kodein.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val di: FullName = kodein.direct.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dni: FullName? = kodein.direct.instanceOrNull(arg = Person(name = "Salomon"))
        val dnni: FullName? = kodein.direct.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val p: () -> FullName by kodein.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val np: (() -> FullName)? by kodein.providerOrNull(arg = Person(name = "Salomon"))
        val nnp: (() -> FullName)? by kodein.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dp: () -> FullName = kodein.direct.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(name = "Salomon"))
        val dnnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))

        assertAllNull(ni, dni, np, dnp)
        assertAllNotNull(nni, dnni, nnp, dnnp)

        assertAllEqual(FullName("Salomon", "BRYS"), i, nni!!, di, dnni, p(), nnp!!(), dp(), dnnp!!())
    }

    @Test
    fun test_02_MultiArgumentsFactoryBadType() {
        val kodein = DI {
            bind<FullName>() with factory { p: Person -> FullName(p.firstName, p.lastName) }
        }

        assertFailsWith<DI.NotFoundException> {
            @Suppress("UNUSED_VARIABLE")
            val fullName: FullName = kodein.direct.instance(arg = org.kodein.di.test.Person("Salomon"))
        }
    }

    @Test
    fun test_03_BigMultiArgumentsFactories() {
        val kodein = DI {
            bind<String>() with factory { m: MultiArgElement -> m.toString() }
        }

        assertEquals("Mr Salomon", kodein.direct.instance(arg = MultiArgElement("Salomon")))
        assertEquals("Mr Salomon BRYS", kodein.direct.instance(arg = MultiArgElement("Salomon", "BRYS")))
        assertEquals("Mr Salomon BRYS of Paris", kodein.direct.instance(arg = MultiArgElement("Salomon", "BRYS", "Paris")))
        assertEquals("Mr Salomon BRYS of Paris, France", kodein.direct.instance(arg = MultiArgElement("Salomon", "BRYS", "Paris", "France")))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", kodein.direct.instance(arg = MultiArgElement("Salomon", "BRYS", "Paris", "France", "Europe")))
    }

    @Test
    fun test_04_MultiArgumentsFactoryFunction() {
        val kodein = DI {
            bind<String>() with factory { m: MultiArgElement -> m.toString() }
        }

        val factory: (MultiArgElement) -> String by kodein.factory()
        val factoryNull: ((Int) -> String)? by kodein.factoryOrNull()

        assertEquals("Mr Salomon", factory(MultiArgElement("Salomon")))
        assertEquals("Mr Salomon BRYS", factory(MultiArgElement("Salomon", "BRYS")))
        assertEquals("Mr Salomon BRYS of Paris", factory(MultiArgElement("Salomon", "BRYS", "Paris")))
        assertEquals("Mr Salomon BRYS of Paris, France", factory(MultiArgElement("Salomon", "BRYS", "Paris", "France")))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", factory(MultiArgElement("Salomon", "BRYS", "Paris", "France", "Europe")))
        assertNull(factoryNull)
    }

    @Test
    fun test_05_MultiArgumentsFactoryDirectFunction() {
        val kodein = DI.direct {
            bind<String>() with factory { m: MultiArgElement -> m.toString() }
        }

        val factory: (MultiArgElement) -> String = kodein.factory()
        val factoryNull: ((Int) -> String)? = kodein.factoryOrNull()

        assertEquals("Mr Salomon", factory(MultiArgElement("Salomon")))
        assertEquals("Mr Salomon BRYS", factory(MultiArgElement("Salomon", "BRYS")))
        assertEquals("Mr Salomon BRYS of Paris", factory(MultiArgElement("Salomon", "BRYS", "Paris")))
        assertEquals("Mr Salomon BRYS of Paris, France", factory(MultiArgElement("Salomon", "BRYS", "Paris", "France")))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", factory(MultiArgElement("Salomon", "BRYS", "Paris", "France", "Europe")))
        assertNull(factoryNull)
    }

    @Test
    fun test_06_SimpleBinding_MultiArgumentsFactory() {
        val kodein = DI {
            bindFactory { p: Person -> FullName(p.firstName, p.lastName) }
        }

        val i: FullName by kodein.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val ni: FullName? by kodein.instanceOrNull(arg = Person(name = "Salomon"))
        val nni: FullName? by kodein.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val di: FullName = kodein.direct.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dni: FullName? = kodein.direct.instanceOrNull(arg = Person(name = "Salomon"))
        val dnni: FullName? = kodein.direct.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val p: () -> FullName by kodein.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val np: (() -> FullName)? by kodein.providerOrNull(arg = Person(name = "Salomon"))
        val nnp: (() -> FullName)? by kodein.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dp: () -> FullName = kodein.direct.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(name = "Salomon"))
        val dnnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))

        assertAllNull(ni, dni, np, dnp)
        assertAllNotNull(nni, dnni, nnp, dnnp)

        assertAllEqual(FullName("Salomon", "BRYS"), i, nni!!, di, dnni, p(), nnp!!(), dp(), dnnp!!())
    }

    @Test
    fun test_07_SimpleBinding_MultiArgumentsMultiton() {
        val kodein = DI {
            bindMultiton { p: Person -> FullName(p.firstName, p.lastName) }
        }

        val i: FullName by kodein.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val ni: FullName? by kodein.instanceOrNull(arg = Person(name = "Salomon"))
        val nni: FullName? by kodein.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val di: FullName = kodein.direct.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dni: FullName? = kodein.direct.instanceOrNull(arg = Person(name = "Salomon"))
        val dnni: FullName? = kodein.direct.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val p: () -> FullName by kodein.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val np: (() -> FullName)? by kodein.providerOrNull(arg = Person(name = "Salomon"))
        val nnp: (() -> FullName)? by kodein.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dp: () -> FullName = kodein.direct.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(name = "Salomon"))
        val dnnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))

        assertAllNull(ni, dni, np, dnp)
        assertAllNotNull(nni, dnni, nnp, dnnp)

        assertAllEqual(FullName("Salomon", "BRYS"), i, nni!!, di, dnni, p(), nnp!!(), dp(), dnnp!!())
    }

    @Test
    fun test_08_SimpleBinding_MultiArgumentsFactoryBadType() {
        val kodein = DI {
            bindFactory { p: Person -> FullName(p.firstName, p.lastName) }
        }

        assertFailsWith<DI.NotFoundException> {
            @Suppress("UNUSED_VARIABLE")
            val fullName: FullName = kodein.direct.instance(arg = org.kodein.di.test.Person("Salomon"))
        }
    }

    @Test
    fun test_09_SimpleBinding_BigMultiArgumentsFactories() {
        val kodein = DI {
            bindFactory { m: MultiArgElement -> m.toString() }
        }

        assertEquals("Mr Salomon", kodein.direct.instance(arg = MultiArgElement("Salomon")))
        assertEquals("Mr Salomon BRYS", kodein.direct.instance(arg = MultiArgElement("Salomon", "BRYS")))
        assertEquals("Mr Salomon BRYS of Paris", kodein.direct.instance(arg = MultiArgElement("Salomon", "BRYS", "Paris")))
        assertEquals("Mr Salomon BRYS of Paris, France", kodein.direct.instance(arg = MultiArgElement("Salomon", "BRYS", "Paris", "France")))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", kodein.direct.instance(arg = MultiArgElement("Salomon", "BRYS", "Paris", "France", "Europe")))
    }

    @Test
    fun test_10_MultiArgumentsFactoryFunction() {
        val kodein = DI {
            bindFactory { m: MultiArgElement -> m.toString() }
        }

        val factory: (MultiArgElement) -> String by kodein.factory()
        val factoryNull: ((Int) -> String)? by kodein.factoryOrNull()

        assertEquals("Mr Salomon", factory(MultiArgElement("Salomon")))
        assertEquals("Mr Salomon BRYS", factory(MultiArgElement("Salomon", "BRYS")))
        assertEquals("Mr Salomon BRYS of Paris", factory(MultiArgElement("Salomon", "BRYS", "Paris")))
        assertEquals("Mr Salomon BRYS of Paris, France", factory(MultiArgElement("Salomon", "BRYS", "Paris", "France")))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", factory(MultiArgElement("Salomon", "BRYS", "Paris", "France", "Europe")))
        assertNull(factoryNull)
    }

    @Test
    fun test_11_MultiArgumentsFactoryDirectFunction() {
        val kodein = DI.direct {
            bindFactory { m: MultiArgElement -> m.toString() }
        }

        val factory: (MultiArgElement) -> String = kodein.factory()
        val factoryNull: ((Int) -> String)? = kodein.factoryOrNull()

        assertEquals("Mr Salomon", factory(MultiArgElement("Salomon")))
        assertEquals("Mr Salomon BRYS", factory(MultiArgElement("Salomon", "BRYS")))
        assertEquals("Mr Salomon BRYS of Paris", factory(MultiArgElement("Salomon", "BRYS", "Paris")))
        assertEquals("Mr Salomon BRYS of Paris, France", factory(MultiArgElement("Salomon", "BRYS", "Paris", "France")))
        assertEquals("Mr Salomon BRYS of Paris, France in Europe", factory(MultiArgElement("Salomon", "BRYS", "Paris", "France", "Europe")))
        assertNull(factoryNull)
    }

}
