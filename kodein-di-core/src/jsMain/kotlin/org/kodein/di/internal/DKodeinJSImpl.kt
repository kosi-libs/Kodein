package org.kodein.di.internal

import org.kodein.di.DKodein
import org.kodein.di.KodeinContainer
import org.kodein.di.KodeinContext

internal actual class DKodeinImpl actual constructor(container: KodeinContainer, context: KodeinContext<*>, receiver: Any?) : DKodeinBaseImpl(container, context, receiver), DKodein
