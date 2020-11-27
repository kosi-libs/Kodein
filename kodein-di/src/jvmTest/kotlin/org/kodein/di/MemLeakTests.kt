package org.kodein.di

import java.lang.ref.WeakReference
import java.lang.reflect.InvocationTargetException
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull


class MemLeakTests {

    companion object {
        const val MAX_GC_ITERATIONS = 50
        const val GC_SLEEP_TIME = 100L
    }

    fun assertGarbageCollected(ref: WeakReference<*>) {
        val runtime = Runtime.getRuntime()
        for (i in 0 until MAX_GC_ITERATIONS) {
            runtime.runFinalization()
            runtime.gc()
            if (ref.get() == null) break

            // Pause for a while and then go back around the loop to try again...
            try {
                Thread.sleep(GC_SLEEP_TIME)
            } catch (e: InterruptedException) {
                // Ignore any interrupts and just try again...
            } catch (e: InvocationTargetException) {
                // Ignore any interrupts and just try again...
            }
        }
        assertNull(ref.get(), "Object should not exist after $MAX_GC_ITERATIONS collections.")
    }

    class Ctx
    class Foo

    @Test
    @Suppress("UNUSED_VALUE")
    fun test_00_provider() {
        val di = DI {
            bind<Foo>() with provider { Foo() }
        }

        fun test(): WeakReference<Ctx> {
            val ctx = Ctx()
            val ref = WeakReference(ctx)
            di.direct.on(ctx).instance<Foo>()
            return ref
        }

        val ref = test()
        assertGarbageCollected(ref)
    }

    @Test
    @Suppress("UNUSED_VALUE")
    fun test_01_singleton() {
        val di = DI {
            bind<Foo>() with singleton { Foo() }
        }

        fun test(): WeakReference<Ctx> {
            val ctx = Ctx()
            val ref = WeakReference(ctx)
            di.direct.on(ctx).instance<Foo>()
            return ref
        }

        val ref = test()
        assertGarbageCollected(ref)
    }

    class ProvidedS(override val di: DI) : DIAware {
        val foo: () -> Foo by provider()
    }

    @Test
    @Suppress("UNUSED_VALUE")
    fun test_02_providedSingleton() {
        val di = DI {
            bind<Foo>() with provider { Foo() }
            bind<ProvidedS>() with singleton { ProvidedS(di) }
        }

        fun test(): WeakReference<Ctx> {
            val ctx = Ctx()
            val ref = WeakReference(ctx)

            di.direct.on(ctx).instance<ProvidedS>()
            return ref
        }

        val ref = test()
        assertGarbageCollected(ref)
    }

    @Test
    @Suppress("UNUSED_VALUE")
    fun test_03_multiton() {
        val di = DI {
            @Suppress("UNUSED_ANONYMOUS_PARAMETER")
            bind<Foo>() with multiton { name: String -> Foo() }
        }

        fun test(): WeakReference<Ctx> {
            val ctx = Ctx()
            val ref = WeakReference(ctx)
            di.direct.on(ctx).instance<String, Foo>(arg = "test")
            return ref
        }

        val ref = test()
        assertGarbageCollected(ref)
    }

    class ProvidedM(override val di: DI, val name: String) : DIAware {
        val foo: () -> Foo by provider()
    }

    @Test
    @Suppress("UNUSED_VALUE")
    fun test_04_providedMultiton() {
        val di = DI {
            bind<Foo>() with provider { Foo() }
            bind<ProvidedM>() with multiton { name: String -> ProvidedM(di, name) }
        }

        fun test(): WeakReference<Ctx> {
            val ctx = Ctx()
            val ref = WeakReference(ctx)

            di.direct.on(ctx).instance<String, ProvidedM>(arg = "test")
            return ref
        }

        val ref = test()
        assertGarbageCollected(ref)
    }

}
