package org.kodein.jxinject

import org.kodein.Kodein
import org.kodein.generic.*

fun test0() = Kodein {
    import(jxInjectorModule)

    bind() from instance("Salomon")
    bind(tag = "lastname") from instance("BRYS")
    bind<List<String>>() with instance(listOf("a", "b", "c"))
    bind<Set<String>>() with instance(setOf("a", "b", "c"))
}

fun test1() = Kodein {
    import(jxInjectorModule)

    var count = 0

    bind() from provider { "Salomon ${count++}" }
    bind(tag = "lastname") from provider { "BRYS ${count++}" }
    bind<List<String>>() with provider { listOf("a", "b", "c") }
    bind<Set<String>>() with provider { setOf("a", "b", "c") }
}

fun test2() = Kodein {
    import(jxInjectorModule)

    bind() from factory { i: Int -> "Salomon $i" }
    bind(tag = "lastname") from factory { i: Int -> "BRYS $i" }
    bind<List<String>>() with factory { i: Int -> listOf("a$i", "b$i", "c$i") }
    bind<Set<String>>() with factory { i: Int -> setOf("a$i", "b$i", "c$i") }
}

fun test4() = Kodein {
    bind(tag = "universe:answer") from instance("fourty-two")

    import(jxInjectorModule)

    jxQualifier<KodeinInjectJvmTests.Test04_00_UniversePrefix> { "universe:" + it.value }
}

interface Test5A
interface Test5B { val a: Test5A }

class Test5AImpl : Test5A
class Test5BImpl(override val a: Test5A) : Test5B

fun test5() = Kodein {
    import(jxInjectorModule)

    bind<Test5A>() with singleton { jx.newInstance<Test5AImpl>() }
    bind<Test5B>() with singleton { jx.newInstance<Test5BImpl>() }
}
