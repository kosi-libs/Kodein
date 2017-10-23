package org.kodein

@Suppress("unused")
class KodeinInjector {

    val properties = ArrayList<Lazy<*>>()

    fun addProperty(prop: Lazy<*>) = properties.add(prop)

    fun inject() = properties.forEach { it.value }

}
