package org.kodein.di

import org.kodein.type.TypeToken
import org.kodein.type.typeToken
import java.lang.reflect.*
import kotlin.reflect.KClass


//@Suppress("unused")
//private abstract class WrappingTest<T> {
//    val type: Type get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
//}

//@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("DIWrappedType"), DeprecationLevel.ERROR)
//typealias KodeinWrappedType = DIWrappedType
/**
 * Wraps a ParameterizedType and implements hashCode / equals.
 *
 * This is because some JVM implementation (such as Android 4.4 and earlier) does NOT implement hashcode / equals for
 * ParameterizedType (I know...).
 *
 * @property type The type object to wrap.
 */
//class DIWrappedType(val type: Type) : Type {
//
//    private var _hashCode: Int = 0
//
//    /** @suppress */
//    override fun hashCode(): Int {
//        if (_hashCode == 0)
//            _hashCode = Func.HashCode(type)
//        return _hashCode
//    }
//
//    /** @suppress */
//    override fun equals(other: Any?): Boolean {
//        if (other == null || other !is Type)
//            return false
//
//        if (hashCode() != other.hashCode())
//            return false
//
//        return Func.Equals(type, other)
//    }
//
//    /** @suppress */
//    override fun toString(): String {
//        return "DIWrappedType{$type}"
//    }
//
//    private object Func {
//
//        fun HashCode(type: Type): Int = when(type) {
//            is Class<*> -> type.hashCode()
//            is ParameterizedType -> {
//                var hashCode = HashCode(type.rawType)
//                for (arg in type.actualTypeArguments)
//                    hashCode = hashCode * 31 + HashCode(arg)
//                hashCode
//            }
//            is WildcardType -> {
//                var hashCode = 0
//                for (arg in type.upperBounds)
//                    hashCode = hashCode * 19 + HashCode(arg)
//                for (arg in type.lowerBounds)
//                    hashCode = hashCode * 17 + HashCode(arg)
//                hashCode
//            }
//            is GenericArrayType -> 53 + HashCode(type.genericComponentType)
//            is TypeVariable<*> -> {
//                var hashCode = 0
//                for (arg in type.bounds)
//                    hashCode = hashCode * 29 + HashCode(arg)
//                hashCode
//            }
//            else -> type.hashCode()
//        }
//
//        fun Equals(l: Type, r: Type): Boolean {
//            val left = l.javaType
//            val right = r.javaType
//
//            if (left.javaClass != right.javaClass)
//                return false
//
//            return when (left) {
//                is Class<*> -> left == right
//                is ParameterizedType -> {
//                    right as ParameterizedType
//                    Equals(left.rawType, right.rawType) && Equals(left.actualTypeArguments, right.actualTypeArguments)
//                }
//                is WildcardType -> {
//                    right as WildcardType
//                    Equals(left.lowerBounds, right.lowerBounds) && Equals(left.upperBounds, right.upperBounds)
//                }
//                is GenericArrayType -> {
//                    right as GenericArrayType
//                    Equals(left.genericComponentType, right.genericComponentType)
//                }
//                is TypeVariable<*> -> {
//                    right as TypeVariable<*>
//                    Equals(left.bounds, right.bounds)
//                }
//                else -> left == right
//            }
//        }
//
//        fun Equals(left: Array<Type>, right: Array<Type>): Boolean {
//            if (left.size != right.size)
//                return false
//            return left.indices.none { !Equals(left[it], right[it]) }
//        }
//    }
//}

/**
 * The true Java `Type` if this is a [DIWrappedType], or itself if this is already a true Java `Type`.
 */
//val Type.javaType: Type get() = (this as? DIWrappedType)?.type ?: this

/**
 * Class used to get a generic type at runtime.
 *
 * @param T The type to extract.
 * @see generic
 */
abstract class TypeReference<T> {

    /**
     * Generic type, unwrapped.
     */
    val superType: Type = (javaClass.genericSuperclass as? ParameterizedType ?: throw RuntimeException("Invalid TypeToken; must specify type parameters")).actualTypeArguments[0]
}

/**
 * Function used to get a generic type at runtime.
 *
 * @param T The type to get.
 * @return The type object representing `T`.
 */
@Suppress("UNCHECKED_CAST")
@Deprecated(DEPRECATED_KODEIN_7X)
inline fun <reified T : Any> generic(): TypeToken<T> = org.kodein.type.generic<T>()

/**
 * Gives a [TypeToken] representing the given `Class`.
 */
fun <T> TT(cls: Class<T>): TypeToken<T> = org.kodein.type.erased(cls)

/**
 * Gives a [TypeToken] representing the given type.
 */
fun TT(type: Type): TypeToken<*> = typeToken(type)

/**
 * Gives a [TypeToken] representing the given [TypeReference].
 */
@Suppress("UNCHECKED_CAST")
fun <T> TT(ref: TypeReference<T>): TypeToken<T> = TT(ref.superType) as TypeToken<T>