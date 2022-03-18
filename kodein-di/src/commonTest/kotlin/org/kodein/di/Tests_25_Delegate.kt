package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_25_Delegate {

    interface Itf
    class Cls : Itf

    @Test
    fun test_00_Delegate_interface_to_impl() {
        val di = DI {
            bind { singleton { Cls() } }
            delegate<Itf>().to<Cls>()
        }

        val cls: Cls by di.instance()
        val itf: Itf by di.instance()

        assertSame(cls, itf)
    }

    @Test
    fun test_00_Delegate_to_same_bound_type() {
        assertFailsWith<DI.OverridingException> {
            DI {
                bind { singleton { Cls() } }
                delegate<Cls>().to<Cls>()
            }
        }
    }
}
