package org.kodein.di.tornadofx

import javafx.scene.*
import org.kodein.di.bindings.*
import tornadofx.*

/**
 * Scope that will leverage scoping instances on [Component]
 * like [View] / [Fragment] / [ViewModel] / etc.
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public object ComponentScope : WeakContextScope<Component>()

/**
 * Scope that will leverage scoping instances on [Node]
 * Simply, every UI component
 * 
 * @deprecated TornadoFX has been abandoned by its creator. Consider using Compose instead.
 * See documentation at: https://kosi-libs.org/kodein/latest/framework/compose.html
 */
@Deprecated("TornadoFX has been abandoned by its creator. Consider using Compose instead. Also, see: https://kosi-libs.org/kodein/latest/framework/compose.html")
public object NodeScope : WeakContextScope<Node>()
