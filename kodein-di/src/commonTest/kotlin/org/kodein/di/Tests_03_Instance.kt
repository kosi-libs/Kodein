package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_03_Instance {

    @Test fun test_00_InstanceBindingGetInstance() {

        val p = Person()

        val di = DI { bind<Person>() with instance(p) }

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


    @Test fun test_03_DirectInstanceBindingGetInstance() {

        val p = Person()

        val di = DI { bind { instance(p) } }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test fun test_04_DirectInstanceBindingGetProvider() {

        val p = Person()

        val di = DI { bind { instance(p) } }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }

    @Test fun test_05_SimpleInstanceBindingGetInstance() {

        val p = Person()

        val di = DI { bindInstance(p) }

        val p1: Person by di.instance()
        val p2: Person by di.instance()

        assertSame(p1, p)
        assertSame(p2, p)
    }

    @Test fun test_06_SimpleInstanceBindingGetProvider() {

        val p = Person()

        val di = DI { bindInstance(p) }

        val p1: () -> Person by di.provider()
        val p2: () -> Person by di.provider()

        assertSame(p1(), p)
        assertSame(p2(), p)
    }
}
