package org.kodein.bindings

import org.kodein.*
import org.kodein.internal.BindingKodeinImpl
import org.kodein.internal.synchronizedIfNull

/**
 * Concrete factory: each time an instance is needed, the function [creator] function will be called.
 *
 * @param A The argument type.
 * @param T The created type.
 * @param argType The type of the argument used by this factory.
 * @param createdType The type of objects created by this factory.
 * @property creator The function that will be called each time an instance is requested. Should create a new instance.
 */
class Factory<C, A, T: Any>(override val contextType: TypeToken<in C>, override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, private val creator: BindingKodein<C>.(A) -> T) : KodeinBinding<C, A, T> {

    override fun factoryName() = "factory"

    override fun getFactory(kodein: BindingKodein<C>, key: Kodein.Key<C, A, T>): (A) -> T = { arg -> this.creator(kodein, arg) }
}

/**
 * Concrete multiton: will create one and only one instance for each argument.
 * Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument.
 *
 * @param T The created type.
 * @property argType The type of the argument used for each value can there be a new instance.
 * @property createdType The type of the created object, *used for debug print only*.
 * @property creator The function that will be called the first time an instance is requested. Guaranteed to be called only once per argument. Should create a new instance.
 */
class Multiton<EC, BC, A, T: Any>(override val scope: Scope<EC, BC>, override val contextType: TypeToken<in EC>, override val argType: TypeToken<in A>, override val createdType: TypeToken<out T>, refMaker: RefMaker? = null, private val creator: SimpleBindingKodein<BC>.(A) -> T) : KodeinBinding<EC, A, T> {
    private val _refMaker = refMaker ?: SingletonReference

    private val _scopeKey = Any()

    private data class Key(val scopeKey: Any, val arg: Any?)

    private fun _factoryName(params: List<String>) = buildString {
        append("multiton")
        if (params.isNotEmpty())
            append(params.joinToString(prefix = "(", separator = ", ", postfix = ")"))
    }

    override fun factoryName(): String {
        val params = ArrayList<String>(2)
        if (_refMaker !is SingletonReference)
            params.add("ref = ${TTOf(_refMaker).simpleDispString()}")
        return _factoryName(params)
    }

    override fun factoryFullName(): String {
        val params = ArrayList<String>(2)
        if (_refMaker !is SingletonReference)
            params.add("ref = ${TTOf(_refMaker).fullDispString()}")
        return _factoryName(params)
    }

    override fun getFactory(kodein: BindingKodein<EC>, key: Kodein.Key<EC, A, T>): (A) -> T {
        val bindContext = scope.getBindingContext(kodein.context)
        val registry = scope.getRegistry(kodein.receiver, kodein.context, bindContext)
        return { arg ->
            @Suppress("UNCHECKED_CAST")
            registry.getOrCreate(Key(_scopeKey, arg)) { _refMaker.make { BindingKodeinContextWrap(kodein, bindContext).creator(arg) } } as T
        }
    }

    override fun copyReset(builder: KodeinContainer.Builder) = Multiton(scope, contextType, argType, createdType, _refMaker, creator)
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
class Provider<C, T: Any>(override val contextType: TypeToken<in C>, override val createdType: TypeToken<out T>, val creator: NoArgBindingKodein<C>.() -> T) : NoArgKodeinBinding<C, T> {
    override fun factoryName() = "provider"

    override fun getFactory(kodein: BindingKodein<C>, key: Kodein.Key<C, Unit, T>): (Unit) -> T = { NoArgBindingKodeinWrap(kodein).creator() }
}

/**
 * Singleton Binding: will create an instance on first request and will subsequently always return the same instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object, *used for debug print only*.
 * @param creator The function that will be called the first time an instance is requested. Guaranteed to be called only once. Should create a new instance.
 */
class Singleton<EC, BC, T: Any>(override val scope: Scope<EC, BC>, override val contextType: TypeToken<in EC>, override val createdType: TypeToken<out T>, refMaker: RefMaker? = null, val creator: NoArgSimpleBindingKodein<BC>.() -> T) : NoArgKodeinBinding<EC, T> {
    @Suppress("UNCHECKED_CAST")
    private val _refMaker = refMaker ?: SingletonReference
    private val _scopeKey = Any()

    private fun _factoryName(params: List<String>) = buildString {
        append("singleton")
        if (params.isNotEmpty())
            append(params.joinToString(prefix = "(", separator = ", ", postfix = ")"))
    }

    override fun factoryName(): String {
        val params = ArrayList<String>(2)
        if (_refMaker !is SingletonReference)
            params.add("ref = ${TTOf(_refMaker).simpleDispString()}")
        return _factoryName(params)
    }

    override fun factoryFullName(): String {
        val params = ArrayList<String>(2)
        if (_refMaker !is SingletonReference)
            params.add("ref = ${TTOf(_refMaker).fullDispString()}")
        return _factoryName(params)
    }

    override fun getFactory(kodein: BindingKodein<EC>, key: Kodein.Key<EC, Unit, T>): (Unit) -> T {
        val bindContext = scope.getBindingContext(kodein.context)
        val registry = scope.getRegistry(kodein.receiver, kodein.context, bindContext)
        return {
            @Suppress("UNCHECKED_CAST")
            registry.getOrCreate(_scopeKey) { _refMaker.make { NoArgBindingKodeinWrap(BindingKodeinContextWrap(kodein, bindContext)).creator() } } as T
        }
    }

    override fun copyReset(builder: KodeinContainer.Builder) = Singleton(scope, contextType, createdType, _refMaker, creator)
}

/**
 * Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance.
 *
 * @param T The created type.
 * @param createdType The type of the created object.
 * @param creator The function that will be called as soon as Kodein is ready. Guaranteed to be called only once. Should create a new instance.
 */
class EagerSingleton<T: Any>(builder: KodeinContainer.Builder, override val createdType: TypeToken<out T>, val creator: NoArgSimpleBindingKodein<Any?>.() -> T) : NoArgKodeinBinding<Any?, T> {

    override val contextType = AnyToken

    private @Volatile var _instance: T? = null
    private val _lock = Any()

    private fun _getFactory(kodein: BindingKodein<Any?>): (Unit) -> T {
        return {
            synchronizedIfNull(
                    lock = _lock,
                    predicate = this@EagerSingleton::_instance,
                    ifNotNull = { it },
                    ifNull = {
                        NoArgBindingKodeinWrap(kodein).creator().also { _instance = it }
                    }
            )
        }
    }

    override fun getFactory(kodein: BindingKodein<Any?>, key: Kodein.Key<Any?, Unit, T>) = _getFactory(kodein)

    override fun factoryName() = "eagerSingleton"

    init {
        val key = Kodein.Key(AnyToken, UnitToken, createdType, null)
        builder.onReady { _getFactory(BindingKodeinImpl(this, key, null, null, 0)).invoke(Unit) }
    }

    override fun copyReset(builder: KodeinContainer.Builder) = EagerSingleton(builder, createdType, creator)
}

/**
 * Concrete instance provider: will always return the given instance.
 *
 * @param T The type of the instance.
 * @param createdType The type of the object, *used for debug print only*.
 * @property instance The object that will always be returned.
 */
class InstanceBinding<T: Any>(override val createdType: TypeToken<out T>, val instance: T) : NoArgKodeinBinding<Any?, T> {
    override fun factoryName() = "instance"
    override val contextType = AnyToken

    override fun getFactory(kodein: BindingKodein<Any?>, key: Kodein.Key<Any?, Unit, T>): (Unit) -> T = { this.instance }

    override val description: String get() = "${factoryName()} ( ${createdType.simpleDispString()} ) "
    override val fullDescription: String get() = "${factoryFullName()} ( ${createdType.fullDispString()} ) "
}
