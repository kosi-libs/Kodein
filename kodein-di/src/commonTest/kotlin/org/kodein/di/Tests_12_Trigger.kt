package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_12_Trigger {

    class T00(override val di: DI): DIAware {
        override val diTrigger = DITrigger()
        val newPerson: () -> Person by provider()
        val salomon: Person by instance(tag = "named")
        val pFactory: (String) -> Person by factory(tag = "factory")
        val pProvider: () -> Person by provider(tag = "factory", arg = "provided")
        val instance: Person by instance(tag = "factory", arg = "reified")
    }

    @Test
    fun test_00_SimpleTrigger() {
        val di = DI {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        val injected = T00(di)

        injected.diTrigger.trigger()
        assertNotSame(injected.newPerson(), injected.newPerson())
        assertEquals("Salomon", injected.salomon.name)
        assertSame(injected.salomon, injected.salomon)
        assertNotSame(injected.pFactory("Laila"), injected.pFactory("Laila"))
        assertEquals("Laila", injected.pFactory("Laila").name)
        assertEquals("provided", injected.pProvider().name)
        assertNotSame(injected.pProvider(), injected.pProvider())
        assertEquals("reified", injected.instance.name)
        assertSame(injected.instance, injected.instance)
    }

    class T01(override val di: DI): DIAware {
        override val diTrigger = DITrigger()
        @Suppress("unused")
        val person: Person by instance()
    }

    @Test
    fun test_01_CreatedAtTrigger() {
        var created = false
        val di = DI {
            bind<Person>() with singleton { created = true; Person() }
        }

        val container = T01(di)

        assertFalse(created)
        container.diTrigger.trigger()
        assertTrue(created)
    }


}
