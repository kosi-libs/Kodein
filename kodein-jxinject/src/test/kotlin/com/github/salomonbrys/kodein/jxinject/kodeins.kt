package com.github.salomonbrys.kodein.jxinject

import com.github.salomonbrys.kodein.*

fun test0() = Kodein {
    bind() from erasedInstance("Salomon")
    bind(tag = "lastname") from erasedInstance("BRYS")
    bindGeneric<List<String>>() with genericInstance(listOf("a", "b", "c"))
    bindErased<Set<String>>() with erasedInstance(setOf("a", "b", "c"))

    import(jxInjectorModule)
}

fun test1() = Kodein {
    var count = 0

    bind() from erasedProvider { "Salomon ${count++}" }
    bind(tag = "lastname") from erasedProvider { "BRYS ${count++}" }
    bindGeneric<List<String>>() with genericProvider { listOf("a", "b", "c") }
    bindErased<Set<String>>() with erasedProvider { setOf("a", "b", "c") }

    import(jxInjectorModule)
}

fun test2() = Kodein {
    bind() from erasedFactory { i: Int -> "Salomon $i" }
    bind(tag = "lastname") from erasedFactory { i: Int -> "BRYS $i" }
    bindGeneric<List<String>>() with genericFactory { i: Int -> listOf("a$i", "b$i", "c$i") }
    bindErased<Set<String>>() with erasedFactory { i: Int -> setOf("a$i", "b$i", "c$i") }

    import(jxInjectorModule)
}
