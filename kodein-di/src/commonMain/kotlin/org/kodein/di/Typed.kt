package org.kodein.di

import org.kodein.type.TypeToken

/**
 * Used as an argument for currying functions, allows to define a value and its type.
 */
public interface Typed<A> {
    /**
     * The argument value.
     */
    public val value: A

    /**
     * The argument type.
     */
    public val type: TypeToken<A>

    public companion object {
        /**
         * Creates a [Typed] instance for the given type and value
         */
        public operator fun <A> invoke(type: TypeToken<A>, value: A): Typed<A> = TypedImpl(value, type)

        /**
         * Creates a [Typed] instance for the given type and value creator (which will be lazily called).
         */
        public operator fun <A> invoke(type: TypeToken<A>, func: () -> A): Typed<A> = TypedFunc(func, type)
    }
}

private data class TypedImpl<A>(
        override val value: A,
        override val type: TypeToken<A>
) : Typed<A>

private class TypedFunc<A>(
        func: () -> A,
        override val type: TypeToken<A>
) : Typed<A> {
    override val value: A by lazy(func)
}
