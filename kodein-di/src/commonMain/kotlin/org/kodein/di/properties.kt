package org.kodein.di

import org.kodein.type.TypeToken
import kotlin.reflect.KProperty

const val DEPRECATED_KODEIN_7X = "!!! THIS HAS BEEN REMOVED FROM 7.0 !!! As soon as you are using _Kodein-DI 7.x_, the old API named _Kodein_ API is broken. we highly recommend that you take some time to move from it to the new API with _DI_ named objects."

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DITrigger"), DeprecationLevel.ERROR)
typealias KodeinTrigger = DITrigger

/**
 * A trigger is used to force retrieval at a given time rather than at first property access.
 *
 * 1. Set a trigger to [DIAware.diTrigger].
 * 2. When you want retrieval to happen, call [trigger].
 */
@Suppress("unused")
class DITrigger {

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

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIProperty<V>"), DeprecationLevel.ERROR)
typealias KodeinProperty<V> = DIProperty<V>
/**
 * A property delegate provider for DI retrieval.
 * Provides a `Lazy` value that, when accessed, retrieve the value from DI.
 *
 * In essence, the DI object is accessed only upon retrieving.
 */
class DIProperty<out V>(internal val trigger: DITrigger?, val originalContext: DIContext<*>, private val get: (DIContext<*>, String) -> V) : LazyDelegate<V> {

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> = lazy {
        @Suppress("UNCHECKED_CAST")
        val context = if (receiver != null && originalContext === AnyDIContext) DIContext(TTOf(receiver) as TypeToken<in Any>, receiver) else originalContext
        get(context, prop.name) } .also { trigger?.properties?.add(it)
    }

}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIPropertyMap<I,O>"), DeprecationLevel.ERROR)
typealias KodeinPropertyMap<I,O> = DIPropertyMap<I,O>

class DIPropertyMap<in I, out O>(private val base: DIProperty<I>, private val map: (I) -> O) : LazyDelegate<O> {

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<O> = lazy { map(base.provideDelegate(receiver, prop).value) }.also { base.trigger?.properties?.add(it) }

}