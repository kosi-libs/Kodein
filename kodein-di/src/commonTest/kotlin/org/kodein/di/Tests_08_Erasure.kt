package org.kodein.di

import org.kodein.di.test.A
import org.kodein.di.test.B
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.type.erasedComp
import org.kodein.type.generic
import kotlin.test.Test
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_08_Erasure {

    @Test
    fun test_00_TypeErasure() {

        val la = listOf(A(null))
        val lb = listOf(B(null))

        val di = DI {
            Bind(erasedComp(List::class, generic<A>())) with instance(la)
            Bind(erasedComp(List::class, generic<B>())) with instance(lb)
        }

        assertSame(di.direct.Instance(erasedComp(List::class, generic<A>()), null), la)
        assertSame(di.direct.Instance(erasedComp(List::class, generic<B>()), null), lb)
    }


}
