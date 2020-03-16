package org.kodein.di

import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.ScopeRegistry
import org.kodein.di.bindings.StandardScopeRegistry
import org.kodein.di.test.CloseableData
import org.kodein.di.test.FixMethodOrder
import org.kodein.di.test.MethodSorters
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ErasedJvmTests_04_Scope {

    // Only the JVM supports up-casting
    @Test
    fun test_00_AbstractContextTranslatorAbstractScope() {
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

}
