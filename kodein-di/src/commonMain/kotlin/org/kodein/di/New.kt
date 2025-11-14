package org.kodein.di

/**
 * Auto resolve a class dependencies by using its constructor reference.
 * The resolution is done at compile time by leveraging inline functions, no reflection is required.
 *
 * Example:
 * ```
 * val myModule = DI.module("myModule") {
 *   bindSingleton { new(::Foo) }
 * }
 * ```
 *
 * Thanks to Raman Gupta (https://gist.github.com/rocketraman) for its contribution
 */
public inline fun <T> DirectDIAware.new(
    constructor: () -> T,
): T = constructor()

/**
 * @see new
 */
public inline fun <
    T,
    reified P1,
> DirectDIAware.new(
    constructor: (
        P1,
    ) -> T,
): T = constructor(
    instance(),
)

/**
 * Auto resolve a class dependencies by using its constructor reference.
 * Allows defining specific arguments (all non-specified arguments are retrieved from the container).
 * The resolution is done at compile time by leveraging inline functions, no reflection is required.
 *
 * Example:
 * ```
 * val myModule = DI.module("myModule") {
 *   bindSingleton { new(::Foo, a1 = 42) }
 * }
 * ```
 */
public inline fun <
    T,
    reified P1,
> DirectDIAware.new(
    constructor: (
        P1,
    ) -> T,
    a1: P1 = instance(),
): T = constructor(
    a1,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2,
> DirectDIAware.new(
    constructor: (
        P1, P2,
    ) -> T,
): T = constructor(
    instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2,
> DirectDIAware.new(
    constructor: (
        P1, P2,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(),
): T = constructor(
    a1, a2,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(),
): T = constructor(
    a1, a2, a3,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(),
): T = constructor(
    a1, a2, a3, a4,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
): T = constructor(a1, a2, a3, a4, a5)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14,
    > DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(), a15: P15 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14, a15,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(), a15: P15 = instance(),
    a16: P16 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14, a15,
    a16,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(), a15: P15 = instance(),
    a16: P16 = instance(), a17: P17 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14, a15,
    a16, a17,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(), a15: P15 = instance(),
    a16: P16 = instance(), a17: P17 = instance(), a18: P18 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14, a15,
    a16, a17, a18,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18, reified P19,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18, P19,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18, reified P19,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18, P19,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(), a15: P15 = instance(),
    a16: P16 = instance(), a17: P17 = instance(), a18: P18 = instance(), a19: P19 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14, a15,
    a16, a17, a18, a19,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18, reified P19, reified P20,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18, P19, P20,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18, reified P19, reified P20,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18, P19, P20,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(), a15: P15 = instance(),
    a16: P16 = instance(), a17: P17 = instance(), a18: P18 = instance(), a19: P19 = instance(), a20: P20 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14, a15,
    a16, a17, a18, a19, a20,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18, reified P19, reified P20,
    reified P21,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18, P19, P20,
        P21,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18, reified P19, reified P20,
    reified P21,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18, P19, P20,
        P21,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(), a15: P15 = instance(),
    a16: P16 = instance(), a17: P17 = instance(), a18: P18 = instance(), a19: P19 = instance(), a20: P20 = instance(),
    a21: P21 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14, a15,
    a16, a17, a18, a19, a20,
    a21,
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18, reified P19, reified P20,
    reified P21, reified P22,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18, P19, P20,
        P21, P22,
    ) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(),
)

/**
 * @see new
 */
public inline fun <
    T,
    reified P1, reified P2, reified P3, reified P4, reified P5,
    reified P6, reified P7, reified P8, reified P9, reified P10,
    reified P11, reified P12, reified P13, reified P14, reified P15,
    reified P16, reified P17, reified P18, reified P19, reified P20,
    reified P21, reified P22,
> DirectDIAware.new(
    constructor: (
        P1, P2, P3, P4, P5,
        P6, P7, P8, P9, P10,
        P11, P12, P13, P14, P15,
        P16, P17, P18, P19, P20,
        P21, P22,
    ) -> T,
    a1: P1 = instance(), a2: P2 = instance(), a3: P3 = instance(), a4: P4 = instance(), a5: P5 = instance(),
    a6: P6 = instance(), a7: P7 = instance(), a8: P8 = instance(), a9: P9 = instance(), a10: P10 = instance(),
    a11: P11 = instance(), a12: P12 = instance(), a13: P13 = instance(), a14: P14 = instance(), a15: P15 = instance(),
    a16: P16 = instance(), a17: P17 = instance(), a18: P18 = instance(), a19: P19 = instance(), a20: P20 = instance(),
    a21: P21 = instance(), a22: P22 = instance(),
): T = constructor(
    a1, a2, a3, a4, a5,
    a6, a7, a8, a9, a10,
    a11, a12, a13, a14, a15,
    a16, a17, a18, a19, a20,
    a21, a22,
)
