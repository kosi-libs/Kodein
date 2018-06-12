package org.kodein.di

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

interface LazyDelegate<out V> {
    /** @suppress */
    operator fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V>
}

/**
 * A property delegate provider for Kodein retrieval.
 * Provides a `Lazy` value that, when accessed, retrieve the value from Kodein.
 *
 * In essence, the Kodein object is accessed only upon retrieving.
 */
class KodeinProperty<out V>(internal val trigger: KodeinTrigger?, @PublishedApi internal val get: (Any?) -> V) : LazyDelegate<V> {

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> = lazy { get(receiver) } .also { trigger?.properties?.add(it) }

}

class KodeinPropertyMap<in I, out O>(private val base: KodeinProperty<I>, private val map: (I) -> O) : LazyDelegate<O> {

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<O> = base.provideDelegate(receiver, prop).let { lazy { map(it.value) } } .also { base.trigger?.properties?.add(it) }

}
