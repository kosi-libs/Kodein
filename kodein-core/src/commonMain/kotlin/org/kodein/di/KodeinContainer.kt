package org.kodein.di

import org.kodein.di.bindings.KodeinBinding
import org.kodein.di.internal.newLinkedList

/**
 * The Container is the entry point for retrieval without Kodein's inline & reified shenanigans.
 *
 * In kodein, every binding is stored as a factory.
 * Providers are special classes of factories that take Unit as parameter.
 */
interface KodeinContainer {

    /**
     * The tree that contains all bindings.
     */
    val tree: KodeinTree

    /**
     * Retrieve a factory for the given key.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the binding.
     * @param receiver The object that shall receive the value.
     * @param overrideLevel 0 if looking for a regular binding, 1 or more if looking for a binding that has been overridden.
     * @return The found factory.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun <C, A, T: Any> factory(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int = 0): (A) -> T

    /**
     * Retrieve a factory for the given key, or null if none is found.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the binding.
     * @param receiver The object that shall receive the value.
     * @param overrideLevel 0 if looking for the regular binding, 1 or more if looking for a binding that has been overridden.
     * @return The found factory, or null if no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun <C, A, T: Any> factoryOrNull(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int = 0): ((A) -> T)?

    /**
     * Retrieve all factories that match the given key.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the bindings.
     * @param receiver The object that shall receive the values.
     * @param overrideLevel 0 if looking for regular bindings, 1 or more if looking for bindings that have been overridden.
     * @return A list of matching factories.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun <C, A, T: Any> allFactories(key: Kodein.Key<C, A, T>, context: C, receiver: Any?, overrideLevel: Int = 0): List<(A) -> T>

    /**
     * Retrieve a provider for the given key.
     *
     * @param C The key context type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the binding.
     * @param receiver The object that shall receive the value.
     * @param overrideLevel 0 if looking for the regular binding, 1 or more if looking for a binding that has been overridden.
     * @return The found provider.
     * @throws Kodein.NotFoundException If no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun <C, T: Any> provider(key: Kodein.Key<C, Unit, T>, context: C, receiver: Any?, overrideLevel: Int = 0): () -> T =
            factory(key, context, receiver).toProvider { Unit }

    /**
     * Retrieve a provider for the given key, or null if none is found.
     *
     * @param C The key context type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the binding.
     * @param receiver The object that shall receive the value.
     * @param overrideLevel 0 if looking for the regular binding, 1 or more if looking for a binding that has been overridden.
     * @return The found provider, or null if no provider was found.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun <C, T: Any> providerOrNull(key: Kodein.Key<C, Unit, T>, context: C, receiver: Any?, overrideLevel: Int = 0): (() -> T)? =
            factoryOrNull(key, context, receiver)?.toProvider { Unit }

    /**
     * Retrieve all providers that match the given key.
     *
     * @param C The key context type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the bindings.
     * @param receiver The object that shall receive the values.
     * @param overrideLevel 0 if looking for regular bindings, 1 or more if looking for bindings that have been overridden.
     * @return A list of matching providers.
     * @throws Kodein.NotFoundException If no factory was found.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun <C, T: Any> allProviders(key: Kodein.Key<C, Unit, T>, context: C, receiver: Any?, overrideLevel: Int = 0): List<() -> T> =
            allFactories(key, context, receiver).map { it.toProvider { Unit } }

    /**
     * This is where you configure the bindings.
     *
     * @param allowOverride Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.
     * @param silentOverride Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.
     * @param bindingsMap The map that contains the bindings. Can be set at construction to construct a sub-builder (with different override permissions).
     */
    interface Builder {

        /**
         * Binds the given key to the given binding.
         *
         * @param key The key to bind.
         * @param binding The binding to bind.
         * @param overrides `true` if it must override, `false` if it must not, `null` if it can but is not required to.
         * @throws Kodein.OverridingException If this bindings overrides an existing binding and is not allowed to.
         */
        fun <C, A, T: Any> bind(key: Kodein.Key<C, A, T>, binding: KodeinBinding<in C, in A, out T>, fromModule: String? = null, overrides: Boolean? = null)

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
        fun extend(container: KodeinContainer, allowOverride: Boolean = false, copy: Set<Kodein.Key<*, *, *>> = emptySet())

        /**
         * Creates a sub builder that will register its bindings to the same map.
         *
         * @param allowOverride Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.
         * @param silentOverride Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.
         */
        fun subBuilder(allowOverride: Boolean = false, silentOverride: Boolean = false): Builder

        /**
         * Adds a callback that will be called once the Kodein object has been initialized.
         *
         * @param cb A callback.
         */
        fun onReady(cb: DKodein.() -> Unit)
    }

}
