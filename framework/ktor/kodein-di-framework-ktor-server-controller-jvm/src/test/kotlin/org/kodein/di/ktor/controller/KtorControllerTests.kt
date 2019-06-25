package org.kodein.di.ktor.controller

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.*
import org.junit.Test
import org.junit.runners.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KtorControllerTests {
    @Test
    fun test_00_successAbstractKodeinControllerFeature(): Unit = withTestApplication(Application::kodeinAbsControllerSuccess) {
        handleRequest(HttpMethod.Get, ROUTE_VERSION).apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, ROUTE_AUTHOR).apply {
            assertEquals(AUTHOR, response.content)
        }
    }

    @Test
    fun test_01_failureAbstractKodeinControllerFeature() {
        assertFailsWith(MissingApplicationFeatureException::class) {
            withTestApplication(Application::kodeinAbsControllerFailure) {}
        }
    }

    @Test
    fun test_02_successKodeinControllerImplFeature(): Unit = withTestApplication(Application::kodeinControllerImplSuccess) {
        handleRequest(HttpMethod.Get, ROUTE_VERSION).apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, ROUTE_AUTHOR).apply {
            assertEquals(AUTHOR, response.content)
        }
    }

    @Test
    fun test_03_failureKodeinControllerImplFeature() {
        assertFailsWith(MissingApplicationFeatureException::class) {
            withTestApplication(Application::kodeincontrollerImplFailure) {}
        }
    }

    @Test
    fun test_00_successKodeinControllerAutoConfiguration(): Unit = withTestApplication(Application::kodeinControllerSuccess) {

        val ROUTE_PROTECTED = "/protected"
        val ROUTE_FIRST = "/first"
        val ROUTE_SECOND = "$ROUTE_FIRST/second"
        val ROUTE_THIRD = "$ROUTE_SECOND/third"

        handleRequest(HttpMethod.Get, ROUTE_VERSION).apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, ROUTE_AUTHOR).apply {
            assertEquals(AUTHOR, response.content)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_FIRST$ROUTE_VERSION").apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_FIRST$ROUTE_AUTHOR").apply {
            assertEquals(AUTHOR, response.content)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_SECOND$ROUTE_VERSION").apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_SECOND$ROUTE_AUTHOR").apply {
            assertEquals(AUTHOR, response.content)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_THIRD$ROUTE_VERSION").apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_THIRD$ROUTE_AUTHOR").apply {
            assertEquals(AUTHOR, response.content)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_PROTECTED$ROUTE_VERSION").apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_PROTECTED$ROUTE_AUTHOR").apply {
            assertEquals(AUTHOR, response.content)
        }
    }
}