package com.github.salomonbrys.kodein

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

private var _needPTWrapperCache: Boolean? = null;

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
        val t = javaClass.getGenericSuperclass()

        if (t !is ParameterizedType)
            throw RuntimeException("Invalid TypeToken; must specify type parameters")

        if (t.getRawType() != javaClass<TypeToken<*>>())
            throw RuntimeException("Invalid TypeToken; must directly extend TypeToken")

        return t.getActualTypeArguments()[0]
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

            var hashCode = HashCode(type.getRawType());
            for (arg in type.getActualTypeArguments())
                hashCode *= 31 + HashCode(if (arg is WildcardType) arg.getUpperBounds()[0] else arg)
            return hashCode
        }

        fun Equals(left: Type, right: Type): Boolean {
            if (left is Class<*> && right is Class<*>)
                return left == right

            if (left is WildcardType)
                return Equals(left.getUpperBounds()[0], right)
            if (right is WildcardType)
                return Equals(left, right.getUpperBounds()[0])

            if (left !is ParameterizedType || right !is ParameterizedType)
                return false

            if (!Equals(left.getRawType(), right.getRawType()))
                return false;

            val leftArgs = left.getActualTypeArguments()
            val rightArgs = right.getActualTypeArguments()
            if (leftArgs.size() != rightArgs.size())
                return false;

            for (i in leftArgs.indices)
                if (!Equals(leftArgs[i], rightArgs[i]))
                    return false

            return true
        }
    }
}
