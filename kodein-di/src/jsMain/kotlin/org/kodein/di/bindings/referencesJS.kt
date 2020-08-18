package org.kodein.di.bindings


private external interface JsRef<T: Any> {
    fun deref(): T?
}

private external class WeakRef<T: Any>(value: T): JsRef<T> {
    override fun deref(): T?
}

private class StrongRef<T: Any>(val value: T): JsRef<T> {
    override fun deref(): T? = value
}

private val isWeakRefSupported by lazy {
    val isSupported = js("typeof WeakRef == \"function\"") as Boolean
    if (!isSupported) {
        console.warn("Downgrading Reference from Weak to Strong because JS WeakRefs are not supported by this interpreter!")
    }
    isSupported
}

public actual class Weak<out T: Any> actual constructor(value: T) : Reference<T> {
    private val ref: JsRef<T> = if (isWeakRefSupported) WeakRef(value) else StrongRef(value)

    override fun get(): T? = ref.deref()

    override val maker: Reference.Maker = Companion

    public actual companion object : Reference.Maker {
        actual override fun <T: Any> make(creator: () -> T): Reference.Local<T, Weak<T>> = Reference.Local(creator, ::Weak)
    }
}
