package com.github.salomonbrys.kodein

import java.util.concurrent.ConcurrentHashMap

internal fun <K, V> newConcurrentMap() = ConcurrentHashMap<K, V>()
