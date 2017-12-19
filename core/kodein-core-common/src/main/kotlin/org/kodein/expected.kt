package org.kodein

expect fun <T: Any> TTOf(obj: T): TypeToken<out T>

expect inline fun <reified T> erased(): TypeToken<T>
