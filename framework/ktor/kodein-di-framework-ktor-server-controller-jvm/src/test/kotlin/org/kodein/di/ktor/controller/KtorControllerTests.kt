package org.kodein.di.ktor.controller

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.server.application.Application
import io.ktor.server.testing.testApplication
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KtorControllerTests {
    @Test
    fun test_00_successDIControllerAutoConfiguration() = testApplication {
        application(Application::diControllerSuccess)

        val ROUTE_PROTECTED = "/protected"
        val ROUTE_FIRST = "/first"
        val ROUTE_SECOND = "$ROUTE_FIRST/second"
        val ROUTE_THIRD = "$ROUTE_SECOND/third"

        assertEquals(VERSION, client.get(ROUTE_VERSION).body())
        assertEquals(AUTHOR, client.get(ROUTE_AUTHOR).body())
        assertEquals(VERSION, client.get("$ROUTE_FIRST$ROUTE_VERSION").body())
        assertEquals(AUTHOR, client.get("$ROUTE_FIRST$ROUTE_AUTHOR").body())
        assertEquals(VERSION, client.get("$ROUTE_SECOND$ROUTE_VERSION").body())
        assertEquals(AUTHOR, client.get("$ROUTE_SECOND$ROUTE_AUTHOR").body())
        assertEquals(VERSION, client.get("$ROUTE_THIRD$ROUTE_VERSION").body())
        assertEquals(AUTHOR, client.get("$ROUTE_THIRD$ROUTE_AUTHOR").body())
        assertEquals(VERSION, client.get("$ROUTE_PROTECTED$ROUTE_VERSION").body())
        assertEquals(AUTHOR, client.get("$ROUTE_PROTECTED$ROUTE_AUTHOR").body())
    }
}