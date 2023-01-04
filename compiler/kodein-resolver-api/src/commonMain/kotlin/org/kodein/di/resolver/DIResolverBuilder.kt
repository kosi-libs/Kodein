package org.kodein.di.resolver

import org.kodein.di.DI
import org.kodein.di.DIContainer
import org.kodein.di.bindings.DIBinding

public abstract class DIResolverBuilder<T: DIResolver>(private val containerBuilder: DIContainer.Builder) {
    @Suppress("FunctionName")
    public fun <T : Any> Bind(
        tag: Any?,
        overrides: Boolean?,
        binding: DIBinding<*, *, T>
    ): Unit = containerBuilder.bind(
        key = DI.Key(binding.contextType, binding.argType, binding.createdType, tag = tag),
        binding = binding,
        fromModule = "",
        overrides = overrides,
    )
}