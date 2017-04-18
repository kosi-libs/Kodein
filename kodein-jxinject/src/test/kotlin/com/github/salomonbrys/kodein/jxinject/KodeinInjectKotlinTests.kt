package com.github.salomonbrys.kodein.jxinject

import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

import org.junit.Assert.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KodeinInjectKotlinTests {

    @Test
    fun test00_0_SimpleInjection() {
        class Test {
            @Inject lateinit var firstname: String
        }

        val test = Test()
        test0().jx.inject(test)

        assertEquals("Salomon", test.firstname)
    }

    @Test
    fun test00_1_NamedInjection() {
        class Test {
            @Inject @field:Named("lastname") lateinit var lastname: String
            @Inject @field:Named("nope") @Optional var unknown: String? = null
        }

        val test = Test()
        test0().jx.inject(test)

        assertEquals("BRYS", test.lastname)
        assertNull(test.unknown)
    }

    @Test
    fun test00_2_LazyInjection() {
        class Test {
            @Inject lateinit var firstname: Lazy<String>
            @Inject @field:Named("nope") @Optional lateinit var unknown: Lazy<String?>
        }

        val test = Test()
        test0().jx.inject(test)

        assertEquals("Salomon", test.firstname.value)
        assertNotNull(test.unknown)
        assertNull(test.unknown.value)
    }

    @Test
    fun test00_3_MethodInjection() {
        class Test {
            internal var passed = false
            @Inject internal fun inject(
                firstname: String,
                @Named("lastname") lastname: String,
                @Named("nope") @Optional unknown: String?,
                lazyFirstname: Lazy<String>,
                @Named("lastname") lazyLastname: Lazy<String>,
                @Named("nope") @Optional lazyUnknown: Lazy<String>
            ) {
                assertEquals("Salomon", firstname)
                assertEquals("BRYS", lastname)
                assertNull(unknown)
                assertEquals("Salomon", lazyFirstname.value)
                assertEquals("BRYS", lazyLastname.value)
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
                passed = true
            }
        }

        val test = Test()
        test0().jx.inject(test)

        assertTrue(test.passed)
    }

    @Test
    fun test01_0_ProviderInjection() {
        class Test {
            @Inject @ProviderFun lateinit var firstname: Function0<String>
        }

        val test = Test()
        test1().jx.inject(test)

        assertEquals("Salomon 0", test.firstname.invoke())
        assertEquals("Salomon 1", test.firstname.invoke())
    }

    @Test
    fun test01_1_ProviderNamedInjection() {
        class Test {
            @Inject @field:Named("lastname") @ProviderFun lateinit var lastname: Function0<String>
            @Inject @field:Named("nope") @ProviderFun @Optional var unknown: Function0<String>? = null
        }

        val test = Test()
        test1().jx.inject(test)

        assertEquals("BRYS 0", test.lastname.invoke())
        assertEquals("BRYS 1", test.lastname.invoke())
        assertNull(test.unknown)
    }

    @Test
    fun test01_2_JavaxProviderInjection() {
        class Test {
            @Inject lateinit var firstname: Provider<String>
        }

        val test = Test()
        test1().jx.inject(test)

        assertEquals("Salomon 0", test.firstname.get())
        assertEquals("Salomon 1", test.firstname.get())
    }

    @Test
    fun test01_3_JavaxProviderNamedInjection() {
        class Test {
            @Inject @field:Named("lastname") lateinit var lastname: Provider<String>
            @Inject @field:Named("nope") @Optional var unknown: Provider<String>? = null
        }

        val test = Test()
        test1().jx.inject(test)

        assertEquals("BRYS 0", test.lastname.get())
        assertEquals("BRYS 1", test.lastname.get())
        assertNull(test.unknown)
    }

    @Test
    fun test01_4_LazyProviderInjection() {
        class Test {
            @Inject @ProviderFun lateinit var firstname: Lazy<Function0<String>>
            @Inject @field:Named("nope") @Optional @ProviderFun lateinit var unknown: Lazy<Function0<String>?>
        }

        val test = Test()
        test1().jx.inject(test)

        assertEquals("Salomon 0", test.firstname.value.invoke())
        assertNotNull(test.unknown)
        assertNull(test.unknown.value)
    }

    @Test
    fun test01_5_LazyJavaxProviderInjection() {
        class Test {
            @Inject lateinit var firstname: Lazy<Provider<String>>
            @Inject @field:Named("nope") @Optional lateinit var unknown: Lazy<Provider<String>?>
        }

        val test = Test()
        test1().jx.inject(test)

        assertEquals("Salomon 0", test.firstname.value.get())
        assertNotNull(test.unknown)
        assertNull(test.unknown.value)
    }

    @Test
    fun test01_6_ProviderMethodInjection() {
        class Test {
            internal var passed = false
            @Inject internal fun inject(
                @ProviderFun firstname: Function0<String>,
                @Named("lastname") @ProviderFun lastname: Function0<String>,
                @Named("nope") @Optional @ProviderFun unknown: Function0<String>?,
                @ProviderFun lazyFirstname: Lazy<Function0<String>>,
                @Named("lastname") @ProviderFun lazyLastname: Lazy<Function0<String>>,
                @Named("nope") @Optional @ProviderFun lazyUnknown: Lazy<Function0<String>?>
            ) {
                assertEquals("Salomon 0", firstname.invoke())
                assertEquals("BRYS 1", lastname.invoke())
                assertNull(unknown)
                assertEquals("Salomon 2", lazyFirstname.value.invoke())
                assertEquals("BRYS 3", lazyLastname.value.invoke())
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
                passed = true
            }
        }

        val test = Test()
        test1().jx.inject(test)

        assertTrue(test.passed)
    }

    @Test
    fun test01_7_JavaxProviderMethodInjection() {
        class Test {
            internal var passed = false
            @Inject internal fun inject(
                firstname: Provider<String>,
                @Named("lastname") lastname: Provider<String>,
                @Named("nope") @Optional unknown: Provider<String>?,
                lazyFirstname: Lazy<Provider<String>>,
                @Named("lastname") lazyLastname: Lazy<Provider<String>>,
                @Named("nope") @Optional lazyUnknown: Lazy<Provider<String>?>
            ) {
                assertEquals("Salomon 0", firstname.get())
                assertEquals("BRYS 1", lastname.get())
                assertNull(unknown)
                assertEquals("Salomon 2", lazyFirstname.value.get())
                assertEquals("BRYS 3", lazyLastname.value.get())
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
                passed = true
            }
        }

        val test = Test()
        test1().jx.inject(test)

        assertTrue(test.passed)
    }

    @Test
    fun test02_0_FactoryInjection() {
        class Test {
            @Inject @FactoryFun lateinit var firstname: Function1<Int, String>
        }

        val test = Test()
        test2().jx.inject(test)

        assertEquals("Salomon 21", test.firstname.invoke(21))
    }

    @Test
    fun test02_1_ProviderNamedInjection() {
        class Test {
            @Inject @field:Named("lastname") @FactoryFun lateinit var lastname: Function1<Int, String>
            @Inject @field:Named("nope") @FactoryFun @Optional var unknown: Function1<Int, String>? = null
        }

        val test = Test()
        test2().jx.inject(test)

        assertEquals("BRYS 42", test.lastname.invoke(42))
        assertNull(test.unknown)
    }

    @Test
    fun test02_2_FactoryMethodInjection() {
        class Test {
            internal var passed = false
            @Inject internal fun inject(
                @FactoryFun firstname: Function1<Int, String>,
                @Named("lastname") @FactoryFun lastname: Function1<Int, String>,
                @Named("nope") @Optional @FactoryFun unknown: Function1<Int, String>?,
                @FactoryFun lazyFirstname: Lazy<Function1<Int, String>>,
                @Named("lastname") @FactoryFun lazyLastname: Lazy<Function1<Int, String>>,
                @Named("nope") @Optional @FactoryFun lazyUnknown: Lazy<Function1<Int, String>?>
            ) {
                assertEquals("Salomon 21", firstname.invoke(21))
                assertEquals("BRYS 42", lastname.invoke(42))
                assertNull(unknown)
                assertEquals("Salomon 63", lazyFirstname.value.invoke(63))
                assertEquals("BRYS 84", lazyLastname.value.invoke(84))
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
                passed = true
            }
        }

        val test = Test()
        test2().jx.inject(test)

        assertTrue(test.passed)
    }

    @Test
    fun test03_0_ConstructorInjection() {
        class Test @Inject constructor(
            firstname: String,
            @Named("lastname") lastname: String,
            @Named("nope") @Optional unknown: String?,
            lazyFirstname: Lazy<String>,
            @Named("lastname") lazyLastname: Lazy<String>,
            @Named("nope") @Optional lazyUnknown: Lazy<String?>
        ) {
            init {
                assertEquals("Salomon", firstname)
                assertEquals("BRYS", lastname)
                assertNull(unknown)
                assertEquals("Salomon", lazyFirstname.value)
                assertEquals("BRYS", lazyLastname.value)
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
            }
        }

        test0().jx.newInstance(Test::class.java)
    }

    @Test
    fun test03_1_ConstructorProviderInjection() {
        class Test @Inject constructor(
            @ProviderFun firstname: Function0<String>,
            @Named("lastname") @ProviderFun lastname: Function0<String>,
            @Named("nope") @Optional @ProviderFun unknown: Function0<String>?,
            @ProviderFun lazyFirstname: Lazy<Function0<String>>,
            @Named("lastname") @ProviderFun lazyLastname: Lazy<Function0<String>>,
            @Named("nope") @Optional @ProviderFun lazyUnknown: Lazy<Function0<String>?>
        ) {
            init {
                assertEquals("Salomon 0", firstname.invoke())
                assertEquals("BRYS 1", lastname.invoke())
                assertNull(unknown)
                assertEquals("Salomon 2", lazyFirstname.value.invoke())
                assertEquals("BRYS 3", lazyLastname.value.invoke())
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
            }
        }

        test1().jx.newInstance(Test::class.java)
    }

    @Test
    fun test03_2_ConstructorJavaxProviderInjection() {
        class Test @Inject constructor(
            firstname: Provider<String>,
            @Named("lastname") lastname: Provider<String>,
            @Named("nope") @Optional unknown: Provider<String>?,
            lazyFirstname: Lazy<Provider<String>>,
            @Named("lastname") lazyLastname: Lazy<Provider<String>>,
            @Named("nope") @Optional lazyUnknown: Lazy<Provider<String>?>
        ) {
            init {
                assertEquals("Salomon 0", firstname.get())
                assertEquals("BRYS 1", lastname.get())
                assertNull(unknown)
                assertEquals("Salomon 2", lazyFirstname.value.get())
                assertEquals("BRYS 3", lazyLastname.value.get())
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
            }
        }

        test1().jx.newInstance(Test::class.java)
    }

    @Test
    fun test03_3_FactoryConstructorInjection() {
        class Test @Inject constructor(
            @FactoryFun firstname: Function1<Int, String>,
            @Named("lastname") @FactoryFun lastname: Function1<Int, String>,
            @Named("nope") @Optional @FactoryFun unknown: Function1<Int, String>?,
            @FactoryFun lazyFirstname: Lazy<Function1<Int, String>>,
            @Named("lastname") @FactoryFun lazyLastname: Lazy<Function1<Int, String>>,
            @Named("nope") @Optional @FactoryFun lazyUnknown: Lazy<Function1<Int, String>?>
        ) {
            init {
                assertEquals("Salomon 21", firstname.invoke(21))
                assertEquals("BRYS 42", lastname.invoke(42))
                assertNull(unknown)
                assertEquals("Salomon 63", lazyFirstname.value.invoke(63))
                assertEquals("BRYS 84", lazyLastname.value.invoke(84))
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
            }
        }

        test2().jx.newInstance(Test::class.java)
    }

    @Test
    fun test03_4_OnlyOneConstructorInjection() {
        class Test(
            firstname: String,
            @Named("lastname") lastname: String,
            @Named("nope") @Optional unknown: String,
            lazyFirstname: Lazy<String>,
            @Named("lastname") lazyLastname: Lazy<String>,
            @Named("nope") @Optional lazyUnknown: Lazy<String>
        ) {
            init {
                assertEquals("Salomon", firstname)
                assertEquals("BRYS", lastname)
                assertNull(unknown)
                assertEquals("Salomon", lazyFirstname.value)
                assertEquals("BRYS", lazyLastname.value)
                assertNotNull(lazyUnknown)
                assertNull(lazyUnknown.value)
            }
        }

        test0().jx.newInstance(Test::class.java)
    }
}
