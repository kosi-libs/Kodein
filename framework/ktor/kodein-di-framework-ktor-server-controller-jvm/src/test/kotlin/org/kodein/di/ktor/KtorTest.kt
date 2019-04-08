package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.*

class KtorTest {
    @Test
    fun testSuccessKodeinControllerFeature(): Unit = withTestApplication(Application::success) {
        handleRequest(HttpMethod.Get, ROUTE_VERSION).apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, ROUTE_AUTHOR).apply {
            assertEquals(AUTHOR, response.content)
        }
    }

    @Test
    fun testFailureKodeinControllerFeature() {
        assertFailsWith(MissingApplicationFeatureException::class) {
            withTestApplication(Application::failure) {}
        }
    }
}