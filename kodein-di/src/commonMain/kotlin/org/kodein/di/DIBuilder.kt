package org.kodein.di

import org.kodein.di.bindings.*
import org.kodein.type.generic


//region Straight bindings
/**
 * Binds a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
public inline fun <reified T: Any> DI.Builder.bindSingleton(tag: Any? = null, overrides: Boolean? = null, sync: Boolean = true, noinline creator: DirectDI.() -> T): Unit = Bind(tag = tag, overrides = overrides, binding = singleton(sync = sync, creator = creator))

/**
 * Binds a multiton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
public inline fun <reified A : Any, reified T: Any> DI.Builder.bindMultiton(tag: Any? = null, overrides: Boolean? = null, sync: Boolean = true, noinline creator: DirectDI.(A) -> T): Unit = Bind(tag = tag, overrides = overrides, binding = multiton(sync = sync, creator = creator))


/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T generics will be erased!
 *
 * A provider is like a [factory], but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 */
public inline fun <reified T: Any> DI.Builder.bindProvider(tag: Any? = null, overrides: Boolean? = null, noinline creator: DirectDI.() -> T): Unit = Bind(tag = tag, overrides = overrides, binding = provider(creator = creator))

/**
 * Binds a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 */
public inline fun <reified A : Any, reified T: Any> DI.Builder.bindFactory(tag: Any? = null, overrides: Boolean? = null, noinline creator: DirectDI.(A) -> T): Unit = Bind(tag = tag, overrides = overrides, binding = factory(creator = creator))

/**
 * Binds an instance provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the instance.
 * @param creator A function that must the instance of type T.
 */
public inline fun <reified T: Any> DI.Builder.bindInstance(tag: Any? = null, overrides: Boolean? = null, creator: () -> T): Unit = Bind(tag = tag, overrides = overrides, binding = instance(creator()))

/**
 * Binds a constant provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the constant.
 * @param creator A function that must the constant of type T.
 */
public inline fun <reified T: Any> DI.Builder.bindConstant(tag: Any, overrides: Boolean? = null, creator: () -> T): Unit = Bind(tag = tag, overrides = overrides, binding = instance(creator()))

/**
 * Attaches a binding to the DI container
 *
 * @param T The type of value to bind.
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must** or **must not** override an existing binding.
 */
public inline fun <reified T: Any> DI.Builder.bind(tag: Any? = null, overrides: Boolean? = null, noinline createBinding: () -> DIBinding<*, *, T>): Unit = Bind(tag, overrides, createBinding())
//endregion

//region Advanced bindings
/**
 * Starts the binding of a given type with a given tag.
 *
 * T generics will be erased!
 *
 * @param T The type to bind.
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call `DI.Builder.TypeBinder.with` on it to finish the binding syntax and register the binding.
 */
public inline fun <reified T : Any> DI.Builder.bind(tag: Any? = null, overrides: Boolean? = null): DI.Builder.TypeBinder<T> = Bind(generic<T>(), tag, overrides)

/**
 * Starts a direct binding with a given tag. A direct bind does not define the type to be bound, the type will be defined according to the bound factory.
 *
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call [DI.Builder.DirectBinder.from]) on it to finish the binding syntax and register the binding.
 */
@Deprecated("'bind() fron [BINDING]' might be replace by 'bind { [BINDING] }' (This will be remove in Kodein-DI 8.0)")
public fun DI.Builder.bind(tag: Any? = null, overrides: Boolean? = null): DI.Builder.DirectBinder = Bind(tag, overrides)

/**
 * Binds the previously given tag to the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of value to bind.
 * @param value The instance to bind.
 */
public inline infix fun <reified T : Any> DI.Builder.ConstantBinder.with(value: T): Unit = With(generic<T>(), value)
//endregion

//region SearchDSL
/**
 * Creates a return type constrained spec.
 *
 * @property tag An optional tag constraint.
 */
@Suppress("unused")
public inline fun <reified T : Any> SearchDSL.binding(tag: Any? = null): SearchDSL.Binding = SearchDSL.Binding(generic<T>(), tag)

/**
 * Creates a context constrained spec.
 */
public inline fun <reified T : Any> SearchDSL.context(): SearchDSL.Spec = Context(generic<T>())

/**
 * Creates an argument constrained spec.
 */
public inline fun <reified T : Any> SearchDSL.argument(): SearchDSL.Spec = Argument(generic<T>())
//endregion

//region Context / Scopes
/**
 * Used to define bindings with a scope: `bind<MyType>() with scoped(myScope).singleton { /*...*/ }`
 *
 * @param EC The scope's environment context type.
 * @param BC The scope's Binding context type.
 */
public inline fun <reified C : Any> DI.Builder.scoped(scope: Scope<C>): DI.BindBuilder.WithScope<C> = DI.BindBuilder.ImplWithScope(generic(), scope)

/**
 * Used to define bindings with a context: `bind<MyType>() with contexted<MyContext>().provider { /*...*/ }`
 *
 * @param C The context type.
 */
public inline fun <reified C : Any> DI.Builder.contexted(): DI.BindBuilder<C> = DI.BindBuilder.ImplWithContext(generic())


/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
public inline fun <C : Any, reified A : Any, reified T: Any> DI.BindBuilder<C>.factory(noinline creator: BindingDI<C>.(A) -> T): Factory<C, A, T> = Factory<C, A, T>(contextType, generic(), generic(), creator)

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T generics will be erased!
 *
 * A provider is like a [factory], but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A provider ready to be bound.
 */
public inline fun <C : Any, reified T: Any> DI.BindBuilder<C>.provider(noinline creator: NoArgBindingDI<C>.() -> T): Provider<C, T> = Provider(contextType, generic(), creator)

/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
public inline fun <C : Any, reified T: Any> DI.BindBuilder.WithScope<C>.singleton(ref: RefMaker? = null, sync: Boolean = true, noinline creator: NoArgBindingDI<C>.() -> T): Singleton<C, T> = Singleton(scope, contextType, explicitContext, generic(), ref, sync, creator)

/**
 * Creates a multiton: will create an instance on first request for each different argument and will subsequently always return the same instance for the same argument.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A multiton ready to be bound.
 */
public inline fun <C : Any, reified A : Any, reified T: Any> DI.BindBuilder.WithScope<C>.multiton(ref: RefMaker? = null, sync: Boolean = true, noinline creator: BindingDI<C>.(A) -> T): Multiton<C, A, T> = Multiton<C, A, T>(scope, contextType, explicitContext, generic(), generic(), ref, sync, creator)

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as DI is ready. Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
public inline fun <reified T: Any> DI.Builder.eagerSingleton(noinline creator: NoArgBindingDI<Any>.() -> T): EagerSingleton<T> = EagerSingleton(containerBuilder, generic(), creator)

/**
 * Creates an instance provider: will always return the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of the instance.
 * @param instance The object that will always be returned.
 * @return An instance provider ready to be bound.
 */
public inline fun <reified T: Any> DI.Builder.instance(instance: T): InstanceBinding<T> = InstanceBinding(generic(), instance)
//endregion

//region ContextTranslator
public inline fun <reified C : Any, reified S : Any> contextTranslator(noinline t: DirectDI.(C) -> S?): ContextTranslator<C, S> = SimpleContextTranslator(generic(), generic(), t)

public inline fun <reified C : Any, reified S : Any> DI.Builder.registerContextTranslator(noinline t: DirectDI.(C) -> S?): Unit = RegisterContextTranslator(contextTranslator(t))

public inline fun <reified S : Any> contextFinder(noinline t: DirectDI.() -> S) : ContextTranslator<Any, S> = SimpleAutoContextTranslator(generic(), t)

public inline fun <reified S : Any> DI.Builder.registerContextFinder(noinline t: DirectDI.() -> S): Unit = RegisterContextTranslator(contextFinder(t))
//endregion

