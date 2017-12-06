package org.kodein.bindings

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
    fun <T: Any> make(creator: () -> T): Pair<T, () -> T?>
}

object SingletonReference : RefMaker {
    override fun <T : Any> make(creator: () -> T): Pair<T, () -> T?> {
        val value = creator()
        return value to { value }
    }
}
