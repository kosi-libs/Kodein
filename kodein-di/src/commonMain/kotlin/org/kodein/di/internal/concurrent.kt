package org.kodein.di.internal

public expect inline fun <R> maySynchronized(lock: Any?, block: () -> R): R

/** @suppress */
public inline fun <T: Any, R> synchronizedIfNull(lock: Any?, predicate: () -> T?, ifNotNull: (T) -> R, ifNull: () -> R): R {
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
internal inline fun <T: Any, R> synchronizedIfNotNull(lock: Any?, predicate: () -> T?, ifNull: () -> R, ifNotNull: (T) -> R): R {
    if (predicate() == null) {
        return ifNull()
    }

    maySynchronized(lock) {
        val value = predicate() ?: return@maySynchronized

        return ifNotNull(value)
    }

    return ifNull()
}
