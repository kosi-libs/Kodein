package org.kodein.bindings

import org.kodein.Kodein
import org.kodein.TTOf
import org.kodein.TypeToken
import org.kodein.internal.synchronizedIfNull
import org.kodein.newConcurrentMap

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

/**
 * Concrete referenced singleton provider: will always return the instance managed by the given reference.
 *
 * @param T The type of the instance.
 * @property refMaker Reference Maker that defines the kind of reference being used.
 * @property creator A function that should always create a new object.
 */
class RefSingletonBinding<T : Any>(override val createdType: TypeToken<out T>, val refMaker: RefMaker, val creator: NoArgSimpleBindingKodein.() -> T) : NoArgKodeinBinding<T> {

     // Should always return the same object or null.
    private @Volatile var _ref: () -> T? = { null }
    private val _lock = Any()

    override fun factoryName() = "refSingleton(${TTOf(refMaker).simpleDispString ()})"
    override fun factoryFullName() = "refSingleton(${TTOf(refMaker).fullDispString()})"

    override fun getProvider(kodein: NoArgBindingKodein, bind: Kodein.Bind<T>): () -> T {
        return {
            synchronizedIfNull(
                    lock = _lock,
                    predicate = { _ref() },
                    ifNotNull = { it },
                    ifNull = {
                        val (value, later) = refMaker.make {
                            creator(kodein)
                        }
                        _ref = later
                        value
                    }
            )
        }
    }
}

/**
 * Concrete referenced multiton factory: for the same argument, will always return the instance managed by the given reference.
 *
 * @param T The type of the instance.
 * @property argType The type of the argument, *used for debug print only*.
 * @property createdType The type of the created object, *used for debug print only*.
 * @property refMaker Reference Maker that defines the kind of reference being used.
 * @property creator A function that should always create a new object.
 */
class RefMultitonBinding<A, T: Any>(override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, val refMaker: RefMaker, val creator: SimpleBindingKodein.(A) -> T): KodeinBinding<A, T> {

    private val _refs = newConcurrentMap<A, () -> T?>()
    private val _lock = Any()

    override fun factoryName() = "refMultiton(${TTOf(refMaker).simpleDispString()})"
    override fun factoryFullName() = "refMultiton(${TTOf(refMaker).fullDispString()})"

    override fun getFactory(kodein: BindingKodein, key: Kodein.Key<A, T>): (A) -> T {
        return { arg ->
            synchronizedIfNull(
                    lock = _lock,
                    predicate = { _refs[arg]?.invoke() },
                    ifNotNull = { it },
                    ifNull = {
                        val (value, later) = refMaker.make { kodein.creator(arg) }
                        _refs[arg] = later
                        value
                    }
            )
        }
    }
}
