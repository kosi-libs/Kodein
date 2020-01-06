package org.kodein.di

import kotlin.reflect.KProperty

const val DEPRECATE_7X = "!!! THIS WILL BE REMOVE FROM 7.0 !!! As soon as you will move on _Kodein-DI 7.x_, we highly recommend that you take some time to move from the old API named _Kodein_ to the new API with _DI_ named objects."

/**
 * A trigger is used to force retrieval at a given time rather than at first property access.
 *
 * 1. Set a trigger to [KodeinAware.kodeinTrigger].
 * 2. When you want retrieval to happen, call [trigger].
 */
@Suppress("unused")
@Deprecated(DEPRECATE_7X)
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
@Deprecated(DEPRECATE_7X)
class KodeinProperty<out V>(internal val trigger: KodeinTrigger?, val originalContext: KodeinContext<*>, private val get: (KodeinContext<*>, String) -> V) : LazyDelegate<V> {

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> = lazy {
        @Suppress("UNCHECKED_CAST")
        val context = if (receiver != null && originalContext === AnyKodeinContext) KodeinContext(TTOf(receiver) as TypeToken<in Any>, receiver) else originalContext
        get(context, prop.name) } .also { trigger?.properties?.add(it)
    }

}

@Deprecated(DEPRECATE_7X)
class KodeinPropertyMap<in I, out O>(private val base: KodeinProperty<I>, private val map: (I) -> O) : LazyDelegate<O> {

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<O> = lazy { map(base.provideDelegate(receiver, prop).value) }.also { base.trigger?.properties?.add(it) }

}