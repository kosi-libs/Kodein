package org.kodein.bindings

import org.kodein.Kodein
import org.kodein.TypeToken
import org.kodein.UnitToken

/**
 * Base class for binding set.
 *
 * @param A The argument type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 */
abstract class BaseMultiBinding<C, A, T: Any> : KodeinBinding<C, A, Set<T>> {
    abstract internal val set: MutableSet<KodeinBinding<C, A, T>>

    override fun factoryName(): String = "bindingSet"
}

private class SetBindingKodein<C>(private val _base: FullBindingKodein<C>) : FullBindingKodein<C> by _base {
    override fun overriddenFactory() = throw IllegalStateException("Cannot access overrides in a Set binding")
    override fun overriddenFactoryOrNull() = throw IllegalStateException("Cannot access overrides in a Set binding")
}


/**
 * Binding that holds multiple factory bindings (e.g. with argument) in a set.
 *
 * @param A The argument type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 * @property elementType The provided type of all bindings in the set.
 */
class ArgSetBinding<C, A, T: Any>(override val contextType: TypeToken<in C>, override val argType: TypeToken<in A>, val elementType: TypeToken<out T>, override val createdType: TypeToken<out Set<T>>) : BaseMultiBinding<C, A, T>() {

    override val set = LinkedHashSet<KodeinBinding<C, A, T>>()

    override fun getFactory(kodein: FullBindingKodein<C>, key: Kodein.Key<C, A, Set<T>>): (A) -> Set<T> {
        val subKodein = SetBindingKodein(kodein)
        val subKey = Kodein.Key(key.contextType, key.argType, elementType, key.tag)
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
class SetBinding<C, T: Any>(override val contextType: TypeToken<in C>, val elementType: TypeToken<out T>, override val createdType: TypeToken<out Set<T>>) : NoArgKodeinBinding<C, Set<T>>, BaseMultiBinding<C, Unit, T>() {

    @Suppress("UNCHECKED_CAST")
    override val set = LinkedHashSet<KodeinBinding<C, Unit, T>>()

    override fun getFactory(kodein: FullBindingKodein<C>, key: Kodein.Key<C, Unit, Set<T>>): (Unit) -> Set<T> {
        val subKodein = SetBindingKodein(kodein)
        val subKey = Kodein.Key(key.contextType, UnitToken, elementType, key.tag)
        val providers = set.map { it.getFactory(subKodein, subKey) }
        return {
            providers.asSequence().map { it.invoke(Unit) }.toSet()
        }
    }

}

/**
 * Second part of the `bind<Type>().inSet() with binding` syntax.
 *
 * @param T The type of the binding in the set.
 */
class TypeBinderInSet<T : Any, S: Any> internal constructor(private val _binder: Kodein.Builder.TypeBinder<T>, private val _colTypeToken: TypeToken<S>) {

    /**
     * Second part of the `bind<Type>().inSet() with binding` syntax.
     *
     * @param binding The binding to add in the set.
     */
    @Suppress("UNCHECKED_CAST")
    infix fun <C> with(binding: KodeinBinding<C, *, T>) {
        val setKey = Kodein.Key(binding.contextType, binding.argType, _colTypeToken, _binder.tag)
        val setBinding = _binder.containerBuilder.bindings[setKey]?.first ?: throw IllegalStateException("No set binding to $setKey")

        setBinding as? BaseMultiBinding<C, *, T> ?: throw IllegalStateException("$setKey is associated to a ${setBinding.factoryName()} while it should be associated with bindingSet")

        (setBinding.set as MutableSet<KodeinBinding<*, *, *>>).add(binding)
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
