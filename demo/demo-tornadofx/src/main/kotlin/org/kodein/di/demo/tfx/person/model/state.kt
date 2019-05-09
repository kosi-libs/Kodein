package org.kodein.di.demo.tfx.person.model

import javafx.beans.property.*
import tornadofx.*

class EditingState {
    val editingProperty = SimpleBooleanProperty(false)
    var editing by editingProperty
}