package org.kodein.di.generic

import org.kodein.di.*
import org.kodein.di.bindings.*

/**
 * Creates a multi-argument factory.
 *
 * @see [Kodein.BindBuilder.WithContext.factory]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <C, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2            ) -> T) = Factory(contextType, generic<Multi2<A1, A2            >>(), generic()) { creator(it.a1, it.a2                     ) }

/**
 * Creates a multi-argument factory.
 *
 * All type arguments will be erased!
 *
 * @see [Kodein.BindBuilder.WithContext.factory]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <C, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3        ) -> T) = Factory(contextType, generic<Multi3<A1, A2, A3        >>(), generic()) { creator(it.a1, it.a2, it.a3              ) }

/**
 * Creates a multi-argument factory.
 *
 * All type arguments will be erased!
 *
 * @see [Kodein.BindBuilder.WithContext.factory]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param A4 The fourth argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <C, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3, A4    ) -> T) = Factory(contextType, generic<Multi4<A1, A2, A3, A4    >>(), generic()) { creator(it.a1, it.a2, it.a3, it.a4       ) }

/**
 * Creates a multi-argument factory.
 *
 * All type arguments will be erased!
 *
 * @see [Kodein.BindBuilder.WithContext.factory]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param A4 The fourth argument type.
 * @param A5 The fifth argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <C, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3, A4, A5) -> T) = Factory(contextType, generic<Multi5<A1, A2, A3, A4, A5>>(), generic()) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }



/**
 * Creates a multi-argument multiton.
 *
 * All type arguments will be erased!
 *
 * @see [Kodein.BindBuilder.WithScope.multiton]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <EC, BC, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2            ) -> T) = Multiton(scope, contextType, generic<Multi2<A1, A2            >>(), generic(), ref) { creator(it.a1, it.a2                     ) }

/**
 * Creates a multi-argument multiton.
 *
 * All type arguments will be erased!
 *
 * @see [Kodein.BindBuilder.WithScope.multiton]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <EC, BC, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3        ) -> T) = Multiton(scope, contextType, generic<Multi3<A1, A2, A3        >>(), generic(), ref) { creator(it.a1, it.a2, it.a3              ) }

/**
 * Creates a multi-argument multiton.
 *
 * All type arguments will be erased!
 *
 * @see [Kodein.BindBuilder.WithScope.multiton]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param A4 The fourth argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4    ) -> T) = Multiton(scope, contextType, generic<Multi4<A1, A2, A3, A4    >>(), generic(), ref) { creator(it.a1, it.a2, it.a3, it.a4       ) }

/**
 * Creates a multi-argument multiton.
 *
 * All type arguments will be erased!
 *
 * @see [Kodein.BindBuilder.WithScope.multiton]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param A4 The fourth argument type.
 * @param A5 The fifth argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithScope<EC, BC>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4, A5) -> T) = Multiton(scope, contextType, generic<Multi5<A1, A2, A3, A4, A5>>(), generic(), ref) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }



/**
 * Creates a `Multi2`.
 *
 * All type arguments will be erased!
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param a1 The first argument.
 * @param a2 The second argument.
 */
inline fun <reified A1, reified A2                                    > M(a1: A1, a2: A2                        ) = Multi2(a1, a2,             generic())

/**
 * Creates a `Multi3`.
 *
 * All type arguments will be erased!
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param a1 The first argument.
 * @param a2 The second argument.
 * @param a3 The third argument.
 */
inline fun <reified A1, reified A2, reified A3                        > M(a1: A1, a2: A2, a3: A3                ) = Multi3(a1, a2, a3,         generic())

/**
 * Creates a `Multi4`.
 *
 * All type arguments will be erased!
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param A4 The fourth argument type.
 * @param a1 The first argument.
 * @param a2 The second argument.
 * @param a3 The third argument.
 * @param a4 The fourth argument.
 */
inline fun <reified A1, reified A2, reified A3, reified A4            > M(a1: A1, a2: A2, a3: A3, a4: A4        ) = Multi4(a1, a2, a3, a4,     generic())

/**
 * Creates a `Multi5`.
 *
 * All type arguments will be erased!
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param A4 The fourth argument type.
 * @param A5 The fifth argument type.
 * @param a1 The first argument.
 * @param a2 The second argument.
 * @param a3 The third argument.
 * @param a4 The fourth argument.
 * @param a5 The fifth argument.
 */
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5> M(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) = Multi5(a1, a2, a3, a4, a5, generic())
