package com.github.salomonbrys.kodein

import java.lang.reflect.Type
import java.util.HashMap

/**
 * Kodein IoC Container
 */
public class Kodein private constructor(
        private val _map: Map<Kodein.Key, (Kodein) -> Any>,
        private val _node: Kodein.Node? = null
) {

    private class Node(private val _key: Key, private val _parent: Node?) {
        fun check(search: Key) {
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

    /**
     * A Key is a Pair(Type, Tag) that defines a binding
     */
    public data class Key(
        val type: Type,
        val tag: Any? = null
    )

    /**
     * Allows for the building of a Kodein object by defining bindings
     */
    public class Builder(init: Builder.() -> Unit) {

        internal val map: MutableMap<Key, (Kodein) -> Any>

        init {
            map = HashMap()
            this.init()
        }

        /**
         * Binds a type. Second part of the DSL `bind<type>() with ...`
         */
        public open inner class TypeBinder<T : Any> internal constructor(val key: Key) {
            public fun <R : T> with(provider: (Kodein) -> R): Unit { map[key] = provider }
        }

        /**
         * Binds a constant. Second part of the DSL `constant(tag) with ...`
         */
        public inner class ConstantBinder internal constructor(val tag: Any) {
            public fun with(value: Any): Unit { map[Key(value.javaClass, tag)] = { value } }
        }

        /**
         * Binds an untagged type. Must be followed by [with].
         */
        public fun <T : Any> bind(type: Type, tag: Any? = null): TypeBinder<T> = TypeBinder(Key(type, tag))

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

    public fun _findUnsafeProvider(key: Key): (() -> Any)? {
        val prov = _map[key] ?: return null
        _node?.check(key)
        return { prov(Kodein(_map, Node(key, _node))) }
    }

    public inline fun <reified T : Any> _providerOrNull(key: Key): (() -> T)? {
        val prov = _findUnsafeProvider(key) ?: return null
        return {
            val instance = prov()
            if (instance !is T)
                throw IllegalStateException("Binding found for $key is ${instance.javaClass}, not ${typeToken<T>()}")
            instance
        }
    }

    public inline fun <reified T : Any> _provider(key: Key): (() -> T) {
        return _providerOrNull<T>(key) ?: throw IllegalStateException("No binding found for $key")
    }

    /**
     * Gets a provider for the given type and tag.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     * Throws an IllegalStateException if the provider could not be found.
     */
    public inline fun <reified T : Any> provider(tag: Any? = null): (() -> T) = _provider(Key(typeToken<T>(), tag))

    /**
     * Gets a provider for the given type and tag, or null if none is found.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     */
    public inline fun <reified T : Any> providerOrNull(tag: Any? = null): (() -> T)? = _providerOrNull(Key(typeToken<T>(), tag))

    /**
     * Gets an instance for the given type and tag.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     * Throws an IllegalStateException if the instance could not be found.
     */
    public inline fun <reified T : Any> instance(tag: Any? = null): T = _provider<T>(Key(typeToken<T>(), tag)).invoke()

    /**
     * Gets an instance for the given type and tag, or null if none is found.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     */
    public inline fun <reified T : Any> instanceOrNull(tag: Any? = null): T? = _providerOrNull<T>(Key(typeToken<T>(), tag))?.invoke()

    /**
     * Same as instance(tag)
     */
    public inline fun <reified T : Any> invoke(tag: Any? = null): T = instance(tag)
}
