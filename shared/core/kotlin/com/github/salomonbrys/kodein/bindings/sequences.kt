@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package com.github.salomonbrys.kodein.bindings

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.TypeToken
import kotlin.coroutines.experimental.*

/**
 * Allows a coroutine to generate a sequence AND access transitive dependencies.
 *
 * @param T The type of instance of the bind.
 */
interface SequenceBindingKodein<T: Any> : NoArgBindingKodein {
    /**
     * Yields this value as the next instance.
     *
     * @param value The next instance for this bind.
     */
    suspend fun yield(value: T)

    /**
     * Yields these values as the next instances.
     *
     * @param iterator The next instances for this bind.
     */
    suspend fun yieldAll(iterator: Iterator<T>)

    /**
     * Yields these values as the next instances.
     *
     * @param elements The next instances for this bind.
     */
    suspend fun yieldAll(elements: Iterable<T>)

    /**
     * Yields these values as the next instances.
     *
     * @param sequence The next instances for this bind.
     */
    suspend fun yieldAll(sequence: Sequence<T>)
}


class SequenceBinding<T: Any> private constructor (override val createdType: TypeToken<T>) : NoArgBinding<T>, SequenceBindingKodein<T>, kotlin.coroutines.experimental.Continuation<Unit> {

    override fun factoryName() = "sequence"

    private val _lock = Any()

    private var _cont: kotlin.coroutines.experimental.Continuation<Unit>? = null

    private var _value: T? = null

    private var _iterator: Iterator<T>? = null

    private var _kodeinDelegate: NoArgBindingKodein? = null

    override fun getInstance(kodein: NoArgBindingKodein, key: Kodein.Key<Unit, T>): T {
        return synchronized(_lock) {
            _kodeinDelegate = kodein
            try {
                _iterator?.let { next ->
                    if (!next.hasNext())
                        _iterator = null
                    else
                        next.next().let {
                            _value = it
                            return@synchronized it
                        }
                }
                _cont?.resume(Unit)
                _value ?: throw IllegalStateException()
            }
            finally {
                _kodeinDelegate = null
            }
        }
    }

    override val context: kotlin.coroutines.experimental.CoroutineContext = kotlin.coroutines.experimental.EmptyCoroutineContext

    override fun resume(value: Unit) {
        _cont = null
    }

    override fun resumeWithException(exception: Throwable) {
        throw exception
    }

    override suspend fun yield(value: T) {
        _value = value
        return kotlin.coroutines.experimental.suspendCoroutine { _cont = it }
    }

    override suspend fun yieldAll(iterator: Iterator<T>) {
        if (!iterator.hasNext())
            return
        _value = iterator.next()
        _iterator = iterator
        return kotlin.coroutines.experimental.suspendCoroutine { _cont = it }
    }

    override suspend fun yieldAll(elements: Iterable<T>) {
        if (elements is Collection && elements.isEmpty()) return
        return yieldAll(elements.iterator())
    }

    override suspend fun yieldAll(sequence: Sequence<T>) = yieldAll(sequence.iterator())

    private val _kodein get() = _kodeinDelegate ?: throw IllegalStateException()
    override val container get() = _kodein.container
    override fun overriddenProvider() = _kodein.overriddenProvider()
    override fun overriddenProviderOrNull() = _kodein.overriddenProviderOrNull()
    override fun overriddenInstance() = _kodein.overriddenInstance()
    override fun overriddenInstanceOrNull() = _kodein.overriddenInstanceOrNull()

    companion object {
        operator fun <T: Any> invoke(createdType: TypeToken<T>, creator: suspend SequenceBindingKodein<T>.() -> Unit): SequenceBinding<T> {
            val binding = SequenceBinding(createdType)
            binding._cont = creator.createCoroutine(receiver = binding, completion = binding)
            return binding
        }
    }
}
