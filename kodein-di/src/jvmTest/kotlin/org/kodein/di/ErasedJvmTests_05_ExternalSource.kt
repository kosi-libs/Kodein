package org.kodein.di

import org.kodein.di.bindings.ExternalSource
import org.kodein.di.bindings.externalFactory
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import org.kodein.type.jvmType
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_05_ExternalSource {

    // Only the JVM supports class.java
    @Test
    fun test_00_ExternalSource() {
        val di = DI.direct {
            bind(tag = "him") { singleton { Person("Salomon") } }

            val laila = Person("Laila")
            externalSources += ExternalSource { key ->
                @Suppress("UNUSED_PARAMETER")
                when (key.type.jvmType) {
                    Person::class.java -> {
                        when (key.tag) {
                            "her" -> externalFactory { laila }
                            null -> externalFactory { Person("Anyone") }
                            else -> null
                        }
                    }
                    else -> null
                }
            }
        }

        assertNotNull(di.instanceOrNull<Person>())

        assertNull(di.instanceOrNull<Person>(tag = "no-one"))

        assertNotNull(di.instanceOrNull<Person>(tag = "him"))
        assertSame(di.instanceOrNull<Person>(tag = "him"), di.instanceOrNull<Person>(tag = "him"))

        assertNotNull(di.instanceOrNull<Person>(tag = "her"))
        assertSame(di.instanceOrNull<Person>(tag = "her"), di.instanceOrNull<Person>(tag = "her"))

        assertNotSame(di.instanceOrNull<Person>(), di.instanceOrNull<Person>())
        assertEquals(di.instanceOrNull<Person>(), di.instanceOrNull<Person>())
    }

}
