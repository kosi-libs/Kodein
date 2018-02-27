package org.kodein

interface Typed<A> {
    val value: A
    val type: TypeToken<A>

    companion object {
        operator fun <A> invoke(type: TypeToken<A>, value: A): Typed<A> = TypedImpl(value, type)
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
