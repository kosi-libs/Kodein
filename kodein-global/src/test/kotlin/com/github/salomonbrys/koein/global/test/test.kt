package com.github.salomonbrys.koein.global.test

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.global.global
import com.github.salomonbrys.kodein.instance
import junit.framework.TestCase
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinGlobalTests : TestCase() {

    @Test fun test00_0_Global() {

        Kodein.global.addImport(Kodein.Module {
            constant("answer") with 42
        })

        val answer: Int = Kodein.global.instance("answer")

        assertEquals(42, answer)
    }

}
