package org.kodein.di.demo.tfx.person.controller

import javafx.collections.*
import org.kodein.di.*
import org.kodein.di.demo.tfx.person.model.*
import org.kodein.di.generic.*
import org.kodein.di.tornadofx.*
import tornadofx.*

class PersonListController : Controller(), KodeinAware {
    override val kodein: Kodein = kodein()

    val personEditorController: PersonEditorController by instance()

    lateinit var personList: ObservableList<Person>

    init {
        updateList()
    }

    fun updateList() {
        personList = PersonRepository.findAll().observable()
    }

    fun save(person: Person) {
        PersonRepository.save(person)
        updateList()
    }

    fun editPerson(person: Person) {
        personEditorController.editPerson(person)
    }
}