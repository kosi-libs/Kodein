package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_03_Instance {

    @Test fun test_00_InstanceBindingGetInstance() {

        val p = Person()

        val di = DI { bind() from instance(p) }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test fun test_01_InstanceBindingGetProvider() {

        val p = Person()

        val di = DI { bind<Person>() with instance(p) }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }
}
