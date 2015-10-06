package com.github.salomonbrys.kodein

import java.util.*

/**
 * Kodein IoC Container
 */
public class Container private constructor(
        private val _map: Map<Kodein.Key, Factory<*, Any>>,
        private val _node: Container.Node? = null
) {

    private class Node(private val _key: Kodein.Key, private val _parent: Node?) {
        internal fun check(search: Kodein.Key) {
            if (!_check(search))
                throw Kodein.DependencyLoopException(tree(_key))
        }

        private fun _check(search: Kodein.Key): Boolean {
            return if (_key == search) false else (_parent?._check(search) ?: true)
        }

        private fun tree(first: Kodein.Key): String {
            return "$_key\n        -> ${_parent?.tree(first) ?: first}"
        }
    }

    /**
     * Allows for the building of a Kodein object by defining bindings
     */
    public class Builder() {

        internal val _map: MutableMap<Kodein.Key, Factory<*, Any>> = HashMap()

        public fun bind(key: Kodein.Key, factory: Factory<*, Any>) { _map[key] = factory }
    }

    /**
     * @param init Binding block
     */
    public constructor(builder: Container.Builder) : this(builder._map)

    public val registered: Map<Kodein.Bind, String> = _map.mapKeys { it.getKey().bind } .mapValues { it.getValue().scopeName }

    public fun notFoundException(reason: String): Kodein.NotFoundException
            = Kodein.NotFoundException("$reason\nRegistered in Kodein:\n" + registered.map { "        ${it.key.toString()} with ${it.value}" } .join("\n"))

    @kotlin.Suppress("UNCHECKED_CAST")
    public fun <A, T : Any> _unsafeFindFactory(key: Kodein.Key): ((A) -> T)? {
        val factory = _map[key] ?: return null
        _node?.check(key)
        return { arg -> (factory as Factory<A, T>).getInstance(Kodein(Container(_map, Node(key, _node))), arg) }
    }

    public inline fun <A, reified T : Any> factoryOrNull(key: Kodein.Key): ((A) -> T)? {
        val prov = _unsafeFindFactory<A, T>(key) ?: return null
        return { arg ->
            val instance = prov(arg)
            if (instance !is T)
                throw notFoundException("Binding found for ${key.bind} is ${instance.javaClass}, not ${typeToken<T>()}")
            instance
        }
    }

    public inline fun <A, reified T : Any> nonNullFactory(key: Kodein.Key): ((A) -> T)
            = factoryOrNull<A, T>(key) ?: throw notFoundException("No factory found for $key")

    public inline fun <reified T : Any> providerOrNull(bind: Kodein.Bind): (() -> T)? {
        val factory = factoryOrNull<Unit, T>(Kodein.Key(bind, Unit.javaClass)) ?: return null
        return { factory(Unit) }
    }

    public inline fun <reified T : Any> nonNullProvider(bind: Kodein.Bind): (() -> T)
            = providerOrNull<T>(bind) ?: throw notFoundException("No provider found for $bind")

}
