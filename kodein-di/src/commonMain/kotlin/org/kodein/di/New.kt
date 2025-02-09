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
public inline fun <T, reified P1> DirectDIAware.new(
    constructor: (P1) -> T,
): T = constructor(instance())

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2> DirectDIAware.new(
    constructor: (P1, P2) -> T,
): T = constructor(instance(), instance())

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2,
        reified P3> DirectDIAware.new(
    constructor: (P1, P2, P3) -> T,
): T = constructor(instance(), instance(), instance())

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2,
        reified P3, reified P4> DirectDIAware.new(
    constructor: (P1, P2, P3, P4) -> T,
): T = constructor(instance(), instance(), instance(), instance())

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2,
        reified P3, reified P4,
        reified P5> DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5) -> T,
): T = constructor(instance(), instance(), instance(), instance(), instance())

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2,
        reified P3, reified P4,
        reified P5, reified P6> DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6) -> T,
): T = constructor(
    instance(), instance(), instance(),
    instance(), instance(), instance()
)

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2,
        reified P3, reified P4,
        reified P5, reified P6,
        reified P7> DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(),
    instance(), instance(), instance()
)

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2,
        reified P3, reified P4,
        reified P5, reified P6,
        reified P7, reified P8> DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance()
)

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2,
        reified P3, reified P4,
        reified P5, reified P6,
        reified P7, reified P8,
        reified P9> DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance()
)

/**
 * @see new
 */
public inline fun <T,
        reified P1, reified P2,
        reified P3, reified P4,
        reified P5, reified P6,
        reified P7, reified P8,
        reified P9, reified P10> DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> T,
): T = constructor(
    instance(), instance(), instance(), instance(), instance(),
    instance(), instance(), instance(), instance(), instance()
)


/**
 * @see new
 */
public inline fun <
        T,
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
        reified P11
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P12
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P13
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P14
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P15
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P16
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P17
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P18
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P19
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)

/**
 * @see new
 */
public inline fun <
        T,
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
        reified P20
        > DirectDIAware.new(
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20) -> T,
): T = constructor(
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance(),
    instance()
)
