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
    fun provider() {
        val di = DI {
            bind<Foo>() with provider { Foo() }
        }

        fun test(): WeakReference<Ctx> {
            val ctx = Ctx()
            val ref = WeakReference(ctx)
            val foo: Foo by di.on(ctx).instance()
            assertNotNull(foo)
            return ref
        }

        val ref = test()
        assertGarbageCollected(ref)
    }

    @Test
    @Suppress("UNUSED_VALUE")
    fun singleton() {
        val di = DI {
            bind<Foo>() with singleton { Foo() }
        }

        fun test(): WeakReference<Ctx> {
            val ctx = Ctx()
            val ref = WeakReference(ctx)
            val foo: Foo by di.on(ctx).instance()
            assertNotNull(foo)
            return ref
        }

        val ref = test()
        assertGarbageCollected(ref)
    }

    class Provided(override val di: DI) : DIAware {
        val foo: () -> Foo by provider()
    }

    @Test
    @Suppress("UNUSED_VALUE")
    fun providedSingleton() {
        val di = DI {
            bind<Foo>() with singleton { Foo() }
            bind<Provided>() with singleton { Provided(di) }
        }

        fun test(): WeakReference<Ctx> {
            val ctx = Ctx()
            val ref = WeakReference(ctx)

            val p: Provided by di.on(ctx).instance()
            assertNotNull(p.foo)
            return ref
        }

        val ref = test()
        assertGarbageCollected(ref)
    }

}
