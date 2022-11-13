package org.kodein.di.bindings

import org.kodein.di.*
import org.kodein.di.internal.DIBuilderImpl
import org.kodein.type.TypeToken

/**
 * Base class for binding set.
 *
 * @param C The context type of all bindings in the set.
 * @param A The argument type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 */
public abstract class BaseMultiBinding<C : Any, A, T : Any> : DIBinding<C, A, Set<T>> {
    internal abstract val set: MutableSet<DIBinding<C, A, T>>

    override fun factoryName(): String = "bindingSet"
}

private class SetBindingDI<out C : Any>(private val _base: BindingDI<C>) : BindingDI<C> by _base {
    override fun overriddenFactory() = throw IllegalStateException("Cannot access overrides in a Set binding")
    override fun overriddenFactoryOrNull() = throw IllegalStateException("Cannot access overrides in a Set binding")
}


/**
 * Binding that holds multiple factory bindings (e.g. with argument) in a set.
 *
 * @param C The context type of all bindings in the set.
 * @param A The argument type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 */
public class ArgSetBinding<C : Any, A, T : Any>(
    override val contextType: TypeToken<in C>,
    override val argType: TypeToken<in A>,
    private val _elementType: TypeToken<out T>,
    override val createdType: TypeToken<out Set<T>>
) : BaseMultiBinding<C, A, T>() {

    override val set = LinkedHashSet<DIBinding<C, A, T>>()

    override fun getFactory(key: DI.Key<C, A, Set<T>>, di: BindingDI<C>): (A) -> Set<T> {
        var lateInitFactories: List<(A) -> T>? = null
        return { arg ->
            val factories = lateInitFactories ?: run {
                val subKey = DI.Key(key.contextType, key.argType, _elementType, key.tag)
                set.map { it.getFactory(subKey, SetBindingDI(di)) }
            }.also { lateInitFactories = it }
            factories.asSequence().map { it.invoke(arg) }.toSet()
        }
    }

    override val copier: DIBinding.Copier<C, A, Set<T>> = DIBinding.Copier { builder ->
        ArgSetBinding(contextType, argType, _elementType, createdType).also {
            it.set.addAll(set.map { it.copier?.copy(builder) ?: it })
        }
    }
}

/**
 * Binding that holds multiple factory bindings (e.g. with argument) in a set.
 *
 * @param C The context type of all bindings in the set.
 * @param T The provided type of all bindings in the set.
 */
public class SetBinding<C : Any, T : Any>(
    override val contextType: TypeToken<in C>,
    private val _elementType: TypeToken<out T>,
    override val createdType: TypeToken<out Set<T>>
) : NoArgDIBinding<C, Set<T>>, BaseMultiBinding<C, Unit, T>() {

    override val set = LinkedHashSet<DIBinding<C, Unit, T>>()

    override fun getFactory(key: DI.Key<C, Unit, Set<T>>, di: BindingDI<C>): (Unit) -> Set<T> {
        var lateInitProviders: List<(Unit) -> T>? = null
        return { _ ->
            val providers = lateInitProviders ?: run {
                val subKey = DI.Key(key.contextType, TypeToken.Unit, _elementType, key.tag)
                val subDI = SetBindingDI(di)
                set.map { it.getFactory(subKey, subDI) }
            }.also { lateInitProviders = it }
            providers.asSequence().map { it.invoke(Unit) }.toSet()
        }
    }

    override val copier: DIBinding.Copier<C, Unit, Set<T>> = DIBinding.Copier { builder ->
        SetBinding(contextType, _elementType, createdType).also {
            it.set.addAll(set.map { it.copier?.copy(builder) ?: it })
        }
    }
}

/**
 * Second part of the `bind<Type>().inSet() with binding` syntax.
 *
 * @param T The type of the binding in the set.
 */
@Deprecated("TypeBinderInSet must be replaced by the use of bindSet / inBindSet / addInBindSet builders.")
public class TypeBinderInSet<in T : Any, S : Any> internal constructor(
    private val _binder: DI.Builder.TypeBinder<T>,
    private val _colTypeToken: TypeToken<S>
) {

    /**
     * Second part of the `bind<Type>().inSet() with binding` syntax.
     *
     * @param C The context type of the binding.
     * @param binding The binding to add in the set.
     */
    @Suppress("UNCHECKED_CAST")
    public infix fun <C : Any> with(binding: DIBinding<in C, *, out T>) {
        _binder as DIBuilderImpl.TypeBinder
        val setKey = DI.Key(binding.contextType, binding.argType, _colTypeToken, _binder.tag)
        val setBinding = _binder.containerBuilder.bindingsMap[setKey]?.first()
            ?: throw IllegalStateException("No set binding to $setKey")

        setBinding.binding as? BaseMultiBinding<C, *, T>
            ?: throw IllegalStateException("$setKey is associated to a ${setBinding.binding.factoryName()} while it should be associated with bindingSet")

        (setBinding.binding.set as MutableSet<DIBinding<*, *, *>>).add(binding)
    }
}

/**
 * Allows to bind in an existing set binding (rather than directly as a new binding).
 *
 * First part of the `bind<Type>().inSet() with binding` syntax.
 *
 * @param T The provided type of all bindings in the set.
 * @param setTypeToken The type of the bound set.
 */
@Suppress("FunctionName", "deprecation")
@Deprecated("InSet must be replaced by the use of bindSet / inBindSet / addInBindSet builders.")
public fun <T : Any> DI.Builder.TypeBinder<T>.InSet(setTypeToken: TypeToken<Set<T>>): TypeBinderInSet<T, Set<T>> =
    TypeBinderInSet(this, setTypeToken)
