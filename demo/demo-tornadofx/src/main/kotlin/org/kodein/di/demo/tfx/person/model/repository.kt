package org.kodein.di.demo.tfx.person.model

import java.time.*

object PersonRepository {
    private val persons = mutableListOf(
            Person(1, "Samantha", "Stuart", LocalDate.of(1981, 12, 4)),
            Person(2, "Tom", "Marks", LocalDate.of(2001, 1, 23)),
            Person(3, "Stuart", "Gills", LocalDate.of(1989, 5, 23)),
            Person(4, "Nicole", "Williams", LocalDate.of(1998, 8, 11))
    )

    fun findAll() = persons

    fun save(person: Person) {
        val index = persons.indexOfFirst { it.id == person.id }

        if (index != -1)
            persons[index] = person
        else {
            if (person.id == 0)
                person.id = persons.map { it.id }.max()?.plus(1) ?: persons.size + 1
            persons.add(person)
        }

    }
}