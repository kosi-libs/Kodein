package org.kodein.internal

fun <K, V> newConcurrentMap(): MutableMap<K, V> = HashMap()
fun <T> newLinkedList(): MutableList<T> = ArrayList() // TODO: This should REALLY be a Linked list !
fun <T> newLinkedList(c: Collection<T>): MutableList<T> = ArrayList(c)
