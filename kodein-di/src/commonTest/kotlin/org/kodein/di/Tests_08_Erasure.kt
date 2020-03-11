package org.kodein.di

import org.kodein.di.erased.instance
import org.kodein.di.test.A
import org.kodein.di.test.B
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_08_Erasure {

    @Test
    fun test_00_TypeErasure() {

        val la = listOf(A(null))
        val lb = listOf(B(null))

        val di = DI {
            Bind(erasedComp1<List<A>, A>()) with instance(la)
            Bind(erasedComp1<List<B>, B>()) with instance(lb)
        }

        assertSame(di.direct.Instance(erasedComp1<List<A>, A>(), null), la)
        assertSame(di.direct.Instance(erasedComp1<List<B>, B>(), null), lb)
    }


}
