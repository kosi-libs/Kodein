package org.kodein.di

import kotlin.jvm.JvmInline
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Tests_27_HasFactory {

    @JvmInline value class Person(val name: String)

    @Test
    fun test_00_containerHasFactory() {
        val di = DI {
            bindSingleton { "Hello" }
            bindSingleton(tag = "author") { "Salomon" }
            bindMultiton<String, String> { str -> "Hello, $str!" }
            bindConstant("CONST") { 42 }
            bindFactory<Int, Int> { i -> i * i }
            bindProvider { Person("Romain") }
        }

        // Singleton of type String
        assertTrue(di.hasFactory<Unit, String>())
        // Tagged singleton of type String
        assertTrue(di.hasFactory<Unit, String>("author"))
        // Multiton of type String
        assertTrue(di.hasFactory<String, String>())
        // Constant of type Int
        assertTrue(di.hasFactory<Unit, Int>("CONST"))
        // Factory of type Int with type argument Int
        assertTrue(di.hasFactory<Int, Int>())
        // Provider of type Person
        assertTrue(di.hasFactory<Unit, Person>())

        assertFalse(di.hasFactory<Int, Double>())
        assertFalse(di.hasFactory<Unit, Double>())
        assertFalse(di.hasFactory<String, Unit>())
    }

    @Test
    fun test_01_containerHasProvider() {
        val di = DI {
            bindSingleton { "Hello" }
            bindSingleton(tag = "author") { "Salomon" }
            bindConstant("CONST") { 42 }
            bindProvider { Person("Romain") }
        }

        // Singleton of type String
        assertTrue(di.hasProvider<String>())
        // Tagged singleton of type String
        assertTrue(di.hasProvider<String>("author"))
        // Constant of type Int
        assertTrue(di.hasProvider<Int>("CONST"))
        // Provider of type Person
        assertTrue(di.hasProvider<Person>())

        assertFalse(di.hasProvider<Double>())
        assertFalse(di.hasProvider<Unit>())
    }
}
