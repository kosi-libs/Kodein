package org.kodein.di.demo.tfx.person.model

import javafx.beans.property.*
import tornadofx.*
import java.time.*

class Person(id: Int, firstname: String, lastname: String, birthday: LocalDate) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val firstnameProperty = SimpleStringProperty(firstname)
    var firstname by firstnameProperty

    val lastnameProperty = SimpleStringProperty(lastname)
    var lastname by lastnameProperty

    val birthdayProperty = SimpleObjectProperty(birthday)
    var birthday by birthdayProperty

    fun toModel() = PersonModel(this)
}

class PersonModel(person: Person) : ItemViewModel<Person>(person) {
    val firstname = bind(Person::firstnameProperty)
    val lastname = bind(Person::lastnameProperty)
    val birthday = bind(Person::birthdayProperty)
}

class PersonScope(person: Person) : Scope() {
    val model = PersonModel(person)
}