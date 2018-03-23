package org.kodein.di

import java.lang.reflect.*

private abstract class TypeStringer {

    fun dispString(type: Type, skipStars: Boolean = false): String {
        val jvmType = type.javaType
        return when (jvmType) {
            is Class<*> -> dispName(jvmType, skipStars)
            is ParameterizedType -> {
                val cls = jvmType.rawType as Class<*>
                val arguments = cls.typeParameters.mapIndexed { i, variable ->
                    val argument = jvmType.actualTypeArguments[i]
                    if (argument is WildcardType && variable.bounds.any { it in argument.upperBounds })
                        "*"
                    else
                        dispString(argument)
                }
                dispString(jvmType.rawType, true) + "<" + arguments.joinToString(", ") + ">"
            }
            is WildcardType -> when {
                jvmType.lowerBounds.isNotEmpty() -> "in " + dispString(jvmType.lowerBounds[0])
                jvmType.upperBounds.isNotEmpty() -> when {
                    jvmType.upperBounds[0] == Any::class.java -> "*"
                    else -> "out " + dispString(jvmType.upperBounds[0])
                }
                else -> "*"
            }
            is GenericArrayType -> "Array<" + dispString(jvmType.genericComponentType) + ">"
            is TypeVariable<*> -> jvmType.name
            else -> throw IllegalStateException("Unknown type $javaClass")
        }
    }

    abstract fun dispName(cls: Class<*>, skipStars: Boolean = false): String
}

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

/**
 * Returns the erased name of a type (e.g. the type name without it's generic parameters).
 */
fun Type.simpleErasedName(): String {
    val jvmType = javaType
    return when (jvmType) {
        is Class<*> -> (jvmType.enclosingClass?.simpleErasedName()?.plus(".") ?: "") + jvmType.simpleName
        is ParameterizedType -> jvmType.rawType.simpleErasedName()
        is GenericArrayType -> jvmType.genericComponentType.simpleErasedName()
        is WildcardType -> "*"
        is TypeVariable<*> -> jvmType.name
        else -> throw IllegalArgumentException("Unknown type $javaClass $this")
    }
}

/**
 * Returns the fully qualified erased name of a type (e.g. the type name without it's generic parameters).
 */
fun Type.fullErasedName(): String {
    val jvmType = javaType
    return when (jvmType) {
        is Class<*> -> jvmType.canonicalName._magic()
        is ParameterizedType -> jvmType.rawType.fullErasedName()
        is GenericArrayType -> jvmType.genericComponentType.fullErasedName()
        is WildcardType -> "*"
        is TypeVariable<*> -> jvmType.name
        else -> throw IllegalArgumentException("Unknown type $javaClass $this")
    }
}