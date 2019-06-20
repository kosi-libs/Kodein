package org.kodein.di.tornadofx

import javafx.scene.*
import org.kodein.di.bindings.*
import tornadofx.*

/**
 * Scope that will leverage scoping instances on [Component]
 * like [View] / [Fragment] / [ViewModel] / etc.
 */
object ComponentScope : WeakContextScope<Component>()
/**
 * Scope that will leverage scoping instances on [Node]
 * Simply, every UI component
 */
object NodeScope : WeakContextScope<Node>()