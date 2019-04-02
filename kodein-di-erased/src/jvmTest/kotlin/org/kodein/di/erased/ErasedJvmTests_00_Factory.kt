package org.kodein.di.erased

import org.kodein.di.Kodein
import org.kodein.di.bindings.subTypes
import org.kodein.di.jvmType
import org.kodein.di.test.*
import kotlin.test.Test
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_00_Factory {

    // Only the JVM supports up-casting
    @Test
    fun test_00_WithSubFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { p: Name -> Person(p.firstName) } }

        val p: Person by kodein.instance(arg = FullName("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    // Only the JVM supports up-casting
    @Test
    fun test_01_WithItfFactoryGetInstance() {

        val kodein = Kodein { bind<Person>() with factory { p: IName -> Person(p.firstName) } }

        val p: Person by kodein.instance(arg = FullName("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    @Test
    fun test_02_subTypeFactory() {
        val kodein = Kodein.direct {
            bind<IName>().subTypes() with { type ->
                when (type.jvmType) {
                    FullName::class.java -> singleton { FullName("Salomon", "BRYS") }
                    Name::class.java -> factory { _: Unit -> Name("Salomon") }
                    else -> throw IllegalStateException("Unknown type ${type.fullDispString()}")
                }
            }
        }

        assertEquals(FullName::class.java, kodein.instance<FullName>().javaClass)
        assertEquals(FullName("Salomon", "BRYS"), kodein.instance())
        assertEquals<FullName>(kodein.instance(), kodein.instance())

        assertEquals(Name::class.java, kodein.instance<Name>().javaClass)
        assertEquals(Name("Salomon"), kodein.instance())
        assertEquals<Name>(kodein.instance(), kodein.instance())
    }

}
