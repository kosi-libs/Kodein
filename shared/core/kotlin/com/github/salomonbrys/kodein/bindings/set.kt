package com.github.salomonbrys.kodein.bindings

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer
import com.github.salomonbrys.kodein.TypeToken

/**
 * Base class for binding set.
 *
 * @param A The argument type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 */
abstract class BaseMultiBinding<A, T: Any, C: Any> : Binding<A, C> {
    abstract internal val set: MutableSet<Binding<A, T>>

    override fun factoryName(): String = "bindingSet"
}

/**
 * Binding that holds multiple factory bindings (e.g. with argument) in a set.
 *
 * @param A The argument type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 * @property elementType The provided type of all bindings in the set.
 */
class ArgSetBinding<A, T: Any>(override val argType: TypeToken<A>, val elementType: TypeToken<out T>, override val createdType: TypeToken<out Set<T>>) : Binding<A, Set<T>>, BaseMultiBinding<A, T, Set<T>>() {

    override val set = LinkedHashSet<Binding<A, T>>()

    override fun getInstance(kodein: BindingKodein, key: Kodein.Key<A, Set<T>>, arg: A): Set<T> {
        val subKey = Kodein.Key(Kodein.Bind(elementType, key.bind.tag), key.argType)
        return set.asSequence() .map { it.getInstance(kodein, subKey, arg) } .toSet()
    }
}

/**
 * Binding that holds multiple factory bindings (e.g. with argument) in a set.
 *
 * @param T The provided type of all bindings in the set.
 * @property elementType The provided type of all bindings in the set.
 */
class SetBinding<T: Any>(val elementType: TypeToken<out T>, override val createdType: TypeToken<out Set<T>>) : NoArgBinding<Set<T>>, BaseMultiBinding<Unit, T, Set<T>>() {

    private val _set = LinkedHashSet<NoArgBinding<T>>()

    @Suppress("UNCHECKED_CAST")
    override val set: MutableSet<Binding<Unit, T>> get() = _set as MutableSet<Binding<Unit, T>>

    override fun getInstance(kodein: NoArgBindingKodein, key: Kodein.Key<Unit, Set<T>>): Set<T> {
        val subKey = Kodein.Key(Kodein.Bind(elementType, key.bind.tag), key.argType)
        return _set.asSequence() .map { it.getInstance(kodein, subKey) } .toSet()
    }

}

/**
 * Second part of the `bind<Type>().inSet() with binding` syntax.
 *
 * @param T The type of the binding in the set.
 */
class TypeBinderInSet<in T : Any, C: Any> internal constructor(private val _binder: KodeinContainer.Builder.BindBinder<T>, private val _colTypeToken: TypeToken<C>) {

    /**
     * Second part of the `bind<Type>().inSet() with binding` syntax.
     *
     * @param binding The binding to add in the set.
     */
    @Suppress("UNCHECKED_CAST")
    infix fun with(binding: Binding<*, out T>) {
        val setKey = Kodein.Key(Kodein.Bind(_colTypeToken, _binder.bind.tag), binding.argType)
        val setBinding = _binder.builder.map[setKey] ?: throw IllegalStateException("No set binding to $setKey")

        setBinding as? BaseMultiBinding<Any, T, C> ?: throw IllegalStateException("$setKey is associated to a ${setBinding.factoryName()} while it should be associated with bindingSet")

        setBinding.set.add(binding as Binding<Any, T>)
    }
}

/**
 * Allows to bind in an existing set binding (rather than directly as a new binding).
 *
 * First part of the `bind<Type>().inSet() with binding` syntax.
 *
 * @param setTypeToken The type of the bound set.
 */
fun <T: Any> Kodein.Builder.TypeBinder<T>.InSet(setTypeToken: TypeToken<Set<T>>) = TypeBinderInSet(binder, setTypeToken)
