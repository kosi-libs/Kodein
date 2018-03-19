package org.kodein

import kotlin.reflect.KProperty

/**
 * A trigger is used to force retrieval at a given time rather than at first property access.
 *
 * 1. Set a trigger to [KodeinAware.kodeinTrigger].
 * 2. When you want retrieval to happen, call [trigger].
 */
@Suppress("unused")
class KodeinTrigger {

    /**
     * All properties that will be retrieved when the trigger happens.
     */
    val properties: MutableList<Lazy<*>> = ArrayList()

    /**
     * Trigger retrieval of all properties that are registered in [properties].
     */
    fun trigger() = properties.forEach { it.value }

}

/**
 * A property delegate provider for Kodein retrieval.
 * Provides a `Lazy` value that, when accessed, retrieve the value from Kodein.
 *
 * In essence, the Kodein object is accessed only upon retrieving.
 */
class KodeinProperty<out V>(internal val trigger: KodeinTrigger?, @PublishedApi internal val get: (Any?) -> V) {

    /** @suppress */
    operator fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> = lazy { get(receiver) } .also { trigger?.properties?.add(it) }

}
