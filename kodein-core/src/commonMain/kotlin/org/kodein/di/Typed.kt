package org.kodein.di

/**
 * Used as an argument for currying functions, allows to define a value and its type.
 */
interface Typed<A> {
    /**
     * The argument value.
     */
    val value: A

    /**
     * The argument type.
     */
    val type: TypeToken<A>

    companion object {
        /**
         * Creates a [Typed] instance for the given type and value
         */
        operator fun <A> invoke(type: TypeToken<A>, value: A): Typed<A> = TypedImpl(value, type)

        /**
         * Creates a [Typed] instance for the given type and value creator (which will be lazily called).
         */
        operator fun <A> invoke(type: TypeToken<A>, func: () -> A): Typed<A> = TypedFunc(func, type)
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
