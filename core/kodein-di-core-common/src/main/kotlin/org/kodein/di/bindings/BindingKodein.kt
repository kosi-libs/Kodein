package org.kodein.di.bindings

import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.toProvider


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
interface WithContext<out C> {
    /**
     * The context that was given at retrieval.
     */
    val context: C
}

/**
 * Indicates that the receiver of a retrieval is accessible.
 */
interface WithReceiver {
    /**
     * The receiver that will get the binding's factory value.
     */
    val receiver: Any?
}

/**
 * Direct Kodein interface to be passed to factory methods that hold references.
 *
 * It is augmented to allow such methods to access the context of the retrieval, as well as a factory from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
@Kodein.KodeinDsl
interface SimpleBindingKodein<out C> : DKodein, WithContext<C> {

    /**
     * Gets a factory from the overridden binding.
     *
     * @return A factory yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun overriddenFactory(): (Any?) -> Any

    /**
     * Gets a factory from the overridden binding, if this binding overrides an existing binding.
     *
     * @return A factory yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
     */
    fun overriddenFactoryOrNull(): ((Any?) -> Any)?
}

/**
 * Direct Kodein interface to be passed to factory methods that do **not** hold references (i.e. that recreate a new instance every time).
 *
 * It is augmented to allow such methods to access the context and receiver of the retrieval, as well as a factory from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
interface BindingKodein<out C> : SimpleBindingKodein<C>, WithReceiver

/**
 * Direct Kodein interface to be passed to provider methods that hold references.
 *
 * It is augmented to allow such methods to access the context of the retrieval, as well as a provider or instance from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
@Kodein.KodeinDsl
interface NoArgSimpleBindingKodein<out C> : DKodein, WithContext<C> {

    /**
     * Gets a provider from the overridden binding.
     *
     * @return A provider yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun overriddenProvider(): () -> Any

    /**
     * Gets a provider from the overridden binding, if this binding overrides an existing binding.
     *
     * @return A provider yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
     */
    fun overriddenProviderOrNull(): (() -> Any)?

    /**
     * Gets an instance from the overridden binding.
     *
     * @return An instance yielded by the overridden binding.
     * @throws Kodein.NotFoundException if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstance(): Any /*= overriddenProvider().invoke()*/

    /**
     * Gets an instance from the overridden binding, if this binding overrides an existing binding.
     *
     * @return An instance yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstanceOrNull(): Any? /*= overriddenProviderOrNull()?.invoke()*/
}

/**
 * Direct Kodein interface to be passed to provider methods that do **not** hold references (i.e. that recreate a new instance every time).
 *
 * It is augmented to allow such methods to access the context and receiver of the retrieval, as well as a provider or instance from the binding it is overriding (if it is overriding).
 *
 * @param C The type of the context
 */
interface NoArgBindingKodein<out C> : NoArgSimpleBindingKodein<C>, WithReceiver

internal class NoArgBindingKodeinWrap<out C>(private val _kodein: BindingKodein<C>) : NoArgBindingKodein<C>, DKodein by _kodein, WithContext<C> by _kodein, WithReceiver by _kodein {
    override fun overriddenProvider() = _kodein.overriddenFactory().toProvider { Unit }
    override fun overriddenProviderOrNull() = _kodein.overriddenFactoryOrNull()?.toProvider { Unit }
    override fun overriddenInstance() = overriddenProvider().invoke()
    override fun overriddenInstanceOrNull() = overriddenProviderOrNull()?.invoke()
}
