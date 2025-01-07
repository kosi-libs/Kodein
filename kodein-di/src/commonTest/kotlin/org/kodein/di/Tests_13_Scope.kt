package org.kodein.di

import org.kodein.di.bindings.*
import org.kodein.di.test.*
import kotlin.test.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Tests_13_Scope {

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
        val di = DI.direct {
            bind { contexted<D>().provider { context.str } }
            bind { contexted<E>().provider { context.int } }
            bind { contexted<F>().provider { context.char } }
            registerContextTranslator { d: D -> d.e }
            registerContextTranslator { e: E -> e.f }
            registerContextTranslator { f: F -> f.d }
        }

        val d = D("test")
        val e = E( 42)
        val f = F('S')
        d.e = e
        e.f = f
        f.d = d

        val str1: String = di.on(e).instance()
        val str2: String = di.on(f).instance()
        val int1: Int = di.on(d).instance()
        val int2: Int = di.on(f).instance()
        val char1: Char = di.on(d).instance()
        val char2: Char = di.on(e).instance()

        assertAllEqual("test", str1, str2)
        assertAllEqual(42, int1, int2)
        assertAllEqual('S', char1, char2)
    }

    @Test
    fun test_09_ContextTranslatorAndReceiver() {
        val di = DI.direct {
            bind { contexted<Name>().provider { FullName(context.firstName, "BRYS") } }
            registerContextFinder { Name("Salomon") }
            registerContextTranslator { name: String -> Name(name) }
        }

        val fn1: FullName = di.instance()
        val fn2: FullName = di.on("Laila").instance()

        assertEquals(FullName("Salomon", "BRYS"), fn1)
        assertEquals(FullName("Laila", "BRYS"), fn2)
    }

    @Test
    fun test_10_ContextFinderInDI() {
        val registries = HashMap<Name, ScopeRegistry>()
        val nameScope = object : Scope<Name> {
            override fun getRegistry(context: Name) = registries.getOrPut(context) { StandardScopeRegistry() }
        }
        class CurrentName(var current: Name)
        val di = DI.direct {
            bind { scoped(nameScope).singleton { FullName(context.firstName, "BRYS") } }
            bind { singleton { CurrentName(Name("Salomon")) } }
            registerContextFinder { instance<CurrentName>().current }
        }

        assertEquals(FullName("Salomon", "BRYS"), di.instance())
        di.instance<CurrentName>().current = Name("Laila")
        assertEquals(FullName("Laila", "BRYS"), di.instance())
    }


    @Test
    fun test_11_DirectBinding_AnyScopeSingleton() {
        val registry = StandardScopeRegistry()
        val myScope = object : Scope<Any?> {
            override fun getRegistry(context: Any?) = registry
        }
        val kodein = DI {
            bind { scoped(myScope).singleton { Person() } }
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
    fun test_12_DirectBinding_ScopeSingleton() {

        val registries = mapOf("a" to SingleItemScopeRegistry(), "b" to SingleItemScopeRegistry())
        val myScope = object : Scope<String> {
            override fun getRegistry(context: String) = registries[context]!!
        }
        val kodein = DI {
            bind { scoped(myScope).singleton { Person() } }
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
    fun test_13_DirectBinding_ScopeIgnoredSingleton() {

        val kodein = DI {
            bind { singleton { Person() } }
        }

        val a: Person by kodein.on(context = "a").instance()
        val b: Person by kodein.on(context = "b").instance()
        assertSame(a, b)
    }

    @Test
    fun test_14_DirectBinding_AutoCloseableSingleton() {

        val myScope = UnboundedScope(SingleItemScopeRegistry())

        val kodein = DI {
            bind { scoped(myScope).singleton { CloseableData() } }
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
    fun test_15_DirectBinding_SubScopedSingleton() {
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
            bind { scoped(requestScope).singleton { CloseableData() } }
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

    @Test
    fun test_16_DirectBinding_ContextTranslatorScope() {
        data class Session(val id: String)
        data class Request(val session: Session)

        val sessionScope = object : Scope<Session> {
            val registries = HashMap<String, ScopeRegistry>()
            override fun getRegistry(context: Session) = registries.getOrPut(context.id, ::StandardScopeRegistry)
        }

        val kodein = DI {
            bind { scoped(sessionScope).singleton { CloseableData() } }
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
    fun test_17_DirectBinding_ContextTranslatorAutoScope() {
        data class Session(val id: String)
        data class Request(val session: Session)

        val sessionScope = object : Scope<Session> {
            val registries = HashMap<String, ScopeRegistry>()
            override fun getRegistry(context: Session) = registries.getOrPut(context.id, ::StandardScopeRegistry)
        }

        val session = Session("sid")
        val request = Request(session)

        val parentDI = DI {
            bind { scoped(sessionScope).singleton { CloseableData() } }
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

}
