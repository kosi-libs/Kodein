package org.kodein.di.bindings

import org.kodein.di.Kodein

/**
 * An external source can provide to an answer to a retrieval that do not correspond to any binding that is registered in Kodein.
 */
interface ExternalSource {

    /**
     * This method is called when Kodein do not find a binding for a requested retrieval.
     *
     * @param kodein The kodein object used for retrieval.
     * @param key Holds all information that are necessary to select an answer.
     * @return A factory method, which will be passed the argument of the retrieval, and must return the answer, or null if the external source itself cannot find an answer.
     */
    fun getFactory(kodein: BindingKodein<*>, key: Kodein.Key<*, *, *>): ((Any?) -> Any)?

    companion object {
        /**
         * Util to create an external source from a simple lambda function.
         *
         * @param f The function that takes a Key and return a factory.
         */
        inline operator fun invoke(crossinline f: BindingKodein<*>.(Kodein.Key<*, *, *>) -> ((Any?) -> Any)?) = object : ExternalSource {
            override fun getFactory(kodein: BindingKodein<*>, key: Kodein.Key<*, *, *>): ((Any?) -> Any)? = kodein.f(key)
        }
    }
}

/**
 * Simple function that types a lambda as a factory function that can be used as the return in the implementation of [ExternalSource.getFactory].
 */
fun externalFactory(f: (Any?) -> Any) = f
