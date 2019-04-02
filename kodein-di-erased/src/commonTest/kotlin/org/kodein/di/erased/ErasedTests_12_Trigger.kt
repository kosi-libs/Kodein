package org.kodein.di.erased

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import org.kodein.di.test.Person
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedTests_12_Trigger {

    class T00(override val kodein: Kodein): KodeinAware {
        override val kodeinTrigger = KodeinTrigger()
        val newPerson: () -> Person by provider()
        val salomon: Person by instance(tag = "named")
        val pFactory: (String) -> Person by factory(tag = "factory")
        val pProvider: () -> Person by provider(tag = "factory", arg = "provided")
        val instance: Person by instance(tag = "factory", arg = "reified")
    }

    @Test
    fun test_00_SimpleTrigger() {
        val kodein = Kodein {
            bind<Person>() with provider { Person() }
            bind<Person>(tag = "named") with singleton { Person("Salomon") }
            bind<Person>(tag = "factory") with factory { name: String -> Person(name) }
        }

        val injected = T00(kodein)

        injected.kodeinTrigger.trigger()
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

    class T01(override val kodein: Kodein): KodeinAware {
        override val kodeinTrigger = KodeinTrigger()
        @Suppress("unused")
        val person: Person by instance()
    }

    @Test
    fun test_01_CreatedAtTrigger() {
        var created = false
        val kodein = Kodein {
            bind<Person>() with singleton { created = true; Person() }
        }

        val container = T01(kodein)

        assertFalse(created)
        container.kodeinTrigger.trigger()
        assertTrue(created)
    }


}
