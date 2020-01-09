package org.kodein.di.internal

import org.kodein.di.*

internal actual class DirectDIImpl actual constructor(container: DIContainer, context: DIContext<*>) : DirectDIBaseImpl(container, context), DirectDI
