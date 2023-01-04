package org.kodein.di.resolver

import org.kodein.di.bindings.DIBinding

public inline fun <B: DIResolver, reified T : Any> DIResolverBuilder<B>.bind(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline createBinding: () -> DIBinding<*, *, T>,
): Unit = Bind(tag = tag, overrides = overrides, binding = createBinding())