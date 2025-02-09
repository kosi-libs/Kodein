package org.kodein.di

import org.kodein.di.bindings.BindingDI
import org.kodein.di.bindings.Multiton
import org.kodein.di.bindings.RefMaker
import org.kodein.type.generic

/**
 * Creates a multiton: will create an instance on first request for each different argument
 * and will subsequently always return the same instance for the same argument.
 *
 * A & T generics will be erased!
 *
 * @param A The argument type.
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested with a new argument.
 * Guaranteed to be called only once per argument. Should create a new instance.
 * @return A multiton ready to be bound.
 */
public inline fun <C : Any, reified A : Any, reified T : Any> DI.BindBuilder.WithScope<C>.multiton(
    ref: RefMaker? = null,
    sync: Boolean = true,
    noinline creator: BindingDI<C>.(A) -> T,
): Multiton<C, A, T> = Multiton(scope, contextType, explicitContext, generic(), generic(), ref, sync, creator)

/**
 * Binds a multiton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested.
 * Guaranteed to be called only once. Should create a new instance.
 */
public inline fun <reified A : Any, reified T : Any> DI.Builder.bindMultiton(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline creator: DirectDI.(A) -> T,
): Unit = Bind(tag = tag, overrides = overrides, binding = multiton(sync = sync, creator = creator))

// region bindMultitonOf overloads
public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

public inline fun <
        reified A : Any,
        reified T : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

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
        > DI.Builder.bindMultitonOf(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21, P22) -> T,
): Unit = bindMultiton<A, T>(tag, overrides, sync) { new(it, constructor) }

// endregion
