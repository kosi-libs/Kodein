package org.kodein.di.erased

import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.test.*
import kotlin.reflect.KClass
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedTests_00_Factory {

    @Test
    fun test_00_FactoryBindingGetFactory() {

        val kodein = DI {
            bind() from factory { name: String -> Person(name) }
        }

        val p1: (String) -> Person by kodein.factory()
        val p2: (String) -> Person by kodein.factory()

        assertNotSame(p1("Salomon"), p2("Salomon"))
    }

    @Test
    fun test_01_WithFactoryGetProvider() {

        val kodein = DI { bind<Person>() with factory { name: String -> Person(name) } }

        val p: () -> Person by kodein.provider(arg = "Salomon")
        val dp: () -> Person = kodein.direct.provider(arg = "Salomon")

        assertAllEqual("Salomon", p().name, dp().name)

        val fp: () -> Person by kodein.provider(fArg = { "Salomon" })
        val dfp: () -> Person = kodein.direct.provider(fArg = { "Salomon" })

        assertAllEqual("Salomon", fp().name, dfp().name)
    }

    @Test
    fun test_02_WithFactoryGetInstance() {

        val kodein = DI { bind<Person>() with factory { name: String -> Person(name) } }

        val p: Person by kodein.instance(arg = "Salomon")

        assertEquals("Salomon", p.name)

        val fp: Person by kodein.instance(fArg = { "Salomon" })

        assertEquals("Salomon", fp.name)
    }

    @Test
    fun test_03_WithGenericFactoryGetInstance() {

        val kodein = DI { bind<Person>() with factory { l: List<*> -> Person(l.first().toString()) } }

        val p: Person by kodein.instance(arg = listOf("Salomon", "BRYS"))

        assertEquals("Salomon", p.name)
    }

    @Test
    fun test_04_WithTwoItfFactoryGetInstance() {

        val kodein = DI {
            bind<Person>() with factory { p: IName -> Person(p.firstName) }
            bind<Person>() with factory { p: IFullName -> Person(p.firstName + " " + p.lastName) }
        }

        val p: Person by kodein.instance(arg = FullInfos("Salomon", "BRYS", 30))

        assertFailsWith<DI.NotFoundException> { p.name }
    }

    @Test
    fun test_05_WithFactoryLambdaArgument() {
        val kodein = DI {
            bind<() -> Unit>() with factory { f: () -> Unit -> f }
        }

        var passed = false
        val f = { passed = true }

        val run: () -> Unit by kodein.instance(arg = f)
        run.invoke()

        assertTrue(passed)
    }

    interface FakeLogger { val cls: KClass<*> }

    class FakeLoggerImpl(override val cls: KClass<*>) : FakeLogger

    class AwareTest(override val di: DI) : DIAware {
        val logger: FakeLogger by instance(arg = this::class)
    }

    @Test
    fun test_06_StarFactory() {
        val kodein = DI {
            bind<FakeLogger>() with factory { cls: KClass<*> -> FakeLoggerImpl(cls) }
        }

        val logger: FakeLogger by kodein.instance(arg = AwareTest::class)
        assertEquals(AwareTest::class, logger.cls)

        val test = AwareTest(kodein)
        assertEquals(AwareTest::class, test.logger.cls)
    }


}
