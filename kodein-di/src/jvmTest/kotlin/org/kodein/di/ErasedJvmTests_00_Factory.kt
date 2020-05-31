package org.kodein.di

import org.kodein.di.bindings.subTypes
import org.kodein.di.test.*
import org.kodein.type.jvmType
import kotlin.test.Test
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_00_Factory {

    // Only the JVM supports up-casting
    @Test
    fun test_00_WithSubFactoryGetInstance() {

        val di = DI { bind<Person>() with factory { p: Name -> Person(p.firstName) } }

        val p: Person by di.instance(arg = FullName("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    // Only the JVM supports up-casting
    @Test
    fun test_01_WithItfFactoryGetInstance() {

        val di = DI { bind<Person>() with factory { p: IName -> Person(p.firstName) } }

        val p: Person by di.instance(arg = FullName("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    @Test
    fun test_02_subTypeFactory() {
        val di = DI.direct {
            bind<IName>().subTypes() with { type ->
                when (type.jvmType) {
                    FullName::class.java -> singleton { FullName("Salomon", "BRYS") }
                    Name::class.java -> factory { _: Unit -> Name("Salomon") }
                    else -> throw IllegalStateException("Unknown type ${type.qualifiedDispString()}")
                }
            }
        }

        assertEquals(FullName::class.java, di.instance<FullName>().javaClass)
        assertEquals(FullName("Salomon", "BRYS"), di.instance())
        assertEquals<FullName>(di.instance(), di.instance())

        assertEquals(Name::class.java, di.instance<Name>().javaClass)
        assertEquals(Name("Salomon"), di.instance())
        assertEquals<Name>(di.instance(), di.instance())
    }

}
