package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.*

class KtorTest {
    @Test
    fun testGetBindController(): Unit = withTestApplication(Application::main) {
        handleRequest(HttpMethod.Get, ROUTE_VERSION).apply {
            assertEquals(VERSION, response.content)
        }
        handleRequest(HttpMethod.Get, ROUTE_AUTHOR).apply {
            assertEquals(AUTHOR, response.content)
        }
    }
}