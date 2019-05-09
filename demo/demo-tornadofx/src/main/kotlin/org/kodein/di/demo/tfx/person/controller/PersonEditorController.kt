package org.kodein.di.demo.tfx.person.controller

import javafx.beans.binding.*
import org.kodein.di.demo.tfx.person.model.*
import org.kodein.di.demo.tfx.person.view.*
import org.kodein.di.generic.*
import org.kodein.di.tornadofx.*
import tornadofx.*

class PersonEditorController : Controller() {
    val personEditorView: PersonEditorView by kodein().instance()

    fun editPerson(person: Person) {
        val tp = personEditorView.tabPane

        val personScope: PersonScope by kodein().instance(arg = person)
        val editor: EditorTabFragment by kodein().instance(arg = personScope)

        tp.tab(Bindings.concat(person.firstnameProperty, " ", person.lastnameProperty).valueSafe) {
            closeableWhen(editor.model.dirty.not())
            editor.tab = this
            this += editor
        }
    }
}