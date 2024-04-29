package org.kodein.di.internal

import org.kodein.di.DIContainer
import org.kodein.di.DIContext
import org.kodein.di.DirectDI

private class DirectDIImpl(
    container: DIContainer,
    context: DIContext<*>,
) : DirectDIBaseImpl(container, context), DirectDI

internal actual fun createDirectDI(container: DIContainer, context: DIContext<*>): DirectDI = DirectDIImpl(container, context)
