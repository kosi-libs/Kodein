package org.kodein.erased

import org.kodein.*
import org.kodein.bindings.*

inline fun <C, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2            ) -> T) = Factory(contextType, erasedComp2<Multi2<A1, A2            >, A1, A2            >(), erased()) { creator(it.a1, it.a2                     ) }
inline fun <C, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3        ) -> T) = Factory(contextType, erasedComp3<Multi3<A1, A2, A3        >, A1, A2, A3        >(), erased()) { creator(it.a1, it.a2, it.a3              ) }
inline fun <C, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3, A4    ) -> T) = Factory(contextType, erasedComp4<Multi4<A1, A2, A3, A4    >, A1, A2, A3, A4    >(), erased()) { creator(it.a1, it.a2, it.a3, it.a4       ) }
inline fun <C, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3, A4, A5) -> T) = Factory(contextType, erasedComp5<Multi5<A1, A2, A3, A4, A5>, A1, A2, A3, A4, A5>(), erased()) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }


inline fun <EC, BC, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2            ) -> T) = Multiton(scope, contextType, erasedComp2<Multi2<A1, A2            >, A1, A2            >(), erased(), ref) { creator(it.a1, it.a2                     ) }
inline fun <EC, BC, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3        ) -> T) = Multiton(scope, contextType, erasedComp3<Multi3<A1, A2, A3        >, A1, A2, A3        >(), erased(), ref) { creator(it.a1, it.a2, it.a3              ) }
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4    ) -> T) = Multiton(scope, contextType, erasedComp4<Multi4<A1, A2, A3, A4    >, A1, A2, A3, A4    >(), erased(), ref) { creator(it.a1, it.a2, it.a3, it.a4       ) }
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4, A5) -> T) = Multiton(scope, contextType, erasedComp5<Multi5<A1, A2, A3, A4, A5>, A1, A2, A3, A4, A5>(), erased(), ref) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }


inline fun <reified A1, reified A2                                    > M(a1: A1, a2: A2                        ) = Multi2(a1, a2,             erasedComp2<Multi2<A1, A2            >, A1, A2            >())
inline fun <reified A1, reified A2, reified A3                        > M(a1: A1, a2: A2, a3: A3                ) = Multi3(a1, a2, a3,         erasedComp3<Multi3<A1, A2, A3        >, A1, A2, A3        >())
inline fun <reified A1, reified A2, reified A3, reified A4            > M(a1: A1, a2: A2, a3: A3, a4: A4        ) = Multi4(a1, a2, a3, a4,     erasedComp4<Multi4<A1, A2, A3, A4    >, A1, A2, A3, A4    >())
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5> M(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) = Multi5(a1, a2, a3, a4, a5, erasedComp5<Multi5<A1, A2, A3, A4, A5>, A1, A2, A3, A4, A5>())

