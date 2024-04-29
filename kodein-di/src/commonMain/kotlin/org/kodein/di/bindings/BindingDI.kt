package org.kodein.di.bindings

import org.kodein.di.*
import org.kodein.type.TypeToken


// /!\
// These classes should be generic but cannot be because the Kotlin's type inference engine cannot infer a function's receiver type from it's return type.
// Ie, this class can be generic if/when the following code becomes legal in Kotlin:
//
// class TestContext<in A, out T: Any>
// inline fun <reified A, reified T: Any> testFactory(f: TestContext<A, T>.(A) -> T): T = ...
// fun test() { /* bind<String>() with */ testFactory { name: String -> "hello, $name!" } }

/**
 * Indicates that the context of a retrieval is accessible.
 *
 * @param C The type of the context
 */
public interface WithContext<out C : Any> {
    /**
     * The context that was given at retrieval.
     */
    public val context: C
}

/**
 * Direct DI interface to be passed to factory methods that hold references.
 *
 * It is augmented to allow such methods to access the context of the retrieval, as well as a factory from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
@DI.DIDsl
public interface BindingDI<out C : Any> : DirectDI, WithContext<C> {

    /**
     * Gets a factory from the overridden binding.
     *
     * @return A factory yielded by the overridden binding.
     * @throws DI.NotFoundException if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    public fun overriddenFactory(): (Any?) -> Any

    /**
     * Gets a factory from the overridden binding, if this binding overrides an existing binding.
     *
     * @return A factory yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    public fun overriddenFactoryOrNull(): ((Any?) -> Any)?

    public fun onErasedContext(): BindingDI<C>
}

public object ErasedContext : DIContext<ErasedContext> {
    override val type: TypeToken<in ErasedContext> get() = TypeToken.Any
    override val value: ErasedContext get() = ErasedContext
}

/**
 * Direct DI interface to be passed to provider methods that hold references.
 *
 * It is augmented to allow such methods to access the context of the retrieval, as well as a provider or instance from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
@DI.DIDsl
public interface NoArgBindingDI<out C : Any> : DirectDI, WithContext<C> {

    /**
     * Gets a provider from the overridden binding.
     *
     * @return A provider yielded by the overridden binding.
     * @throws DI.NotFoundException if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    public fun overriddenProvider(): () -> Any

    /**
     * Gets a provider from the overridden binding, if this binding overrides an existing binding.
     *
     * @return A provider yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    public fun overriddenProviderOrNull(): (() -> Any)?

    /**
     * Gets an instance from the overridden binding.
     *
     * @return An instance yielded by the overridden binding.
     * @throws DI.NotFoundException if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    public fun overriddenInstance(): Any /*= overriddenProvider().invoke()*/

    /**
     * Gets an instance from the overridden binding, if this binding overrides an existing binding.
     *
     * @return An instance yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    public fun overriddenInstanceOrNull(): Any? /*= overriddenProviderOrNull()?.invoke()*/
}

internal class NoArgBindingDIWrap<out C : Any>(private val _di: BindingDI<C>) : NoArgBindingDI<C>, DirectDI by _di, WithContext<C> by _di {
    override fun overriddenProvider() = _di.overriddenFactory().toProvider { }
    override fun overriddenProviderOrNull() = _di.overriddenFactoryOrNull()?.toProvider { }
    override fun overriddenInstance() = overriddenProvider().invoke()
    override fun overriddenInstanceOrNull() = overriddenProviderOrNull()?.invoke()
}
