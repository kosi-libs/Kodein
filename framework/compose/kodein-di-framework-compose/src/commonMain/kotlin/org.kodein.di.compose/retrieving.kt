package org.kodein.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.kodein.di.*

/**
 * Gets an instance of `T` for the given type and tag.
 *
 * T generics will be preserved!
 *
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @return An instance.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
@Composable
public inline fun <reified T : Any> rememberInstance(tag: Any? = null): DIProperty<T> = with(localDI()) {
    remember { instance(tag) }
}

// Deprecated since 7.7.0
@Deprecated("Renamed rememberInstance", ReplaceWith("rememberInstance(tag)"))
@Composable
public inline fun <reified T : Any> instance(tag: Any? = null): DIProperty<T> = rememberInstance(tag)

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Composable
public inline fun <reified A : Any, reified T : Any> rememberInstance(tag: Any? = null, arg: A): DIProperty<T> = with(localDI()) {
    remember { instance(tag, arg) }
}

// Deprecated since 7.7.0
@Deprecated("Renamed rememberInstance", ReplaceWith("rememberInstance(tag, arg)"))
@Composable
public inline fun <reified A : Any, reified T : Any> instance(tag: Any? = null, arg: A): DIProperty<T> = rememberInstance(tag, arg)

/**
 * Gets an instance of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve.
 * @param tag The bound tag, if any.
 * @param fArg The argument that will be given to the factory when curried.
 * @return An instance of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException If the value construction triggered a dependency loop.
 */
@Composable
public inline fun <reified A : Any, reified T : Any> rememberInstance(tag: Any? = null, noinline fArg: () -> A): DIProperty<T> = with(localDI()) {
    remember { instance(tag, fArg) }
}

/**
 * Gets a factory of `T` for the given argument type, return type and tag.
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws DI.NotFoundException if no factory was found.
 * @throws DI.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
@Composable
public inline fun <reified A : Any, reified T : Any> rememberFactory(tag: Any? = null): DIProperty<(A) -> T> = with(localDI()) {
    remember { factory(tag) }
}

// Deprecated since 7.7.0
@Deprecated("Renamed rememberFactory", ReplaceWith("rememberFactory(tag)"))
@Composable
public inline fun <reified A : Any, reified T : Any> factory(tag: Any? = null): DIProperty<(A) -> T> = rememberFactory(tag)

/**
 * Gets a provider of `T` for the given type and tag.
 *
 * T generics will be preserved!
 *
 * @param T The type of object the provider returns.
 * @param tag The bound tag, if any.
 * @return A provider.
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException When calling the provider function, if the instance construction triggered a dependency loop.
 */
@Composable
public inline fun <reified T : Any> rememberProvider(tag: Any? = null): DIProperty<() -> T> = with(localDI()) {
    remember { provider(tag) }
}

// Deprecated since 7.7.0
@Deprecated("Renamed rememberProvider", ReplaceWith("rememberProvider(tag)"))
@Composable
public inline fun <reified T : Any> provider(tag: Any? = null): DIProperty<() -> T> = rememberProvider(tag)

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param arg The argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
@Composable
public inline fun <reified A : Any, reified T : Any> rememberProvider(tag: Any? = null, arg: A): DIProperty<() -> T> = with(localDI()) {
    remember { provider(tag, arg) }
}

// Deprecated since 7.7.0
@Deprecated("Renamed rememberProvider", ReplaceWith("rememberProvider(tag, arg)"))
@Composable
public inline fun <reified A : Any, reified T : Any> provider(tag: Any? = null, arg: A): DIProperty<() -> T> = rememberProvider(tag, arg)

/**
 * Gets a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
 *
 * A & T generics will be preserved!
 *
 * @param A The type of argument the curried factory takes.
 * @param T The type of object to retrieve with the returned provider.
 * @param tag The bound tag, if any.
 * @param fArg A function that returns the argument that will be given to the factory when curried.
 * @return A provider of [T].
 * @throws DI.NotFoundException If no provider was found.
 * @throws DI.DependencyLoopException When calling the provider, if the value construction triggered a dependency loop.
 */
@Composable
public inline fun <reified A : Any, reified T : Any> rememberProvider(tag: Any? = null, noinline fArg: () -> A): DIProperty<() -> T> = with(localDI()) {
    remember { provider(tag, fArg) }
}

// Deprecated since 7.7.0
@Deprecated("Renamed rememberProvider", ReplaceWith("rememberProvider(tag, fArg)"))
@Composable
public inline fun <reified A : Any, reified T : Any> provider(tag: Any? = null, noinline fArg: () -> A): DIProperty<() -> T> = rememberProvider(tag, fArg)
