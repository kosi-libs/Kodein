package org.kodein.bindings

import org.kodein.*


// /!\
// These classes should be generic but cannot be because the Kotlin's type inference engine cannot infer a function's receiver type from it's return type.
// Ie, this class can be generic if/when the following code becomes legal in Kotlin:
//
// class TestContext<in A, out T: Any>
// inline fun <reified A, reified T: Any> testFactory(f: TestContext<A, T>.(A) -> T): T = ...
// fun test() { /* bind<String>() with */ testFactory { name: String -> "hello, $name!" } }

interface WithContext<out C> {
    val context: C
}

interface WithReceiver {
    val receiver: Any?
}

/**
 * Kodein interface to be passed to factory scope methods.
 *
 * It is augmented to allow such methods to access a factory or instance from the binding it is overriding (if it is overriding).
 *
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

interface BindingKodein<out C> : SimpleBindingKodein<C>, WithReceiver

@Suppress("UNCHECKED_CAST")
class BindingKodeinContextWrap<out C>(val base: BindingKodein<*>, override val context: C) : BindingKodein<C> by (base as BindingKodein<C>) {
}

/**
 * Kodein interface to be passed to provider or instance scope methods.
 *
 * It is augmented to allow such methods to access a provider or instance from the binding it is overriding (if it is overriding).
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

interface NoArgBindingKodein<out C> : NoArgSimpleBindingKodein<C>, WithReceiver

@PublishedApi
internal class NoArgBindingKodeinWrap<out C>(private val _kodein: BindingKodein<C>) : NoArgBindingKodein<C>, DKodein by _kodein, WithContext<C> by _kodein, WithReceiver by _kodein {
    override fun overriddenProvider() = _kodein.overriddenFactory().toProvider { Unit }
    override fun overriddenProviderOrNull() = _kodein.overriddenFactoryOrNull()?.toProvider { Unit }
    override fun overriddenInstance() = overriddenProvider().invoke()
    override fun overriddenInstanceOrNull() = overriddenProviderOrNull()?.invoke()
}

//interface NoArgScopedBindingKodein<out C> : NoArgSimpleBindingKodein<C>, WithReceiver
//
//interface NoArgFullBindingKodein<out C> : NoArgBindingKodein, NoArgScopedBindingKodein<C>
//
//@PublishedApi
//internal class NoArgFullBindingKodeinWrap<out C>(private val _kodein: FullBindingKodein<C>) : NoArgFullBindingKodein<C>, DKodein by _kodein {
//    override val receiver get() = _kodein.receiver
//    override val scopeContext get() = _kodein.context
//    override fun overriddenProvider() = _kodein.overriddenFactory().toProvider { Unit }
//    override fun overriddenProviderOrNull() = _kodein.overriddenFactoryOrNull()?.toProvider { Unit }
//    override fun overriddenInstance() = overriddenProvider().invoke()
//    override fun overriddenInstanceOrNull() = overriddenProviderOrNull()?.invoke()
//
//
////    override val receiver get() = _kodein.receiver
////    override val kodein get() = _kodein.kodein
////    override val context get() = _kodein.context
////    override fun on(context: KodeinContext<*>, receiver: Any?) = _kodein.on(context, receiver)
////    override fun <A, T : Any> Factory(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?) = _kodein.Factory(argType, type, tag)
////    override fun <A, T : Any> FactoryOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?) = _kodein.FactoryOrNull(argType, type, tag)
////    override fun <T : Any> Provider(type: TypeToken<T>, tag: Any?) = _kodein.Provider(type, tag)
////    override fun <A, T : Any> Provider(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A) = _kodein.Provider(argType, type, tag, arg)
////    override fun <T : Any> ProviderOrNull(type: TypeToken<T>, tag: Any?) = _kodein.ProviderOrNull(type, tag)
////    override fun <A, T : Any> ProviderOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: () -> A) = _kodein.ProviderOrNull(argType, type, tag, arg)
////    override fun <T : Any> Instance(type: TypeToken<T>, tag: Any?) = _kodein.Instance(type, tag)
////    override fun <A, T : Any> Instance(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A) = _kodein.Instance(argType, type, tag, arg)
////    override fun <T : Any> InstanceOrNull(type: TypeToken<T>, tag: Any?) = _kodein.InstanceOrNull(type, tag)
////    override fun <A, T : Any> InstanceOrNull(argType: TypeToken<in A>, type: TypeToken<T>, tag: Any?, arg: A) = _kodein.InstanceOrNull(argType, type, tag, arg)
//}
