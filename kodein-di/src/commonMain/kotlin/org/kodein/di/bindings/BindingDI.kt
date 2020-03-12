package org.kodein.di.bindings

import org.kodein.di.*


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
interface WithContext<out C : Any> {
    /**
     * The context that was given at retrieval.
     */
    val context: C
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("SimpleBindingDI<C>"), DeprecationLevel.ERROR)
typealias SimpleBindingKodein<C> = SimpleBindingDI<C>
/**
 * Direct DI interface to be passed to factory methods that hold references.
 *
 * It is augmented to allow such methods to access the context of the retrieval, as well as a factory from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
@DI.DIDsl
interface SimpleBindingDI<out C : Any> : DirectDI, WithContext<C> {

    /**
     * Gets a factory from the overridden binding.
     *
     * @return A factory yielded by the overridden binding.
     * @throws DI.NotFoundException if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun overriddenFactory(): (Any?) -> Any

    /**
     * Gets a factory from the overridden binding, if this binding overrides an existing binding.
     *
     * @return A factory yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun overriddenFactoryOrNull(): ((Any?) -> Any)?
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("BindingDI<C>"), DeprecationLevel.ERROR)
typealias BindingKodein<C> = BindingDI<C>
/**
 * Direct DI interface to be passed to factory methods that do **not** hold references (i.e. that recreate a new instance every time).
 *
 * It is augmented to allow such methods to access the context of the retrieval, as well as a factory from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
interface BindingDI<out C : Any> : SimpleBindingDI<C>

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("NoArgSimpleBindingDI<C>"), DeprecationLevel.ERROR)
typealias NoArgSimpleBindingKodein<C> = NoArgSimpleBindingDI<C>
/**
 * Direct DI interface to be passed to provider methods that hold references.
 *
 * It is augmented to allow such methods to access the context of the retrieval, as well as a provider or instance from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
@DI.DIDsl
interface NoArgSimpleBindingDI<out C : Any> : DirectDI, WithContext<C> {

    /**
     * Gets a provider from the overridden binding.
     *
     * @return A provider yielded by the overridden binding.
     * @throws DI.NotFoundException if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun overriddenProvider(): () -> Any

    /**
     * Gets a provider from the overridden binding, if this binding overrides an existing binding.
     *
     * @return A provider yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun overriddenProviderOrNull(): (() -> Any)?

    /**
     * Gets an instance from the overridden binding.
     *
     * @return An instance yielded by the overridden binding.
     * @throws DI.NotFoundException if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstance(): Any /*= overriddenProvider().invoke()*/

    /**
     * Gets an instance from the overridden binding, if this binding overrides an existing binding.
     *
     * @return An instance yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstanceOrNull(): Any? /*= overriddenProviderOrNull()?.invoke()*/
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("NoArgBindingDI<C>"), DeprecationLevel.ERROR)
typealias NoArgBindingKodein<C> = NoArgBindingDI<C>
/**
 * Direct DI interface to be passed to provider methods that do **not** hold references (i.e. that recreate a new instance every time).
 *
 * It is augmented to allow such methods to access the context of the retrieval, as well as a provider or instance from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
interface NoArgBindingDI<out C : Any> : NoArgSimpleBindingDI<C>

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("NoArgBindingDIWrap<C>"), DeprecationLevel.ERROR)
internal typealias NoArgBindingKodeinWrap<C> = NoArgBindingDIWrap<C>

internal class NoArgBindingDIWrap<out C : Any>(private val _di: BindingDI<C>) : NoArgBindingDI<C>, DirectDI by _di, WithContext<C> by _di {
    override fun overriddenProvider() = _di.overriddenFactory().toProvider { Unit }
    override fun overriddenProviderOrNull() = _di.overriddenFactoryOrNull()?.toProvider { Unit }
    override fun overriddenInstance() = overriddenProvider().invoke()
    override fun overriddenInstanceOrNull() = overriddenProviderOrNull()?.invoke()
}
