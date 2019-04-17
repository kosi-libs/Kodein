package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlin.test.*

@KtorExperimentalAPI
class KtorTests {

    @Test
    fun test_00_getSession(): Unit = withTestApplication(Application::main) {
        handleRequest(HttpMethod.Get, ROUTE_SESSION).apply {
            assertTrue { response.content?.contains("java.security.SecureRandom@.*".toRegex()) ?: false }
        }
    }

    @Test
    fun test_01_getIncrement(): Unit = withTestApplication(Application::main) {
        handleRequest(HttpMethod.Get, ROUTE_SESSION + ROUTE_INC).apply {
            assertTrue { response.content == "${MockSession(1)}" }
        }
    }

    @Test
    fun test_02_getClear(): Unit = withTestApplication(Application::main) {
        handleRequest(HttpMethod.Get, ROUTE_SESSION + ROUTE_CLEAR).apply {
            assertTrue { response.content == "null" }
        }
    }

    @Test
    fun test_03_sessionScoped(): Unit = withTestApplication(Application::main) {
        cookiesSession {
            // set a session context
            val sessionRandomPairs = mutableListOf<Pair<MockSession, String>>()
            val sessionCookie: Cookie

            // (1) Call '/session' for the first time
            // Should send a new instance of SecureRandom
            // Session is actually MockSession(counter=0) - default value
            handleRequest(HttpMethod.Get, ROUTE_SESSION).apply {
                val newRandomInstance = response.content.toString()
                sessionRandomPairs.add(MockSession() to newRandomInstance)
            }

            // (2) Call '/session/increment' to create a session - MockSession(counter=1)
            handleRequest(HttpMethod.Get, ROUTE_SESSION + ROUTE_INC).apply {
                sessionCookie = response.headers.values(HttpHeaders.SetCookie)
                        .map { parseServerSetCookieHeader(it) }
                        .first { it.name == SESSION_FEATURE_SESSION_ID }

                assertNotNull(sessionCookie)
                assertTrue { sessionCookie.value != NO_SESSION }
            }

            // (3) Call '/session' with the session - MockSession(counter=1)
            // Should send a new instance of SecureRandom != from (1)
            handleRequest(HttpMethod.Get, ROUTE_SESSION) {
                addHeader(HttpHeaders.Cookie, sessionCookie.toString())
            }.apply {
                val newRandomInstance = response.content ?: "null"
                val pairs = sessionRandomPairs.toList()
                sessionRandomPairs.add(MockSession(1) to newRandomInstance)
                assertTrue { pairs.none { it.second == newRandomInstance } }
            }

            // (4) Call '/session/increment' to create a new session - MockSession(counter=2)
            // Session cookie is still the same
            handleRequest(HttpMethod.Get, ROUTE_SESSION + ROUTE_INC) {
                addHeader(HttpHeaders.Cookie, sessionCookie.toString())
            }.apply {
                val cookie = response.headers.values(HttpHeaders.SetCookie)
                        .map { parseServerSetCookieHeader(it) }
                        .first { it.name == SESSION_FEATURE_SESSION_ID }

                assertNotNull(cookie)
                assertTrue { cookie == sessionCookie }
            }

            // (5) Call '/session' with the session MockSession(counter=2)
            // Should send a new instance of SecureRandom != from (1) and (3)
            handleRequest(HttpMethod.Get, ROUTE_SESSION) {
                addHeader(HttpHeaders.Cookie, sessionCookie.toString())
            }.apply {
                val newRandomInstance = response.content ?: "null"
                val pairs = sessionRandomPairs.toList()
                sessionRandomPairs.add(MockSession(2) to newRandomInstance)

                assertTrue { pairs.none { it.second == newRandomInstance } }
            }

            // (6) Call '/session/clear'
            // Should return 'null
            handleRequest(HttpMethod.Get, ROUTE_SESSION + ROUTE_CLEAR) {
                addHeader(HttpHeaders.Cookie, sessionCookie.toString())
            }.apply {
                assertTrue { response.content == "null" }
            }

            // (7) Call '/session' after clearing the session
            // Should send a scoped instance of SecureRandom; same as (1)
            // Session is actually MockSession(counter=0) - default value
            handleRequest(HttpMethod.Get, ROUTE_SESSION).apply {
                assertTrue { sessionRandomPairs.any { it.first == MockSession() && it.second == response.content } }
            }
        }
    }


    @Test
    fun test_04_getRequestScoped(): Unit = withTestApplication(Application::main) {
        handleRequest(HttpMethod.Get, ROUTE_REQUEST).apply {
            assertNotNull(response.content)

            response.content?.let {
                val pairs = it.split(",")
                        .map {
                            val keyValue = it.split("=")
                            keyValue.first() to keyValue.last()
                        }

                assertTrue {
                    pairs.size == 5 && // Ensure we pass through 5 phases (Setup, Monitoring, Features, Call, GET)
                            pairs.map { it.second }.distinct().count() == 1 // For all the phases we only have 1 Random instance
                }
            }
        }
    }

    @Test
    fun test_05_closest(): Unit = withTestApplication(Application::main) {
        val kodeinInstance = this.application.attributes[KodeinKey]

        assertSame(kodeinInstance, kodein { this.application }.baseKodein)

        handleRequest(HttpMethod.Get, ROUTE_CLOSEST) {
            assertSame(kodeinInstance, kodein { this.call.application }.baseKodein)
        }.apply {
            assertNotNull(response.content)

            response.content?.let {
                val kodeinInstances = it.split(",").map { it.trim() }
                assertTrue { kodeinInstances.distinct().size == 1 }
                assertEquals("$kodeinInstance", kodeinInstances.first())
            }

            assertSame(kodeinInstance, kodein().baseKodein)
        }
    }

    @Test
    fun test_06_subKodein(): Unit = withTestApplication(Application::main) {
        val kodeinInstance = this.application.attributes[KodeinKey]

        assertSame(kodeinInstance, kodein { this.application }.baseKodein)

        handleRequest(HttpMethod.Post, "$ROUTE_SUBKODEIN$ROUTE_SUB_UPPER") {
            assertSame(kodeinInstance, kodein { this.call.application }.baseKodein)
        }.apply {
            assertNotNull(response.content)

            response.content?.let {
                val kodeinInstances = it.split(",").map { it.trim() }
                assertEquals(2, kodeinInstances.distinct().size)
                assertEquals("$kodeinInstance", kodeinInstances.first())
            }

            assertSame(kodeinInstance, kodein().baseKodein)
        }

        val lowerAuthor: String
        val upperAuthor: String
        val NO_RESPONSE = "NO_RESPONSE"

        handleRequest(HttpMethod.Get, "$ROUTE_SUBKODEIN$ROUTE_SUB_LOWER").apply {
            lowerAuthor = response.content ?: NO_RESPONSE
            assertEquals(AUTHOR.toLowerCase(), lowerAuthor)
        }
        handleRequest(HttpMethod.Get, "$ROUTE_SUBKODEIN$ROUTE_SUB_UPPER").apply {
            upperAuthor = response.content ?: NO_RESPONSE
            assertEquals(AUTHOR.toUpperCase(), upperAuthor)
        }

        assertNotSame(lowerAuthor, upperAuthor, NO_RESPONSE)
    }
}