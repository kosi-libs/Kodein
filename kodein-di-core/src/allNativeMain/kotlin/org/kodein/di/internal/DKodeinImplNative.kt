package org.kodein.di.internal

import org.kodein.di.*

@Deprecated(DEPRECATE_7X)
internal actual class DKodeinImpl actual constructor(container: KodeinContainer, context: KodeinContext<*>) : DKodeinBaseImpl(container, context), DKodein
