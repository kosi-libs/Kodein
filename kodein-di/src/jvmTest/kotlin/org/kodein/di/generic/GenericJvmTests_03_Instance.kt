package org.kodein.di.generic

import org.kodein.di.DI
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_03_Instance {

    @Test
    fun test_00_InstanceBindingGetInstance() {

        val p = Person()

        val kodein = DI { bind() from instance(p) }

        val p1: Person by kodein.instance()
        val p2: Person by kodein.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test
    fun test_01_InstanceBindingGetProvider() {

        val p = Person()

        val kodein = DI { bind<Person>() with instance(p) }

        val p1: () -> Person by kodein.provider()
        val p2: () -> Person by kodein.provider()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }


}
