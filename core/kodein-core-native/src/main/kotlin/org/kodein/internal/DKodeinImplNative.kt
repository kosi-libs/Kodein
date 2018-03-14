package org.kodein.internal

import org.kodein.DKodein
import org.kodein.KodeinContainer
import org.kodein.KodeinContext

actual class DKodeinImpl actual constructor(container: KodeinContainer, context: KodeinContext<*>, receiver: Any?) : DKodeinBaseImpl(container, context, receiver), DKodein
