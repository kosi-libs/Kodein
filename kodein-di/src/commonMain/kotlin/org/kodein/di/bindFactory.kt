package org.kodein.di

import org.kodein.di.bindings.BindingDI
import org.kodein.di.bindings.Factory
import org.kodein.type.generic

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A factory ready to be bound.
 */
public inline fun <C : Any, reified A : Any, reified T : Any> DI.BindBuilder<C>.factory(
    noinline creator: BindingDI<C>.(A) -> T,
): Factory<C, A, T> = Factory(contextType, generic(), generic(), creator)

/**
 * Binds a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 */
public inline fun <reified A : Any, reified T : Any> DI.Builder.bindFactory(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline creator: DirectDI.(A) -> T,
): Unit = Bind(tag = tag, overrides = overrides, binding = factory(creator = creator))

// region bindFactoryOf() overloads

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        reified P15,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        reified P15,
        reified P16,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        reified P15,
        reified P16,
        reified P17,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        reified P15,
        reified P16,
        reified P17,
        reified P18,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        reified P15,
        reified P16,
        reified P17,
        reified P18,
        reified P19,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        reified P15,
        reified P16,
        reified P17,
        reified P18,
        reified P19,
        reified P20,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        reified P15,
        reified P16,
        reified P17,
        reified P18,
        reified P19,
        reified P20,
        reified P21,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        reified P10,
        reified P11,
        reified P12,
        reified P13,
        reified P14,
        reified P15,
        reified P16,
        reified P17,
        reified P18,
        reified P19,
        reified P20,
        reified P21,
        reified P22,
        > DI.Builder.bindFactoryOf(
    tag: Any? = null,
    overrides: Boolean? = null,
     crossinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21, P22) -> T,
): Unit = bindFactory<A, T>(tag, overrides) { new(it, constructor) }

// endregion
