package org.kodein.di.jxinject

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

internal fun Type.rawType(): Class<*> = when (this) {
    is Class<*> -> this
    is ParameterizedType -> rawType.rawType()
    is WildcardType -> upperBounds[0].rawType()
    else -> throw IllegalStateException("Cannot get raw type of $this")
}

internal fun Type.lower(): Type = when (this) {
    is WildcardType -> lowerBounds[0]
    else -> this
}
