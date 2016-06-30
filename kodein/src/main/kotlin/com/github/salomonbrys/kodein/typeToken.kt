package com.github.salomonbrys.kodein

import java.lang.reflect.*

private var _needPTWrapperCache: Boolean? = null;

/**
 * Detectes whether KodeinParameterizedType is needed.
 */
fun _needPTWrapper(): Boolean {
    if (_needPTWrapperCache == null)
        _needPTWrapperCache = (object : TypeReference<List<String>>() {}).trueType != (object : TypeReference<List<String>>() {}).trueType
    return _needPTWrapperCache!!
}

interface TypeToken<T> {
    val type: Type
}

/**
 * Class used to get a generic type at runtime
 */
@Suppress("unused")
abstract class TypeReference<T> : TypeToken<T> {
    val trueType: Type

    private var _type: Type? = null

    override val type: Type get() {
        if (_type == null) {
            if (trueType is ParameterizedType && _needPTWrapper())
                _type = KodeinParameterizedType(trueType)
            else if (trueType !is ParameterizedType && trueType !is Class<*>)
                throw RuntimeException("Invalid TypeToken; must specify type parameters")
            else
                _type = trueType
        }

        return _type!!
    }

    private fun _check(type: Type) {
        if (type is TypeVariable<*>)
            throw IllegalArgumentException("${type.name} is not a real type")
        else if (type is ParameterizedType) {
            for (arg in type.actualTypeArguments)
                _check(arg)
        }
        else if (type is GenericArrayType)
            _check(type.genericComponentType)
    }

    protected constructor() {
        trueType = extractType()
        _check(trueType)
    }

    private fun extractType(): Type {
        val t = javaClass.genericSuperclass

        if (t !is ParameterizedType)
            throw RuntimeException("Invalid TypeToken; must specify type parameters")

        if (t.rawType != TypeReference::class.java)
            throw RuntimeException("Invalid TypeToken; must directly extend TypeReference")

        return t.actualTypeArguments[0]
    }
}

/**
 * Function used to get a generic type at runtime
 */
inline fun <reified T> typeToken(): TypeToken<T> = (object : TypeReference<T>() {})

/**
 * Wraps a ParameterizedType and implements hashCode / equals.
 * This is because some JVM implementation (such as Android 4.4 and earlier) does NOT implement hashcode / equals for
 * ParameterizedType (I know...).
 */
class KodeinParameterizedType(val type: ParameterizedType) : Type {

    private var _hashCode: Int = 0;

    override fun hashCode(): Int {
        if (_hashCode == 0)
            _hashCode = Func.HashCode(type)
        return _hashCode
    }

    override fun equals(other: Any?): Boolean {

        val otherType =
                if (other is KodeinParameterizedType) other
                else if (other is ParameterizedType) KodeinParameterizedType(other)
                else return false

        if (hashCode() != otherType.hashCode())
            return false

        return Func.Equals(this.type, otherType.type)
    }

    override fun toString(): String {
        return "ParameterizedType $type"
    }

    private object Func {
        fun HashCode(type: Type): Int {
            if (type is Class<*>)
                return type.hashCode()

            if (type !is ParameterizedType)
                throw RuntimeException("Invalid TypeToken; must specify type parameters")

            var hashCode = HashCode(type.rawType);
            for (arg in type.actualTypeArguments)
                hashCode *= 31 + HashCode(if (arg is WildcardType) arg.upperBounds[0] else arg)
            return hashCode
        }

        fun Equals(left: Type, right: Type): Boolean {
            if (left is Class<*> && right is Class<*>)
                return left == right

            if (left is WildcardType)
                return Equals(left.upperBounds[0], right)
            if (right is WildcardType)
                return Equals(left, right.upperBounds[0])

            if (left !is ParameterizedType || right !is ParameterizedType)
                return false

            if (!Equals(left.rawType, right.rawType))
                return false;

            val leftArgs = left.actualTypeArguments
            val rightArgs = right.actualTypeArguments
            if (leftArgs.size != rightArgs.size)
                return false;

            for (i in leftArgs.indices)
                if (!Equals(leftArgs[i], rightArgs[i]))
                    return false

            return true
        }
    }
}

private var hasTypeName = true

val Type.dispName: String get() {
    if (hasTypeName)
        try {
            return typeName
        }
        catch (ignored: NoSuchMethodError) {
            hasTypeName = false
        }
        catch (ignored: Throwable) {}

    if (this is Class<*>)
        return this.name

    return toString()
}