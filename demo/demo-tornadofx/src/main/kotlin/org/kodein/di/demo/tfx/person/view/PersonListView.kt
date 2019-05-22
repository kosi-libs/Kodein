package org.kodein.di.demo.tfx.person.view

import org.kodein.di.demo.tfx.person.controller.*
import org.kodein.di.demo.tfx.person.model.*
import org.kodein.di.generic.*
import org.kodein.di.tornadofx.*
import tornadofx.*

class PersonListView : View() {
    private val listController: PersonListController by kodein().instance()

    override val root = tableview(listController.personList) {
        column("ID", Person::idProperty)
        column("Firstname", Person::firstnameProperty)
        column("Lastname", Person::lastnameProperty)
        column("Birthday", Person::birthdayProperty)

        smartResize()
        onUserSelect(2) {
            listController.editPerson(it)
        }
    }
}