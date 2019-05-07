package org.kodein.di.tornadofx

import org.kodein.di.bindings.*
import tornadofx.*

/**
 * Scope that will leverage scoping instances on [Component]
 * like [View] / [Fragment] / [ViewModel] / etc.
 */
object ComponentScope : WeakContextScope<Component>()