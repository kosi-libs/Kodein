package org.kodein

class LateInitKodein : Kodein {

    lateinit var baseKodein: Kodein

    override val container: KodeinContainer get() = baseKodein.container

}

class LazyKodein(private val f: () -> Kodein) : Kodein {

    val baseKodein by lazy(f)

    override val container: KodeinContainer get() = baseKodein.container

}
