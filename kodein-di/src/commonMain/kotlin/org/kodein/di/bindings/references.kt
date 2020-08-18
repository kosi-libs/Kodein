package org.kodein.di.bindings

/**
 * A reference gives a data and a function to later check the validity and retrieve that data.
 *
 *  @param current The value of the reference at the time of reference creation.
 *  @param next A function that returns the value of the reference, or null if the reference has become invalid, when later needed.
 */
public interface Reference<out T: Any> {
    public fun get(): T?

    public val maker: Maker

    public data class Local<out T: Any, out R: Reference<T>>(val value: T, val reference: R) {
        public companion object {
            internal operator fun <T: Any, R: Reference<T>> invoke(creator: () -> T, refConstructor: (T) -> R): Local<T, R> = creator().let { Local(it, refConstructor(it)) }
        }
    }

    public interface Maker {
        public fun <T: Any> make(creator: () -> T): Local<T, Reference<T>>
    }
}

public class Strong<out T: Any>(private val value: T) : Reference<T> {
    override fun get(): T = value

    override val maker: Reference.Maker = Companion

    public companion object : Reference.Maker {
        override fun <T: Any> make(creator: () -> T): Reference.Local<T, Strong<T>> = Reference.Local(creator, ::Strong)
    }
}

public class Clearable<out T: Any>(value: T) : Reference<T> {
    private var value: T? = value

    override fun get(): T? = value

    public fun clear() { value = null }

    override val maker: Reference.Maker = Companion

    public companion object : Reference.Maker {
        override fun <T: Any> make(creator: () -> T): Reference.Local<T, Clearable<T>> = Reference.Local(creator, ::Clearable)
    }
}

public expect class Weak<out T: Any>(value: T) : Reference<T> {
    public companion object : Reference.Maker {
        override fun <T: Any> make(creator: () -> T): Reference.Local<T, Weak<T>>
    }
}
