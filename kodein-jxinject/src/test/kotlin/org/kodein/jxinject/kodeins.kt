package org.kodein.jxinject

import org.kodein.*
import org.kodein.bindings.*

fun test0() = Kodein {
    import(jxInjectorModule)

    bind() from InstanceBinding(erased(), "Salomon")
    bind(tag = "lastname") from InstanceBinding(erased(), "BRYS")
    Bind<List<String>>(generic()) with InstanceBinding(generic(), listOf("a", "b", "c"))
    Bind<Set<String>>(erased()) with InstanceBinding(erased(), setOf("a", "b", "c"))
}

fun test1() = Kodein {
    import(jxInjectorModule)

    var count = 0

    bind() from Provider(AnyToken, erased()) { "Salomon ${count++}" }
    bind(tag = "lastname") from Provider(AnyToken, erased()) { "BRYS ${count++}" }
    Bind<List<String>>(generic()) with Provider(AnyToken, generic()) { listOf("a", "b", "c") }
    Bind<Set<String>>(erased()) with Provider(AnyToken, erased()) { setOf("a", "b", "c") }
}

fun test2() = Kodein {
    import(jxInjectorModule)

    bind() from Factory(AnyToken, erased(), erased()) { i: Int -> "Salomon $i" }
    bind(tag = "lastname") from Factory(AnyToken, erased(), erased()) { i: Int -> "BRYS $i" }
    Bind<List<String>>(generic()) with Factory(AnyToken, erased(), generic()) { i: Int -> listOf("a$i", "b$i", "c$i") }
    Bind<Set<String>>(erased()) with Factory(AnyToken, erased(), erased()) { i: Int -> setOf("a$i", "b$i", "c$i") }
}

fun test4() = Kodein {
    bind(tag = "universe:answer") from InstanceBinding(erased(), "fourty-two")

    import(jxInjectorModule)

    jxQualifier<KodeinInjectJvmTests.Test04_00_UniversePrefix> { "universe:" + it.value }
}

interface Test5A
interface Test5B { val a: Test5A }

class Test5AImpl : Test5A
class Test5BImpl(override val a: Test5A) : Test5B

fun test5() = Kodein {
    import(jxInjectorModule)

    Bind<Test5A>(generic()) with Singleton(NoScope(), AnyToken, generic()) { jx.newInstance<Test5AImpl>() }
    Bind<Test5B>(generic()) with Singleton(NoScope(), AnyToken, generic()) { jx.newInstance<Test5BImpl>() }
}
