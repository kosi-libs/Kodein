package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.*

/**
 * Kodein IoC Container
 */
public class Kodein private constructor(
        private val _map: Map<Kodein.Key, Factory<*, Any>>,
        private val _node: Kodein.Node? = null
) {

    private class Node(private val _key: Key, private val _parent: Node?) {
        internal fun check(search: Key) {
            if (!_check(search))
                throw DependencyLoopException(tree(_key))
        }

        private fun _check(search: Key): Boolean {
            return if (_key == search) false else (_parent?._check(search) ?: true)
        }

        private fun tree(first: Key): String {
            return "$_key\n        -> ${_parent?.tree(first) ?: first}"
        }
    }

    /**
     * Exception thrown when there is a dependency loop.
     */
    public class DependencyLoopException internal constructor(message: String) : RuntimeException(message)

    public data class Bind(
            val type: Type,
            val tag: Any?
    ) {
        override fun toString() = "bind<${type.typeName}>(${ if (tag != null) "\"$tag\"" else "" })"
    }

    /**
     * A Key is a Pair(Type, Tag) that defines a binding
     */
    public data class Key(
            val bind: Bind,
            val argType: Type
        )

    /**
     * Allows for the building of a Kodein object by defining bindings
     */
    public class Builder(init: Builder.() -> Unit) {

        internal val map: MutableMap<Key, Factory<*, Any>>

        init {
            map = HashMap()
            this.init()
        }

        /**
         * Binds a type. Second part of the DSL `bind<type>() with ...`
         */
        public open inner class TypeBinder<T : Any> internal constructor(val bind: Bind) {
            public fun <R : T, A> with(factory: Factory<A, R>): Unit {
                map[Key(bind, factory.argType)] = factory
            }
        }

        /**
         * Binds a constant. Second part of the DSL `constant(tag) with ...`
         */
        public inner class ConstantBinder internal constructor(val tag: Any) {
            public fun with(value: Any): Unit { map[Key(Bind(value.javaClass, tag), Unit.javaClass)] = instance(value) }
        }

        /**
         * Binds an untagged type. Must be followed by [with].
         */
        public fun <T : Any> bind(type: Type, tag: Any? = null): TypeBinder<T> = TypeBinder(Bind(type, tag))

        /**
         * Binds a tagged type. Must be followed by [with].
         */
        public inline fun <reified T : Any> bind(tag: Any? = null): TypeBinder<T> = bind(typeToken<T>(), tag)

        /**
         * Binds a constant. Must be followed by [with].
         */
        public fun constant(tag: Any): ConstantBinder = ConstantBinder(tag)
    }

    /**
     * @param init Binding block
     */
    public constructor(init: Kodein.Builder.() -> Unit) : this(Kodein.Builder(init).map)

    public val registered: Map<Bind, String> = _map.mapKeys { it.getKey().bind } .mapValues { it.getValue().scopeName }

    public inner class NotFoundException(message: String) : RuntimeException("$message\nRegistered in Kodein:\n" + registered.map { "        ${it.key.toString()} with ${it.value}" } .join("\n"));

    @Suppress("UNCHECKED_CAST")
    public fun <A, T : Any> _unsafeFindFactory(key: Key): ((A) -> T)? {
        val factory = _map[key] ?: return null
        _node?.check(key)
        return { arg -> (factory as Factory<A, T>).getInstance(Kodein(_map, Node(key, _node)), arg) }
    }

    public inline fun <A, reified T : Any> _factoryOrNull(key: Key): ((A) -> T)? {
        val prov = _unsafeFindFactory<A, T>(key) ?: return null
        return { arg ->
            val instance = prov(arg)
            if (instance !is T)
                throw NotFoundException("Binding found for ${key.bind} is ${instance.javaClass}, not ${typeToken<T>()}")
            instance
        }
    }

    public inline fun <A, reified T : Any> _nonNullFactory(key: Key): ((A) -> T)
            = _factoryOrNull<A, T>(key) ?: throw NotFoundException("No factory found for $key")

    public inline fun <reified A, reified T : Any> factory(tag: Any? = null): ((A) -> T) = _nonNullFactory<A, T>(Key(Bind(typeToken<T>(), tag), typeToken<A>()))

    public inline fun <reified A, reified T : Any> factoryOrNull(tag: Any? = null): ((A) -> T)? = _factoryOrNull<A, T>(Key(Bind(typeToken<T>(), tag), typeToken<A>()))


    public inline fun <reified T : Any> _providerOrNull(bind: Bind): (() -> T)? {
        val factory = _factoryOrNull<Unit, T>(Key(bind, Unit.javaClass)) ?: return null
        return { factory(Unit) }
    }

    public inline fun <reified T : Any> _nonNullProvider(bind: Bind): (() -> T)
            = _providerOrNull<T>(bind) ?: throw NotFoundException("No provider found for $bind")

    /**
     * Gets a provider for the given type and tag.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     * Throws an IllegalStateException if the provider could not be found.
     */
    public inline fun <reified T : Any> provider(tag: Any? = null): (() -> T) = _nonNullProvider(Bind(typeToken<T>(), tag))

    /**
     * Gets a provider for the given type and tag, or null if none is found.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     */
    public inline fun <reified T : Any> providerOrNull(tag: Any? = null): (() -> T)? = _providerOrNull(Bind(typeToken<T>(), tag))

    /**
     * Gets an instance for the given type and tag.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     * Throws an IllegalStateException if the instance could not be found.
     */
    public inline fun <reified T : Any> instance(tag: Any? = null): T = _nonNullProvider<T>(Bind(typeToken<T>(), tag)).invoke()

    /**
     * Gets an instance for the given type and tag, or null if none is found.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     */
    public inline fun <reified T : Any> instanceOrNull(tag: Any? = null): T? = _providerOrNull<T>(Bind(typeToken<T>(), tag))?.invoke()

}
