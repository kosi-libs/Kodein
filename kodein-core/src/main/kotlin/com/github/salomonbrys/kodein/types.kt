package com.github.salomonbrys.kodein

import java.lang.reflect.*
import kotlin.reflect.KClass


/**
 * Internal class used ONLY to test if wrapping is needed
 */
private abstract class _Test<T> {
    val type: Type get() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
}

/**
 * Whether or not the running JVM needs [ParameterizedType] to be wrapped.
 *
 * @see KodeinWrappedType
 */
private val _needPTWrapper: Boolean by lazy {
    val t1 = (object : _Test<List<String>>() {}).type as ParameterizedType
    val t2 = (object : _Test<List<String>>() {}).type as ParameterizedType
    t1 != t2
}


/**
 * Whether or not the running JVM needs [GenericArrayType] to be wrapped.
 *
 * @see KodeinWrappedType
 */
private val _needGATWrapper: Boolean by lazy {
    val t1 = (object : _Test<Array<List<String>>>() {}).type as GenericArrayType
    val t2 = (object : _Test<Array<List<String>>>() {}).type as GenericArrayType
    t1 != t2
}

/**
 * Checks that a type is reified. Meaning that it is not or does not reference a [TypeVariable].
 *
 * @param disp An object to print if the check fails. *For debug print only*.
 * @param type The type to check.
 * @throws IllegalArgumentException If the type does contain a [TypeVariable].
 */
private fun _checkIsReified(disp: Any, type: Type) {
    when (type) {
        is Class<*> -> {}
        is ParameterizedType -> for (arg in type.actualTypeArguments) _checkIsReified(disp, arg)
        is GenericArrayType -> _checkIsReified(disp, type.genericComponentType)
        is KodeinWrappedType -> _checkIsReified(disp, type.type)
        is WildcardType -> {
            for (arg in type.lowerBounds)
                _checkIsReified(disp, arg)
            for (arg in type.upperBounds)
                _checkIsReified(disp, arg)
        }
        is TypeVariable<*> -> throw IllegalArgumentException("$disp uses a type variable named ${type.name}, therefore, the bound value can never be retrieved.")
        else -> throw IllegalArgumentException("Unknown type ${type.javaClass} $type")
    }
}

internal abstract class JVMTypeToken<T> : TypeToken<T> {

    abstract fun type(): Type

    override fun simpleDispString() = type().simpleDispString()
    override fun fullDispString() = type().fullDispString()

    override fun checkIsReified(disp: Any) = _checkIsReified(disp, type())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JVMTypeToken<*>) return false

        if (type() != other.type()) return false

        return true
    }

    override fun hashCode(): Int {
        return type().hashCode()
    }
}

val TypeToken<*>.jvmType get() = (this as? JVMTypeToken<*>)?.type() ?: throw IllegalStateException("Not a JVM Type Token")

internal abstract class ATypeTypeToken<T> : JVMTypeToken<T>() {
    abstract val trueType: Type

    private var _type: Type? = null

    override fun simpleErasedDispString() = trueType.simpleErasedName()

    override fun fullErasedDispString() = trueType.fullErasedName()

    private val rawType: Class<*> get() = ((((trueType as? KodeinWrappedType)?.type ?: trueType) as? ParameterizedType)?.rawType ?: trueType) as Class<*>

    override fun type(): Type = _type ?: run {
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
    override fun getRaw(): TypeToken<T> {
        return ClassTypeToken(rawType as Class<T>)
    }

    override fun isGeneric() = ((trueType as? KodeinWrappedType)?.type ?: trueType) is ParameterizedType

    @Suppress("UNCHECKED_CAST")
    override fun isWildcard(): Boolean {
        val realType = ((trueType as? KodeinWrappedType)?.type ?: trueType) as? ParameterizedType ?: return false

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
    override fun getSuper(): TypeToken<in T>? = trueType._getTypeSuperTT()

    override fun getInterfaces() = rawType.interfaces.map { TT(it) }
}

/**
 * Class used to get a generic type at runtime.
 *
 * @param T The type to extract.
 * @see generic
 */
@PublishedApi
internal abstract class TypeReference<T> : ATypeTypeToken<T>() {

    /**
     * Generic type, unwrapped.
     */
    override val trueType: Type = (javaClass.genericSuperclass as? ParameterizedType ?: throw RuntimeException("Invalid TypeToken; must specify type parameters")).actualTypeArguments[0]
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

/**
 * Function used to get a generic type at runtime.
 *
 * @param T The type to get.
 * @return The type object representing `T`.
 */
inline fun <reified T> generic(): TypeToken<T> = (object : TypeReference<T>() {})

internal class TypeTypeToken<T>(override val trueType: Type) : ATypeTypeToken<T>()

private fun <T> Class<T>._getClassSuperTT(): TypeToken<in T>? {
    if (this == Unit::class.java)
        return null
    val parent = genericSuperclass
    if (parent == null || parent == Unit::class.java)
        return null
    @Suppress("UNCHECKED_CAST")
    return TT(parent) as TypeToken<in T>
}

@Suppress("UNCHECKED_CAST")
private fun <T> Type._getTypeSuperTT(): TypeToken<in T>? =
    when (this) {
        is ParameterizedType -> (rawType as Class<T>)._getClassSuperTT()
        is KodeinWrappedType -> this.type._getTypeSuperTT()
        is Class<*> -> (this as Class<T>)._getClassSuperTT() // This should never happen
        else -> null
    }

@PublishedApi
internal class ClassTypeToken<T>(private val _type: Class<T>) : JVMTypeToken<T>() {

    override fun type() = _type

    override fun simpleErasedDispString() = type().simpleErasedName()
    override fun fullErasedDispString() = type().fullErasedName()

    override fun getRaw() = this

    override fun isGeneric() = false
    override fun isWildcard() = false

    override fun checkIsReified(disp: Any) {}

    override fun getSuper() = _type._getClassSuperTT()

    override fun getInterfaces() = _type.interfaces.map { TT(it) }
}

/**
 * Function used to get a TypeToken representing the provided type **being erased**.
 *
 * @param T The type to get.
 * @return The type object representing `T`.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T> erased(): TypeToken<T> = ClassTypeToken((T::class as KClass<*>).java as Class<T>)

/**
 * Gives a [TypeToken] representing the given class.
 */
fun <T> TT(cls: Class<T>): TypeToken<T> = ClassTypeToken(cls)

fun <T: Any> TT(cls: KClass<T>): TypeToken<T> = TT(cls.java)

/**
 * Gives a [TypeToken] representing the given type.
 */
fun TT(type: Type): TypeToken<*> =
    if (type is Class<*>)
        ClassTypeToken(type)
    else
        TypeTypeToken<Any>(type)

/**
 * Gives a [TypeToken] representing the *erased* type of the given object.
 */
fun <T: Any> TTOf(obj: T): TypeToken<out T> = ClassTypeToken(obj.javaClass)
