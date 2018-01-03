package org.kodein

import kotlin.reflect.KProperty

@Suppress("unused")
class KodeinInjector {

    val properties = ArrayList<Lazy<*>>()

    fun addProperty(prop: Lazy<*>) = properties.add(prop)

    fun inject() = properties.forEach { it.value }

}

class KodeinProperty<out V>(internal val injector: KodeinInjector?, @PublishedApi internal val get: (Any?) -> V) {

    operator fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> = lazy { get(receiver) } .also { injector?.addProperty(it) }

}
