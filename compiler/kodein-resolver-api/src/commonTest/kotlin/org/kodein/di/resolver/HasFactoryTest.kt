package org.kodein.di.resolver

import org.kodein.di.DI
import org.kodein.di.bindConstant
import org.kodein.di.bindFactory
import org.kodein.di.bindMultiton
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import kotlin.jvm.JvmInline
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HasFactoryTest {

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
        assertTrue(di.hasFactory<String, Unit>())
        // Tagged singleton of type String
        assertTrue(di.hasFactory<String, Unit>("author"))
        // Multiton of type String
        assertTrue(di.hasFactory<String, String>())
        // Constant of type Int
        assertTrue(di.hasFactory<Int, Unit>("CONST"))
        // Factory of type Int with type argument Int
        assertTrue(di.hasFactory<Int, Int>())
        // Provider of type Person
        assertTrue(di.hasFactory<Person, Unit>())

        assertFalse(di.hasFactory<Double, Int>())
        assertFalse(di.hasFactory<Double, Unit>())
        assertFalse(di.hasFactory<Unit, String>())
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
