package org.kodein.di.conf.test

import org.kodein.di.*
import org.kodein.di.bindings.ExternalSource
import org.kodein.di.bindings.Factory
import org.kodein.di.bindings.externalFactory
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.instanceOrNull
import org.kodein.di.erased.provider
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinConfJvmTests {

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

        assertEquals("bar", kodein.direct.instance(tag = "foo"))
        assertNull(kodein.direct.instanceOrNull<String>())

        kodein.addConfig {
            bind() from provider { "def" }
        }

        assertEquals("bar", kodein.direct.instance(tag = "foo"))
        assertEquals("def", kodein.direct.instance())
    }
}
