package org.kodein

data class Multi2<A1, A2>(
        val a1: A1,
        val a2: A2,
        override val type: TypeToken<Multi2<A1, A2>>
) : Typed<Multi2<A1, A2>> {
    override val value: Multi2<A1, A2> get() = this
}

data class Multi3<A1, A2, A3>(
        val a1: A1,
        val a2: A2,
        val a3: A3,
        override val type: TypeToken<Multi3<A1, A2, A3>>
) : Typed<Multi3<A1, A2, A3>> {
    override val value: Multi3<A1, A2, A3> get() = this
}

data class Multi4<A1, A2, A3, A4>(
        val a1: A1,
        val a2: A2,
        val a3: A3,
        val a4: A4,
        override val type: TypeToken<Multi4<A1, A2, A3, A4>>
) : Typed<Multi4<A1, A2, A3, A4>> {
    override val value: Multi4<A1, A2, A3, A4> get() = this
}

data class Multi5<A1, A2, A3, A4, A5>(
        val a1: A1,
        val a2: A2,
        val a3: A3,
        val a4: A4,
        val a5: A5,
        override val type: TypeToken<Multi5<A1, A2, A3, A4, A5>>
) : Typed<Multi5<A1, A2, A3, A4, A5>> {
    override val value: Multi5<A1, A2, A3, A4, A5> get() = this
}
