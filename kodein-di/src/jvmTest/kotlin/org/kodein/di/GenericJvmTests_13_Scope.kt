package org.kodein.di

import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.SingleItemScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import org.kodein.di.bindings.SubScope
import org.kodein.di.bindings.UnboundedScope
import org.kodein.di.test.*
import java.lang.AutoCloseable
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GenericJvmTests_13_Scope {

    @Test
    fun test_00_AnyScopeSingleton() {
        val registry = StandardScopeRegistry()
        val myScope = object : Scope<Any?> {
            override fun getRegistry(context: Any?) = registry
        }
        val kodein = DI {
            bind<Person>() with scoped(myScope).singleton { Person() }
        }

        assertTrue(registry.isEmpty())

        val person: Person by kodein.instance()
        assertSame(person, kodein.direct.instance())

        assertFalse(registry.isEmpty())

        registry.clear()

        assertTrue(registry.isEmpty())

        assertNotSame(person, kodein.direct.instance())
    }

    @Test
    fun test_01_ScopeSingleton() {
        val registries = mapOf("a" to SingleItemScopeRegistry(), "b" to SingleItemScopeRegistry())
        val myScope = object : Scope<String> {
            override fun getRegistry(context: String) = registries[context]!!
        }
        val kodein = DI {
            bind<Person>() with scoped(myScope).singleton { Person() }
        }

        assertTrue(registries["a"]!!.isEmpty())

        val a: Person by kodein.on(context = "a").instance()
        val b: Person by kodein.on(context = "b").instance()
        assertNotSame(a, b)
        assertSame(a, kodein.direct.on(context = "a").instance())
        assertSame(b, kodein.direct.on(context = "b").instance())

        assertFalse(registries["a"]!!.isEmpty())

        registries.values.forEach { it.clear() }

        assertTrue(registries["a"]!!.isEmpty())

        assertNotSame(a, kodein.direct.on(context = "a").instance())
        assertNotSame(b, kodein.direct.on(context = "b").instance())
    }

    @Test
    fun test_02_ScopeIgnoredSingleton() {
        val kodein = DI {
            bind<Person>() with singleton { Person() }
        }

        val a: Person by kodein.on(context = "a").instance()
        val b: Person by kodein.on(context = "b").instance()
        assertSame(a, b)
    }

    class CloseableData(val name: String? = null) : AutoCloseable {
        var closed = false
            private set

        override fun close() {
            closed = true
        }
    }

    @Test
    fun test_03_AutoCloseableSingleton() {
        val myScope = UnboundedScope(SingleItemScopeRegistry())

        val kodein = DI {
            bind<CloseableData>() with scoped(myScope).singleton { CloseableData() }
        }

        val a: CloseableData by kodein.instance()
        val b: CloseableData by kodein.instance()
        assertSame(a, b)
        assertFalse(a.closed)
        myScope.registry.clear()
        val c: CloseableData by kodein.instance()

        assertNotSame(a, c)
        assertTrue(a.closed)
        assertFalse(c.closed)
    }

    @Test
    fun test_04_SubScopedSingleton() {
        data class Session(val id: String)
        data class Request(val session: Session)

        val sessionScope = object : Scope<Session> {
            val registries = HashMap<String, ScopeRegistry>()
            override fun getRegistry(context: Session) = registries.getOrPut(context.id, ::StandardScopeRegistry)
        }

        val requestScope = object : SubScope<Request, Session>(sessionScope) {
            override fun getParentContext(context: Request) = context.session
        }

        val kodein = DI {
            bind<CloseableData>() with scoped(requestScope).singleton { CloseableData() }
        }

        val session = Session("sid")
        val request = Request(session)

        val a: CloseableData by kodein.on(request).instance()
        val b: CloseableData by kodein.on(request).instance()
        assertSame(a, b)
        assertFalse(a.closed)
        sessionScope.getRegistry(session).clear()
        val c: CloseableData by kodein.on(request).instance()

        assertNotSame(a, c)
        assertTrue(a.closed)
        assertFalse(c.closed)
    }

    class T05(override val di: DI, val name: String) : DIAware {
        val registry = StandardScopeRegistry()
        val person1: Person by instance()
        val person2: Person by instance()
    }

    @Test
    fun test_05_ReceiverAsScopeSingleton() {
        val testScope = object : Scope<T05> {
            override fun getRegistry(context: T05) = context.registry
        }

        val kodein = DI {
            bind { scoped(testScope).singleton { Person(context.name) } }
        }

        val test1 = T05(kodein, "one")
        assertSame(test1.person1, test1.person2)
        assertEquals("one", test1.person1.name)

        val test2 = T05(kodein, "two")
        assertNotSame(test1.person1, test2.person1)
        assertEquals("two", test2.person1.name)
    }

    @Test
    fun test_06_ContextTranslatorScope() {
        data class Session(val id: String)
        data class Request(val session: Session)

        val sessionScope = object : Scope<Session> {
            val registries = HashMap<String, ScopeRegistry>()
            override fun getRegistry(context: Session) = registries.getOrPut(context.id, ::StandardScopeRegistry)
        }

        val kodein = DI {
            bind<CloseableData>() with scoped(sessionScope).singleton { CloseableData() }
            registerContextTranslator { r: Request -> r.session }
        }

        val session = Session("sid")
        val request = Request(session)

        val c: CloseableData by kodein.on(request).instance()
        assertFalse(c.closed)
        sessionScope.registries[session.id]!!.clear()
        assertTrue(c.closed)
    }

    @Test
    fun test_07_ContextTranslatorAutoScope() {
        data class Session(val id: String)
        data class Request(val session: Session)

        val sessionScope = object : Scope<Session> {
            val registries = HashMap<String, ScopeRegistry>()
            override fun getRegistry(context: Session) = registries.getOrPut(context.id, ::StandardScopeRegistry)
        }

        val session = Session("sid")
        val request = Request(session)

        val parentDI = DI {
            bind<CloseableData>() with scoped(sessionScope).singleton { CloseableData() }
            registerContextTranslator { r: Request -> r.session }
            registerContextFinder { request }
        }

        val kodein = DI {
            extend(parentDI)
        }

        val c: CloseableData by kodein.instance()
        assertFalse(c.closed)
        sessionScope.registries[session.id]!!.clear()
        assertTrue(c.closed)
    }

    @Test
    fun test_08_CircularScopes() {
        val kodein = DI.direct {
            bind { contexted<A>().provider { context.str } }
            bind { contexted<B>().provider { context.int } }
            bind { contexted<C>().provider { context.char } }
            registerContextTranslator { a: A -> a.b }
            registerContextTranslator { b: B -> b.c }
            registerContextTranslator { c: C -> c.a }
        }

        val a = A(null, "test")
        val b = B(null, 42)
        val c = C(null, 'S')
        a.b = b
        b.c = c
        c.a = a

        val str1: String = kodein.on(b).instance()
        val str2: String = kodein.on(c).instance()
        val int1: Int = kodein.on(a).instance()
        val int2: Int = kodein.on(c).instance()
        val char1: Char = kodein.on(a).instance()
        val char2: Char = kodein.on(b).instance()

        assertAllEqual("test", str1, str2)
        assertAllEqual(42, int1, int2)
        assertAllEqual('S', char1, char2)
    }

    @Test
    fun test_09_AbstractContextTranslatorAbstractScope() {
        abstract class AbstractSession(val id: String)
        class SessionImpl(id: String) : AbstractSession(id)
        abstract class AbstractRequest(val session: SessionImpl)
        class RequestImpl(session: SessionImpl) : AbstractRequest(session)

        val sessionScope = object : Scope<AbstractSession> {
            val registries = HashMap<String, ScopeRegistry>()
            override fun getRegistry(context: AbstractSession) = registries.getOrPut(context.id, ::StandardScopeRegistry)
        }

        val kodein = DI {
            bind<CloseableData>() with scoped(sessionScope).singleton { CloseableData() }
            registerContextTranslator { r: AbstractRequest -> r.session }
        }

        val session = SessionImpl("sid")
        val request = RequestImpl(session)

        val c: CloseableData by kodein.on(request).instance()
        assertFalse(c.closed)
        sessionScope.registries[session.id]!!.clear()
        assertTrue(c.closed)
    }

    @Test
    fun test_10_ContextTranslatorAndReceiver() {
        val kodein = DI.direct {
            bind { contexted<Name>().provider { FullName(context.firstName, "BRYS") } }
            registerContextFinder { Name("Salomon") }
            registerContextTranslator { name: String -> Name(name) }
        }

        val fn1: FullName = kodein.instance()
        val fn2: FullName = kodein.on("Laila").instance()

        assertEquals(fn1, FullName("Salomon", "BRYS"))
        assertEquals(fn2, FullName("Laila", "BRYS"))
    }


}
