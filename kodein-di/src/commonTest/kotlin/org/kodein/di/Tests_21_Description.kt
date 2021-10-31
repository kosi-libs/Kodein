package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.type.erasedComp
import org.kodein.type.generic
import kotlin.test.Test
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_21_Description {

    @Test
    fun test_00_SimpleKeySimpleDescription() {
        val key = DI.Key(
                contextType = generic<Any>(),
                argType = generic<Unit>(),
                type = generic<String>(),
                tag = null
        )

        assertEquals("bind<String>", key.bindDescription)
        assertEquals("String", key.description)
    }

    @Test
    fun test_01_ComplexKeySimpleDescription() {
        val key = DI.Key(
                contextType = generic<String>(),
                argType = erasedComp(Pair::class, generic<String>(), generic<String>()),
                type = generic<IntRange>(),
                tag = "someTag"
        )

        assertEquals("""bind<IntRange>(tag = "someTag")""", key.bindDescription)
        assertEquals("""IntRange tagged "someTag" on context String, with argument Pair<String, String>""", key.description)
    }

}
