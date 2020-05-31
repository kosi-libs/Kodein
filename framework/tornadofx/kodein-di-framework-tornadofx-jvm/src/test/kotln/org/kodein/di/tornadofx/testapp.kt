package org.kodein.di.tornadofx

import javafx.stage.*
import org.junit.*
import org.junit.Test
import org.junit.runners.*
import org.kodein.di.*
import org.testfx.api.*
import tornadofx.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AppTest {
    companion object {
        val kodeinDI = DI {
            installTornadoSource()
        }

        lateinit var primaryStage: Stage

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            primaryStage = FxToolkit.registerPrimaryStage()
        }
    }

    @Test
    fun test_00_instanceCheck() {
        val controller1: C by kodeinDI.instance()
        val controller2: C by kodeinDI.instance()

        assertNotNull(controller1)
        assertNotNull(controller2)
        assertSame(controller1, controller2)
    }

    @Test
    fun test_01_scope() {
        val scope1 = PersonScope()
        val controller1: C by kodeinDI.on(scope1).instance()
        val controller2: C by kodeinDI.on(scope1).instance()

        assertNotNull(controller1)
        assertSame(controller1, controller2)

        val scope2 = PersonScope()
        val controller3: C by kodeinDI.on(scope2).instance()

        assertNotNull(controller3)
        assertNotSame(controller1, controller3)
    }


    @Test
    fun test_02_scopedControllerAndViewModel() {
        val scope1 = PersonScope()

        val fragment1: F by kodeinDI.on(scope1).instance()
        val fragment2: F by kodeinDI.on(scope1).instance()

        assertNotNull(fragment1)
        assertNotNull(fragment2)

        assertSame(fragment1.c, fragment2.c)
        assertSame(fragment1.vm, fragment2.vm)

        val scope2 = PersonScope()
        val fragment3: F by kodeinDI.on(scope2).instance()

        assertNotNull(fragment3)

        assertNotSame(fragment1.c, fragment3.c)
        assertNotSame(fragment1.vm, fragment3.vm)
    }
}

class C : Controller()

class F : Fragment() {
    override val root = label()
    override val scope = super.scope as PersonScope
    val c: C by inject()
    val vm: VM by inject()
}

class VM : ViewModel(), ScopedInstance

class Person
class PersonModel : ItemViewModel<Person>(), ScopedInstance
class PersonScope : Scope() {
    val model = PersonModel()
}
