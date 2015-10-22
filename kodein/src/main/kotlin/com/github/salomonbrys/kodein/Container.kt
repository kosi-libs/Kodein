package com.github.salomonbrys.kodein

import java.util.*

/**
 * Container class where the bindings and their factories are stored.
 * This is an internal class, it is public and contains public methods to allow inlining from the Kodein class methods.
 * However, this class should not be considered public and should only be used by Kodein's internals
 *
 * In kodein, every binding is stored as a factory (that's why a scope is a function creating a factory).
 * Providers are special classes of factories that take Unit as parameter.
 */
public class Container private constructor(
        private val _map: Map<Kodein.Key, Factory<*, Any>>,
        private val _node: Container.Node? = null
) {

    /**
     * Class used to check for recursive dependencies
     *
     * Each factory, in their Factory@getInstance methods receive a Kodein instance to enable transient dependency.
     * However, it is not the same kodein instance as the one used to get the main dependency.
     * Each time a transient dependency is needed, a new Kodein instance is constructed, with a new Node that has
     * the current Node as it's parent.
     * This allows, at each step, to walk up the node tree and check if the requested key has not yet been requested.
     * If the same key exists twice in the tree, it means that it has, and that there's a dependency recursion.
     */
    private class Node(private val _key: Kodein.Key, private val _parent: Node?) {
        internal fun check(search: Kodein.Key) {
            if (!_check(search))
                throw Kodein.DependencyLoopException("Dependency recursion:\n" + tree(search) + "\n       ╚═> ${search.bind}")
        }

        private fun _check(search: Kodein.Key): Boolean {
            return if (_key == search) false else (_parent?._check(search) ?: true)
        }

        private fun tree(first: Kodein.Key): String {
            if (first == _key)
                return "       ╔═> ${_key.bind}"
            else
                return "${_parent?.tree(first)}\n       ╠─> ${_key.bind}"
        }
    }

    /**
     * Allows for the building of a Kodein object by defining bindings
     */
    public class Builder() {

        internal val _map: MutableMap<Kodein.Key, Factory<*, Any>> = HashMap()

        public fun bind(key: Kodein.Key, factory: Factory<*, Any>) { _map[key] = factory }
    }

    public constructor(builder: Container.Builder) : this(builder._map)

    /**
     * This is for debug. It allows to print all binded keys.
     */
    public val registeredBindings: Map<Kodein.Bind, String> get() = _map.mapKeys { it.key.bind } .mapValues { it.value.scopeName() }

    public val bindingsDescription: String get() = registeredBindings.map { "        ${it.key.toString()} with ${it.value}" }.joinToString("\n")

    public fun notFoundException(reason: String): Kodein.NotFoundException
            = Kodein.NotFoundException("$reason\nRegistered in Kodein:\n" + bindingsDescription)

    /**
     * All Kodein getter methods, whether it's instance(), provider() or factory() eventually ends up calling this
     * function.
     */
    @kotlin.Suppress("UNCHECKED_CAST")
    public fun <A, T : Any> factoryOrNull(key: Kodein.Key): ((A) -> T)? {
        val factory = _map[key] ?: return null
        _node?.check(key)
        return { arg -> (factory as Factory<A, T>).getInstance(Kodein(Container(_map, Node(key, _node))), arg) }
    }

    public fun <A, T : Any> nonNullFactory(key: Kodein.Key): ((A) -> T)
            = factoryOrNull<A, T>(key) ?: throw notFoundException("No factory found for $key")

    public fun <T : Any> providerOrNull(bind: Kodein.Bind): (() -> T)? {
        val factory = factoryOrNull<Unit, T>(Kodein.Key(bind, Unit.javaClass)) ?: return null
        return { factory(Unit) }
    }

    public fun <T : Any> nonNullProvider(bind: Kodein.Bind): (() -> T)
            = providerOrNull<T>(bind) ?: throw notFoundException("No provider found for $bind")
}
