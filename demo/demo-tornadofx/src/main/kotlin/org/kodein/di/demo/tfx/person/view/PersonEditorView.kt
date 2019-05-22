package org.kodein.di.demo.tfx.person.view

import javafx.beans.binding.*
import javafx.scene.control.*
import javafx.scene.layout.*
import org.kodein.di.*
import org.kodein.di.demo.tfx.person.model.*
import org.kodein.di.generic.*
import org.kodein.di.tornadofx.*
import tornadofx.*

class PersonEditorView : View() {
    lateinit var tabPane: TabPane

    override val root = hbox {
        tabpane {

            val test: String by kodein().instance("test")
            println("tabpane: $test")

            hboxConstraints { hGrow = Priority.ALWAYS }
            tabPane = this
        }
    }
}

class EditorTabFragment : Fragment(), KodeinAware {
    override val kodein: Kodein = subKodein {
        constant("item") with "Person"
    }

    private val item: String by instance("item")

    private val editingState: EditingState by on(this).instance()

    override val scope = super.scope as PersonScope
    val model = scope.model
    lateinit var tab: Tab

    override val root = hbox {
        val test: String by kodein().instance("test")
        println("hbox: $test")

        form {
            subKodein(allowSilentOverride = true) {
                constant("test") with "MyForm"
            }

            val test2: String by kodein().instance("test")
            println("form: $test2")

            fieldset("Edit $item") {
                field("Firstname") {
                    textfield(model.firstname) {
                        setOnKeyReleased {
                            editingState.editing = true
                        }
                    }
                }
                field("Lastname") {
                    textfield(model.lastname)
                    setOnKeyReleased {
                        editingState.editing = true
                    }
                }
                field("Birthdate") {
                    datepicker(model.birthday)
                    setOnKeyReleased {
                        editingState.editing = true
                    }
                }

                button("Save") {
                    enableWhen(editingState.editingProperty)
                    action {
                        save()
                    }
                }
            }
        }
    }

    private fun save() {
        model.commit()
        editingState.editing = false
        tab.text = Bindings.concat(model.firstname, " ", model.lastname).valueSafe
        PersonRepository.save(person = model.item)
    }
}
