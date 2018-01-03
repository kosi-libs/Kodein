package org.kodein.internal

/** @suppress */
expect fun <K, V> newConcurrentMap(): MutableMap<K, V>

/** @suppress */
expect fun <T> newLinkedList(): MutableList<T>

/** @suppress */
expect fun <T> newLinkedList(c: Collection<T>): MutableList<T>

/** @suppress */
expect inline fun <T: Any, R> synchronizedIfNull(lock: Any, predicate: () -> T?, ifNotNull: (T) -> R, ifNull: () -> R): R

/** @suppress */
expect inline fun <T: Any, R> synchronizedIfNotNull(lock: Any, predicate: () -> T?, ifNull: () -> R, ifNotNull: (T) -> R): R
