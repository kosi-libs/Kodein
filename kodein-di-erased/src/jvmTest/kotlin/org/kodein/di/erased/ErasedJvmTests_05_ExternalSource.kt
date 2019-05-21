package org.kodein.di.erased

import org.kodein.di.Kodein
import org.kodein.di.bindings.ExternalSource
import org.kodein.di.bindings.externalFactory
import org.kodein.di.jvmType
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_05_ExternalSource {

    // Only the JVM supports class.java
    @Test
    fun test_00_ExternalSource() {
        val kodein = Kodein.direct {
            bind(tag = "him") from singleton { Person("Salomon") }

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

        assertNotNull(kodein.instanceOrNull<Person>())

        assertNull(kodein.instanceOrNull<Person>(tag = "no-one"))

        assertNotNull(kodein.instanceOrNull<Person>(tag = "him"))
        assertSame(kodein.instanceOrNull<Person>(tag = "him"), kodein.instanceOrNull<Person>(tag = "him"))

        assertNotNull(kodein.instanceOrNull<Person>(tag = "her"))
        assertSame(kodein.instanceOrNull<Person>(tag = "her"), kodein.instanceOrNull<Person>(tag = "her"))

        assertNotSame(kodein.instanceOrNull<Person>(), kodein.instanceOrNull<Person>())
        assertEquals(kodein.instanceOrNull<Person>(), kodein.instanceOrNull<Person>())
    }

}
