package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_07_Error {

    @Test
    fun test_00_DependencyLoopFullDescription() {

        val di = DI {
            fullDescriptionOnError = true
            bind<A>() with singleton { A(instance()) }
            bind<B>() with singleton { B(instance()) }
            bind<C>() with singleton { C(instance()) }
        }

        val ex = assertFailsWith<DI.DependencyLoopException> {
            di.direct.instance<A>()
        }

        assertEquals("""
Dependency recursion:
     bind<org.kodein.di.test.A>()
    ╔╩>bind<org.kodein.di.test.B>()
    ║  ╚>bind<org.kodein.di.test.C>()
    ║    ╚>bind<org.kodein.di.test.A>()
    ╚══════╝
        """.trim(), ex.message?.trim()
        )
    }

    @Test
    fun test_01_TypeNotFoundFullDescription() {

        val di = DI.direct {
            fullDescriptionOnError = true
        }

        assertEquals("No binding found for bind<org.kodein.di.test.Person>() with ? { ? }\nRegistered in this DI container:\n", assertFailsWith<DI.NotFoundException> { di.instance<Person>() }.message)
    }


}
