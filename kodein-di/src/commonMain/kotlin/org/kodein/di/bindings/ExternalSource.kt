package org.kodein.di.bindings

import org.kodein.di.DI

/**
 * An external source can provide to an answer to a retrieval that do not correspond to any binding that is registered in DI.
 */
public interface ExternalSource {

    /**
     * This method is called when DI do not find a binding for a requested retrieval.
     *
     * @param di The DI object used for retrieval.
     * @param key Holds all information that are necessary to select an answer.
     * @return A factory method, which will be passed the argument of the retrieval, and must return the answer, or null if the external source itself cannot find an answer.
     */
    public fun getFactory(di: BindingDI<*>, key: DI.Key<*, *, *>): ((Any?) -> Any)?

    public companion object {
        /**
         * Util to create an external source from a simple lambda function.
         *
         * @param f The function that takes a Key and return a factory.
         */
        public inline operator fun invoke(crossinline f: BindingDI<*>.(DI.Key<*, *, *>) -> ((Any?) -> Any)?): ExternalSource = object : ExternalSource {
            override fun getFactory(di: BindingDI<*>, key: DI.Key<*, *, *>): ((Any?) -> Any)? = di.f(key)
        }
    }
}

/**
 * Simple function that types a lambda as a factory function that can be used as the return in the implementation of [ExternalSource.getFactory].
 */
public fun externalFactory(f: (Any?) -> Any): (Any?) -> Any = f
