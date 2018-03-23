package org.kodein.di

import org.kodein.di.bindings.RefMaker
import org.kodein.di.bindings.Reference
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

/**
 * Thread Local Reference Maker.
 *
 * Use this with `refSingleton` or `refMultiton` to bind a thread local singleton or multiton.
 *
 * A thread local singleton is guaranteed to be unique inside a thread.
 */
@Suppress("ClassName")
object threadLocal : RefMaker {
    override fun <T: Any> make(creator: () -> T): Reference<T> {
        val threadLocal = object : ThreadLocal<T>() { override fun initialValue() = creator() }
        return Reference(threadLocal.get()) { threadLocal.get() }
    }
}

/**
 * Soft Reference Maker.
 *
 * Use this with `refSingleton` or `refMultiton` to bind a soft singleton or multiton.
 *
 * A soft singleton is guaranteed to be unique inside the JVM but not during the application lifetime.
 * It **may** be GC'd if there are no strong references to it and therefore may be re-created later.
 */
@Suppress("ClassName", "unused") // There is no way to "cleanly" test soft references.
object softReference : RefMaker {
    override fun <T: Any> make(creator: () -> T): Reference<T> {
        val value = creator()
        val softRef = SoftReference(value)
        return Reference(value) { softRef.get() }
    }
}

/**
 * Weak Reference Maker.
 *
 * Use this with `refSingleton` or `refMultiton` to bind a weak singleton or multiton.
 *
 * A weak singleton is guaranteed to be unique inside the JVM but not during the application lifetime.
 * It **will** be GC'd if there are no strong references to it and therefore may be re-created later.
 */
@Suppress("ClassName")
object weakReference : RefMaker {
    override fun <T: Any> make(creator: () -> T): Reference<T> {
        val value = creator()
        val weakRef = WeakReference(value)
        return Reference(value) { weakRef.get() }
    }
}
