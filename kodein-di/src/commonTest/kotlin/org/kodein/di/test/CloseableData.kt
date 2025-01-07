package org.kodein.di.test

class CloseableData(val name: String? = null) : AutoCloseable {
    var closed = false
        private set

    override fun close() {
        closed = true
    }
}

