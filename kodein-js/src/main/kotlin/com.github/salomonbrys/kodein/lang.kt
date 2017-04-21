package com.github.salomonbrys.kodein

import com.github.salomonbrys.kodein.bindings.MultitonBinding

internal fun <K, V> newConcurrentMap() = HashMap<K, V>()
