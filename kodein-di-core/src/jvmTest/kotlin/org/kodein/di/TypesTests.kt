package org.kodein.di

import kotlin.test.Test
import kotlin.test.assertTrue

class TypesTests {

    interface A

    interface B : A

    interface Base<T : A>

    class Concrete : Base<B>

    @Test
    fun test_01_genericInterface() {
        val concrete = Concrete()

        val typeOfConcrete = TTOf(concrete)

        val typeToTest1 = generic<Base<out A>>()
        assertTrue(typeToTest1.isAssignableFrom(typeOfConcrete))

        val typeToTest2 = generic<Base<out B>>()
        assertTrue(typeToTest2.isAssignableFrom(typeOfConcrete))
    }

}
