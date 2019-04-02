package org.kodein.di.erased

import org.kodein.di.*
import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_06_All {

    @Test
    fun test_00_AllInstances() {
        val kodein = Kodein {
            bind<Person>() with provider { Person("Salomon") }
            bind<String>() with provider { "Laila" }
        }

        val instances: List<Any> by kodein.allInstances()
        assertTrue(Person("Salomon") in instances)
        assertTrue("Laila" in instances)
    }

    // Only the JVM supports reflection
    @Test
    fun test_01_MultipleMultiArgumentsAllFactories() {
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

    @Test
    fun test_02_AllFactories() {
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

    @Test
    fun test_03_AllProviders() {
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

    @Test
    fun test_04_AllInstances() {
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


}
