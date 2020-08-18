package org.kodein.di.bindings

import kotlin.native.ref.WeakReference


public actual class Weak<out T: Any> actual constructor(value: T) : Reference<T> {
    private val ref = WeakReference(value)

    override fun get(): T? = ref.get()

    override val maker: Reference.Maker = Companion

    public actual companion object : Reference.Maker {
        actual override fun <T: Any> make(creator: () -> T): Reference.Local<T, Weak<T>> = Reference.Local(creator, ::Weak)
    }
}
