package org.kodein.di.ktor

import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlinx.coroutines.coroutineScope
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import org.kodein.di.DI
import org.kodein.di.LazyDI
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class KtorTests {

    private fun assertContained(regex: Regex, content: String) {
        asserter.assertTrue({ "Expected <$regex> to be contained in <$content>" }, regex in content)
    }

    private fun <T> assertNone(iterable: Iterable<T>, predicate: (T) -> Boolean) {
        val found = iterable.firstOrNull(predicate)
        if (found != null)
            fail("Expected iterable NOT to contain <$found>\nIterable contains:\n${iterable.joinToString(separator = "\n", prefix = "  ")}")
    }

    private fun <T> assertAny(iterable: Iterable<T>, predicate: (T) -> Boolean) {
        if (!iterable.any(predicate))
            fail("Expected iterable to contain value matching predicate.\nIterable contains:\n${iterable.joinToString(separator = "\n", prefix = "  ")}")
    }


    @Test
    fun test_00_getSession(): Unit = testApplication {
        application(Application::main)

        val body = client.get(ROUTE_SESSION).body<String?>()
        assertNotNull(body)
        assertContained("java.util.Random@.*".toRegex(), body)
    }

    @Test
    fun test_01_getIncrement(): Unit = testApplication {
        application(Application::main)
        assertEquals(MockSession(1).toString(), client.get(ROUTE_SESSION + ROUTE_INC).body())
    }

    @Test
    fun test_02_getClear(): Unit = testApplication {
        application(Application::main)
        assertEquals("null", client.get(ROUTE_SESSION + ROUTE_CLEAR).body())
    }

    @Test
    fun test_03_sessionScoped(): Unit = testApplication {
        application(Application::main)

        // set a session context
        val sessionRandomPairs = mutableListOf<Pair<MockSession, String>>()
        val sessionCookie: Cookie

        // (1) Call '/session' for the first time
        // Should send a new instance of SecureRandom
        // Session is actually MockSession(counter=0) - default value
        sessionRandomPairs.add(MockSession() to client.get(ROUTE_SESSION).body())

        // (2) Call '/session/increment' to create a session - MockSession(counter=1)
        client.get(ROUTE_SESSION + ROUTE_INC).apply {
            sessionCookie = headers[HttpHeaders.SetCookie]
                ?.let { parseServerSetCookieHeader(it) }  ?: fail()
            assertNotNull(sessionCookie)
            assertNotEquals(NO_SESSION, sessionCookie.value)
        }

        // (3) Call '/session' with the session - MockSession(counter=1)
        // Should send a new instance of SecureRandom != from (1)
        client.get(ROUTE_SESSION) {
            cookie(sessionCookie.name, sessionCookie.value)
        }.apply {
            val newRandomInstance = body() ?: "null"
            val pairs = sessionRandomPairs.toList()
            sessionRandomPairs.add(MockSession(1) to newRandomInstance)
            assertNone(pairs) { it.second == newRandomInstance }
        }


        // (4) Call '/session/increment' to create a new session - MockSession(counter=2)
        // Session cookie is still the same
        client.get(ROUTE_SESSION + ROUTE_INC) {
            cookie(sessionCookie.name, sessionCookie.value)
        }.apply {
            val cookie = headers[HttpHeaders.SetCookie]
                ?.let { parseServerSetCookieHeader(it) } ?: fail()

            assertNotNull(cookie)
            assertEquals(sessionCookie.name, cookie.name)
            assertEquals(sessionCookie.value, cookie.value)
        }

        // (5) Call '/session' with the session MockSession(counter=2)
        // Should send a new instance of SecureRandom != from (1) and (3)
        client.get(ROUTE_SESSION) {
            cookie(sessionCookie.name, sessionCookie.value)
        }.apply {
            val newRandomInstance = body() ?: "null"
            val pairs = sessionRandomPairs.toList()
            sessionRandomPairs.add(MockSession(2) to newRandomInstance)

            assertNone(pairs) { it.second == newRandomInstance }
        }

        // (6) Call '/session/clear'
        // Should return 'null
        client.get(ROUTE_SESSION + ROUTE_CLEAR) {
            cookie(sessionCookie.name, sessionCookie.value)
        }.apply {
            assertEquals("null", body())
        }

        // (7) Call '/session' after clearing the session
        // Should send a scoped instance of SecureRandom; same as (1)
        // Session is actually MockSession(counter=0) - default value
        client.get(ROUTE_SESSION).apply {
            val content = body<String>()
            assertAny(sessionRandomPairs) { it.first == MockSession() && it.second == content }
        }
    }

    @Test
    fun test_04_getRequestScoped(): Unit = testApplication {
        application(Application::main)

        val pairs = client.get(ROUTE_REQUEST).body<String>().split(",")
                .map {
                    val keyValue = it.split("=")
                    keyValue.first() to keyValue.last()
                }

        assertEquals(5, pairs.size) // Ensure we pass through 5 phases (Setup, Monitoring, Plugins, Call, GET)
        assertEquals(1, pairs.map { it.second }.distinct().count()) // For all the phases we only have 1 Random instance
    }


    @Test
    fun test_05_closest(): Unit = testApplication {
        application(Application::main)

        client.get(ROUTE_CLOSEST).body<String>().apply {
            val diInstances = split(",").map { it.trim() }
            assertEquals(1, diInstances.distinct().size)
        }
    }


    @Test
    fun test_06_subKodein(): Unit = testApplication {
        application(Application::main)

        val body = client.post("$ROUTE_SUBKODEIN$ROUTE_SUB_UPPER").body<String>()
        val diInstances = body.split(",").map { it.trim() }
        assertEquals(2, diInstances.distinct().size)

        val lowerAuthor: String
        val upperAuthor: String
        val NO_RESPONSE = "NO_RESPONSE"

        client.get("$ROUTE_SUBKODEIN$ROUTE_SUB_LOWER").apply {
            lowerAuthor = body() ?: NO_RESPONSE
            assertEquals(AUTHOR.lowercase(), lowerAuthor)
        }
        client.get("$ROUTE_SUBKODEIN$ROUTE_SUB_UPPER").apply {
            upperAuthor = body() ?: NO_RESPONSE
            assertEquals(AUTHOR.uppercase(), upperAuthor)
        }

        assertNotSame(lowerAuthor, upperAuthor, NO_RESPONSE)
    }
}