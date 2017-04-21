package com.github.salomonbrys.kodein.jxinject

import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.bindings.FactoryBinding
import com.github.salomonbrys.kodein.bindings.InstanceBinding
import com.github.salomonbrys.kodein.bindings.ProviderBinding

fun test0() = Kodein {
    bind() from InstanceBinding(erased(), "Salomon")
    bind(tag = "lastname") from InstanceBinding(erased(), "BRYS")
    Bind<List<String>>(generic()) with InstanceBinding(generic(), listOf("a", "b", "c"))
    Bind<Set<String>>(erased()) with InstanceBinding(erased(), setOf("a", "b", "c"))

    import(jxInjectorModule)
}

fun test1() = Kodein {
    var count = 0

    bind() from ProviderBinding(erased()) { "Salomon ${count++}" }
    bind(tag = "lastname") from ProviderBinding(erased()) { "BRYS ${count++}" }
    Bind<List<String>>(generic()) with ProviderBinding(generic()) { listOf("a", "b", "c") }
    Bind<Set<String>>(erased()) with ProviderBinding(erased()) { setOf("a", "b", "c") }

    import(jxInjectorModule)
}

fun test2() = Kodein {
    bind() from FactoryBinding(erased(), erased()) { i: Int -> "Salomon $i" }
    bind(tag = "lastname") from FactoryBinding(erased(), erased()) { i: Int -> "BRYS $i" }
    Bind<List<String>>(generic()) with FactoryBinding(erased(), generic()) { i: Int -> listOf("a$i", "b$i", "c$i") }
    Bind<Set<String>>(erased()) with FactoryBinding(erased(), erased()) { i: Int -> setOf("a$i", "b$i", "c$i") }

    import(jxInjectorModule)
}

fun test4() = Kodein {
    bind(tag = "universe:answer") from InstanceBinding(erased(), "fourty-two")

    import(jxInjectorModule)

    jxQualifier<KodeinInjectJavaTests.Test04_0_UniversePrefix> { "universe:" + it.value }
}
