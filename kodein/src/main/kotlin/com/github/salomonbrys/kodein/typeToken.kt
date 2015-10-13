package com.github.salomonbrys.kodein

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

private var _needPTWrapperCache: Boolean? = null;

/**
 * Detectes whether KodeinParameterizedType is needed.
 */
public fun _needPTWrapper(): Boolean {
    if (_needPTWrapperCache == null)
        _needPTWrapperCache = (object : TypeToken<List<String>>() {}).type != (object : TypeToken<List<String>>() {}).type
    return _needPTWrapperCache!!
}

/**
 * Class used to get a generic type at runtime
 */
public abstract class TypeToken<T> {
    public val type: Type

    protected constructor() {
        this.type = extractType()
    }

    private fun extractType(): Type {
        val t = javaClass.genericSuperclass

        if (t !is ParameterizedType)
            throw RuntimeException("Invalid TypeToken; must specify type parameters")

        if (t.rawType != TypeToken::class.java)
            throw RuntimeException("Invalid TypeToken; must directly extend TypeToken")

        return t.actualTypeArguments[0]
    }
}

/**
 * Function used to get a generic type at runtime
 */
public inline fun <reified T> typeToken(): Type {
    val type = (object : TypeToken<T>() {}).type

    if (type is ParameterizedType && _needPTWrapper())
        return KodeinParameterizedType(type)

    if (type !is ParameterizedType && type !is Class<*>)
        throw RuntimeException("Invalid TypeToken; must specify type parameters")

    return type
}

/**
 * Wraps a ParameterizedType and implements hashCode / equals.
 * This is because some JVM implementation (such as Android 4.4 and earlier) does NOT implement hashcode / equals for
 * ParameterizedType (I know...).
 */
public class KodeinParameterizedType(public val type: ParameterizedType) : Type {

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
            if (leftArgs.size() != rightArgs.size())
                return false;

            for (i in leftArgs.indices)
                if (!Equals(leftArgs[i], rightArgs[i]))
                    return false

            return true
        }
    }
}

private var hasTypeName = true

public val Type.dispName: String get() {
    if (hasTypeName)
        try {
            return typeName
        }
        catch (ignored: Throwable) {
            hasTypeName = false
        }

    if (this is Class<*>)
        return this.name

    return toString()
}
