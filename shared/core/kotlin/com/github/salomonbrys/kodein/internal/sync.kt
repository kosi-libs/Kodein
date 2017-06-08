package com.github.salomonbrys.kodein.internal

/** @suppress */
inline fun <T> synchronizedIfNull(lock: Any, crossinline predicate: () -> T?, ifNotNull: (T) -> Unit, crossinline ifNull: () -> Unit) {
    predicate()?.let {
        ifNotNull(it)
        return
    }

    val value = synchronized(lock) {
        predicate()?.let { return@synchronized it }

        ifNull()
        null
    }

    if (value != null)
        ifNotNull(value)
}

/** @suppress */
inline fun <T> synchronizedIfNotNull(lock: Any, crossinline predicate: () -> T?, ifNull: () -> Unit, crossinline ifNotNull: (T) -> Unit) {
    if (predicate() == null) {
        ifNull()
        return
    }

    val wasNull = synchronized(lock) {
        val value = predicate() ?: return@synchronized true

        ifNotNull(value)
        false
    }

    if (wasNull)
        ifNull()
}
