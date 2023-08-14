package org.kodein.di.test

public interface IPerson { public val name: String? }

public data class Person(override val name: String? = null ) : IPerson

public data class A(var b: B?, val str: String = "")
public data class B(var c: C?, val int: Int = 0)
public data class C(var a: A?, val char: Char = ' ')
public data class D(val str: String = "") {
    lateinit var e: E
}
public data class E(val int: Int = 0) {
    lateinit var f: F
}
public data class F(val char: Char = ' ') {
    lateinit var d: D
}

public interface IName {
    public val firstName: String
}

public open class Name(override val firstName: String) : IName {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Name) return false
        if (firstName != other.firstName) return false
        return true
    }

    override fun hashCode(): Int{
        return firstName.hashCode()
    }

    override fun toString(): String = firstName
}

public interface IFullName : IName {
    public val lastName: String
}

public open class FullName(firstName: String, override val lastName: String) : Name(firstName), IFullName {
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

    override fun toString(): String = "$firstName $lastName"
}

public interface IAge {
    public val age: Int
}

public class FullInfos(firstName: String, lastName: String, override val age: Int) : FullName(firstName, lastName), IAge {
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other !is FullInfos) return false
        if (!super.equals(other)) return false
        if (age != other.age) return false
        return true
    }

    override fun hashCode(): Int {
        return 31 * super.hashCode() + age.hashCode()
    }

    override fun toString(): String = "$firstName $lastName, $age"
}

public typealias PersonEntry = Pair<String, Person>
public typealias PersonEntries = Set<PersonEntry>
