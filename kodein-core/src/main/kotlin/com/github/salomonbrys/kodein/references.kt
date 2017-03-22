package com.github.salomonbrys.kodein

import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

interface RefMaker {
    fun <T: Any> make(creator: () -> T): Pair<T, () -> T?>
}

object threadLocal : RefMaker {
    override fun <T: Any> make(creator: () -> T): Pair<T, () -> T?> {
        val threadLocal = ThreadLocal.withInitial(creator)
        return threadLocal.get() to { threadLocal.get() }
    }
}

object softReference : RefMaker {
    override fun <T: Any> make(creator: () -> T): Pair<T, () -> T?> {
        val value = creator()
        val softRef = SoftReference(value)
        return value to { softRef.get() }
    }
}

object weakReference : RefMaker {
    override fun <T: Any> make(creator: () -> T): Pair<T, () -> T?> {
        val value = creator()
        val weakRef = WeakReference(value)
        return value to { weakRef.get() }
    }
}


class CRefSingleton<out T : Any>(createdType: Type, val refMaker: RefMaker, val creator: ProviderKodein.() -> T) : AProvider<T>("refSingleton(${refMaker.javaClass.simpleDispString})", createdType) {

    private var _ref: () -> T? = { null }

    private val _lock = Any()

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

@Suppress("unused")
inline fun <reified T : Any> Kodein.Builder.genericRefSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T): Provider<T>
    = CRefSingleton(genericToken<T>().type, refMaker, creator)

@Suppress("unused")
inline fun <reified T : Any> Kodein.Builder.erasedRefSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T): Provider<T>
    = CRefSingleton(T::class.java, refMaker, creator)


class CRefMultiton<in A, out T: Any>(argType: Type, createdType: Type, val refMaker: RefMaker, val creator: FactoryKodein.(A) -> T): AFactory<A, T>("refMultiton(${refMaker.javaClass.simpleDispString})", argType, createdType) {

    private val _refs = ConcurrentHashMap<A, () -> T?>()

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

@Suppress("unused")
inline fun <reified A, reified T : Any> Kodein.Builder.genericRefMultiton(refMaker: RefMaker, noinline creator: FactoryKodein.(A) -> T): Factory<A, T>
    = CRefMultiton(genericToken<A>().type, genericToken<T>().type, refMaker, creator)

@Suppress("unused")
inline fun <reified A, reified T : Any> Kodein.Builder.erasedRefMultiton(refMaker: RefMaker, noinline creator: FactoryKodein.(A) -> T): Factory<A, T>
    = CRefMultiton(typeClass<A>(), T::class.java, refMaker, creator)
