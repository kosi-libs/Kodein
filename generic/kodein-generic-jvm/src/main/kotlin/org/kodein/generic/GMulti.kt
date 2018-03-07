package org.kodein.generic

import org.kodein.*
import org.kodein.bindings.*

inline fun <C, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2            ) -> T) = Factory(contextType, generic<Multi2<A1, A2            >>(), generic()) { creator(it.a1, it.a2                     ) }
inline fun <C, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3        ) -> T) = Factory(contextType, generic<Multi3<A1, A2, A3        >>(), generic()) { creator(it.a1, it.a2, it.a3              ) }
inline fun <C, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3, A4    ) -> T) = Factory(contextType, generic<Multi4<A1, A2, A3, A4    >>(), generic()) { creator(it.a1, it.a2, it.a3, it.a4       ) }
inline fun <C, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3, A4, A5) -> T) = Factory(contextType, generic<Multi5<A1, A2, A3, A4, A5>>(), generic()) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }


inline fun <EC, BC, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2            ) -> T) = Multiton(scope, contextType, generic<Multi2<A1, A2            >>(), generic(), ref) { creator(it.a1, it.a2                     ) }
inline fun <EC, BC, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3        ) -> T) = Multiton(scope, contextType, generic<Multi3<A1, A2, A3        >>(), generic(), ref) { creator(it.a1, it.a2, it.a3              ) }
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4    ) -> T) = Multiton(scope, contextType, generic<Multi4<A1, A2, A3, A4    >>(), generic(), ref) { creator(it.a1, it.a2, it.a3, it.a4       ) }
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4, A5) -> T) = Multiton(scope, contextType, generic<Multi5<A1, A2, A3, A4, A5>>(), generic(), ref) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }


inline fun <reified A1, reified A2                                    > M(a1: A1, a2: A2                        ) = Multi2(a1, a2,             generic())
inline fun <reified A1, reified A2, reified A3                        > M(a1: A1, a2: A2, a3: A3                ) = Multi3(a1, a2, a3,         generic())
inline fun <reified A1, reified A2, reified A3, reified A4            > M(a1: A1, a2: A2, a3: A3, a4: A4        ) = Multi4(a1, a2, a3, a4,     generic())
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5> M(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) = Multi5(a1, a2, a3, a4, a5, generic())
