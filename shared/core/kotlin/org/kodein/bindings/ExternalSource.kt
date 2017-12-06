package org.kodein.bindings

import org.kodein.Kodein

interface ExternalSource {

    fun getFactory(kodein: BindingKodein, key: Kodein.Key<*, *, *>): ((Any?) -> Any)?

    companion object {
        inline operator fun invoke(crossinline f: BindingKodein.(Kodein.Key<*, *, *>) -> ((Any?) -> Any)?) = object : ExternalSource {
            override fun getFactory(kodein: BindingKodein, key: Kodein.Key<*, *, *>): ((Any?) -> Any)? = kodein.f(key)
        }
    }
}

fun externalFactory(f: (Any?) -> Any) = f