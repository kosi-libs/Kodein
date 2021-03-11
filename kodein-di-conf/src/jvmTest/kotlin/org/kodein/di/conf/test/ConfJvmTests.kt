package org.kodein.di.conf.test

import org.kodein.di.*
import org.kodein.di.bindings.ExternalSource
import org.kodein.di.bindings.Factory
import org.kodein.di.bindings.externalFactory
import org.kodein.di.conf.ConfigurableDI
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.type.TypeToken
import org.kodein.type.erased
import org.kodein.type.jvmType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ConfJvmTests {

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

    // Only the JVM supports generics
    @Test fun test_00_GenericOverride() {
        val di = ConfigurableDI(true)

        di.addConfig {
            Bind<String>(erased()) with Factory(TypeToken.Any, org.kodein.type.generic(), erased()) { l: List<*> -> l.first().toString() + " *" }
        }

        assertEquals("Salomon *", di.direct.Factory<List<String>, String>(org.kodein.type.generic(), org.kodein.type.generic(), null).invoke(listOf("Salomon", "BRYS")))

        di.addConfig {
            Bind<String>(erased()) with Factory(TypeToken.Any, org.kodein.type.generic(), erased()) { l: List<String> -> l[0] + " " + l[1] }
        }

        assertEquals("Salomon BRYS", di.direct.Factory<List<String>, String>(org.kodein.type.generic(), org.kodein.type.generic(), null).invoke(listOf("Salomon", "BRYS")))
        assertEquals("42 *", di.direct.Factory<List<Int>, String>(org.kodein.type.generic(), org.kodein.type.generic(), null).invoke(listOf(42)))
    }

    @Test fun test_01_ExternalSource() {
        val di = ConfigurableDI(mutable = true)
        di.addConfig {
            externalSources += ExternalSource { key ->
                if (key.type.jvmType == String::class.java && key.tag == "foo")
                    externalFactory { "bar" }
                else
                    null
            }
        }

        assertEquals("bar", di.direct.instance(tag = "foo"))
        assertNull(di.direct.instanceOrNull<String>())

        di.addConfig {
            bind { provider { "def" } }
        }

        assertEquals("bar", di.direct.instance(tag = "foo"))
        assertEquals("def", di.direct.instance())
    }
}
