package org.kodein.di.internal

import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal actual fun <K, V> newConcurrentMap(): MutableMap<K, V> = ConcurrentHashMap()
internal actual fun <T> newLinkedList(): MutableList<T> = LinkedList()
internal actual fun <T> newLinkedList(c: Collection<T>): MutableList<T> = LinkedList(c)

public actual inline fun <R> maySynchronized(lock: Any?, block: () -> R): R =
        if (lock == null)
            block()
        else
            synchronized(lock, block)
