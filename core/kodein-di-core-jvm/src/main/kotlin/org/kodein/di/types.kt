package org.kodein.di

import java.lang.reflect.*
import kotlin.reflect.KClass


@Suppress("unused")
private abstract class WrappingTest<T> {
    val type: Type get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
}

private val _needPTWrapper: Boolean by lazy {
    val t1 = (object : WrappingTest<List<String>>() {}).type as ParameterizedType
    val t2 = (object : WrappingTest<List<String>>() {}).type as ParameterizedType
    t1 != t2
}

private val _needGATWrapper: Boolean by lazy {
    val t1 = (object : WrappingTest<Array<List<String>>>() {}).type as GenericArrayType
    val t2 = (object : WrappingTest<Array<List<String>>>() {}).type as GenericArrayType
    t1 != t2
}

/**
 * Wraps a ParameterizedType and implements hashCode / equals.
 *
 * This is because some JVM implementation (such as Android 4.4 and earlier) does NOT implement hashcode / equals for
 * ParameterizedType (I know...).
 *
 * @property type The type object to wrap.
 */
class KodeinWrappedType(val type: Type) : Type {

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

    /** @suppress */
    override fun toString(): String {
        return "KodeinWrappedType{$type}"
    }

    private object Func {

        fun HashCode(type: Type): Int = when(type) {
            is Class<*> -> type.hashCode()
            is ParameterizedType -> {
                var hashCode = HashCode(type.rawType)
                for (arg in type.actualTypeArguments)
                    hashCode = hashCode * 31 + HashCode(arg)
                hashCode
            }
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

        fun Equals(l: Type, r: Type): Boolean {
            val left = l.javaType
            val right = r.javaType

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

        fun Equals(left: Array<Type>, right: Array<Type>): Boolean {
            if (left.size != right.size)
                return false
            return left.indices.none { !Equals(left[it], right[it]) }
        }
    }
}

/**
 * The JVM type that is wrapped by a TypeToken.
 *
 * Note that this function may return a [KodeinWrappedType].
 * If you only want Java types, you should call [javaType] on the result.
 */
val TypeToken<*>.jvmType: Type get() =
        when (this) {
            is JVMTypeToken -> jvmType.javaType
            is CompositeTypeToken -> main.jvmType
            else -> throw IllegalStateException("${javaClass.simpleName} is not a JVM Type Token")
        }

/**
 * The true Java `Type` if this is a [KodeinWrappedType], or itself if this is already a true Java `Type`.
 */
val Type.javaType: Type get() = (this as? KodeinWrappedType)?.type ?: this


internal abstract class JVMTypeToken<T> : TypeToken<T> {

    abstract val jvmType: Type

    override fun simpleDispString() = jvmType.simpleDispString()
    override fun fullDispString() = jvmType.fullDispString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JVMTypeToken<*>) return false

        if (jvmType != other.jvmType) return false

        return true
    }

    override fun hashCode(): Int {
        return jvmType.hashCode()
    }
}

private fun <T> Class<T>._getClassSuperTT(): TypeToken<in T>? {
    val parent = genericSuperclass ?: return null
    @Suppress("UNCHECKED_CAST")
    return TT(parent) as TypeToken<in T>
}

internal class ParameterizedTypeToken<T>(val trueType: Type) : JVMTypeToken<T>() {
    private var _type: Type? = null

    override fun simpleErasedDispString() = trueType.simpleErasedName()

    override fun fullErasedDispString() = trueType.fullErasedName()

    private val rawType: Class<*>? get() =
        when (trueType) {
            is Class<*> -> trueType
            is ParameterizedType -> trueType.rawType as Class<*>
            else -> null
        }

    override fun getGenericParameters(): Array<TypeToken<*>> {
        val type = _type as? ParameterizedType ?: return rawType?.typeParameters?.map { TT(it.bounds[0]) } ?.toTypedArray() ?: emptyArray()
        return type.actualTypeArguments.map {
            if (it is WildcardType)
                TT(it.upperBounds[0])
            else
                TT(it)
        }.toTypedArray()
    }

    override val jvmType: Type = _type ?: run {
        // TypeReference cannot create WildcardTypes nor TypeVariables
        when {
            !_needPTWrapper && !_needGATWrapper -> trueType
            trueType is Class<*> -> trueType
            _needPTWrapper && trueType is ParameterizedType -> KodeinWrappedType(trueType)
            _needGATWrapper && trueType is GenericArrayType -> KodeinWrappedType(trueType)
            else -> trueType
        }.also { _type = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getRaw(): TypeToken<T>? {
        return rawType?.let { ClassTypeToken(it as Class<T>) }
    }

    override fun isGeneric() = true

    private fun Type._checkIsReified(disp: Any) {
        val jvmType = javaType
        when (jvmType) {
            is Class<*> -> {}
            is ParameterizedType -> for (arg in jvmType.actualTypeArguments) arg._checkIsReified(disp)
            is GenericArrayType -> jvmType.genericComponentType._checkIsReified(disp)
            is WildcardType -> {
                for (arg in jvmType.lowerBounds)
                    arg._checkIsReified(disp)
                for (arg in jvmType.upperBounds)
                    arg._checkIsReified(disp)
            }
            is TypeVariable<*> -> throw IllegalArgumentException("$disp uses a type variable named ${jvmType.name}, therefore, the bound value can never be retrieved.")
            else -> throw IllegalArgumentException("Unknown type ${jvmType.javaClass} $jvmType")
        }
    }

    override fun checkIsReified(disp: Any) = jvmType._checkIsReified(disp)

    @Suppress("UNCHECKED_CAST")
    override fun isWildcard(): Boolean {
        val realType = trueType as? ParameterizedType ?: return false

        var hasWildCard = false
        var hasSpecific = false

        val cls = realType.rawType as Class<*>
        cls.typeParameters.forEachIndexed { i, variable ->
            val argument = realType.actualTypeArguments[i]

            if (argument is WildcardType && variable.bounds.any { it in argument.upperBounds })
                hasWildCard = true
            else
                hasSpecific = true
        }

        return hasWildCard && !hasSpecific
    }

    @Suppress("UNCHECKED_CAST")
    override fun getSuper(): List<TypeToken<*>> {
        val jvmType = jvmType
        val extends = when (jvmType) {
            is ParameterizedType -> (jvmType.rawType as Class<T>)._getClassSuperTT()
            else -> null
        }
        val implements = rawType?.interfaces?.map { TT(it) } ?: emptyList()
        return (extends?.let { listOf(it) } ?: emptyList()) + implements
    }
}

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
inline fun <reified T> generic(): TypeToken<T> = TT((object : TypeReference<T>() {}))

@PublishedApi
internal class ClassTypeToken<T>(override val jvmType: Class<T>) : JVMTypeToken<T>() {

    override fun simpleErasedDispString() = jvmType.simpleErasedName()
    override fun fullErasedDispString() = jvmType.fullErasedName()

    override fun getRaw() = this

    override fun getGenericParameters(): Array<TypeToken<*>> = jvmType.typeParameters.map { TT(it.bounds[0]) } .toTypedArray()

    override fun isGeneric() = false
    override fun isWildcard() = false

    override fun checkIsReified(disp: Any) {}

    override fun getSuper() = (jvmType._getClassSuperTT()?.let { listOf(it) } ?: emptyList()) + jvmType.interfaces.map { TT(it) }

    override fun isAssignableFrom(typeToken: TypeToken<*>): Boolean {
        return if (typeToken is ClassTypeToken)
            jvmType.isAssignableFrom(typeToken.jvmType)
        else
            super.isAssignableFrom(typeToken)
    }
}

@Suppress("UNCHECKED_CAST")
actual inline fun <reified T> erased(): TypeToken<T> = ClassTypeToken((T::class as KClass<*>).java as Class<T>)

/**
 * Gives a [TypeToken] representing the given `Class`.
 */
fun <T> TT(cls: Class<T>): TypeToken<T> = ClassTypeToken(cls)

/**
 * Gives a [TypeToken] representing the given `KClass`.
 */
fun <T: Any> TT(cls: KClass<T>): TypeToken<T> = TT(cls.java)

/**
 * Gives a [TypeToken] representing the given type.
 */
fun TT(type: Type): TypeToken<*> =
    if (type is Class<*>)
        ClassTypeToken(type)
    else
        ParameterizedTypeToken<Any>(type)

/**
 * Gives a [TypeToken] representing the given [TypeReference].
 */
@Suppress("UNCHECKED_CAST")
fun <T> TT(ref: TypeReference<T>): TypeToken<T> = TT(ref.superType) as TypeToken<T>

actual fun <T: Any> TTOf(obj: T): TypeToken<out T> = ClassTypeToken(obj.javaClass)
