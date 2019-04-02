package org.kodein.di

import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import kotlin.test.Test
import kotlin.test.assertTrue

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TypesTests {

    interface A

    interface B : A

    interface Base<T : A>

    class Concrete : Base<B>

    @Test
    fun test_00_genericInterface() {
        val concrete = Concrete()

        val typeOfConcrete = TTOf(concrete)

        val typeToTest1 = generic<Base<out A>>()
        assertTrue(typeToTest1.isAssignableFrom(typeOfConcrete))

        val typeToTest2 = generic<Base<out B>>()
        assertTrue(typeToTest2.isAssignableFrom(typeOfConcrete))
    }

}
