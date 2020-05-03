package org.kodein.di

import org.kodein.di.test.*
import org.kodein.type.erasedComp
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
            bind<Name>() with factory { name: Pair<String, String> -> FullName(name.first, name.second) }
            bind<FullName>() with factory { name: Pair<String, String> -> FullName(name.first, name.second) }
            bind<String>() with factory { name: Pair<String, String> -> "Mr ${name.first} ${name.second}" }
        }

        val f by di.AllFactories<Pair<String, String>, Name>(erasedComp(Pair::class, org.kodein.type.generic<String>(), org.kodein.type.generic<String>()), org.kodein.type.generic())
        val df = di.direct.AllFactories<Pair<String, String>, Name>(erasedComp(Pair::class, org.kodein.type.generic<String>(), org.kodein.type.generic<String>()), org.kodein.type.generic())
        val p by di.allProviders<Pair<String, String>, Name>(arg = Pair("Salomon", "BRYS"))
        val dp = di.direct.allProviders<Pair<String, String>, Name>(arg = Pair("Salomon", "BRYS"))
        val i by di.allInstances<Pair<String, String>, Name>(arg = Pair("Salomon", "BRYS"))
        val ddi = di.direct.allInstances<Pair<String, String>, Name>(arg = Pair("Salomon", "BRYS"))

        assertAllEqual(2, f.size, df.size, p.size, dp.size, i.size, ddi.size)

        val values =
                f.map { it(Pair("Salomon", "BRYS")) } +
                        df.map { it(Pair("Salomon", "BRYS")) } +
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
