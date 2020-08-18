package org.kodein.di

import org.kodein.di.bindings.Reference
import org.kodein.di.bindings.Weak
import org.kodein.type.TypeToken
import org.kodein.type.erasedOf
import kotlin.reflect.KProperty

/**
 * A trigger is used to force retrieval at a given time rather than at first property access.
 *
 * 1. Set a trigger to [DIAware.diTrigger].
 * 2. When you want retrieval to happen, call [trigger].
 */
@Suppress("unused")
public class DITrigger {

    /**
     * All properties that will be retrieved when the trigger happens.
     */
    public val properties: MutableList<Lazy<*>> = ArrayList()

    /**
     * Trigger retrieval of all properties that are registered in [properties].
     */
    public fun trigger(): Unit = properties.forEach { it.value }

}

public interface LazyDelegate<out V> {
    /** @suppress */
    public operator fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V>
}

/**
 * A property delegate provider for DI retrieval.
 * Provides a `Lazy` value that, when accessed, retrieve the value from DI.
 *
 * In essence, the DI object is accessed only upon retrieving.
 */
public class DIProperty<out V>(internal val trigger: DITrigger?, private val originalContext: DIContext<*>, private val get: (DIContext<*>, String) -> V) : LazyDelegate<V> {

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> = lazy {
        @Suppress("UNCHECKED_CAST")
        val context = if (receiver != null && originalContext === DIContext.Any) DIContext(erasedOf(receiver) as TypeToken<in Any>, receiver, Weak) else originalContext
        get(context, prop.name)
    } .also { trigger?.properties?.add(it) }

}

public class DIPropertyMap<in I, out O>(private val base: DIProperty<I>, private val map: (I) -> O) : LazyDelegate<O> {

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<O> = lazy { map(base.provideDelegate(receiver, prop).value) }.also { base.trigger?.properties?.add(it) }

}