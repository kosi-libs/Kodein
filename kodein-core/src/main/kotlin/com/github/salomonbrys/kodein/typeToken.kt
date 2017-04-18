package com.github.salomonbrys.kodein

import java.lang.reflect.*
import kotlin.reflect.KClass

/**
 * Whether or not the running JVM needs [ParameterizedType] to be wrapped.
 *
 * @see KodeinWrappedType
 */
private val _needPTWrapper: Boolean by lazy {
    val t1 = (object : TypeReference<List<String>>() {}).trueType as ParameterizedType
    val t2 = (object : TypeReference<List<String>>() {}).trueType as ParameterizedType
    t1 != t2
}
//private val _needPTWrapper = true // For Tests

/**
 * Whether or not the running JVM needs [GenericArrayType] to be wrapped.
 *
 * @see KodeinWrappedType
 */
@Suppress("REDUNDANT_PROJECTION")
private val _needGATWrapper: Boolean by lazy {
    val t1 = (object : TypeReference<Array<List<String>>>() {}).trueType as GenericArrayType
    val t2 = (object : TypeReference<Array<List<String>>>() {}).trueType as GenericArrayType
    t1 != t2
}
//private val _needGATWrapper = true // For Tests

/**
 * An interface that contains a simple [Type] but is parameterized to enable type safety.
 *
 * @param T The type represented by the [type] object.
 */
interface TypeToken<T> {
    /**
     * This type **should** reflect the type `T`.
     */
    val type: Type
}

/**
 * Class used to get a generic type at runtime.
 *
 * @param T The type to extract.
 * @see genericToken
 */
@Suppress("unused")
abstract class TypeReference<T> protected constructor() : TypeToken<T> {

    /**
     * Generic type, unwrapped.
     */
    val trueType: Type

    init {
        val t = javaClass.genericSuperclass as? ParameterizedType ?: throw RuntimeException("Invalid TypeToken; must specify type parameters")

        if (t.rawType != TypeReference::class.java)
            throw RuntimeException("Invalid TypeToken; must directly extend TypeReference")

        trueType = t.actualTypeArguments[0]
    }

    override val type: Type by lazy {
        //  TypeReference cannot create WildcardTypes nor TypeVariables
        when {
            trueType is Class<*> -> trueType
            trueType is ParameterizedType && _needPTWrapper -> KodeinWrappedType(trueType)
            trueType is GenericArrayType && _needGATWrapper -> KodeinWrappedType(trueType)
            else -> trueType
        }
    }
}

/**
 * Function used to get a generic type at runtime.
 *
 * @param T The type to get.
 * @return The type object representing `T`.
 */
inline fun <reified T> genericToken(): TypeToken<T> = (object : TypeReference<T>() {})


/**
 * Function used to get a Class object. Same as T::class but with T being possibly nullable.
 *
 * This should be used only when T is (possibly) nullable. When possible, T::class.java is faster.
 *
 * @param T The type to get.
 * @return The type object representing `T`.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T> typeClass(): Class<T> = (T::class as KClass<*>).java as Class<T>


/**
 * Wraps a ParameterizedType and implements hashCode / equals.
 *
 * This is because some JVM implementation (such as Android 4.4 and earlier) does NOT implement hashcode / equals for
 * ParameterizedType (I know...).
 *
 * @property type The type object to wrap.
 */
class KodeinWrappedType(val type: Type) : Type {

    /**
     * Hash code cash, so that it is computed only once.
     */
    private var _hashCode: Int = 0

    /** @suppress */
    override fun hashCode(): Int {
        if (_hashCode == 0)
            _hashCode = Func.HashCode(type)
        return _hashCode
    }

    /** @suppress */
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Type)
            return false

        if (hashCode() != other.hashCode())
            return false

        return Func.Equals(type, other)
    }

    /**
     * Stringify.
     */
    override fun toString(): String {
        return "KodeinWrappedType{$type}"
    }

    /**
     * Static private functions
     */
    private object Func {

        /**
         * Computes the hash code of a type object.
         *
         * @param type The type whose hash needs to be computed.
         * @return The computed hash code.
         */
        fun HashCode(type: Type): Int = when(type) {
            is Class<*> -> type.hashCode()
            is ParameterizedType -> {
                var hashCode = HashCode(type.rawType)
                for (arg in type.actualTypeArguments)
                    hashCode = hashCode * 31 + HashCode(arg)
                hashCode
            }
            is KodeinWrappedType -> type.hashCode()
            is WildcardType -> {
                var hashCode = 0
                for (arg in type.upperBounds)
                    hashCode = hashCode * 19 + HashCode(arg)
                for (arg in type.lowerBounds)
                    hashCode = hashCode * 17 + HashCode(arg)
                hashCode
            }
            is GenericArrayType -> 53 + HashCode(type.genericComponentType)
            is TypeVariable<*> -> {
                var hashCode = 0
                for (arg in type.bounds)
                    hashCode = hashCode * 29 + HashCode(arg)
                hashCode
            }
            else -> type.hashCode()
        }

        /**
         * @return Whether the two given types are equal.
         */
        fun Equals(left: Type, right: Type): Boolean {
            if (left is KodeinWrappedType)
                return Equals(left.type, right)

            if (right is KodeinWrappedType)
                return Equals(left, right.type)

            if (left.javaClass != right.javaClass)
                return false

            return when (left) {
                is Class<*> -> left == right
                is ParameterizedType -> {
                    right as ParameterizedType
                    Equals(left.rawType, right.rawType) && Equals(left.actualTypeArguments, right.actualTypeArguments)
                }
                is WildcardType -> {
                    right as WildcardType
                    Equals(left.lowerBounds, right.lowerBounds) && Equals(left.upperBounds, right.upperBounds)
                }
                is GenericArrayType -> {
                    right as GenericArrayType
                    Equals(left.genericComponentType, right.genericComponentType)
                }
                is TypeVariable<*> -> {
                    right as TypeVariable<*>
                    Equals(left.bounds, right.bounds)
                }
                else -> left == right
            }
        }

        /**
         * @return Whether the two given arrays of types are equals.
         */
        fun Equals(left: Array<Type>, right: Array<Type>): Boolean {
            if (left.size != right.size)
                return false
            return left.indices.none { !Equals(left[it], right[it]) }
        }
    }
}
