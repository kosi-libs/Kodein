package org.kodein.di

import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_04_NullBinding {


    @Test fun test_00_NullBindingProviderAndInstance() {

        val kodein = DI {}

        val i: Person? by kodein.instanceOrNull()
        val di: Person? = kodein.direct.instanceOrNull()
        val p: (() -> Person)? by kodein.providerOrNull()
        val dp: (() -> Person)? = kodein.direct.providerOrNull()

        assertAllNull(i, di, p, dp)
    }

    @Test fun test_01_NullBindingGetFactory() {

        val kodein = DI {}

        val f: ((String) -> Person)? by kodein.factoryOrNull()
        val df: ((String) -> Person)? = kodein.direct.factoryOrNull()
        val p: (() -> Person)? by kodein.providerOrNull(arg = "Salomon")
        val dp: (() -> Person)? = kodein.direct.providerOrNull(arg = "Salomon")
        val fp: (() -> Person)? by kodein.providerOrNull(fArg = { "Salomon" })
        val dfp: (() -> Person)? = kodein.direct.providerOrNull(fArg = { "Salomon" })
        val i: Person? by kodein.instanceOrNull(arg = "Salomon")
        val di: Person? = kodein.direct.instanceOrNull(arg = "Salomon")
        val fi: Person? by kodein.instanceOrNull(fArg = { "Salomon" })

        assertAllNull(f, df, p, dp, fp, dfp, i, di, fi)
    }

    @Test fun test_02_NonNullBindingProviderAndInstance() {

        val kodein = DI {
            bind<String>() with provider { "Salomon" }
        }

        val i: String? by kodein.instanceOrNull()
        val di: String? = kodein.direct.instanceOrNull()
        val p: (() -> String)? by kodein.providerOrNull()
        val dp: (() -> String)? = kodein.direct.providerOrNull()

        assertAllNotNull(i, di, p, dp)
        assertAllEqual("Salomon", i!!, di!!, p!!(), dp!!())
    }

    @Test fun test_03_NonNullBindingGetFactory() {

        val kodein = DI {
            bind<String>() with factory { name: String -> "$name BRYS" }
        }

        val f: ((String) -> String)? by kodein.factoryOrNull()
        val df: ((String) -> String)? = kodein.direct.factoryOrNull()
        val p: (() -> String)? by kodein.providerOrNull(arg = "Salomon")
        val dp: (() -> String)? = kodein.direct.providerOrNull(arg = "Salomon")
        val fp: (() -> String)? by kodein.providerOrNull(fArg = { "Salomon" })
        val dfp: (() -> String)? = kodein.direct.providerOrNull(fArg = { "Salomon" })
        val i: String? by kodein.instanceOrNull(arg = "Salomon")
        val di: String? = kodein.direct.instanceOrNull(arg = "Salomon")
        val fi: String? by kodein.instanceOrNull(fArg = { "Salomon" })

        assertAllNotNull(f, df, p, dp, fp, dfp, i, di, fi)

        assertAllEqual("Salomon BRYS", f!!.invoke("Salomon"), df!!.invoke("Salomon"), p!!(), dp!!(), fp!!(), dfp!!(), i!!, di!!, fi!!)
    }

    @Test fun test_04_NonNullDirectBindingProviderAndInstance() {

        val kodein = DI {
            bind { provider { "Salomon" } }
        }

        val i: String? by kodein.instanceOrNull()
        val di: String? = kodein.direct.instanceOrNull()
        val p: (() -> String)? by kodein.providerOrNull()
        val dp: (() -> String)? = kodein.direct.providerOrNull()

        assertAllNotNull(i, di, p, dp)
        assertAllEqual("Salomon", i!!, di!!, p!!(), dp!!())
    }

    @Test fun test_05_NonNullDirectBindingGetFactory() {

        val kodein = DI {
            bind { factory { name: String -> "$name BRYS" } }
        }

        val f: ((String) -> String)? by kodein.factoryOrNull()
        val df: ((String) -> String)? = kodein.direct.factoryOrNull()
        val p: (() -> String)? by kodein.providerOrNull(arg = "Salomon")
        val dp: (() -> String)? = kodein.direct.providerOrNull(arg = "Salomon")
        val fp: (() -> String)? by kodein.providerOrNull(fArg = { "Salomon" })
        val dfp: (() -> String)? = kodein.direct.providerOrNull(fArg = { "Salomon" })
        val i: String? by kodein.instanceOrNull(arg = "Salomon")
        val di: String? = kodein.direct.instanceOrNull(arg = "Salomon")
        val fi: String? by kodein.instanceOrNull(fArg = { "Salomon" })

        assertAllNotNull(f, df, p, dp, fp, dfp, i, di, fi)

        assertAllEqual("Salomon BRYS", f!!.invoke("Salomon"), df!!.invoke("Salomon"), p!!(), dp!!(), fp!!(), dfp!!(), i!!, di!!, fi!!)
    }
}
