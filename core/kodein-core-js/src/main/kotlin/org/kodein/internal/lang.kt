package org.kodein.internal

actual fun <K, V> newConcurrentMap(): MutableMap<K, V> = HashMap()
actual fun <T> newLinkedList(): MutableList<T> = ArrayList()
actual fun <T> newLinkedList(c: Collection<T>): MutableList<T> = ArrayList(c)
