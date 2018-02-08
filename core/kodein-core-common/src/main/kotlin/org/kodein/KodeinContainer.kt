package org.kodein

import org.kodein.bindings.KodeinBinding
import org.kodein.internal.newLinkedList

/**
 * Container class where the bindings and their factories are stored.
 *
 * In kodein, every binding is stored as a factory (that's why a scope is a function creating a factory).
 * Providers are special classes of factories that take Unit as parameter.
 */
interface KodeinContainer {

    val initCallbacks: (() -> Unit)?

    val tree: KodeinTree

    /**
     * Retrieve a factory for the given key, or null if none is found.
     *
     * @param key The key to look for.
     * @return The found factory, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun <C, A, T: Any> factoryOrNull(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int = 0): ((A) -> T)?

    /**
     * Retrieve a factory for the given key.
     *
     * @param key The key to look for.
     * @return The found factory.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun <C, A, T: Any> factory(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int = 0): (A) -> T

    fun <C, A, T: Any> allFactories(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int = 0): List<(A) -> T>

    /**
     * Retrieve a provider for the given bind, or null if none is found.
     *
     * @param bind The bind (type and tag) to look for.
     * @return The found provider, or null if no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun <C, T: Any> providerOrNull(key: Kodein.Key<C, Unit, T>, context: C, receiver: Any?, overrideLevel: Int = 0): (() -> T)? =
            factoryOrNull(key, context, receiver)?.toProvider { Unit }

    /**
     * Retrieve a provider for the given bind.
     *
     * @param bind The bind (type and tag) to look for.
     * @return The found provider.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun <C, T: Any> provider(key: Kodein.Key<C, Unit, T>, context: C, receiver: Any?, overrideLevel: Int = 0): () -> T =
            factory(key, context, receiver).toProvider { Unit }

    fun <C, T: Any> allProviders(key: Kodein.Key<C, Unit, T>, context: C, receiver: Any?, overrideLevel: Int = 0): List<() -> T> =
            allFactories(key, context, receiver).map { it.toProvider { Unit } }

    /**
     * This is where you configure the bindings.
     *
     * @param allowOverride Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.
     * @param silentOverride Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.
     * @property map The map that contains the bindings. Can be set at construction to construct a sub-builder (with different override permissions).
     */
    open class Builder internal constructor(
            allowOverride: Boolean,
            silentOverride: Boolean,
            internal val bindingsMap: MutableMap<Kodein.Key<*, *, *>, MutableList<KodeinDefining<*, *, *>>>,
            internal val callbacks: MutableList<DKodein.() -> Unit>
    ) {

        /**
         * The override permission for a builder.
         */
        private enum class OverrideMode {

            /**
             * Bindings are allowed to **non-explicit** overrides.
             */
            ALLOW_SILENT {
                override val isAllowed: Boolean get() = true
                override fun must(overrides: Boolean?) = overrides
            },

            /**
             * Bindings are allowed to overrides, but only **explicit**.
             */
            ALLOW_EXPLICIT {
                override val isAllowed: Boolean get() = true
                override fun must(overrides: Boolean?) = overrides ?: false
            },

            /**
             * Bindings are forbidden to override.
             */
            FORBID {
                override val isAllowed: Boolean get() = false
                override fun must(overrides: Boolean?) = if (overrides != null && overrides) throw Kodein.OverridingException("Overriding has been forbidden") else false
            };

            /**
             * Whether or not this mode allows overrides.
             */
            abstract val isAllowed: Boolean

            /**
             * Given a binding overriding declaration (true=yes, false=no, null=maybe), returns whether or not the binding **must** override an existing binding.
             *
             * @return `true` if it must override, `false` if it must not, `null` if it can but is not required to.
             * @throws Kodein.OverridingException If it asks to override, if the binding overriding declaration is not permitted.
             */
            abstract fun must(overrides: Boolean?): Boolean?

            companion object {

                /**
                 * Get the mode according to the given permissions.
                 *
                 * @param allow The permission to override explicitly.
                 * @param silent The permission to override silently.
                 * @return The mode corresponding to the permissions.
                 */
                fun get(allow: Boolean, silent: Boolean): OverrideMode {
                    if (!allow)
                        return FORBID
                    if (silent)
                        return ALLOW_SILENT
                    return ALLOW_EXPLICIT
                }
            }
        }

        /**
         * The mode that defines the overriding permissions for this builder.
         */
        private val _overrideMode = OverrideMode.get(allowOverride, silentOverride)

        /**
         * Checks that the bindings conforms to it's overriding declaration.
         *
         * If [overrides] is null, only checks if the binding is allowed to override, else checks if it conforms.
         *
         * @param key The key that the binding must, may or must not override.
         * @param overrides `true` if it must override, `false` if it must not, `null` if it can but is not required to.
         * @throws Kodein.OverridingException If overrides is `null` or `true` but the permission to override is not granted,
         *                                    or if the binding is allowed to override, but does not conforms to it's overriding declaration.
         */
        private fun _checkOverrides(key: Kodein.Key<*, *, *>, overrides: Boolean?) {
            val mustOverride = _overrideMode.must(overrides)

            if (mustOverride != null) {
                if (mustOverride && key !in bindingsMap)
                    throw Kodein.OverridingException("Binding $key must override an existing binding.")
                if (!mustOverride && key in bindingsMap)
                    throw Kodein.OverridingException("Binding $key must not override an existing binding.")
            }
        }

        /**
         * Binds the given key to the given binding.
         *
         * @param key The key to bind.
         * @param binding The binding to bind.
         * @param overrides `true` if it must override, `false` if it must not, `null` if it can but is not required to.
         * @throws Kodein.OverridingException If this bindings overrides an existing binding and is not allowed to.
         */
        fun <C, A, T: Any> bind(key: Kodein.Key<C, A, T>, binding: KodeinBinding<in C, in A, out T>, fromModule: String? = null, overrides: Boolean? = null) {
            key.type.checkIsReified(key)
            key.argType.checkIsReified(key)
            _checkOverrides(key, overrides)

            val bindings = bindingsMap.getOrPut(key) { newLinkedList() }
            bindings.add(0, KodeinDefining(binding, fromModule))
        }

        /**
         * Checks whether the given overriding declaration is allowed.
         *
         * @param allowOverride The overriding declaration.
         * @throws Kodein.OverridingException If it is not allowed to bind.
         */
        private fun _checkMatch(allowOverride: Boolean) {
            if (!_overrideMode.isAllowed && allowOverride)
                throw Kodein.OverridingException("Overriding has been forbidden")
        }

        /**
         * Imports all bindings defined in the given [KodeinContainer] into this builder.
         *
         * Note that this preserves scopes, meaning that a singleton-bound in the container argument will continue to exist only once.
         * Both containers will share the same instance.
         *
         * @param container The container object to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *                      If it is not, overrides (even explicit) will throw an [Kodein.OverridingException].
         * @throws Kodein.OverridingException If this kodein overrides an existing binding and is not allowed to
         *                                    OR [allowOverride] is true while YOU don't have the permission to override.
         */
        fun extend(container: KodeinContainer, allowOverride: Boolean = false, copy: Set<Kodein.Key<*, *, *>> = emptySet()) {
            _checkMatch(allowOverride)

            container.tree.bindings.forEach { (key, bindings) ->
                if (!allowOverride)
                    _checkOverrides(key, null)

                val newBindings = if (key in copy) {
                    newLinkedList<KodeinDefining<*, *, *>>().also { bindings.mapTo(it) { KodeinDefining(it.binding.copier?.copy(this@Builder) ?: it.binding, it.fromModule) } }
                }
                else {
                    newLinkedList<KodeinDefining<*, *, *>>(bindings)
                }

                bindingsMap[key] = newBindings
            }
        }

        /**
         * Creates a sub builder that will register its bindings to the same map.
         *
         * @param allowOverride Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.
         * @param silentOverride Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.
         */
        fun subBuilder(allowOverride: Boolean = false, silentOverride: Boolean = false): Builder {
            _checkMatch(allowOverride)
            return Builder(allowOverride, silentOverride, bindingsMap, callbacks)
        }

        fun onReady(cb: DKodein.() -> Unit) {
            callbacks += cb
        }
    }

}
