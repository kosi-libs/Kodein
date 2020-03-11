package org.kodein.di

import org.kodein.di.DI
import org.kodein.di.erased
import org.kodein.di.erasedComp2
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_21_Description {

    @Test
    fun test_00_SimpleKeySimpleDescription() {
        val key = DI.Key(
                contextType = erased<Any>(),
                argType = erased<Unit>(),
                type = erased<String>(),
                tag = null
        )

        assertEquals("bind<String>()", key.bindDescription)
        assertEquals("bind<String>() with ? { ? }", key.description)
    }

    @Test
    fun test_01_ComplexKeySimpleDescription() {
        val key = DI.Key(
                contextType = erased<String>(),
                argType = erasedComp2<Pair<String, String>, String, String>(),
                type = erased<IntRange>(),
                tag = "tag"
        )

        assertEquals("bind<IntRange>(tag = \"tag\")", key.bindDescription)
        assertEquals("bind<IntRange>(tag = \"tag\") with ?<String>().? { Pair<String, String> -> ? }", key.description)
    }

}
