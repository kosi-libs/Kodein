package com.github.salomonbrys.kodein

import java.lang.reflect.*

/**
 * Base class whose role is to get a string representing a type in a Kotlin-esque fashion.
 */
private abstract class TypeStringer {

    /**
     * get a string representing a type in a Kotlin-esque fashion.
     *
     * @param type The type to stringify.
     */
    fun dispString(type: Type): String = when (type) {
        is Class<*> -> dispName(type)
        is ParameterizedType -> {
            val cls = type.rawType as Class<*>
            val arguments = cls.typeParameters.mapIndexed { i, variable ->
                val argument = type.actualTypeArguments[i]
                if (argument is WildcardType && variable.bounds.any { it in argument.upperBounds })
                    "*"
                else
                    dispString(argument)
            }
            dispString(type.rawType) + "<" + arguments.joinToString(", ") + ">"
        }
        is KodeinWrappedType -> dispString(type.type)
        is WildcardType -> when {
            type.lowerBounds.isNotEmpty() -> "in " + dispString(type.lowerBounds[0])
            type.upperBounds.isNotEmpty() -> when {
                type.upperBounds[0] == Any::class.java -> "*"
                else -> "out " + dispString(type.upperBounds[0])
            }
            else -> "*"
        }
        is GenericArrayType -> "Array<" + dispString(type.genericComponentType) + ">"
        is TypeVariable<*> -> type.name
        else -> throw IllegalStateException("Unknown type $javaClass")
    }

    /**
     * Returns a type name, either its simple name, or its full name.
     *
     * @param cls The class whose name to get.
     */
    abstract fun dispName(cls: Class<*>): String
}

/**
 * Type stringer that displays simple type names.
 */
private object SimpleTypeStringer : TypeStringer() {
    override fun dispName(cls: Class<*>): String = when {
        cls.isArray -> "Array<" + dispString(cls.componentType) + ">"
        cls.enclosingClass != null -> dispName(cls.enclosingClass) + "." + cls.simpleName
        else -> cls.primitiveName ?: cls.simpleName
    }
}

private val Class<*>.primitiveName: String?
    get() = when (this) {
        Boolean::class.javaPrimitiveType, Boolean::class.javaObjectType -> "Boolean"
        Byte::class.javaPrimitiveType, Byte::class.javaObjectType -> "Byte"
        Char::class.javaPrimitiveType, Char::class.javaObjectType -> "Char"
        Short::class.javaPrimitiveType, Short::class.javaObjectType -> "Short"
        Int::class.javaPrimitiveType, Int::class.javaObjectType -> "Int"
        Long::class.javaPrimitiveType, Long::class.javaObjectType -> "Long"
        Float::class.javaPrimitiveType, Float::class.javaObjectType -> "Float"
        Double::class.javaPrimitiveType, Double::class.javaObjectType -> "Double"
        else -> null
    }

/**
 * Type stringer that displays full type names.
 */
private object FullTypeStringer : TypeStringer() {
    override fun dispName(cls: Class<*>) = when {
        cls.isArray -> "Array<" + dispString(cls.componentType) + ">"
        cls.enclosingClass != null -> dispString(cls.enclosingClass) + "." + cls.simpleName
        else -> cls.primitiveName?.let { "kotlin.$it" }
                ?: cls.`package`.name + "." + SimpleTypeStringer.dispName(cls)
    }
}

/**
 * A string representing this type in a Kotlin-esque fashion using simple type names.
 */
val Type.simpleDispString: String get() = SimpleTypeStringer.dispString(this)

/**
 * A string representing this type in a Kotlin-esque fashion using full type names.
 */
val Type.fullDispString: String get() = FullTypeStringer.dispString(this)
