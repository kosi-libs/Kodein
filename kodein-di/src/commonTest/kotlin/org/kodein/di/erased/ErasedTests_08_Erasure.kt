package org.kodein.di.erased

import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.erasedComp1
import org.kodein.di.test.A
import org.kodein.di.test.B
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedTests_08_Erasure {

    @Test
    fun test_00_TypeErasure() {

        val la = listOf(A(null))
        val lb = listOf(B(null))

        val kodein = DI {
            Bind(erasedComp1<List<A>, A>()) with instance(la)
            Bind(erasedComp1<List<B>, B>()) with instance(lb)
        }

        assertSame(kodein.direct.Instance(erasedComp1<List<A>, A>(), null), la)
        assertSame(kodein.direct.Instance(erasedComp1<List<B>, B>(), null), lb)
    }


}
