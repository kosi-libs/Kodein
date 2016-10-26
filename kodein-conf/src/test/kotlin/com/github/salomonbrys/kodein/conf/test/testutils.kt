package com.github.salomonbrys.kodein.conf.test
inline fun <reified T : Throwable> assertThrown(body: () -> Unit) {
    try {
        body()
    }
    catch (t: Throwable) {
        if (t is T)
            return
        throw t
    }
    throw AssertionError("Expected ${T::class.java.name} to be thrown")
}
