package org.kodein.internal

import java.util.*
import java.util.concurrent.ConcurrentHashMap

actual fun <K, V> newConcurrentMap(): MutableMap<K, V> = ConcurrentHashMap()

actual fun <T> newLinkedList(): MutableList<T> = LinkedList()
actual fun <T> newLinkedList(c: Collection<T>): MutableList<T> = LinkedList(c)
