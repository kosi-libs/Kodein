package org.kodein.di

import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertNotSame
import kotlin.test.assertSame

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_87_BaseClassBinding {

    open class Resource1
    open class Resource2 : Resource1()

    val kodein00 = DI {
        bind<Resource1>() with singleton { Resource1() }
        bind<Resource2>() with singleton { Resource2() }
    }

    private val instanceLevelResource1: Resource1 by kodein00.instance()

    @Test
    fun test_00_BaseClassInjection() {
        val localResource1: Resource1 by kodein00.instance()
        assertSame(localResource1, instanceLevelResource1)
    }

    val kodein01 = DI {
        bind<Resource1>() with contexted<String>().provider { Resource1() }
        bind<Resource2>() with singleton { Resource2() }
    }

    private val _01_uncontextedInstanceLevelResource1: Resource1 by kodein01.instance()
    private val _01_contextedInstanceLevelResource1: Resource1 by kodein01.on("context").instance()

    @Test
    fun test_01_ContextedBaseClassInjection() {
        val localResource1: Resource1 by kodein01.instance()
        assertSame(localResource1, _01_uncontextedInstanceLevelResource1)
        assertNotSame(localResource1, _01_contextedInstanceLevelResource1)
    }
}
