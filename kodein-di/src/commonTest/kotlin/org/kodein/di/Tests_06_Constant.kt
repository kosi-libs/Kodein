package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.IPerson
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.Test
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_06_Constant {

    @Test
    fun test_00_ConstantBindingGetInstance() {

        val di = DI {
            constant(tag = "answer") with 42
        }

        val c: Int by di.instance(tag = "answer")
        val answer: Int by di.constant()

        assertEquals(42, c)
        assertEquals(42, answer)
    }

    @Test
    fun test_01_ConstantBindingGetProvider() {

        val di = DI {
            constant(tag = "answer") with 42
        }

        val c: () -> Int by di.provider(tag = "answer")

        assertEquals(42, c())
    }

    @Test
    fun test_02_ConstantBindingGetPolymorphic() {

        val di = DI {
            constant(tag = "salomon") with Person("Salomon") as IPerson
        }

        val p: IPerson by di.instance(tag = "salomon")
        val salomon: IPerson by di.constant()

        assertEquals(Person("Salomon"), p)
        assertEquals(Person("Salomon"), salomon)
    }


}
