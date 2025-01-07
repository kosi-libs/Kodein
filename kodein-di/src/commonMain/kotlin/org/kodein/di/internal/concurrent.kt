package org.kodein.di.internal

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized

/**
 * Using kotlinx-atomicfu may cause some performance issues on native platforms.
 * @see https://youtrack.jetbrains.com/issue/CMP-1182 and https://youtrack.jetbrains.com/issue/KT-70751
 */
public inline fun <R> maySynchronized(lock: SynchronizedObject?, block: () -> R): R =
    if (lock == null) {
        block()
    } else {
        synchronized(lock, block)
    }

/** @suppress */
public inline fun <T: Any, R> synchronizedIfNull(lock: SynchronizedObject?, predicate: () -> T?, ifNotNull: (T) -> R, ifNull: () -> R): R {
    predicate()?.let {
        return ifNotNull(it)
    }

    val value = maySynchronized(lock) {
        predicate()?.let { return@maySynchronized it }

        return ifNull()
    }

    return ifNotNull(value)
}

/** @suppress */
internal inline fun <T: Any, R> synchronizedIfNotNull(lock: SynchronizedObject?, predicate: () -> T?, ifNull: () -> R, ifNotNull: (T) -> R): R {
    if (predicate() == null) {
        return ifNull()
    }

    maySynchronized(lock) {
        val value = predicate() ?: return@maySynchronized

        return ifNotNull(value)
    }

    return ifNull()
}
