package org.kodein.conf.test

import org.kodein.*
import org.kodein.bindings.FactoryBinding
import org.kodein.conf.ConfigurableKodein
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinGlobalJvmTests {

    private open class Name(val firstName: String) {
        override fun equals(other: Any?): Boolean{
            if (this === other) return true
            if (other !is Name) return false
            if (firstName != other.firstName) return false
            return true
        }

        override fun hashCode(): Int{
            return firstName.hashCode()
        }
    }

    private class FullName(firstName: String, val lastName: String) : Name(firstName) {
        override fun equals(other: Any?): Boolean{
            if (this === other) return true
            if (other !is FullName) return false
            if (!super.equals(other)) return false
            if (lastName != other.lastName) return false
            return true
        }

        override fun hashCode(): Int{
            return 31 * super.hashCode() + lastName.hashCode()
        }
    }

    @Test
    // Only the JVM supports up cast argument searching
    fun test03_00_ChildOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(erased(), erased()) { n: Name -> n.firstName }
        }

        assertEquals("Salomon", kodein.direct.Factory<FullName, String>(erased(), erased()).invoke(FullName ("Salomon", "BRYS")))

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(erased(), erased()) { n: FullName -> n.firstName + " " + n.lastName }
        }

        assertEquals("Salomon BRYS", kodein.direct.Factory<FullName, String>(erased(), erased()).invoke(FullName("Salomon", "BRYS")))
    }

    // Only the JVM supports generics
    @Test fun test03_01_GenericOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(erased(), erased()) { l: List<*> -> l.first().toString() + " *" }
        }

        assertEquals("Salomon *", kodein.direct.Factory<List<String>, String>(erased(), erased()).invoke(listOf("Salomon", "BRYS")))

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(generic(), erased()) { l: List<String> -> l[0] + " " + l[1] }
        }

        assertEquals("Salomon BRYS", kodein.direct.Factory<List<String>, String>(generic(), erased()).invoke(listOf("Salomon", "BRYS")))
        assertEquals("42 *", kodein.direct.Factory<List<Int>, String>(generic(), erased()).invoke(listOf(42)))
    }

}
