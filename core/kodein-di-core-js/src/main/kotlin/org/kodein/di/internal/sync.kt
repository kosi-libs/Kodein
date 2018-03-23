package org.kodein.di.internal

/** @suppress */
actual inline fun <T: Any, R> synchronizedIfNull(lock: Any, predicate: () -> T?, ifNotNull: (T) -> R, ifNull: () -> R): R {
    predicate()?.let { return ifNotNull(it) }

    return ifNull()
}

/** @suppress */
actual inline fun <T: Any, R> synchronizedIfNotNull(lock: Any, predicate: () -> T?, ifNull: () -> R, ifNotNull: (T) -> R): R {
    val value = predicate() ?: return ifNull()

    return ifNotNull(value)
}
