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
        is ParameterizedType -> dispString(type.rawType) + "<" + type.actualTypeArguments.joinToString(", ") { dispString(it) } + ">"
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
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    override fun dispName(cls: Class<*>): String = when {
        cls.isArray -> "Array<" + dispString(cls.componentType) + ">"
        cls.enclosingClass != null -> dispName(cls.enclosingClass) + "." + cls.simpleName
        cls == java.lang.Character::class.java -> "Char"
        cls == java.lang.Integer::class.java -> "Int"
        else -> cls.simpleName
    }
}

/**
 * Type stringer that displays full type names.
 */
private object FullTypeStringer : TypeStringer() {
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    override fun dispName(cls: Class<*>) = when {
        cls.isArray -> "Array<" + dispString(cls.componentType) + ">"
        cls.enclosingClass != null -> dispString(cls.enclosingClass) + "." + cls.simpleName
        cls == java.lang.Boolean::class.java -> "kotin.Boolean"
        cls == java.lang.Byte::class.java -> "kotin.Byte"
        cls == java.lang.Character::class.java -> "kotin.Char"
        cls == java.lang.Short::class.java -> "kotin.Short"
        cls == java.lang.Integer::class.java -> "kotin.Int"
        cls == java.lang.Long::class.java -> "kotin.Long"
        cls == java.lang.Float::class.java -> "kotin.Float"
        cls == java.lang.Double::class.java -> "kotin.Double"
        else -> cls.`package`.name + "." + SimpleTypeStringer.dispName(cls)
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
