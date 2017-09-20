package com.github.salomonbrys.kodein.test

interface IPerson { val name: String? }

data class Person(override val name: String? = null ) : IPerson

data class A(val b: B?)
data class B(val c: C?)
data class C(val a: A?)

open class Name(val firstName: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Name) return false
        if (firstName != other.firstName) return false
        return true
    }

    override fun hashCode(): Int{
        return firstName.hashCode()
    }
}
class FullName(firstName: String, val lastName: String) : Name(firstName) {
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is FullName) return false
        if (!super.equals(other)) return false
        if (lastName != other.lastName) return false
        return true
    }

    override fun hashCode(): Int {
        return 31 * super.hashCode() + lastName.hashCode()
    }
}

typealias PersonEntry = Pair<String, Person>
typealias PersonEntries = Set<PersonEntry>
