package com.github.salomonbrys.kodein

import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import java.lang.reflect.Type

interface RefMaker {
    fun <T: Any> make(creator: () -> T): Pair<T, () -> T?>
}

class CRefSingleton<T : Any>(createdType: Type, val refMaker: RefMaker, val creator: ProviderKodein.() -> T) : AProvider<T>("refSingleton(${refMaker.javaClass.simpleDispString})", createdType) {

    private var _ref: () -> T? = { null }

    override fun getInstance(kodein: ProviderKodein, key: Kodein.Key): T {
        var instance = _ref.invoke()
        if (instance == null) {
            synchronized(this) {
                instance = _ref.invoke()
                if (instance == null) {
                    val pair = refMaker.make { kodein.creator() }
                    instance = pair.first
                    _ref = pair.second
                }
            }
        }
        return instance!!
    }
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

@Suppress("unused")
inline fun <reified T : Any> Kodein.Builder.genericRefSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T): AProvider<T> = CRefSingleton(genericToken<T>().type, refMaker, creator)

@Suppress("unused")
inline fun <reified T : Any> Kodein.Builder.erasedRefSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T): AProvider<T> = CRefSingleton(T::class.java, refMaker, creator)
