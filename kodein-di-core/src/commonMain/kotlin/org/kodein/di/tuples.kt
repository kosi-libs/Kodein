package org.kodein.di

/**
 * Two arguments in one. Used for multi-argument bindings.
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @property a1 The first argument.
 * @property a2 The second argument.
 */
data class Multi2<A1, A2>(
        val a1: A1,
        val a2: A2,
        override val type: TypeToken<Multi2<A1, A2>>
) : Typed<Multi2<A1, A2>> {
    override val value: Multi2<A1, A2> get() = this

    companion object
}

/**
 * Three arguments in one. Used for multi-argument bindings.
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @property a1 The first argument.
 * @property a2 The second argument.
 * @property a3 The third argument.
 */
data class Multi3<A1, A2, A3>(
        val a1: A1,
        val a2: A2,
        val a3: A3,
        override val type: TypeToken<Multi3<A1, A2, A3>>
) : Typed<Multi3<A1, A2, A3>> {
    override val value: Multi3<A1, A2, A3> get() = this

    companion object
}

/**
 * Four arguments in one. Used for multi-argument bindings.
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param A4 The fourth argument type.
 * @property a1 The first argument.
 * @property a2 The second argument.
 * @property a3 The third argument.
 * @property a4 The fourth argument.
 */
data class Multi4<A1, A2, A3, A4>(
        val a1: A1,
        val a2: A2,
        val a3: A3,
        val a4: A4,
        override val type: TypeToken<Multi4<A1, A2, A3, A4>>
) : Typed<Multi4<A1, A2, A3, A4>> {
    override val value: Multi4<A1, A2, A3, A4> get() = this

    companion object
}

/**
 * Five arguments in one. Used for multi-argument bindings.
 *
 * If you're using this, maybe you shouldn't...
 * That's a bit to many arguments.
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param A4 The fourth argument type.
 * @param A5 The fifth argument type.
 * @property a1 The first argument.
 * @property a2 The second argument.
 * @property a3 The third argument.
 * @property a4 The fourth argument.
 * @property a5 The fifth argument.
 */
data class Multi5<A1, A2, A3, A4, A5>(
        val a1: A1,
        val a2: A2,
        val a3: A3,
        val a4: A4,
        val a5: A5,
        override val type: TypeToken<Multi5<A1, A2, A3, A4, A5>>
) : Typed<Multi5<A1, A2, A3, A4, A5>> {
    override val value: Multi5<A1, A2, A3, A4, A5> get() = this

    companion object
}
