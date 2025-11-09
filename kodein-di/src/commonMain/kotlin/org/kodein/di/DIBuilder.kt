package org.kodein.di

import kotlin.reflect.KClass
import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.bindings.DIBinding
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.SimpleAutoContextTranslator
import org.kodein.di.bindings.SimpleContextTranslator
import org.kodein.type.erased
import org.kodein.type.generic

//region Advanced bindings
/**
 * Attaches a binding to the DI container
 *
 * @param T The type of value to bind.
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must** or **must not** override an existing binding.
 */
public inline fun <reified T : Any> DI.Builder.bind(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline createBinding: () -> DIBinding<*, *, T>,
): Unit = Bind(tag, overrides, createBinding())

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
public inline fun <reified T : Any> DI.Builder.bind(
    tag: Any? = null,
    overrides: Boolean? = null,
): DI.Builder.TypeBinder<T> = Bind(generic<T>(), tag, overrides)

/**
 * Binds a class type to a dependency injection (DI) builder.
 *
 * @param cls The class to be bound.
 * @param tag An optional tag to associate with the binding, used to differentiate bindings of the same type.
 * @param overrides An optional boolean indicating if the binding should override existing bindings. Default is null.
 * @return A TypeBinder instance that allows further customisation of the binding.
 */
public inline fun <reified T : Any> DI.Builder.bind(
    cls: KClass<T>,
    tag: Any? = null,
    overrides: Boolean? = null,
): DI.Builder.TypeBinder<T> = Bind(erased(cls), tag, overrides)

/**
 * Binds the previously given tag to the given instance.
 *
 * T generics will be erased!
 *
 * @param T The type of value to bind.
 * @param value The instance to bind.
 */
public inline infix fun <reified T : Any> DI.Builder.ConstantBinder.with(
    value: T
): Unit = With(generic<T>(), value)

/**
 * Delegate the targeted type to a new binding type
 * @param tag The tag to bind.
 * @param overrides Whether this bind **must**, **may** or **must not** override an existing binding.
 * @return The binder: call [DI.Builder.DelegateBinder.To]) on it to finish the binding syntax and register the binding.
 */
public inline fun <reified T : Any> DI.Builder.delegate(
    tag: Any? = null,
    overrides: Boolean? = null,
): DI.Builder.DelegateBinder<T> = Delegate(generic(), tag, overrides)
//endregion

//region SearchDSL
/**
 * Creates a return type constrained spec.
 *
 * @property tag An optional tag constraint.
 */
@Suppress("unused")
public inline fun <reified T : Any> SearchDSL.binding(
    tag: Any? = null
): SearchDSL.Binding = SearchDSL.Binding(generic<T>(), tag)

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
public inline fun <reified C : Any> DI.Builder.scoped(
    scope: Scope<C>
): DI.BindBuilder.WithScope<C> = DI.BindBuilder.ImplWithScope(generic(), scope)

/**
 * Used to define bindings with a context: `bind<MyType>() with contexted<MyContext>().provider { /*...*/ }`
 *
 * @param C The context type.
 */
public inline fun <reified C : Any> DI.Builder.contexted(): DI.BindBuilder<C> =
    DI.BindBuilder.ImplWithContext(generic())
//endregion

//region ContextTranslator
public inline fun <reified C : Any, reified S : Any> contextTranslator(
    noinline t: DirectDI.(C) -> S?
): ContextTranslator<C, S> = SimpleContextTranslator(generic(), generic(), t)

public inline fun <reified C : Any, reified S : Any> DI.Builder.registerContextTranslator(
    noinline t: DirectDI.(C) -> S?
): Unit = RegisterContextTranslator(contextTranslator(t))

public inline fun <reified S : Any> contextFinder(
    noinline t: DirectDI.() -> S
): ContextTranslator<Any, S> = SimpleAutoContextTranslator(generic(), t)

public inline fun <reified S : Any> DI.Builder.registerContextFinder(
    noinline t: DirectDI.() -> S
): Unit = RegisterContextTranslator(contextFinder(t))
//endregion
