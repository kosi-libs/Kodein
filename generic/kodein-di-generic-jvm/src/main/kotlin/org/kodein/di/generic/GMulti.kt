package org.kodein.di.generic

import org.kodein.di.*
import org.kodein.di.bindings.*

/**
 * Creates a multi-argument factory.
 *
 * A1, A2 & T generics will be preserved!
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
 * A1, A2, A3 & T generics will be preserved!
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
 * A1, A2, A3, A4 & T generics will be preserved!
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
 * A1, A2, A3, A4, A5 & T generics will be preserved!
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
 * A1, A2 & T generics will be preserved!
 *
 * @see [Kodein.BindBuilder.WithScope.multiton]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <EC, BC, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithScope<EC, BC, Multi2<A1, A2            >>.multiton(ref: RefMaker? = null, sync: Boolean = true, noinline creator: SimpleBindingKodein<BC>.(A1, A2            ) -> T) = Multiton(scope, contextType, generic<Multi2<A1, A2            >>(), generic(), ref, sync) { creator(it.a1, it.a2                     ) }

/**
 * Creates a multi-argument multiton.
 *
 * A1, A2, A3 & T generics will be preserved!
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
inline fun <EC, BC, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithScope<EC, BC, Multi3<A1, A2, A3        >>.multiton(ref: RefMaker? = null, sync: Boolean = true, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3        ) -> T) = Multiton(scope, contextType, generic<Multi3<A1, A2, A3        >>(), generic(), ref, sync) { creator(it.a1, it.a2, it.a3              ) }

/**
 * Creates a multi-argument multiton.
 *
 * A1, A2, A3, A4 & T generics will be preserved!
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
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithScope<EC, BC, Multi4<A1, A2, A3, A4    >>.multiton(ref: RefMaker? = null, sync: Boolean = true, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4    ) -> T) = Multiton(scope, contextType, generic<Multi4<A1, A2, A3, A4    >>(), generic(), ref, sync) { creator(it.a1, it.a2, it.a3, it.a4       ) }

/**
 * Creates a multi-argument multiton.
 *
 * A1, A2, A3, A4, A5 & T generics will be preserved!
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
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithScope<EC, BC, Multi5<A1, A2, A3, A4, A5>>.multiton(ref: RefMaker? = null, sync: Boolean = true, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4, A5) -> T) = Multiton(scope, contextType, generic<Multi5<A1, A2, A3, A4, A5>>(), generic(), ref, sync) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }



/**
 * Creates a `Multi2`.
 *
 * A1 & A2 generics will be preserved!
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param a1 The first argument.
 * @param a2 The second argument.
 */
@Suppress("FunctionName")
inline fun <reified A1, reified A2                                    > M(a1: A1, a2: A2                        ) = Multi2(a1, a2,             generic())

/**
 * Creates a `Multi3`.
 *
 * A1, A2, & A3 generics will be preserved!
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param a1 The first argument.
 * @param a2 The second argument.
 * @param a3 The third argument.
 */
@Suppress("FunctionName")
inline fun <reified A1, reified A2, reified A3                        > M(a1: A1, a2: A2, a3: A3                ) = Multi3(a1, a2, a3,         generic())

/**
 * Creates a `Multi4`.
 *
 * A1, A2, A3 & A4 generics will be preserved!
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
@Suppress("FunctionName")
inline fun <reified A1, reified A2, reified A3, reified A4            > M(a1: A1, a2: A2, a3: A3, a4: A4        ) = Multi4(a1, a2, a3, a4,     generic())

/**
 * Creates a `Multi5`.
 *
 * A1, A2, A3, A4 & A5 generics will be preserved!
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
@Suppress("FunctionName")
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5> M(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) = Multi5(a1, a2, a3, a4, a5, generic())


/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2,                                     reified T: Any> KodeinAware.factory2(tag: Any? = null) =
        KodeinPropertyMap(Factory<Multi2<A1, A2            >, T>(generic(), generic(), tag)) { { a1: A1, a2: A2                         -> it(M(a1, a2            )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3,                         reified T: Any> KodeinAware.factory3(tag: Any? = null) =
        KodeinPropertyMap(Factory<Multi3<A1, A2, A3        >, T>(generic(), generic(), tag)) { { a1: A1, a2: A2, a3: A3                 -> it(M(a1, a2, a3        )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3, A4 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param A4 The type of the fourth argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3, reified A4,             reified T: Any> KodeinAware.factory4(tag: Any? = null) =
        KodeinPropertyMap(Factory<Multi4<A1, A2, A3, A4    >, T>(generic(), generic(), tag)) { { a1: A1, a2: A2, a3: A3, a4: A4         -> it(M(a1, a2, a3, a4    )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3, A4, A5 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param A4 The type of the fourth argument the factory takes.
 * @param A5 The type of the fifth argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> KodeinAware.factory5(tag: Any? = null) =
        KodeinPropertyMap(Factory<Multi5<A1, A2, A3, A4, A5>, T>(generic(), generic(), tag)) { { a1: A1, a2: A2, a3: A3, a4: A4, a5: A5 -> it(M(a1, a2, a3, a4, a5)) } }


/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2,                                     reified T: Any> KodeinAware.factory2OrNull(tag: Any? = null) =
        KodeinPropertyMap(FactoryOrNull<Multi2<A1, A2            >, T>(generic(), generic(), tag)) { factory -> factory?.let { { a1: A1, a2: A2                         -> it(M(a1, a2            )) } } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3,                         reified T: Any> KodeinAware.factory3OrNull(tag: Any? = null) =
        KodeinPropertyMap(FactoryOrNull<Multi3<A1, A2, A3        >, T>(generic(), generic(), tag)) { factory -> factory?.let { { a1: A1, a2: A2, a3: A3                 -> it(M(a1, a2, a3        )) } } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3, A4 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param A4 The type of the fourth argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3, reified A4,             reified T: Any> KodeinAware.factory4OrNull(tag: Any? = null) =
        KodeinPropertyMap(FactoryOrNull<Multi4<A1, A2, A3, A4    >, T>(generic(), generic(), tag)) { factory -> factory?.let { { a1: A1, a2: A2, a3: A3, a4: A4         -> it(M(a1, a2, a3, a4    )) } } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3, A4, A5 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param A4 The type of the fourth argument the factory takes.
 * @param A5 The type of the fifth argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> KodeinAware.factory5OrNull(tag: Any? = null) =
        KodeinPropertyMap(FactoryOrNull<Multi5<A1, A2, A3, A4, A5>, T>(generic(), generic(), tag)) { factory -> factory?.let { { a1: A1, a2: A2, a3: A3, a4: A4, a5: A5 -> it(M(a1, a2, a3, a4, a5)) } } }


/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2,                                     reified T: Any> DKodein.factory2(tag: Any? = null) =
    Factory<Multi2<A1, A2            >, T>(generic(), generic(), tag).let { { a1: A1, a2: A2                         -> it(M(a1, a2            )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3,                         reified T: Any> DKodein.factory3(tag: Any? = null) =
    Factory<Multi3<A1, A2, A3        >, T>(generic(), generic(), tag).let { { a1: A1, a2: A2, a3: A3                 -> it(M(a1, a2, a3        )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3, A4 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param A4 The type of the fourth argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3, reified A4,             reified T: Any> DKodein.factory4(tag: Any? = null) =
    Factory<Multi4<A1, A2, A3, A4    >, T>(generic(), generic(), tag).let { { a1: A1, a2: A2, a3: A3, a4: A4         -> it(M(a1, a2, a3, a4    )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3, A4, A5 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param A4 The type of the fourth argument the factory takes.
 * @param A5 The type of the fifth argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> DKodein.factory5(tag: Any? = null) =
    Factory<Multi5<A1, A2, A3, A4, A5>, T>(generic(), generic(), tag).let { { a1: A1, a2: A2, a3: A3, a4: A4, a5: A5 -> it(M(a1, a2, a3, a4, a5)) } }


/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2,                                     reified T: Any> DKodein.factory2OrNull(tag: Any? = null) =
        FactoryOrNull<Multi2<A1, A2            >, T>(generic(), generic(), tag).let { factory -> factory?.let { { a1: A1, a2: A2                         -> it(M(a1, a2            )) } } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3,                         reified T: Any> DKodein.factory3OrNull(tag: Any? = null) =
        FactoryOrNull<Multi3<A1, A2, A3        >, T>(generic(), generic(), tag).let { factory -> factory?.let { { a1: A1, a2: A2, a3: A3                 -> it(M(a1, a2, a3        )) } } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3, A4 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param A4 The type of the fourth argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3, reified A4,             reified T: Any> DKodein.factory4OrNull(tag: Any? = null) =
        FactoryOrNull<Multi4<A1, A2, A3, A4    >, T>(generic(), generic(), tag).let { factory -> factory?.let { { a1: A1, a2: A2, a3: A3, a4: A4         -> it(M(a1, a2, a3, a4    )) } } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3, A4, A5 & T generics will be preserved!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param A3 The type of the third argument the factory takes.
 * @param A4 The type of the fourth argument the factory takes.
 * @param A5 The type of the fifth argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> DKodein.factory5OrNull(tag: Any? = null) =
        FactoryOrNull<Multi5<A1, A2, A3, A4, A5>, T>(generic(), generic(), tag).let { factory -> factory?.let { { a1: A1, a2: A2, a3: A3, a4: A4, a5: A5 -> it(M(a1, a2, a3, a4, a5)) } } }
