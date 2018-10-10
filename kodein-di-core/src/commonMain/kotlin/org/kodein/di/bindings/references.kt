package org.kodein.di.bindings

/**
 * A reference gives a data and a function to later check the validity and retrieve that data.
 *
 *  @param current The value of the reference at the time of reference creation.
 *  @param next A function that returns the value of the reference, or null if the reference has become invalid, when later needed.
 */
data class Reference<out T>(
    val current: T,
    val next: () -> T?
)

/**
 * A Function that creates a reference.
 */
interface RefMaker {

    /**
     * A Function that creates a reference.
     *
     * @param T The type of the referenced object.
     * @param creator A function that can create a new T.
     * @return The referenced object and a function that returns the same object if the reference is still valid.
     */
    fun <T: Any> make(creator: () -> T): Reference<T>
}

/**
 * A reference that acts as a Singleton: calls a creator function the first time, and then always return the same instance.
 */
object SingletonReference : RefMaker {
    override fun <T : Any> make(creator: () -> T): Reference<T> {
        val value = creator()
        return Reference(value) { value }
    }
}
