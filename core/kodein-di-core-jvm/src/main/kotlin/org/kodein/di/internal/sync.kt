package org.kodein.di.internal

/** @suppress */
actual inline fun <T: Any, R> synchronizedIfNull(lock: Any, predicate: () -> T?, ifNotNull: (T) -> R, ifNull: () -> R): R {
    predicate()?.let {
        return ifNotNull(it)
    }

    val value = synchronized(lock) {
        predicate()?.let { return@synchronized it }

        return ifNull()
    }

    return ifNotNull(value)
}

/** @suppress */
actual inline fun <T: Any, R> synchronizedIfNotNull(lock: Any, predicate: () -> T?, ifNull: () -> R, ifNotNull: (T) -> R): R {
    if (predicate() == null) {
        return ifNull()
    }

    synchronized(lock) {
        val value = predicate() ?: return@synchronized

        return ifNotNull(value)
    }

    return ifNull()
}
