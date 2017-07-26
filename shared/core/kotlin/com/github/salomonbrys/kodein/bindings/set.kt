package com.github.salomonbrys.kodein.bindings

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer
import com.github.salomonbrys.kodein.TypeToken

abstract class BaseSetBinding<A, T: Any> : Binding<A, Set<T>> {
    abstract internal val set: MutableSet<Binding<A, T>>
}

class ArgSetBinding<A, T: Any>(override val argType: TypeToken<A>, val elementType: TypeToken<out T>, override val createdType: TypeToken<out Set<T>>) : Binding<A, Set<T>>, BaseSetBinding<A, T>() {

    override val set = LinkedHashSet<Binding<A, T>>()

    override fun factoryName(): String = "bindingSet"

    override fun getInstance(kodein: BindingKodein, key: Kodein.Key<A, Set<T>>, arg: A): Set<T> {
        val subKey = Kodein.Key(Kodein.Bind(elementType, key.bind.tag), key.argType)
        return set.asSequence() .map { it.getInstance(kodein, subKey, arg) } .toSet()
    }
}

class SetBinding<T: Any>(val elementType: TypeToken<out T>, override val createdType: TypeToken<out Set<T>>) : NoArgBinding<Set<T>>, BaseSetBinding<Unit, T>() {

    private val _set = LinkedHashSet<NoArgBinding<T>>()

    @Suppress("UNCHECKED_CAST")
    override val set: MutableSet<Binding<Unit, T>> get() = _set as MutableSet<Binding<Unit, T>>

    override fun factoryName(): String = "bindingSet"

    override fun getInstance(kodein: NoArgBindingKodein, key: Kodein.Key<Unit, Set<T>>): Set<T> {
        val subKey = Kodein.Key(Kodein.Bind(elementType, key.bind.tag), key.argType)
        return _set.asSequence() .map { it.getInstance(kodein, subKey) } .toSet()
    }

}

class TypeBinderInSet<in T : Any> internal constructor(private val _binder: KodeinContainer.Builder.BindBinder<T>, private val _setTypeToken: TypeToken<Set<T>>) {

    @Suppress("UNCHECKED_CAST")
    infix fun <R: T> with(binding: Binding<*, R>) {
        val setKey = Kodein.Key(Kodein.Bind(_setTypeToken, _binder.bind.tag), binding.argType)
        val setBinding = _binder.builder.map[setKey] ?: throw IllegalStateException("No set binding to $setKey")

        setBinding as? BaseSetBinding<Any, T> ?: throw IllegalStateException("$setKey is associated to a ${setBinding.factoryName()} while it should be associated with bindingSet")

        setBinding.set.add(binding as Binding<Any, T>)
    }
}

fun <T: Any> Kodein.Builder.TypeBinder<T>.InSet(setTypeToken: TypeToken<Set<T>>) = TypeBinderInSet(binder, setTypeToken)
