package org.kodein.di.jxinject

import org.kodein.di.*

fun test0() = DI {
    import(jxInjectorModule)

    bind { instance("Salomon") }
    bind(tag = "lastname") { instance("BRYS") }
    bind<List<String>>() with instance(listOf("a", "b", "c"))
    bind<Set<String>>() with instance(setOf("a", "b", "c"))
}

fun test1() = DI.direct {
    import(jxInjectorModule)

    var count = 0

    bind { provider { "Salomon ${count++}" } }
    bind(tag = "lastname") { provider { "BRYS ${count++}" } }
    bind<List<String>>() with provider { listOf("a", "b", "c") }
    bind<Set<String>>() with provider { setOf("a", "b", "c") }
}

fun test2() = DI {
    import(jxInjectorModule)

    bind { factory { i: Int -> "Salomon $i" } }
    bind(tag = "lastname") { factory { i: Int -> "BRYS $i" } }
    bind<List<String>>() with factory { i: Int -> listOf("a$i", "b$i", "c$i") }
    bind<Set<String>>() with factory { i: Int -> setOf("a$i", "b$i", "c$i") }
}

fun test4() = DI.direct {
    bind(tag = "universe:answer") { instance("fourty-two") }

    import(jxInjectorModule)

    jxQualifier<InjectJvmTests_04_Qualifier.UniversePrefix> { "universe:" + it.value }
}
