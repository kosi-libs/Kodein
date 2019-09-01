package org.kodein.di.erased

import org.kodein.di.*
import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedTests_20_MultiArguments {

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
        val kodein = Kodein {
            bind<FullName>() with factory { p: Person -> FullName(p.firstName, p.lastName) }
        }

        val i: FullName by kodein.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val nni: FullName? by kodein.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val di: FullName = kodein.direct.instance(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dnni: FullName? = kodein.direct.instanceOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val p: () -> FullName by kodein.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val nnp: (() -> FullName)? by kodein.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dp: () -> FullName = kodein.direct.provider(arg = Person(firstName = "Salomon", lastName = "BRYS"))
        val dnnp: (() -> FullName)? = kodein.direct.providerOrNull(arg = Person(firstName = "Salomon", lastName = "BRYS"))

        assertAllNotNull(nni, dnni, nnp, dnnp)

        assertAllEqual(FullName("Salomon", "BRYS"), i, nni!!, di, dnni, p(), nnp!!(), dp(), dnnp!!())
    }

    @Test
    fun test_01_MultiArgumentsMultiton() {
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

    @Test
    fun test_02_MultiArgumentsFactoryBadType() {
        val kodein = Kodein {
            bind<FullName>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
        }

        assertFailsWith<Kodein.NotFoundException> {
            @Suppress("UNUSED_VARIABLE")
            val fullName: FullName = kodein.direct.instance(arg = M("Salomon", 42))
        }
    }

    @Test
    fun test_03_BigMultiArgumentsFactories() {
        val kodein = Kodein {
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
        val kodein = Kodein {
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
        val kodein = Kodein.direct {
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
    
}
