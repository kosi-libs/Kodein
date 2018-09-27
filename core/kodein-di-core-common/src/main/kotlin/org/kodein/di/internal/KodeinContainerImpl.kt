package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.di.bindings.BindingKodein
import org.kodein.di.bindings.ExternalSource

internal class KodeinContainerImpl private constructor(
        override val tree: KodeinTree,
        private val node: Node? = null
) : KodeinContainer {

    @Volatile var initCallbacks: (() -> Unit)? = null
        private set

    /**
     * "Main" constructor that uses the bindings map configured by a [KodeinContainer.Builder].
     */
    internal constructor(builder: KodeinContainerBuilderImpl, externalSource: ExternalSource?, runCallbacks: Boolean) : this(KodeinTreeImpl(builder.bindingsMap, externalSource)) {
        val init: () -> Unit = {
            val direct = DKodeinImpl(this, AnyKodeinContext, null)
            builder.callbacks.forEach { @Suppress("UNUSED_EXPRESSION") it(direct) }
        }

        if (runCallbacks)
            init()
        else {
            val lock = Any()
            initCallbacks = {
                synchronizedIfNotNull(
                        lock = lock,
                        predicate = this::initCallbacks,
                        ifNull = {},
                        ifNotNull = {
                            initCallbacks = null
                            init()
                        }
                )
            }
        }
    }

    /**
     * Class used to check for recursive dependencies, represents a node in the dependency tree.
     *
     * Each factory, in their Binding@getInstance methods receives a Kodein instance to enable transient dependency.
     * However, it is not the same kodein instance as the one used to get the main dependency.
     * Each time a transient dependency is needed, a new Kodein instance is constructed, with a new Node that has
     * the current Node as it's parent.
     * This allows, at each step, to walk up the node tree and check if the requested key has not yet been requested.
     * If the same key exists twice in the tree, it means that it has, and that there's a dependency recursion.
     *
     * @property _key The key of this node, meaning that this key has been looked for once.
     * @property _parent The parent node, meaning the parent lookup that needed this key.
     */
    private class Node(private val _key: Kodein.Key<*, *, *>, private val _overrideLevel: Int, private val _parent: Node?) {

        /**
         * Check that given key does **not** exist in the node tree or throws an exception if it does.
         *
         * @throws Kodein.DependencyLoopException if the key exists in the dependency tree.
         */
        internal fun check(searchedKey: Kodein.Key<*, *, *>, searchedOverrideLevel: Int) {
            if (!recursiveCheck(this, searchedKey, searchedOverrideLevel)) {
                val list = recursiveLoop(this, searchedKey, searchedOverrideLevel, emptyList()) + displayString(searchedKey, _overrideLevel)
                val sb = StringBuilder()
                list.forEachIndexed { index, string ->
                    sb.append("  ")
                    when (index) {
                        0 -> sb.append("   ")
                        1 -> sb.append("  ╔╩>")
                        else -> {
                            sb.append("  ║")
                            sb.append("  ".repeat(index - 1))
                            sb.append("╚>")
                        }
                    }
                    sb.append(string)
                    sb.append("\n") // appendln does not exist in JS
                }
                sb.append("    ╚")
                sb.append("══".repeat(list.size - 1))
                sb.append("╝")
                throw Kodein.DependencyLoopException("Dependency recursion:\n$sb")
            }
        }

        private companion object {
            private fun displayString(key: Kodein.Key<*, *, *>, overrideLevel: Int) = if (overrideLevel != 0) "overridden ${key.bindDescription}" else key.bindDescription

            /**
             * Recursive function that walks up the node tree to check if a specific key can be found.
             *
             * @return whether the given key exists in the tree.
             */
            private tailrec fun recursiveCheck(node: Node, searchedKey: Kodein.Key<*, *, *>, searchedOverrideLevel: Int): Boolean {
                return if (node._key == searchedKey && node._overrideLevel == searchedOverrideLevel)
                    false
                else if (node._parent == null)
                    true
                else
                    recursiveCheck(node._parent, searchedKey, searchedOverrideLevel)
            }

            /**
             * @return The current transitive dependency tree as a list of string.
             */
            private tailrec fun recursiveLoop(node: Node, firstKey: Kodein.Key<*, *, *>, firstOverrideLevel: Int, tail: List<String>): List<String> {
                return if (node._parent == null || (firstKey == node._key && firstOverrideLevel == node._overrideLevel))
                    listOf(displayString(node._key, node._overrideLevel)) + tail
                else
                    return recursiveLoop(node._parent, firstKey, firstOverrideLevel, listOf(displayString(node._key, node._overrideLevel)) + tail)
            }
        }
    }

    private fun <C, A, T: Any> bindingKodein(key: Kodein.Key<C, A, T>, context: KodeinContext<C>, receiver: Any?, tree: KodeinTree, overrideLevel: Int) : BindingKodein<C> {
        val container = KodeinContainerImpl(tree, Node(key, overrideLevel, node))
        return BindingKodeinImpl(DKodeinImpl(container, context, receiver), key, context.value, receiver, overrideLevel)
    }

    override fun <C, A, T: Any> factoryOrNull(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int): ((A) -> T)? {
        val kContext = KodeinContext(key.contextType, context)

        tree.find(key, 0).let {
            if (it.size == 1) {
                val (_, definition) = it[0]
                node?.check(key, 0)
                val bindingKodein = bindingKodein(key, kContext, receiver, definition.tree, 0)
                return definition.binding.getFactory(bindingKodein, key)
            }
        }

        val bindingKodein = bindingKodein(key, kContext, receiver, tree, 0)
        tree.externalSource?.getFactory(bindingKodein, key)?.let {
            node?.check(key, 0)
            @Suppress("UNCHECKED_CAST")
            return it as (A) -> T
        }

        return null
    }

    override fun <C, A, T: Any> factory(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int): (A) -> T {
        val kContext = KodeinContext(key.contextType, context)

        val result = tree.find(key, overrideLevel)

        if (result.size == 1) {
            val (_, definition) = result[0]
            node?.check(key, overrideLevel)
            val bindingKodein = bindingKodein(key, kContext, receiver, definition.tree, overrideLevel)
            return definition.binding.getFactory(bindingKodein, key)
        }

        val bindingKodein = bindingKodein(key, kContext, receiver, tree, overrideLevel)
        tree.externalSource?.getFactory(bindingKodein, key)?.let {
            node?.check(key, overrideLevel)
            @Suppress("UNCHECKED_CAST")
            return it as (A) -> T
        }

        val withOverrides = overrideLevel != 0

        if (result.isEmpty()) {
            val description = buildString {
                append("No binding found for $key\n")
                val forType = tree.find(SearchSpecs(type = key.type))
                if (forType.isNotEmpty()) {
                    append("Available bindings for this type:\n${forType.toMap().description(withOverrides)}")
                }
                append("Registered in this Kodein container:\n${tree.bindings.description(withOverrides)}")
            }

            throw Kodein.NotFoundException(key, description)
        }

        val potentials: BindingsMap = result.associate { it.first to tree[it.first]!! }
        val others: BindingsMap = tree.bindings.filter { (key, _) -> key !in potentials.keys } // Map.minus does not yet exist in Konan
        throw Kodein.NotFoundException(key, "${potentials.size} bindings found that match $key:\n${potentials.description(withOverrides)}Other bindings registered in Kodein:\n${others.description(withOverrides)}")
    }

    override fun <C, A, T: Any> allFactories(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int): List<(A) -> T> {
        val kContext = KodeinContext(key.contextType, context)

        val result = tree.find(key, overrideLevel, all = true)

        return result.map { (_, definition) ->
            node?.check(key, overrideLevel)
            val bindingKodein = bindingKodein(key, kContext, receiver, definition.tree, overrideLevel)
            definition.binding.getFactory(bindingKodein, key)
        }
    }

}
