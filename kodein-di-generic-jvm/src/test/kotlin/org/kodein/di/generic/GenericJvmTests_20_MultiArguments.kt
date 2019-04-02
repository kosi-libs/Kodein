package org.kodein.di.generic

import org.kodein.di.Kodein
import org.kodein.di.Multi2
import org.kodein.di.direct
import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_20_MultiArguments {

    @Test
    fun test_00_multiArgumentsFactory() {
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

    @Test
    fun test_01_multiArgumentsMultiton() {
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
    fun test_02_multiArgumentsFactoryBadType() {
        val kodein = Kodein {
            bind<FullName>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
        }

        assertFailsWith<Kodein.NotFoundException> {
            @Suppress("UNUSED_VARIABLE")
            val fullName: FullName = kodein.direct.instance(arg = M("Salomon", 42))
        }
    }

    @Test
    fun test_03_multipleMultiArgumentsFactories() {
        val kodein = Kodein {
            bind<Name>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
            bind<FullName>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
            bind<Name>() with factory { name: String, age: Int -> FullInfos(name, "BRYS", age) }
            bind<String>() with factory { firstName: String, lastName: String -> "Mr $firstName $lastName" }
        }

        val f by kodein.allFactories<Multi2<String, String>, Name>()
        val df = kodein.direct.allFactories<Multi2<String, String>, Name>()
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

    @Test
    fun test_04_BigMultiArgumentsFactories() {
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

    @Test
    fun test_05_MultiArgumentsFactoryFunction() {
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

    @Test
    fun test_06_MultiArgumentsFactoryDirectFunction() {
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

}
