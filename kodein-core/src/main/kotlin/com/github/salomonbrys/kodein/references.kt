package com.github.salomonbrys.kodein

import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

/**
 * A Function that creates a reference.
 */
// TODO: This should be a typealias in Kotlin 1.1
interface RefMaker {

    /**
     * A Function that creates a reference.
     *
     * @param T The type of the referenced object.
     * @param creator A function that can create a new T.
     * @return The referenced object and a function that returns the same object if the reference is still valid.
     */
    fun <T: Any> make(creator: () -> T): Pair<T, () -> T?>
}

/**
 * Thread Local Reference Maker.
 *
 * Use this with `refSingleton` or `refMultiton` to bind a thread local singleton or multiton.
 *
 * A thread local singleton is guaranteed to be unique inside a thread.
 */
object threadLocal : RefMaker {
    override fun <T: Any> make(creator: () -> T): Pair<T, () -> T?> {
        val threadLocal = object : ThreadLocal<T>() { override fun initialValue() = creator() }
        return threadLocal.get() to { threadLocal.get() }
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
object softReference : RefMaker {
    override fun <T: Any> make(creator: () -> T): Pair<T, () -> T?> {
        val value = creator()
        val softRef = SoftReference(value)
        return value to { softRef.get() }
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
object weakReference : RefMaker {
    override fun <T: Any> make(creator: () -> T): Pair<T, () -> T?> {
        val value = creator()
        val weakRef = WeakReference(value)
        return value to { weakRef.get() }
    }
}


/**
 * Concrete referenced singleton provider: will always return the instance managed by the given reference.
 *
 * @param T The type of the instance.
 * @property refMaker Reference Maker that defines the kind of reference being used.
 * @property creator A function that should always create a new object.
 */
class CRefSingleton<out T : Any>(override val createdType: Type, val refMaker: RefMaker, val creator: ProviderKodein.() -> T) : Provider<T> {

     // Should always return the same object or null.
    private var _ref: () -> T? = { null }

    private val _lock = Any()

    override fun factoryName() = "refSingleton(${refMaker.javaClass.simpleDispString})"
    override fun factoryFullName() = "refSingleton(${refMaker.javaClass.fullDispString})"

    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key): T {
        _ref.invoke()?.let { return it }
        synchronized(_lock) {
            _ref.invoke()?.let { return it }
            val pair = refMaker.make { kodein.creator() }
            _ref = pair.second
            return pair.first
        }
    }
}

/**
 * Creates a referenced singleton, will return always the same object as long as the reference is valid.
 *
 * T generics will be kept.
 *
 * @param T The singleton type.
 * @param refMaker The reference maker that will define the type of reference.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist or if the reference is not valid anymore.
 */
@Suppress("unused")
inline fun <reified T : Any> Kodein.Builder.genericRefSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T): Provider<T>
    = CRefSingleton(genericToken<T>().type, refMaker, creator)

/**
 * Creates a referenced singleton, will return always the same object as long as the reference is valid.
 *
 * T generics will be erased!
 *
 * @param T The singleton type.
 * @param refMaker The reference maker that will define the type of reference.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist or if the reference is not valid anymore.
 */
@Suppress("unused")
inline fun <reified T : Any> Kodein.Builder.erasedRefSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T): Provider<T>
    = CRefSingleton(T::class.java, refMaker, creator)


/**
 * Concrete referenced multiton factory: for the same argument, will always return the instance managed by the given reference.
 *
 * @param T The type of the instance.
 * @property argType The type of the argument, *used for debug print only*.
 * @property createdType The type of the created object, *used for debug print only*.
 * @property refMaker Reference Maker that defines the kind of reference being used.
 * @property creator A function that should always create a new object.
 */
class CRefMultiton<in A, out T: Any>(override val argType: Type, override val createdType: Type, val refMaker: RefMaker, val creator: FactoryKodein.(A) -> T): Factory<A, T> {

    private val _refs = ConcurrentHashMap<A, () -> T?>()

    override fun factoryName() = "refMultiton(${refMaker.javaClass.simpleDispString})"
    override fun factoryFullName() = "refMultiton(${refMaker.javaClass.fullDispString})"

    override fun getInstance(kodein: FactoryKodein, key: Kodein.Key, arg: A): T {
        _refs[arg]?.invoke()?.let { return it }
        synchronized(_refs) {
            _refs[arg]?.invoke()?.let { return it }
            val pair = refMaker.make { kodein.creator(arg) }
            _refs[arg] = pair.second
            return pair.first
        }
    }

}

/**
 * Creates a referenced multiton, for the same argument, will return always the same object as long as the reference is valid.
 *
 * A & T generics will be kept.
 *
 * @param T The multiton type.
 * @param refMaker The reference maker that will define the type of reference.
 * @param creator A function that creates the multiton object. For the same argument, will be called only if the multiton does not already exist or if the reference is not valid anymore.
 */
@Suppress("unused")
inline fun <reified A, reified T : Any> Kodein.Builder.genericRefMultiton(refMaker: RefMaker, noinline creator: FactoryKodein.(A) -> T): Factory<A, T>
    = CRefMultiton(genericToken<A>().type, genericToken<T>().type, refMaker, creator)

/**
 * Creates a referenced multiton, for the same argument, will return always the same object as long as the reference is valid.
 *
 * A & T generics will be erased!
 *
 * @param T The multiton type.
 * @param refMaker The reference maker that will define the type of reference.
 * @param creator A function that creates the multiton object. For the same argument, will be called only if the multiton does not already exist or if the reference is not valid anymore.
 */
@Suppress("unused")
inline fun <reified A, reified T : Any> Kodein.Builder.erasedRefMultiton(refMaker: RefMaker, noinline creator: FactoryKodein.(A) -> T): Factory<A, T>
    = CRefMultiton(typeClass<A>(), T::class.java, refMaker, creator)
