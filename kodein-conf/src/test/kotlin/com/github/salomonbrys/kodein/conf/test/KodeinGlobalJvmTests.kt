package com.github.salomonbrys.kodein.conf.test

import com.github.salomonbrys.kodein.Instance
import com.github.salomonbrys.kodein.With
import com.github.salomonbrys.kodein.bindings.FactoryBinding
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.erased
import com.github.salomonbrys.kodein.generic
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

        assertEquals("Salomon", kodein.With(erased(), FullName ("Salomon", "BRYS")).Instance<String>(erased()))

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(erased(), erased()) { n: FullName -> n.firstName + " " + n.lastName }
        }

        assertEquals("Salomon BRYS", kodein.With(erased(), FullName("Salomon", "BRYS")).Instance<String>(erased()))
    }

    // Only the JVM supports generics
    @Test fun test03_01_GenericOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(erased(), erased()) { l: List<*> -> l.first().toString() + " *" }
        }

        assertEquals("Salomon *", kodein.With(generic(), listOf("Salomon", "BRYS")).Instance<String>(erased()))

        kodein.addConfig {
            Bind<String>(erased()) with FactoryBinding(generic(), erased()) { l: List<String> -> l[0] + " " + l[1] }
        }

        assertEquals("Salomon BRYS", kodein.With(generic(), listOf("Salomon", "BRYS")).Instance<String>(erased()))
        assertEquals("42 *", kodein.With(generic(), listOf(42)).Instance<String>(erased()))
    }

}
