package org.kodein.conf.test

import org.kodein.*
import org.kodein.bindings.ExternalSource
import org.kodein.bindings.Factory
import org.kodein.bindings.Provider
import org.kodein.bindings.externalFactory
import org.kodein.conf.ConfigurableKodein
import org.kodein.test.FixMethodOrder
import org.kodein.test.MethodSorters
import kotlin.test.*

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
            Bind<String>(erased()) with Factory(AnyToken, erased(), erased()) { n: Name -> n.firstName }
        }

        assertEquals("Salomon", kodein.direct.Factory<FullName, String>(erased(), erased(), null).invoke(FullName ("Salomon", "BRYS")))

        kodein.addConfig {
            Bind<String>(erased(), overrides = true) with Factory(AnyToken, erased(), erased()) { n: FullName -> n.firstName + " " + n.lastName }
        }

        assertEquals("Salomon BRYS", kodein.direct.Factory<FullName, String>(erased(), erased(), null).invoke(FullName("Salomon", "BRYS")))
    }

    // Only the JVM supports generics
    @Test fun test03_01_GenericOverride() {
        val kodein = ConfigurableKodein(true)

        kodein.addConfig {
            Bind<String>(erased()) with Factory(AnyToken, erased(), erased()) { l: List<*> -> l.first().toString() + " *" }
        }

        assertEquals("Salomon *", kodein.direct.Factory<List<String>, String>(erased(), erased(), null).invoke(listOf("Salomon", "BRYS")))

        kodein.addConfig {
            Bind<String>(erased()) with Factory(AnyToken, generic(), erased()) { l: List<String> -> l[0] + " " + l[1] }
        }

        assertEquals("Salomon BRYS", kodein.direct.Factory<List<String>, String>(generic(), erased(), null).invoke(listOf("Salomon", "BRYS")))
        assertEquals("42 *", kodein.direct.Factory<List<Int>, String>(generic(), erased(), null).invoke(listOf(42)))
    }

    @Test fun test07_00_ExternalSource() {
        val kodein = ConfigurableKodein(mutable = true)
        kodein.addConfig {
            externalSource = ExternalSource { key ->
                if (key.type.jvmType == String::class.java && key.tag == "foo")
                    externalFactory { "bar" }
                else
                    null
            }
        }

        assertEquals("bar", kodein.direct.Instance<String>(erased(), tag = "foo"))
        assertNull(kodein.direct.InstanceOrNull<String>(erased(), null))

        kodein.addConfig {
            bind() from Provider(AnyToken, erased()) { "def" }
        }

        assertEquals("bar", kodein.direct.Instance<String>(erased(), tag = "foo"))
        assertEquals("def", kodein.direct.Instance<String>(erased(), null))
    }
}
