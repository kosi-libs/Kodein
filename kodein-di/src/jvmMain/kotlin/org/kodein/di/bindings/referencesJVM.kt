package org.kodein.di.bindings

import java.lang.ThreadLocal as jThreadLocal
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

/**
 * Thread Local Reference.
 *
 * A thread local singleton is guaranteed to be unique inside a thread.
 */
@Suppress("ClassName")
public class ThreadLocal<out T: Any>(creator: () -> T) : Reference<T> {
    private val threadLocal = object : jThreadLocal<T>() { override fun initialValue() = creator() }

    override fun get(): T = threadLocal.get()

    override val maker: Reference.Maker = Companion

    public companion object : Reference.Maker {
        override fun <T: Any> make(creator: () -> T): Reference.Local<T, ThreadLocal<T>> = ThreadLocal(creator).let { Reference.Local(it.get(), it) }
    }
}

/**
 * Soft Reference.
 *
 * A soft singleton is guaranteed to be unique inside the JVM but not during the application lifetime.
 * It **may** be GC'd if there are no strong references to it and therefore may be re-created later.
 */
@Suppress("ClassName", "unused") // There is no way to "cleanly" test soft references.
public class Soft<out T: Any>(value: T) : Reference<T> {
    private val ref = SoftReference(value)

    override fun get(): T? = ref.get()

    override val maker: Reference.Maker = Companion

    public companion object : Reference.Maker {
        override fun <T: Any> make(creator: () -> T): Reference.Local<T, Soft<T>> = Reference.Local(creator, ::Soft)
    }
}

/**
 * Weak Reference Maker.
 *
 * A weak singleton is guaranteed to be unique inside the JVM but not during the application lifetime.
 * It **will** be GC'd if there are no strong references to it and therefore may be re-created later.
 */
public actual class Weak<out T: Any> actual constructor(value: T) : Reference<T> {
    private val ref = WeakReference(value)

    override fun get(): T? = ref.get()

    override val maker: Reference.Maker = Companion

    public actual companion object : Reference.Maker {
        actual override fun <T: Any> make(creator: () -> T): Reference.Local<T, Weak<T>> = Reference.Local(creator, ::Weak)
    }
}
