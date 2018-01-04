package org.kodein

import kotlin.reflect.KProperty

@Suppress("unused")
class KodeinTrigger {

    val properties = ArrayList<Lazy<*>>()

    fun addProperty(prop: Lazy<*>) = properties.add(prop)

    fun trigger() = properties.forEach { it.value }

}

class KodeinProperty<out V>(internal val trigger: KodeinTrigger?, @PublishedApi internal val get: (Any?) -> V) {

    operator fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> = lazy { get(receiver) } .also { trigger?.addProperty(it) }

}
