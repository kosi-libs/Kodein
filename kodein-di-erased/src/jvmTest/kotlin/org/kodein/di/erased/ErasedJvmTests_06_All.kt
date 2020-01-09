package org.kodein.di.erased

import org.kodein.di.*
import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_06_All {

    @Test
    fun test_00_AllInstances() {
        val di = DI {
            bind<Person>() with provider { Person("Salomon") }
            bind<String>() with provider { "Laila" }
        }

        val instances: List<Any> by di.allInstances()
        assertTrue(Person("Salomon") in instances)
        assertTrue("Laila" in instances)
    }

    // Only the JVM supports reflection
    @Test
    fun test_01_MultipleMultiArgumentsAllFactories() {
        val di = DI {
            bind<Name>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
            bind<FullName>() with factory { firstName: String, lastName: String -> FullName(firstName, lastName) }
            bind<Name>() with factory { name: String, age: Int -> FullInfos(name, "BRYS", age) }
            bind<String>() with factory { firstName: String, lastName: String -> "Mr $firstName $lastName" }
        }

        val f by di.AllFactories<Multi2<String, String>, Name>(Multi2.erased(), erased())
        val df = di.direct.AllFactories<Multi2<String, String>, Name>(Multi2.erased(), erased())
        val p by di.allProviders<Multi2<String, String>, Name>(arg = M("Salomon", "BRYS"))
        val dp = di.direct.allProviders<Multi2<String, String>, Name>(arg = M("Salomon", "BRYS"))
        val i by di.allInstances<Multi2<String, String>, Name>(arg = M("Salomon", "BRYS"))
        val ddi = di.direct.allInstances<Multi2<String, String>, Name>(arg = M("Salomon", "BRYS"))

        assertAllEqual(2, f.size, df.size, p.size, dp.size, i.size, ddi.size)

        val values =
                f.map { it(M("Salomon", "BRYS")) } +
                        df.map { it(M("Salomon", "BRYS")) } +
                        p.map { it() } +
                        dp.map { it() } +
                        i +
                        ddi

        assertAllEqual(FullName("Salomon", "BRYS"), *values.toTypedArray())
    }

    @Test
    fun test_02_AllFactories() {
        val di = DI {
            bind<Name>() with factory { name: String -> Name(name) }
            bind<FullName>() with factory { name: String -> FullName(name, "BRYS") }
            bind<String>() with factory { name: String -> "Mr $name BRYS" }
        }

        val f by di.allFactories<String, Name>()
        val df = di.direct.allFactories<String, Name>()
        val p by di.allProviders<String, Name>(arg = "Salomon")
        val dp = di.direct.allProviders<String, Name>(arg = "Salomon")
        val fp by di.allProviders<String, Name>(fArg = { "Salomon" })
        val dfp = di.direct.allProviders<String, Name>(fArg = { "Salomon" })
        val i by di.allInstances<String, Name>(arg = "Salomon")
        val ddi = di.direct.allInstances<String, Name>(arg = "Salomon")
        val fi by di.allInstances<String, Name>(fArg = { "Salomon" })

        assertAllEqual(2, f.size, df.size, p.size, dp.size, fp.size, dfp.size, i.size, ddi.size, fi.size)

        arrayOf(
                f.map { it("Salomon") },
                df.map { it("Salomon") },
                p.map { it() },
                dp.map { it() },
                fp.map { it() },
                dfp.map { it() },
                i,
                ddi,
                fi
        ).forEach {
            assertTrue(Name("Salomon") in it)
            assertTrue(FullName("Salomon", "BRYS") in it)
        }
    }

    @Test
    fun test_03_AllProviders() {
        val di = DI {
            bind<Name>() with provider { Name("Salomon") }
            bind<FullName>() with provider { FullName("Salomon", "BRYS") }
            bind<String>() with provider { "Mr Salomon BRYS" }
        }

        val providers by di.allProviders<IName>()

        assertEquals(2, providers.size)

        val values = providers.map { it() }

        assertTrue(Name("Salomon") in values)
        assertTrue(FullName("Salomon", "BRYS") in values)

        val dProviders = di.direct.allProviders<IName>()

        assertEquals(2, providers.size)

        val dValues = dProviders.map { it() }

        assertTrue(Name("Salomon") in dValues)
        assertTrue(FullName("Salomon", "BRYS") in dValues)
    }

    @Test
    fun test_04_AllInstances() {
        val di = DI {
            bind<Name>() with provider { Name("Salomon") }
            bind<FullName>() with provider { FullName("Salomon", "BRYS") }
            bind<String>() with provider { "Mr Salomon BRYS" }
        }

        val values by di.allInstances<IName>()

        assertTrue(Name("Salomon") in values)
        assertTrue(FullName("Salomon", "BRYS") in values)

        val dValues = di.direct.allInstances<IName>()

        assertTrue(Name("Salomon") in dValues)
        assertTrue(FullName("Salomon", "BRYS") in dValues)
    }


}
