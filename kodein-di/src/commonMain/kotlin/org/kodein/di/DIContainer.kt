package org.kodein.di

import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.bindings.DIBinding

/**
 * The Container is the entry point for retrieval without DI's inline & reified shenanigans.
 *
 * In DI, every binding is stored as a factory.
 * Providers are special classes of factories that take Unit as parameter.
 */
public interface DIContainer {

    /**
     * The tree that contains all bindings.
     */
    public val tree: DITree

    /**
     * Retrieve a factory for the given key.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the binding.
     * @param overrideLevel 0 if looking for a regular binding, 1 or more if looking for a binding that has been overridden.
     * @return The found factory.
     * @throws DI.NotFoundException If no factory was found.
     * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    public fun <C : Any, A, T: Any> factory(key: DI.Key<C, A, T>, context: C, overrideLevel: Int = 0): (A) -> T

    /**
     * Retrieve a factory for the given key, or null if none is found.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the binding.
     * @param overrideLevel 0 if looking for the regular binding, 1 or more if looking for a binding that has been overridden.
     * @return The found factory, or null if no factory was found.
     * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    public fun <C : Any, A, T: Any> factoryOrNull(key: DI.Key<C, A, T>, context: C, overrideLevel: Int = 0): ((A) -> T)?

    /**
     * Retrieve all factories that match the given key.
     *
     * @param C The key context type.
     * @param A The key argument type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the bindings.
     * @param overrideLevel 0 if looking for regular bindings, 1 or more if looking for bindings that have been overridden.
     * @return A list of matching factories.
     * @throws DI.NotFoundException If no factory was found.
     * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    public fun <C : Any, A, T: Any> allFactories(key: DI.Key<C, A, T>, context: C, overrideLevel: Int = 0): List<(A) -> T>

    /**
     * Retrieve a provider for the given key.
     *
     * @param C The key context type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the binding.
     * @param overrideLevel 0 if looking for the regular binding, 1 or more if looking for a binding that has been overridden.
     * @return The found provider.
     * @throws DI.NotFoundException If no provider was found.
     * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    public fun <C : Any, T: Any> provider(key: DI.Key<C, Unit, T>, context: C, overrideLevel: Int = 0): () -> T =
            factory(key, context).toProvider { }

    /**
     * Retrieve a provider for the given key, or null if none is found.
     *
     * @param C The key context type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the binding.
     * @param overrideLevel 0 if looking for the regular binding, 1 or more if looking for a binding that has been overridden.
     * @return The found provider, or null if no provider was found.
     * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    public fun <C : Any, T: Any> providerOrNull(key: DI.Key<C, Unit, T>, context: C, overrideLevel: Int = 0): (() -> T)? =
            factoryOrNull(key, context)?.toProvider { }

    /**
     * Retrieve all providers that match the given key.
     *
     * @param C The key context type.
     * @param T The key return type.
     * @param key The key to look for.
     * @param context The context to pass to the bindings.
     * @param overrideLevel 0 if looking for regular bindings, 1 or more if looking for bindings that have been overridden.
     * @return A list of matching providers.
     * @throws DI.NotFoundException If no factory was found.
     * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    public fun <C : Any, T: Any> allProviders(key: DI.Key<C, Unit, T>, context: C, overrideLevel: Int = 0): List<() -> T> =
            allFactories(key, context).map { it.toProvider { } }

    /**
     * This is where you configure the bindings.
     *
     * @param allowOverride Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.
     * @param silentOverride Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.
     * @param bindingsMap The map that contains the bindings. Can be set at construction to construct a sub-builder (with different override permissions).
     */
    public interface Builder {

        /**
         * Binds the given key to the given binding.
         *
         * @param key The key to bind.
         * @param binding The binding to bind.
         * @param overrides `true` if it must override, `false` if it must not, `null` if it can but is not required to.
         * @throws DI.OverridingException If this bindings overrides an existing binding and is not allowed to.
         */
        public fun <C : Any, A, T: Any> bind(key: DI.Key<C, A, T>, binding: DIBinding<in C, in A, out T>, fromModule: String? = null, overrides: Boolean? = null)

        /**
         * Imports all bindings defined in the given [DIContainer] into this builder.
         *
         * Note that this preserves scopes, meaning that a singleton-bound in the container argument will continue to exist only once.
         * Both containers will share the same instance.
         *
         * @param container The container object to import.
         * @param allowOverride Whether this module is allowed to override existing bindings.
         *                      If it is not, overrides (even explicit) will throw an [DI.OverridingException].
         * @throws DI.OverridingException If this DI overrides an existing binding and is not allowed to
         *                                    OR [allowOverride] is true while YOU don't have the permission to override.
         */
        public fun extend(container: DIContainer, allowOverride: Boolean = false, copy: Set<DI.Key<*, *, *>> = emptySet())

        /**
         * Creates a sub builder that will register its bindings to the same map.
         *
         * @param allowOverride Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.
         * @param silentOverride Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.
         */
        public fun subBuilder(allowOverride: Boolean = false, silentOverride: Boolean = false): Builder

        /**
         * Adds a callback that will be called once the DI object has been initialized.
         *
         * @param cb A callback.
         */
        public fun onReady(cb: DirectDI.() -> Unit)

        public fun registerContextTranslator(translator: ContextTranslator<*, *>)
    }

}
