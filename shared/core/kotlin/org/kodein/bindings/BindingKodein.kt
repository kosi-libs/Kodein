package org.kodein.bindings

import org.kodein.DKodein
import org.kodein.Kodein
import org.kodein.toProvider


// /!\
// These classes should be generic but cannot be because the Kotlin's type inference engine cannot infer a function's receiver type from it's return type.
// Ie, this class can be generic if/when the following code becomes legal in Kotlin:
//
// class TestContext<in A, out T: Any>
// inline fun <reified A, reified T: Any> testFactory(f: TestContext<A, T>.(A) -> T): T = ...
// fun test() { /* bind<String>() with */ testFactory { name: String -> "hello, $name!" } }

/**
 * Kodein interface to be passed to factory scope methods.
 *
 * It is augmented to allow such methods to access a factory or instance from the binding it is overriding (if it is overriding).
 *
 */
@Kodein.KodeinDsl
interface SimpleBindingKodein : DKodein {

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

interface BindingKodein : SimpleBindingKodein {
    val receiver: Any?
}

interface ScopedBindingKodein<out C> : SimpleBindingKodein {
    val context: C
}

interface FullBindingKodein<out C> : BindingKodein, ScopedBindingKodein<C>


/**
 * Kodein interface to be passed to provider or instance scope methods.
 *
 * It is augmented to allow such methods to access a provider or instance from the binding it is overriding (if it is overriding).
 */
@Kodein.KodeinDsl
interface NoArgSimpleBindingKodein : DKodein {

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
    fun overriddenInstance() = overriddenProvider().invoke()

    /**
     * Gets an instance from the overridden binding, if this binding overrides an existing binding.
     *
     * @return An instance yielded by the overridden binding, or null if this binding does not override an existing binding.
     * @throws Kodein.DependencyLoopException If the instance construction triggered a dependency loop.
     */
    fun overriddenInstanceOrNull() = overriddenProviderOrNull()?.invoke()
}

interface NoArgBindingKodein : NoArgSimpleBindingKodein {
    val receiver: Any?
}

@PublishedApi
internal class NoArgBindingKodeinWrap(private val _kodein: BindingKodein) : NoArgBindingKodein, DKodein by _kodein {
    override val receiver get() = _kodein.receiver
    override fun overriddenProvider() = _kodein.overriddenFactory().toProvider { Unit }
    override fun overriddenProviderOrNull() = _kodein.overriddenFactoryOrNull()?.toProvider { Unit }
}

interface NoArgScopedBindingKodein<out C> : NoArgSimpleBindingKodein {
    val scopeContext: C
}

interface NoArgFullBindingKodein<out C> : NoArgBindingKodein, NoArgScopedBindingKodein<C>

@PublishedApi
internal class NoArgFullBindingKodeinWrap<out C>(private val _kodein: FullBindingKodein<C>) : NoArgFullBindingKodein<C>, DKodein by _kodein {
    override val receiver get() = _kodein.receiver
    override val scopeContext get() = _kodein.context
    override fun overriddenProvider() = _kodein.overriddenFactory().toProvider { Unit }
    override fun overriddenProviderOrNull() = _kodein.overriddenFactoryOrNull()?.toProvider { Unit }
}
