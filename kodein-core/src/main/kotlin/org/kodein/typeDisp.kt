package org.kodein

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
    fun dispString(type: Type, skipStars: Boolean = false): String = when (type) {
        is Class<*> -> dispName(type, skipStars)
        is ParameterizedType -> {
            val cls = type.rawType as Class<*>
            val arguments = cls.typeParameters.mapIndexed { i, variable ->
                val argument = type.actualTypeArguments[i]
                if (argument is WildcardType && variable.bounds.any { it in argument.upperBounds })
                    "*"
                else
                    dispString(argument)
            }
            dispString(type.rawType, true) + "<" + arguments.joinToString(", ") + ">"
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
    abstract fun dispName(cls: Class<*>, skipStars: Boolean = false): String
}

/**
 * Type stringer that displays simple type names.
 */
private object SimpleTypeStringer : TypeStringer() {
    override fun dispName(cls: Class<*>, skipStars: Boolean): String = when {
        cls.isArray -> "Array<" + dispString(cls.componentType) + ">"
        else -> cls.primitiveName ?: cls.simpleErasedName() + (if (!skipStars) cls.stars else "")
    }
}

private val Class<*>.stars: String get() {
    if (typeParameters.isEmpty())
        return ""

    val stars = Array(typeParameters.size) { "*" }
    return stars.joinToString(prefix = "<", separator = ", ", postfix = ">")
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
        Object::class.java -> "Any"
        else -> null
    }

/**
 * Type stringer that displays full type names.
 */
private object FullTypeStringer : TypeStringer() {
    override fun dispName(cls: Class<*>, skipStars: Boolean) = when {
        cls.isArray -> "kotlin.Array<" + dispString(cls.componentType) + ">"
        else -> cls.primitiveName?.let { "kotlin.$it" }
                ?: ((cls.`package`?.name?.plus(".") ?: "") + SimpleTypeStringer.dispName(cls, true))._magic() + (if (!skipStars) cls.stars else "")
    }
}

private fun String._magic(): String = when {
    startsWith("java.") -> when (this) {
        "java.util.List" -> "kotlin.collections.List"
        "java.util.ArrayList" -> "kotlin.collections.ArrayList"

        "java.util.Map" -> "kotlin.collections.Map"
        "java.util.LinkedHashMap" -> "kotlin.collections.LinkedHashMap"
        "java.util.HashMap" -> "kotlin.collections.HashMap"

        "java.util.Set" -> "kotlin.collections.Set"
        "java.util.HashSet" -> "kotlin.collections.HashSet"
        "java.util.LinkedHashSet" -> "kotlin.collections.LinkedHashSet"

        "java.lang.String" -> "kotlin.String"
        "java.lang.Object" -> "kotlin.Any"
        "java.lang.Error" -> "kotlin.Error"
        "java.lang.Throwable" -> "kotlin.Throwable"
        "java.lang.Exception" -> "kotlin.Exception"
        "java.lang.RuntimeException" -> "kotlin.RuntimeException"
        "java.lang.IllegalArgumentException" -> "kotlin.IllegalArgumentException"
        "java.lang.IllegalStateException" -> "kotlin.IllegalStateException"
        "java.lang.IndexOutOfBoundsException" -> "kotlin.IndexOutOfBoundsException"
        "java.lang.UnsupportedOperationException" -> "kotlin.UnsupportedOperationException"
        "java.lang.NumberFormatException" -> "kotlin.NumberFormatException"
        "java.lang.NullPointerException" -> "kotlin.NullPointerException"
        "java.lang.ClassCastException" -> "kotlin.ClassCastException"
        "java.lang.AssertionError" -> "kotlin.AssertionError"
        "java.util.NoSuchElementException" -> "kotlin.NoSuchElementException"

        "java.util.Comparator" -> "kotlin.Comparator"
        else -> this
    }
    else -> this
}

/**
 * A string representing this type in a Kotlin-esque fashion using simple type names.
 */
fun Type.simpleDispString(): String = SimpleTypeStringer.dispString(this)

/**
 * A string representing this type in a Kotlin-esque fashion using full type names.
 */
fun Type.fullDispString(): String = FullTypeStringer.dispString(this)

fun Type.simpleErasedName(): String {
        return when (this) {
            is Class<*> -> (this.enclosingClass?.simpleErasedName()?.plus(".") ?: "") + this.simpleName
            is ParameterizedType -> this.rawType.simpleErasedName()
            is GenericArrayType -> this.genericComponentType.simpleErasedName()
            is KodeinWrappedType -> this.type.simpleErasedName()
            is WildcardType -> "*"
            is TypeVariable<*> -> this.name
            else -> throw IllegalArgumentException("Unknown type $javaClass $this")
        }
}

fun Type.fullErasedName(): String =
        when (this) {
            is Class<*> -> this.canonicalName._magic()
            is ParameterizedType -> this.rawType.fullErasedName()
            is GenericArrayType -> this.genericComponentType.fullErasedName()
            is KodeinWrappedType -> this.type.fullErasedName()
            is WildcardType -> "*"
            is TypeVariable<*> -> this.name
            else -> throw IllegalArgumentException("Unknown type $javaClass $this")
        }
