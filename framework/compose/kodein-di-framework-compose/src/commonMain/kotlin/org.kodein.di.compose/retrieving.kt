package org.kodein.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import org.kodein.di.DI
import org.kodein.di.LazyDelegate
import org.kodein.di.factory
import org.kodein.di.instance
import org.kodein.di.named
import org.kodein.di.provider
import kotlin.reflect.KProperty

/**
 * A property delegate provider for DI retrieval in a Composable tree.
 * Provides a `Lazy` value that, when accessed, retrieve the value from DI.
 *
 * In essence, the DI object is accessed only upon retrieving.
 */
@PublishedApi
internal class ComposableDILazyDelegate<V>(private val base: LazyDelegate<V>) : LazyDelegate<V> {
    private lateinit var lazy: Lazy<V>

    override fun provideDelegate(receiver: Any?, prop: KProperty<Any?>): Lazy<V> {
        if (!this::lazy.isInitialized) lazy = base.provideDelegate(null, prop)
        return lazy
    }
}

/**
 * Access DI container in a Composable tree and retrieve a [T] reference
 *
 * T generics will be preserved!
 *
 * @param T The type of object to retrieve.
 * @return A Lazy delegate for the [T] instance.
 */
@Composable
public inline fun <reified T : Any> rememberDI(
    crossinline block: @DisallowComposableCalls DI.() -> LazyDelegate<T>
): LazyDelegate<T> = with(localDI()) {
    remember { ComposableDILazyDelegate(block()) }
}

/**
 * Retrieves and keeps reference on an instance of [T] for the given type and tag.
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
public inline fun <reified T : Any> rememberInstance(tag: Any? = null): LazyDelegate<T> =
    rememberDI { instance(tag = tag) }

/**
 * Retrieves and keeps reference on a named instance of [T] for the given type and tag.
 *
 * T generics will be preserved!
 *
 * @param T The type of object to retrieve.
 * @return An instance of [T].
 * @throws DI.NotFoundException if no provider was found.
 * @throws DI.DependencyLoopException If the instance construction triggered a dependency loop.
 */
@Composable
public inline fun <reified T : Any> rememberNamedInstance(): LazyDelegate<T> =
    rememberDI { named.instance() }

/**
 * Retrieves and keeps a reference on an instance of [T] for the given type and tag,
 * curried from a factory that takes an argument [A].
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
public inline fun <reified A : Any, reified T : Any> rememberInstance(tag: Any? = null, arg: A): LazyDelegate<T> =
    rememberDI { instance(tag = tag, arg = arg) }

/**
 * Retrieves and keeps a reference on an instance of [T] for the given,
 * curried from a factory that takes an argument [A].
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
public inline fun <reified A : Any, reified T : Any> rememberNamedInstance(arg: A): LazyDelegate<T> =
    rememberDI { named.instance(arg = arg) }

/**
 * Retrieves and keeps of [T] for the given type and tag,
 * curried from a factory that takes an argument [A].
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
public inline fun <reified A : Any, reified T : Any> rememberInstance(
    tag: Any? = null,
    noinline fArg: () -> A,
): LazyDelegate<T> =
    rememberDI { instance(tag = tag, fArg = fArg) }

@Composable
public inline fun <reified A : Any, reified T : Any> rememberNamedInstance(noinline fArg: () -> A): LazyDelegate<T> =
    rememberDI { named.instance(fArg = fArg) }

/**
 * Retrieves and keeps a reference on a factory of `T` for the given argument type, return type and tag.
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
public inline fun <reified A : Any, reified T : Any> rememberFactory(tag: Any? = null): LazyDelegate<(A) -> T> =
    rememberDI { factory(tag = tag) }

/**
 * Retrieves and keeps a reference on a provider of `T` for the given type and tag.
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
public inline fun <reified T : Any> rememberProvider(tag: Any? = null): LazyDelegate<() -> T> =
    rememberDI { provider(tag = tag) }

/**
 * Retrieves and keeps a reference on a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
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
public inline fun <reified A : Any, reified T : Any> rememberProvider(tag: Any? = null, arg: A): LazyDelegate<() -> T> =
    rememberDI { provider(tag = tag, arg = arg) }

/**
 * Retrieves and keeps a reference on a provider of [T] for the given type and tag, curried from a factory that takes an argument [A].
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
public inline fun <reified A : Any, reified T : Any> rememberProvider(
    tag: Any? = null,
    noinline fArg: () -> A,
): LazyDelegate<() -> T> =
    rememberDI { provider(tag = tag, fArg = fArg) }
