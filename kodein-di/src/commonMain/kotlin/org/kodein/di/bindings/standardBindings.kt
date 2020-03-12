package org.kodein.di.bindings

import org.kodein.di.*
import org.kodein.di.internal.BindingDIImpl
import org.kodein.di.internal.synchronizedIfNull

/**
 * Concrete factory: each time an instance is needed, the function creator function will be called.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param argType The type of the argument used by this factory.
 * @param createdType The type of objects created by this factory.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class Factory<C : Any, A, T: Any>(override val contextType: TypeToken<in C>, override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, private val creator: BindingDI<C>.(A) -> T) : DIBinding<C, A, T> {

    override fun factoryName() = "factory"

    override fun getFactory(di: BindingDI<C>, key: DI.Key<C, A, T>): (A) -> T = { arg -> this.creator(di, arg) }
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("BindingContextedDI<C>"), DeprecationLevel.ERROR)
private typealias BindingContextedKodein<C>  = BindingContextedDI<C>

@Suppress("UNCHECKED_CAST")
private class BindingContextedDI<out C : Any>(val base: BindingDI<*>, override val context: C) : BindingDI<C> by (base as BindingDI<C>)

private data class ScopeKey<out A>(val scopeId: Any, val arg: A)

/**
 * Concrete multiton: will create one and only one instance for each argument.
 * Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument.
 *
 * @param T The created type.
 * @property argType The type of the argument used for each value can there be a new instance.
 * @property createdType The type of the created object, *used for debug print only*.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once per argument. Should create a new instance.
 */
class Multiton<C : Any, A, T: Any>(override val scope: Scope<C>, override val contextType: TypeToken<in C>, override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, refMaker: RefMaker? = null, val sync: Boolean = true, private val creator: SimpleBindingDI<C>.(A) -> T) : DIBinding<C, A, T> {
    private val _refMaker = refMaker ?: SingletonReference

    private val _scopeId = Any()

    private fun factoryName(params: List<String>) = buildString {
        append("multiton")
        if (params.isNotEmpty())
            append(params.joinToString(prefix = "(", separator = ", ", postfix = ")"))
    }

    override fun factoryName(): String {
        val params = ArrayList<String>(2)
        if (_refMaker != SingletonReference)
            params.add("ref = ${TTOf(_refMaker).simpleDispString()}")
        return factoryName(params)
    }

    override fun factoryFullName(): String {
        val params = ArrayList<String>(2)
        if (_refMaker != SingletonReference)
            params.add("ref = ${TTOf(_refMaker).fullDispString()}")
        return factoryName(params)
    }

    override fun getFactory(di: BindingDI<C>, key: DI.Key<C, A, T>): (A) -> T {
        val registry = scope.getRegistry(di.context)
        return { arg ->
            @Suppress("UNCHECKED_CAST")
            registry.getOrCreate(ScopeKey(_scopeId, arg), sync) { _refMaker.make { BindingContextedDI(di, di.context).creator(arg) } } as T
        }
    }

    override val copier = DIBinding.Copier { Multiton(scope, contextType, argType, createdType, _refMaker, sync, creator) }
}

/**
 * Concrete provider: each time an instance is needed, the function [creator] function will be called.
 *
 * A provider is like a [Factory], but without argument.
 *
 * @param T The created type.
 * @param createdType The type of objects created by this provider, *used for debug print only*.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class Provider<C : Any, T: Any>(override val contextType: TypeToken<in C>, override val createdType: TypeToken<out T>, val creator: NoArgBindingDI<C>.() -> T) : NoArgDIBinding<C, T> {
    override fun factoryName() = "provider"

    /**
     * @see [DIBinding.getFactory]
     */
    override fun getFactory(di: BindingDI<C>, key: DI.Key<C, Unit, T>): (Unit) -> T = { NoArgBindingDIWrap(di).creator() }
}

/**
 * Singleton Binding: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object, *used for debug print only*.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
class Singleton<C : Any, T: Any>(override val scope: Scope<C>, override val contextType: TypeToken<in C>, override val createdType: TypeToken<out T>, refMaker: RefMaker? = null, val sync: Boolean = true, val creator: NoArgSimpleBindingDI<C>.() -> T) : NoArgDIBinding<C, T> {
    @Suppress("UNCHECKED_CAST")
    private val _refMaker = refMaker ?: SingletonReference
    private val _scopeKey = ScopeKey(Any(), Unit)

    private fun factoryName(params: List<String>) = buildString {
        append("singleton")
        if (params.isNotEmpty())
            append(params.joinToString(prefix = "(", separator = ", ", postfix = ")"))
    }

    override fun factoryName(): String {
        val params = ArrayList<String>(2)
        if (_refMaker != SingletonReference)
            params.add("ref = ${TTOf(_refMaker).simpleDispString()}")
        return factoryName(params)
    }

    override fun factoryFullName(): String {
        val params = ArrayList<String>(2)
        if (_refMaker != SingletonReference)
            params.add("ref = ${TTOf(_refMaker).fullDispString()}")
        return factoryName(params)
    }

    /**
     * @see [DIBinding.getFactory]
     */
    override fun getFactory(di: BindingDI<C>, key: DI.Key<C, Unit, T>): (Unit) -> T {
        val registry = scope.getRegistry(di.context)
        return {
            @Suppress("UNCHECKED_CAST")
            registry.getOrCreate(_scopeKey, sync) { _refMaker.make { NoArgBindingDIWrap(BindingContextedDI(di, di.context)).creator() } } as T
        }
    }

    override val copier = DIBinding.Copier { Singleton(scope, contextType, createdType, _refMaker, sync, creator) }
}

/**
 * Concrete eager singleton: will create an instance as soon as di is ready (all bindings are set) and will always return this instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object.
 * @param creator The function that will be called as soon as DI is ready. Guaranteed to be called only once. Should create a new instance.
 */
class EagerSingleton<T: Any>(builder: DIContainer.Builder, override val createdType: TypeToken<out T>, val creator: NoArgSimpleBindingDI<Any>.() -> T) : NoArgDIBinding<Any, T> {

    override val contextType = AnyToken

    @Volatile private var _instance: T? = null
    private val _lock = Any()

    private fun getFactory(di: BindingDI<Any>): (Unit) -> T {
        return { _ ->
            synchronizedIfNull(
                    lock = _lock,
                    predicate = this@EagerSingleton::_instance,
                    ifNotNull = { it },
                    ifNull = {
                        NoArgBindingDIWrap(di).creator().also { _instance = it }
                    }
            )
        }
    }

    /**
     * @see [DIBinding.getFactory]
     */
    override fun getFactory(di: BindingDI<Any>, key: DI.Key<Any, Unit, T>) = getFactory(di)

    override fun factoryName() = "eagerSingleton"

    init {
        val key = DI.Key(AnyToken, UnitToken, createdType, null)
        builder.onReady { getFactory(BindingDIImpl(this, key, Any(), 0)).invoke(Unit) }
    }

    override val copier = DIBinding.Copier { builder -> EagerSingleton(builder, createdType, creator) }
}

/**
 * Concrete instance provider: will always return the given instance.
 *
 * @param T The type of the instance.
 * @param createdType The type of the object, *used for debug print only*.
 * @property instance The object that will always be returned.
 */
class InstanceBinding<T: Any>(override val createdType: TypeToken<out T>, val instance: T) : NoArgDIBinding<Any, T> {
    override fun factoryName() = "instance"
    override val contextType = AnyToken

    /**
     * @see [DIBinding.getFactory]
     */
    override fun getFactory(di: BindingDI<Any>, key: DI.Key<Any, Unit, T>): (Unit) -> T = { this.instance }

    override val description: String get() = "${factoryName()} ( ${createdType.simpleDispString()} ) "
    override val fullDescription: String get() = "${factoryFullName()} ( ${createdType.fullDispString()} ) "
}
