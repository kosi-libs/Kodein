package org.kodein.bindings

import org.kodein.Kodein
import org.kodein.TypeToken

/**
 * Base class for binding set.
 *
 * @param A The argument type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 */
abstract class BaseMultiBinding<A, T: Any, C: Any> : KodeinBinding<A, C> {
    abstract internal val set: MutableSet<KodeinBinding<A, T>>

    override fun factoryName(): String = "bindingSet"
}

private class SetBindingKodein(private val _base: BindingKodein) : BindingKodein by _base {
    override fun overriddenFactory() = throw IllegalStateException("Cannot access overrides in a Set binding")
    override fun overriddenFactoryOrNull() = throw IllegalStateException("Cannot access overrides in a Set binding")
}

private class SetNoArgBindingKodein(private val _base: NoArgBindingKodein) : NoArgBindingKodein by _base {
    override fun overriddenProvider() = throw IllegalStateException("Cannot access overrides in a Set binding")
    override fun overriddenProviderOrNull() = throw IllegalStateException("Cannot access overrides in a Set binding")
}


/**
 * Binding that holds multiple factory bindings (e.g. with argument) in a set.
 *
 * @param A The argument type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 * @property elementType The provided type of all bindings in the set.
 */
class ArgSetBinding<A, T: Any>(override val argType: TypeToken<in A>, val elementType: TypeToken<out T>, override val createdType: TypeToken<out Set<T>>) : KodeinBinding<A, Set<T>>, BaseMultiBinding<A, T, Set<T>>() {

    override val set = LinkedHashSet<KodeinBinding<A, T>>()

    override fun getFactory(kodein: BindingKodein, key: Kodein.Key<A, Set<T>>): (A) -> Set<T> {
        val subKodein = SetBindingKodein(kodein)
        val subKey = Kodein.Key(Kodein.Bind(elementType, key.bind.tag), key.argType)
        val factories = set.map { it.getFactory(subKodein, subKey) }
        return { arg ->
            factories.asSequence().map { it.invoke(arg) } .toSet()
        }
    }
}

/**
 * Binding that holds multiple factory bindings (e.g. with argument) in a set.
 *
 * @param T The provided type of all bindings in the set.
 * @property elementType The provided type of all bindings in the set.
 */
class SetBinding<T: Any>(val elementType: TypeToken<out T>, override val createdType: TypeToken<out Set<T>>) : NoArgKodeinBinding<Set<T>>, BaseMultiBinding<Unit, T, Set<T>>() {

    private val _set = LinkedHashSet<NoArgKodeinBinding<T>>()

    @Suppress("UNCHECKED_CAST")
    override val set: MutableSet<KodeinBinding<Unit, T>> get() = _set as MutableSet<KodeinBinding<Unit, T>>

    override fun getProvider(kodein: NoArgBindingKodein, bind: Kodein.Bind<Set<T>>): () -> Set<T> {
        val subKodein = SetNoArgBindingKodein(kodein)
        val subBind = Kodein.Bind(elementType, bind.tag)
        val providers = _set.map { it.getProvider(subKodein, subBind) }
        return {
            providers.asSequence().map { it.invoke() }.toSet()
        }
    }

}

/**
 * Second part of the `bind<Type>().inSet() with binding` syntax.
 *
 * @param T The type of the binding in the set.
 */
class TypeBinderInSet<in T : Any, C: Any> internal constructor(private val _binder: Kodein.Builder.TypeBinder<T>, private val _colTypeToken: TypeToken<C>) {

    /**
     * Second part of the `bind<Type>().inSet() with binding` syntax.
     *
     * @param binding The binding to add in the set.
     */
    @Suppress("UNCHECKED_CAST")
    infix fun with(binding: KodeinBinding<*, out T>) {
        val setKey = Kodein.Key(Kodein.Bind(_colTypeToken, _binder.bind.tag), binding.argType)
        val setBinding = _binder.containerBuilder.bindings[setKey] ?: throw IllegalStateException("No set binding to $setKey")

        setBinding as? BaseMultiBinding<Any, T, C> ?: throw IllegalStateException("$setKey is associated to a ${setBinding.factoryName()} while it should be associated with bindingSet")

        setBinding.set.add(binding as KodeinBinding<Any, T>)
    }
}

/**
 * Allows to bind in an existing set binding (rather than directly as a new binding).
 *
 * First part of the `bind<Type>().inSet() with binding` syntax.
 *
 * @param setTypeToken The type of the bound set.
 */
fun <T: Any> Kodein.Builder.TypeBinder<T>.InSet(setTypeToken: TypeToken<Set<T>>) = TypeBinderInSet(this, setTypeToken)
