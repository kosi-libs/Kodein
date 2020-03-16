package org.kodein.di

import org.kodein.di.bindings.ExternalSource
import org.kodein.di.bindings.externalFactory
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import org.kodein.type.jvmType
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_85_ExternalSource {

    @Test
    fun test_00_ExternalSource() {
        val kodein = DI.direct {
            bind(tag = "him") from singleton { Person("Salomon") }

            val laila = Person("Laila")
            externalSources += ExternalSource { key ->
                @Suppress("UNUSED_PARAMETER")
                when (key.type.jvmType) {
                    Person::class.java -> when (key.argType.jvmType) {
                        Unit::class.java -> when (key.tag) {
                            "her" -> externalFactory { laila }
                            null -> externalFactory { Person("Anyone") }
                            else -> null
                        }
                        else -> null
                    }
                    else -> null
                }
            }
        }

        assertNotNull(kodein.instanceOrNull<Person>())

        assertNull(kodein.instanceOrNull<Person>(tag = "no-one"))

        assertNotNull(kodein.instanceOrNull<Person>(tag = "him"))
        assertSame(kodein.instanceOrNull<Person>(tag = "him"), kodein.instanceOrNull(tag = "him"))

        assertNotNull(kodein.instanceOrNull<Person>(tag = "her"))
        assertSame(kodein.instanceOrNull<Person>(tag = "her"), kodein.instanceOrNull(tag = "her"))

        assertNotSame(kodein.instanceOrNull<Person>(), kodein.instanceOrNull())
        assertEquals(kodein.instanceOrNull<Person>(), kodein.instanceOrNull())
    }

}
