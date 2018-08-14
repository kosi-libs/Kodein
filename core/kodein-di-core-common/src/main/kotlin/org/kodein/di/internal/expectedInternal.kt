package org.kodein.di.internal

internal expect fun <K, V> newConcurrentMap(): MutableMap<K, V>

internal expect fun <T> newLinkedList(): MutableList<T>

internal expect fun <T> newLinkedList(c: Collection<T>): MutableList<T>
