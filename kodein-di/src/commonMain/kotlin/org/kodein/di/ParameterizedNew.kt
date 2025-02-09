@file:Suppress("MethodOverloading", "TooManyFunctions")

package org.kodein.di

import kotlinx.atomicfu.atomic
import org.kodein.type.TypeToken
import org.kodein.type.generic

@PublishedApi
internal interface ParameterizedNew {

    operator fun <T : Any> invoke(t: TypeToken<T>): T
}

@PublishedApi
internal inline fun <reified P : Any, T> DirectDIAware.parameterized(
    params: P,
    block: ParameterizedNew.() -> T,
) = object : ParameterizedNew {

    private val pType = generic<P>()
    private var consumed by atomic(false)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> invoke(t: TypeToken<T>): T {
        if (consumed || !pType.isAssignableFrom(t)) return directDI.Instance(t, null)
        consumed = true
        return params as T
    }
}.run(block)

@PublishedApi
internal inline fun <reified T : Any> ParameterizedNew.invoke(): T = invoke(generic<T>())

/**
 * @see new
 */
public inline fun <T, reified P : Any, reified P1> DirectDIAware.new(
    param: P,
    constructor: (P1) -> T,
): T = parameterized(param) { constructor(invoke()) }

public inline fun <T, reified P : Any, reified P1, reified P2> DirectDIAware.new(
    param: P,
    constructor: (P1, P2) -> T,
): T = parameterized(param) { constructor(invoke(), invoke()) }

public inline fun <T, reified P : Any, reified P1, reified P2, reified P3> DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke()) }

public inline fun <T, reified P : Any, reified P1, reified P2, reified P3, reified P4> DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3, P4) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke(), invoke()) }

public inline fun <T, reified P : Any, reified P1, reified P2, reified P3, reified P4, reified P5> DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3, P4, P5) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke(), invoke(), invoke()) }

public inline fun <
    T,
    reified P : Any,
    reified P1,
    reified P2,
    reified P3,
    reified P4,
    reified P5,
    reified P6
    > DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke(), invoke(), invoke(), invoke()) }

public inline fun <
    T,
    reified P : Any,
    reified P1,
    reified P2,
    reified P3,
    reified P4,
    reified P5,
    reified P6,
    reified P7
    > DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke()) }

public inline fun <
    T,
    reified P : Any,
    reified P1,
    reified P2,
    reified P3,
    reified P4,
    reified P5,
    reified P6,
    reified P7,
    reified P8
    > DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8) -> T,
): T = parameterized(param) {
    constructor(
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
    reified P1,
    reified P2,
    reified P3,
    reified P4,
    reified P5,
    reified P6,
    reified P7,
    reified P8,
    reified P9
    > DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9) -> T,
): T = parameterized(param) {
    constructor(
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
    reified P1,
    reified P2,
    reified P3,
    reified P4,
    reified P5,
    reified P6,
    reified P7,
    reified P8,
    reified P9,
    reified P10
    > DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> T,
): T = parameterized(param) {
    constructor(
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> T,
): T = parameterized(param) {
    constructor(
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12) -> T,
): T = parameterized(param) {
    constructor(
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13) -> T,
): T = parameterized(param) {
    constructor(
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke(),
        invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke()
    )
}

public inline fun <
    T,
    reified P : Any,
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
    reified P21
    > DirectDIAware.new(
    param: P,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke()
    )
}

@Suppress("Indentation")
public inline fun <
    T,
    reified P : Any,
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
    reified P22
    > DirectDIAware.new(
    param: P,
    constructor:
        (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21, P22) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke()
    )
}
