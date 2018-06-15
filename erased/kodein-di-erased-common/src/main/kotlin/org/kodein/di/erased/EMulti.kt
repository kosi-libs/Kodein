package org.kodein.di.erased

import org.kodein.di.*
import org.kodein.di.bindings.*

/**
 * Creates a multi-argument factory.
 *
 * A1, A2 & T generics will be erased!
 *
 * @see [Kodein.BindBuilder.WithContext.factory]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <C, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2            ) -> T) = Factory(contextType, Multi2.erased<A1, A2            >(), erased()) { creator(it.a1, it.a2                     ) }

/**
 * Creates a multi-argument factory.
 *
 * A1, A2, A3 & T generics will be erased!
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
inline fun <C, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3        ) -> T) = Factory(contextType, Multi3.erased<A1, A2, A3        >(), erased()) { creator(it.a1, it.a2, it.a3              ) }

/**
 * Creates a multi-argument factory.
 *
 * A1, A2, A3, A4 & T generics will be erased!
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
inline fun <C, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3, A4    ) -> T) = Factory(contextType, Multi4.erased<A1, A2, A3, A4    >(), erased()) { creator(it.a1, it.a2, it.a3, it.a4       ) }

/**
 * Creates a multi-argument factory.
 *
 * A1, A2, A3, A4, A5 & T generics will be erased!
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
inline fun <C, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithContext<C>.factory(noinline creator: BindingKodein<C>.(A1, A2, A3, A4, A5) -> T) = Factory(contextType, Multi5.erased<A1, A2, A3, A4, A5>(), erased()) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }



/**
 * Creates a multi-argument multiton.
 *
 * A1, A2 & T generics will be erased!
 *
 * @see [Kodein.BindBuilder.WithScope.multiton]
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument. Guaranteed to be called only once per argument. Should create a new instance.
 * @return A factory ready to be bound.
 */
inline fun <EC, BC, reified A1, reified A2,                                     reified T: Any> Kodein.BindBuilder.WithScope<EC, BC, Multi2<A1, A2            >>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2            ) -> T) = Multiton(scope, contextType, Multi2.erased<A1, A2            >(), erased(), ref) { creator(it.a1, it.a2                     ) }

/**
 * Creates a multi-argument multiton.
 *
 * A1, A2, A3 & T generics will be erased!
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
inline fun <EC, BC, reified A1, reified A2, reified A3,                         reified T: Any> Kodein.BindBuilder.WithScope<EC, BC, Multi3<A1, A2, A3        >>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3        ) -> T) = Multiton(scope, contextType, Multi3.erased<A1, A2, A3        >(), erased(), ref) { creator(it.a1, it.a2, it.a3              ) }

/**
 * Creates a multi-argument multiton.
 *
 * A1, A2, A3, A4 & T generics will be erased!
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
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4,             reified T: Any> Kodein.BindBuilder.WithScope<EC, BC, Multi4<A1, A2, A3, A4    >>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4    ) -> T) = Multiton(scope, contextType, Multi4.erased<A1, A2, A3, A4    >(), erased(), ref) { creator(it.a1, it.a2, it.a3, it.a4       ) }

/**
 * Creates a multi-argument multiton.
 *
 * A1, A2, A3, A4, A5 & T generics will be erased!
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
inline fun <EC, BC, reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> Kodein.BindBuilder.WithScope<EC, BC, Multi5<A1, A2, A3, A4, A5>>.multiton(ref: RefMaker? = null, noinline creator: SimpleBindingKodein<BC>.(A1, A2, A3, A4, A5) -> T) = Multiton(scope, contextType, Multi5.erased<A1, A2, A3, A4, A5>(), erased(), ref) { creator(it.a1, it.a2, it.a3, it.a4, it.a5) }



/**
 * Creates a `Multi2`.
 *
 * A1 & A2 generics will be erased!
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param a1 The first argument.
 * @param a2 The second argument.
 */
@Suppress("FunctionName")
inline fun <reified A1, reified A2                                    > M(a1: A1, a2: A2                        ) = Multi2(a1, a2,             Multi2.erased())

/**
 * Creates a `Multi3`.
 *
 * A1, A2, & A3 generics will be erased!
 *
 * @param A1 The first argument type.
 * @param A2 The second argument type.
 * @param A3 The third argument type.
 * @param a1 The first argument.
 * @param a2 The second argument.
 * @param a3 The third argument.
 */
@Suppress("FunctionName")
inline fun <reified A1, reified A2, reified A3                        > M(a1: A1, a2: A2, a3: A3                ) = Multi3(a1, a2, a3,         Multi3.erased())

/**
 * Creates a `Multi4`.
 *
 * A1, A2, A3 & A4 generics will be erased!
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
inline fun <reified A1, reified A2, reified A3, reified A4            > M(a1: A1, a2: A2, a3: A3, a4: A4        ) = Multi4(a1, a2, a3, a4,     Multi4.erased())

/**
 * Creates a `Multi5`.
 *
 * A1, A2, A3, A4 & A5 generics will be erased!
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
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5> M(a1: A1, a2: A2, a3: A3, a4: A4, a5: A5) = Multi5(a1, a2, a3, a4, a5, Multi5.erased())


/**
 * Creates a `CompositeTypeToken` that describes a `Multi2` and its generic parameters.
 *
 * The generic parameters themselves will be erased!
 */
inline fun <reified A1, reified A2                                    > Multi2.Companion.erased() = erasedComp2<Multi2<A1, A2            >, A1, A2            >()

/**
 * Creates a `CompositeTypeToken` that describes a `Multi3` and its generic parameters.
 *
 * The generic parameters themselves will be erased!
 */
inline fun <reified A1, reified A2, reified A3                        > Multi3.Companion.erased() = erasedComp3<Multi3<A1, A2, A3        >, A1, A2, A3        >()

/**
 * Creates a `CompositeTypeToken` that describes a `Multi4` and its generic parameters.
 *
 * The generic parameters themselves will be erased!
 */
inline fun <reified A1, reified A2, reified A3, reified A4            > Multi4.Companion.erased() = erasedComp4<Multi4<A1, A2, A3, A4    >, A1, A2, A3, A4    >()

/**
 * Creates a `CompositeTypeToken` that describes a `Multi5` and its generic parameters.
 *
 * The generic parameters themselves will be erased!
 */
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5> Multi5.Companion.erased() = erasedComp5<Multi5<A1, A2, A3, A4, A5>, A1, A2, A3, A4, A5>()




























/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2 & T generics will be erased!
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
        KodeinPropertyMap(Factory<Multi2<A1, A2            >, T>(Multi2.erased(), erased(), tag)) { { a1: A1, a2: A2                         -> it(M(a1, a2            )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3 & T generics will be erased!
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
        KodeinPropertyMap(Factory<Multi3<A1, A2, A3        >, T>(Multi3.erased(), erased(), tag)) { { a1: A1, a2: A2, a3: A3                 -> it(M(a1, a2, a3        )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3, A4 & T generics will be erased!
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
        KodeinPropertyMap(Factory<Multi4<A1, A2, A3, A4    >, T>(Multi4.erased(), erased(), tag)) { { a1: A1, a2: A2, a3: A3, a4: A4         -> it(M(a1, a2, a3, a4    )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3, A4, A5 & T generics will be erased!
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
        KodeinPropertyMap(Factory<Multi5<A1, A2, A3, A4, A5>, T>(Multi5.erased(), erased(), tag)) { { a1: A1, a2: A2, a3: A3, a4: A4, a5: A5 -> it(M(a1, a2, a3, a4, a5)) } }


/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2 & T generics will be erased!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
inline fun <reified A1, reified A2,                                     reified T: Any> KodeinAware.factory2OrNull(tag: Any? = null): LazyDelegate<((A1, A2            ) -> T)?> {
    return KodeinPropertyMap(FactoryOrNull<Multi2<A1, A2            >, T>(Multi2.erased(), erased(), tag)) {
        val factory = it ?: return@KodeinPropertyMap null
        return@KodeinPropertyMap { a1: A1, a2: A2                         -> factory(M(a1, a2            )) }
    }
}

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3 & T generics will be erased!
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
inline fun <reified A1, reified A2, reified A3,                         reified T: Any> KodeinAware.factory3OrNull(tag: Any? = null): LazyDelegate<((A1, A2, A3        ) -> T)?> {
    return KodeinPropertyMap(FactoryOrNull<Multi3<A1, A2, A3        >, T>(Multi3.erased(), erased(), tag)) {
        val factory = it ?: return@KodeinPropertyMap null
        return@KodeinPropertyMap { a1: A1, a2: A2, a3: A3                 -> factory(M(a1, a2, a3        )) }
    }
}

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3, A4 & T generics will be erased!
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
inline fun <reified A1, reified A2, reified A3, reified A4,             reified T: Any> KodeinAware.factory4OrNull(tag: Any? = null): LazyDelegate<((A1, A2, A3, A4    ) -> T)?> {
    return KodeinPropertyMap(FactoryOrNull<Multi4<A1, A2, A3, A4    >, T>(Multi4.erased(), erased(), tag)) {
        val factory = it ?: return@KodeinPropertyMap null
        return@KodeinPropertyMap { a1: A1, a2: A2, a3: A3, a4: A4         -> factory(M(a1, a2, a3, a4    )) }
    }
}

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3, A4, A5 & T generics will be erased!
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
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> KodeinAware.factory5OrNull(tag: Any? = null): LazyDelegate<((A1, A2, A3, A4, A5) -> T)?> {
    return KodeinPropertyMap(FactoryOrNull<Multi5<A1, A2, A3, A4, A5>, T>(Multi5.erased(), erased(), tag)) {
        val factory = it ?: return@KodeinPropertyMap null
        return@KodeinPropertyMap { a1: A1, a2: A2, a3: A3, a4: A4, a5: A5 -> factory(M(a1, a2, a3, a4, a5)) }
    }
}


/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2 & T generics will be erased!
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
        Factory<Multi2<A1, A2            >, T>(Multi2.erased(), erased(), tag).let { { a1: A1, a2: A2                         -> it(M(a1, a2            )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3 & T generics will be erased!
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
        Factory<Multi3<A1, A2, A3        >, T>(Multi3.erased(), erased(), tag).let { { a1: A1, a2: A2, a3: A3                 -> it(M(a1, a2, a3        )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3, A4 & T generics will be erased!
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
        Factory<Multi4<A1, A2, A3, A4    >, T>(Multi4.erased(), erased(), tag).let { { a1: A1, a2: A2, a3: A3, a4: A4         -> it(M(a1, a2, a3, a4    )) } }

/**
 * Gets a factory of `T` for the given argument types, return type and tag.
 *
 * A1, A2, A3, A4, A5 & T generics will be erased!
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
        Factory<Multi5<A1, A2, A3, A4, A5>, T>(Multi5.erased(), erased(), tag).let { { a1: A1, a2: A2, a3: A3, a4: A4, a5: A5 -> it(M(a1, a2, a3, a4, a5)) } }


/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2 & T generics will be erased!
 *
 * @param A1 The type of the first argument the factory takes.
 * @param A2 The type of the second argument the factory takes.
 * @param T The type of object the factory returns.
 * @param tag The bound tag, if any.
 * @return A factory, or null if no factory was found.
 * @throws Kodein.NotFoundException if no factory was found.
 * @throws Kodein.DependencyLoopException When calling the factory function, if the instance construction triggered a dependency loop.
 */
//inline fun <reified A1, reified A2,                                     reified T: Any> DKodein.factory2OrNull(tag: Any? = null) =
//        FactoryOrNull<Multi2<A1, A2            >, T>(Multi2.erased(), erased(), tag).let { it?.let { { a1: A1, a2: A2                         -> it(M(a1, a2            )) } } }

inline fun <reified A1, reified A2,                                     reified T: Any> DKodein.factory2OrNull(tag: Any? = null): ((A1, A2            ) -> T)? {
    val factory = FactoryOrNull<Multi2<A1, A2            >, T>(Multi2.erased(), erased(), tag) ?: return null
    return { a1, a2             -> factory(M(a1, a2            )) }
}

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3 & T generics will be erased!
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
inline fun <reified A1, reified A2, reified A3,                         reified T: Any> DKodein.factory3OrNull(tag: Any? = null): ((A1, A2, A3        ) -> T)? {
    val factory = FactoryOrNull<Multi3<A1, A2, A3        >, T>(Multi3.erased(), erased(), tag) ?: return null
    return { a1, a2, a3         -> factory(M(a1, a2, a3        )) }
}

/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3, A4 & T generics will be erased!
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
inline fun <reified A1, reified A2, reified A3, reified A4,             reified T: Any> DKodein.factory4OrNull(tag: Any? = null): ((A1, A2, A3, A4    ) -> T)? {
    val factory = FactoryOrNull<Multi4<A1, A2, A3, A4    >, T>(Multi4.erased(), erased(), tag) ?: return null
    return { a1, a2, a3, a4     -> factory(M(a1, a2, a3, a4    )) }
}


/**
 * Gets a factory of `T` for the given argument types, return type and tag, or null if none is found.
 *
 * A1, A2, A3, A4, A5 & T generics will be erased!
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
inline fun <reified A1, reified A2, reified A3, reified A4, reified A5, reified T: Any> DKodein.factory5OrNull(tag: Any? = null): ((A1, A2, A3, A4, A5) -> T)? {
    val factory = FactoryOrNull<Multi5<A1, A2, A3, A4, A5>, T>(Multi5.erased(), erased(), tag) ?: return null
    return { a1, a2, a3, a4, a5 -> factory(M(a1, a2, a3, a4, a5)) }
}

