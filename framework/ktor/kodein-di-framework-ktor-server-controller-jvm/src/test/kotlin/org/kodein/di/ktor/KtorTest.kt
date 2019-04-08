package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.*

class KtorTest {
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
}