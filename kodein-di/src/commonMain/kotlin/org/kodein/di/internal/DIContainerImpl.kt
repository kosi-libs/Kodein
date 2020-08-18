package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.di.bindings.*

internal class DIContainerImpl private constructor(
        override val tree: DITree,
        private val node: Node?,
        private val fullDescriptionOnError: Boolean
) : DIContainer {

    @Volatile var initCallbacks: (() -> Unit)? = null
        private set

    /**
     * "Main" constructor that uses the bindings map configured by a [DIContainer.Builder].
     */
    internal constructor(builder: DIContainerBuilderImpl, externalSources: List<ExternalSource>, fullDescriptionOnError: Boolean, runCallbacks: Boolean) : this(DITreeImpl(builder.bindingsMap, externalSources, builder.translators), null, fullDescriptionOnError) {
        val init: () -> Unit = {
            val direct = DirectDIImpl(this, DIContext.Any)
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
     * Each factory, in their Binding@getInstance methods receives a DI instance to enable transient dependency.
     * However, it is not the same kodein instance as the one used to get the main dependency.
     * Each time a transient dependency is needed, a new DI instance is constructed, with a new Node that has
     * the current Node as it's parent.
     * This allows, at each step, to walk up the node tree and check if the requested key has not yet been requested.
     * If the same key exists twice in the tree, it means that it has, and that there's a dependency recursion.
     *
     * @property key The key of this node, meaning that this key has been looked for once.
     * @property parent The parent node, meaning the parent lookup that needed this key.
     */
    private class Node(private val key: DI.Key<*, *, *>, private val overrideLevel: Int, private val parent: Node?, private val fullDescriptionOnError: Boolean) {

        /**
         * Check that given key does **not** exist in the node tree or throws an exception if it does.
         *
         * @throws DI.DependencyLoopException if the key exists in the dependency tree.
         */
        internal fun check(searchedKey: DI.Key<*, *, *>, searchedOverrideLevel: Int) {
            if (!recursiveCheck(this, searchedKey, searchedOverrideLevel)) {
                val list = recursiveLoop(this, searchedKey, searchedOverrideLevel, emptyList()) + displayString(searchedKey, overrideLevel)
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
                throw DI.DependencyLoopException("Dependency recursion:\n$sb")
            }
        }

        private fun displayString(key: DI.Key<*, *, *>, overrideLevel: Int): String {
            val descProp = if (fullDescriptionOnError) key::bindFullDescription else key::bindDescription
            return if (overrideLevel != 0) "overridden ${descProp.get()}" else descProp.get()
        }

        /**
         * @return The current transitive dependency tree as a list of string.
         */
        private tailrec fun recursiveLoop(node: Node, firstKey: DI.Key<*, *, *>, firstOverrideLevel: Int, tail: List<String>): List<String> {
            return if (node.parent == null || (firstKey == node.key && firstOverrideLevel == node.overrideLevel))
                listOf(displayString(node.key, node.overrideLevel)) + tail
            else
                return recursiveLoop(node.parent, firstKey, firstOverrideLevel, listOf(displayString(node.key, node.overrideLevel)) + tail)
        }

        /**
         * Recursive function that walks up the node tree to check if a specific key can be found.
         *
         * @return whether the given key exists in the tree.
         */
        private tailrec fun recursiveCheck(node: Node, searchedKey: DI.Key<*, *, *>, searchedOverrideLevel: Int): Boolean {
            return if (node.key == searchedKey && node.overrideLevel == searchedOverrideLevel)
                false
            else if (node.parent == null)
                true
            else
                recursiveCheck(node.parent, searchedKey, searchedOverrideLevel)
        }

    }

    private fun <C : Any, A, T: Any> bindingDI(key: DI.Key<C, A, T>, context: DIContext<C>, tree: DITree, overrideLevel: Int) : BindingDI<C> {
        val container = DIContainerImpl(tree, Node(key, overrideLevel, node, fullDescriptionOnError), fullDescriptionOnError)
        return BindingDIImpl(DirectDIImpl(container, context), key, context, overrideLevel)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any, A, T: Any> factoryOrNull(key: DI.Key<C, A, T>, context: DIContext<C>, overrideLevel: Int): ((A) -> T)? {
        tree.find(key, 0).let {
            if (it.size == 1) {
                val (foundKey, definition, translator) = it[0]
                node?.check(key, 0)
                val kContext = translator?.toKContext(context.value(foundKey.contextType) as C, context.reference.maker) ?: context as DIContext<Any>
                key as DI.Key<Any, A, T>
                val bindingDI = bindingDI(foundKey, kContext, definition.tree, overrideLevel)
                val bindingFactory = definition.binding.getFactory(bindingDI, key)
                return { bindingFactory(it) }
            }
        }

        val bindingDI = bindingDI(key, context, tree, overrideLevel)
        tree.externalSources.forEach { source ->
            source.getFactory(bindingDI, key)?.let {
                node?.check(key, 0)
                @Suppress("UNCHECKED_CAST")
                return it as (A) -> T
            }
        }

        return null
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any, A, T: Any> factory(key: DI.Key<C, A, T>, context: DIContext<C>, overrideLevel: Int): (A) -> T {
        val result = tree.find(key, overrideLevel)

        if (result.size == 1) {
            val (foundKey, definition, translator) = result[0]
            node?.check(key, overrideLevel)
            val kContext = translator?.toKContext(context.value(foundKey.contextType) as C, context.reference.maker) ?: context as DIContext<Any>
            key as DI.Key<Any, A, T>
            val bindingDI = bindingDI(foundKey, kContext, definition.tree, overrideLevel)
            val bindingFactory = definition.binding.getFactory(bindingDI, key)
            return { bindingFactory(it) }
        }

        val bindingDI = bindingDI(key, context, tree, overrideLevel)
        tree.externalSources.forEach { source ->
            source.getFactory(bindingDI, key)?.let {
                node?.check(key, overrideLevel)
                @Suppress("UNCHECKED_CAST")
                return it as (A) -> T
            }
        }

        val withOverrides = overrideLevel != 0

        val descProp = if (fullDescriptionOnError) key::fullDescription else key::description
        val descFun: BindingsMap.(Boolean) -> String = if (fullDescriptionOnError) ({ fullDescription(it) }) else ({ description(it) })

        if (result.isEmpty()) {
            val description = buildString {
                append("No binding found for ${descProp.get()}\n")
                val forType = tree.find(SearchSpecs(type = key.type))
                if (forType.isNotEmpty()) {
                    append("Available bindings for this type:\n${forType.associate { it.first to it.second }.descFun(withOverrides)}")
                }
                append("Registered in this DI container:\n${tree.bindings.descFun(withOverrides)}")
            }

            throw DI.NotFoundException(key, description)
        }

        val potentials: BindingsMap = result.associate {
            it.first to tree[it.first]!!.second
        }
        val others: BindingsMap = tree.bindings.filter { (key, _) -> key !in potentials.keys } // Map.minus does not yet exist in Konan
        throw DI.NotFoundException(key, "${potentials.size} bindings found that match $key:\n${potentials.descFun(withOverrides)}Other bindings registered in DI:\n${others.descFun(withOverrides)}")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any, A, T: Any> allFactories(key: DI.Key<C, A, T>, context: DIContext<C>, overrideLevel: Int): List<(A) -> T> {
        val result = tree.find(key, overrideLevel, all = true)

        return result.map { (foundKey, definition, translator) ->
            node?.check(key, overrideLevel)
            val kContext = translator?.toKContext(context.value(foundKey.contextType) as C, context.reference.maker) ?: context as DIContext<Any>
            key as DI.Key<Any, A, T>
            val bindingDI = bindingDI(foundKey, kContext, definition.tree, overrideLevel)
            val bindingFactory = definition.binding.getFactory(bindingDI, key)
            ({ arg: A -> bindingFactory(arg) })
        }
    }

}
