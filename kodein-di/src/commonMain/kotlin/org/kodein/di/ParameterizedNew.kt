@file:Suppress("MethodOverloading", "TooManyFunctions")

package org.kodein.di

import kotlinx.atomicfu.atomic
import org.kodein.type.TypeToken
import org.kodein.type.generic

@PublishedApi
internal fun UnusedParamMessage(arg: Any): String = """
The new() operator has processed all parameters, but your provided argument "$arg" was not used.
This is likely a developer error because if you provided an argument using factory() or multiton(), 
you probably meant to use it in some way.

Possible solutions:
1. Use new() overload without a parameter (e.g. new(::MyClass) )
2. Replace factory()/multiton() with provider()/singleton()
3. Use the argument in your dependency's constructor.
"""

@PublishedApi
internal interface ParameterizedNew {

    operator fun <T : Any> invoke(t: TypeToken<T>): T
}

@PublishedApi
internal inline fun <reified A : Any, T> DirectDIAware.parameterized(
    param: A,
    block: ParameterizedNew.() -> T,
) = object : ParameterizedNew {

    private val pType = generic<A>()
    private var consumed by atomic(false)
    fun verify() = if (!consumed) throw DI.UnusedParameterException(UnusedParamMessage(param)) else Unit

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> invoke(t: TypeToken<T>): T {
        if (consumed || !pType.isAssignableFrom(t)) return directDI.Instance(t, null)
        consumed = true
        return param as T
    }

}.run { block().also { verify() } }

@PublishedApi
internal inline fun <reified T : Any> ParameterizedNew.invoke(): T = invoke(generic<T>())

/**
 * This is a [new] factory function overload that takes a parameter [param].
 *
 * This function will try to merge [instance] calls used to retrieve graph dependencies with usages of your provided
 * [param].
 *
 * - This function will try to use the [param] **once** after which it will always inject dependencies from the graph.
 *   Make sure your parameter is declared only once in the target dependency constructor, unless you want to also inject
 *   it from the graph
 * - This function will throw [DI.UnusedParameterException] if you never use the provided [param].
 * - On jvm, this function can correctly resolve inheritance hierarchies if provided [param] is a subclass of the
 *   parameter used in the constructor, but due to compiler limitations, this will **only** work on JVM!
 *   Avoid relying on inheritance when injecting parameters using this function.
 */
public inline fun <T, reified A : Any> DirectDIAware.new(
    param: A,
    constructor: (A) -> T,
): T = constructor(param)

// region new() overloads

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <T, reified A : Any, reified P1, reified P2> DirectDIAware.new(
    param: A,
    constructor: (P1, P2) -> T,
): T = parameterized(param) { constructor(invoke(), invoke()) }

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <T, reified A : Any, reified P1, reified P2, reified P3> DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke()) }

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <T, reified A : Any, reified P1, reified P2, reified P3, reified P4> DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke(), invoke()) }

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <T, reified A : Any, reified P1, reified P2, reified P3, reified P4, reified P5> DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke(), invoke(), invoke()) }

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke(), invoke(), invoke(), invoke()) }

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7) -> T,
): T = parameterized(param) { constructor(invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke()) }

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        > DirectDIAware.new(
    param: A,
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

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
        reified P1,
        reified P2,
        reified P3,
        reified P4,
        reified P5,
        reified P6,
        reified P7,
        reified P8,
        reified P9,
        > DirectDIAware.new(
    param: A,
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

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
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

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
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

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
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

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
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

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke()
    )
}

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke()
    )
}

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke()
    )
}

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke()
    )
}

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke()
    )
}

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke()
    )
}

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke()
    )
}

/**
 * Please see parent [new] function documentation for more info.
 */
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
    constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, P13, P14, P15, P16, P17, P18, P19, P20, P21) -> T,
): T = parameterized(param) {
    constructor(
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke(),
        invoke(), invoke(), invoke(), invoke(), invoke(), invoke(), invoke()
    )
}

/**
 * Please see parent [new] function documentation for more info.
 */
@Suppress("Indentation")
public inline fun <
        T,
        reified A : Any,
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
        > DirectDIAware.new(
    param: A,
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

// endregion
