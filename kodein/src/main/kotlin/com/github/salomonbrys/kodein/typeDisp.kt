package com.github.salomonbrys.kodein

import java.lang.reflect.*

private abstract class TypeStringer {

    fun dispString(type: Type): String = when (type) {
        is Class<*> -> dispName(type)
        is ParameterizedType -> dispString(type.rawType) + "<" + type.actualTypeArguments.joinToString(", ") { dispString(it) } + ">"
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

    abstract fun dispName(cls: Class<*>): String
}

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

val Type.simpleDispString: String get() = SimpleTypeStringer.dispString(this)
val Type.fullDispString: String get() = FullTypeStringer.dispString(this)
