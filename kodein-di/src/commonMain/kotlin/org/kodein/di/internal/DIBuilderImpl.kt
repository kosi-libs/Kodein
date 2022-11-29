@file:Suppress("FunctionName")

package org.kodein.di.internal

import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindings.ArgSetBinding
import org.kodein.di.bindings.BaseMultiBinding
import org.kodein.di.bindings.ContextTranslator
import org.kodein.di.bindings.DIBinding
import org.kodein.di.bindings.ExternalSource
import org.kodein.di.bindings.InstanceBinding
import org.kodein.di.bindings.NoScope
import org.kodein.di.bindings.Provider
import org.kodein.di.bindings.Scope
import org.kodein.di.bindings.SetBinding
import org.kodein.type.TypeToken
import org.kodein.type.erasedComp

internal open class DIBuilderImpl internal constructor(
    private val moduleName: String?,
    private val prefix: String,
    internal val importedModules: MutableSet<String>,
    override val containerBuilder: DIContainerBuilderImpl
) : DI.Builder {

    override val contextType = TypeToken.Any

    override val scope: Scope<Any?> get() = NoScope() // Recreating a new NoScope every-time *on purpose*!

    override val explicitContext: Boolean get() = false

    inner class TypeBinder<T : Any> internal constructor(
        val type: TypeToken<out T>,
        val tag: Any?,
        val overrides: Boolean?
    ) : DI.Builder.TypeBinder<T> {
        internal val containerBuilder get() = this@DIBuilderImpl.containerBuilder

        override infix fun <C : Any, A> with(binding: DIBinding<in C, in A, out T>) = containerBuilder.bind(
            DI.Key(binding.contextType, binding.argType, type, tag),
            binding,
            moduleName,
            overrides
        )
    }

    /**
     * @see [DI.Builder.DelegateBinder]
     */
    inner class DelegateBinder<T : Any> internal constructor(
        private val builder: DI.Builder,
        private val bindType: TypeToken<out T>,
        private val bindTag: Any? = null,
        private val overrides: Boolean? = null
    ) : DI.Builder.DelegateBinder<T>() {
        /**
         * @see [DI.Builder.DelegateBinder.To]
         */
        override fun <A : T> To(type: TypeToken<A>, tag: Any?) {
            builder.Bind(bindTag, overrides, Provider(builder.contextType, bindType) { Instance(type, tag) })
        }
    }

    inner class DirectBinder internal constructor(private val _tag: Any?, private val _overrides: Boolean?) :
        DI.Builder.DirectBinder

    inner class ConstantBinder internal constructor(private val _tag: Any, private val _overrides: Boolean?) :
        DI.Builder.ConstantBinder {
        override fun <T : Any> With(valueType: TypeToken<out T>, value: T) =
            Bind(tag = _tag, overrides = _overrides, binding = InstanceBinding(valueType, value))
    }

    @Suppress("unchecked_cast")
    inner class SetBinder<T : Any> internal constructor(
        private val setBindingTag: Any? = null,
        private val setBindingType: TypeToken<out T>,
        setBindingOverrides: Boolean? = null,
        addSetBindingToContainer: Boolean = true,
    ) : DI.Builder.SetBinder<T> {

        private val setBinding: BaseMultiBinding<*, *, T> by lazy {
            val setType = erasedComp(Set::class, setBindingType) as TypeToken<Set<T>>
            val setKey = DI.Key(TypeToken.Any, TypeToken.Unit, setType, setBindingTag)

            val setBinding = containerBuilder.bindingsMap[setKey]?.first()
                ?: throw IllegalStateException("No set binding to $setKey")
            setBinding.binding as? BaseMultiBinding<*, *, T>
                ?: throw IllegalStateException("$setKey is associated to a ${setBinding.binding.factoryName()} while it should be associated with bindingSet")
        }

        init {
            if (addSetBindingToContainer) {
                Bind(
                    tag = setBindingTag,
                    overrides = setBindingOverrides,
                    binding = SetBinding(
                        contextType = TypeToken.Any,
                        _elementType = setBindingType,
                        createdType = erasedComp(Set::class, setBindingType) as TypeToken<Set<T>>
                    )
                )
            }
        }

        override fun add(createBinding: () -> DIBinding<*, *, out T>) {
            val binding = createBinding()
            (setBinding.set as MutableSet<DIBinding<*, *, *>>).add(binding)
        }

        override fun bind(tag: Any?, overrides: Boolean?, createBinding: () -> DIBinding<*, *, out T>) {
            val binding = createBinding()
            (setBinding.set as MutableSet<DIBinding<*, *, *>>).add(binding)

            Bind(tag = tag, overrides = overrides, binding = binding)
        }
    }

    @Suppress("unchecked_cast")
    inner class ArgSetBinder<A : Any, T : Any>(
        private val setBindingTag: Any? = null,
        setBindingOverrides: Boolean? = null,
        private val setBindingArgType: TypeToken<in A>,
        private val setBindingType: TypeToken<out T>,
        addSetBindingToContainer: Boolean = true,
    ) : DI.Builder.ArgSetBinder<A, T> {

        private val setBinding: BaseMultiBinding<*, in A, out T> by lazy {
            val setType = erasedComp(Set::class, setBindingType) as TypeToken<Set<T>>
            val setKey = DI.Key(TypeToken.Any, setBindingArgType, setType, setBindingTag)

            val setBinding = containerBuilder.bindingsMap[setKey]?.first()
                ?: throw IllegalStateException("No set binding to $setKey")
            setBinding.binding as? BaseMultiBinding<*, A, T>
                ?: throw IllegalStateException("$setKey is associated to a ${setBinding.binding.factoryName()} while it should be associated with bindingSet")
        }

        init {
            if (addSetBindingToContainer) {
                Bind(
                    tag = setBindingTag,
                    overrides = setBindingOverrides,
                    binding = ArgSetBinding(
                        TypeToken.Any,
                        setBindingArgType,
                        setBindingType,
                        erasedComp(Set::class, setBindingType) as TypeToken<Set<T>>
                    )
                )
            }
        }

        override fun add(createBinding: () -> DIBinding<*, in A, out T>) {
            val binding = createBinding()
            (setBinding.set as MutableSet<DIBinding<*, *, *>>).add(binding)
        }

        override fun bind(tag: Any?, overrides: Boolean?, createBinding: () -> DIBinding<*, in A, out T>) {
            val binding = createBinding()
            (setBinding.set as MutableSet<DIBinding<*, *, *>>).add(binding)

            Bind(tag = tag, overrides = overrides, binding = binding)
        }
    }

    @Suppress("FunctionName")
    override fun <T : Any> Bind(
        type: TypeToken<out T>,
        tag: Any?,
        overrides: Boolean?
    ) = TypeBinder(type, tag, overrides)

    @Suppress("FunctionName")
    override fun <T : Any> Bind(
        tag: Any?,
        overrides: Boolean?,
        binding: DIBinding<*, *, T>
    ) {
        containerBuilder.bind(
            key = DI.Key(binding.contextType, binding.argType, binding.createdType, tag = tag),
            binding = binding,
            fromModule = moduleName,
            overrides = overrides,
        )
    }

    override fun <T : Any> BindInSet(
        tag: Any?,
        overrides: Boolean?,
        type: TypeToken<out T>,
        creator: DI.Builder.SetBinder<T>.() -> Unit
    ) = creator(SetBinder(tag, type, overrides))

    override fun <T : Any> InBindSet(
        tag: Any?,
        overrides: Boolean?,
        type: TypeToken<out T>,
        creator: DI.Builder.SetBinder<T>.() -> Unit
    ) = creator(
        SetBinder(
            setBindingTag = tag,
            setBindingType = type,
            setBindingOverrides = overrides,
            addSetBindingToContainer = false
        )
    )

    override fun <A : Any, T : Any> BindInArgSet(
        tag: Any?,
        overrides: Boolean?,
        argType: TypeToken<in A>,
        type: TypeToken<out T>,
        creator: DI.Builder.ArgSetBinder<A, T>.() -> Unit
    ) = creator(
        ArgSetBinder(
            setBindingTag = tag,
            setBindingOverrides = overrides,
            setBindingArgType = argType,
            setBindingType = type
        )
    )

    override fun <A : Any, T : Any> InBindArgSet(
        tag: Any?,
        overrides: Boolean?,
        argType: TypeToken<in A>,
        type: TypeToken<out T>,
        creator: DI.Builder.ArgSetBinder<A, T>.() -> Unit
    ) = creator(
        ArgSetBinder(
            setBindingTag = tag,
            setBindingOverrides = overrides,
            setBindingArgType = argType,
            setBindingType = type,
            addSetBindingToContainer = false
        )
    )

    @Deprecated("Use inBindSet { add { BINDING } } instead.")
    @Suppress("deprecation")
    override fun <T : Any> BindSet(
        tag: Any?,
        overrides: Boolean?,
        binding: DIBinding<*, *, T>,
    ) = AddBindInSet(tag = tag, overrides = overrides, binding = binding)

    @Suppress("unchecked_cast")
    @Deprecated("Use inBindSet { add { BINDING } } instead.")
    override fun <T : Any> AddBindInSet(
        tag: Any?,
        overrides: Boolean?,
        binding: DIBinding<*, *, T>,
    ) {
        val setType = erasedComp(Set::class, binding.createdType) as TypeToken<Set<T>>
        val setKey = DI.Key(binding.contextType, binding.argType, setType, tag)
        val setBinding =
            containerBuilder.bindingsMap[setKey]?.first() ?: throw IllegalStateException("No set binding to $setKey")

        val multipleBinding =
            setBinding.binding as? BaseMultiBinding<*, *, T>
                ?: throw IllegalStateException("$setKey is associated to a ${setBinding.binding.factoryName()} while it should be associated with bindingSet")
        (multipleBinding.set as MutableSet<DIBinding<*, *, *>>).add(binding)
    }

    @Deprecated("This is not used, it will be removed")
    override fun Bind(tag: Any?, overrides: Boolean?): DirectBinder = DirectBinder(tag, overrides)

    override fun constant(tag: Any, overrides: Boolean?) = ConstantBinder(tag, overrides)

    override fun <T : Any> Delegate(
        type: TypeToken<out T>,
        tag: Any?,
        overrides: Boolean?,
    ): DelegateBinder<T> = DelegateBinder(this, type, tag, overrides)

    override fun import(module: DI.Module, allowOverride: Boolean) {
        val moduleName = prefix + module.name
        if (moduleName.isNotEmpty() && moduleName in importedModules) {
            throw IllegalStateException("Module \"$moduleName\" has already been imported!")
        }
        importedModules += moduleName
        DIBuilderImpl(
            moduleName,
            prefix + module.prefix,
            importedModules,
            containerBuilder.subBuilder(allowOverride, module.allowSilentOverride)
        ).apply(module.init)
    }

    override fun importAll(modules: Iterable<DI.Module>, allowOverride: Boolean) =
        modules.forEach { import(it, allowOverride) }

    override fun importAll(vararg modules: DI.Module, allowOverride: Boolean) =
        modules.forEach { import(it, allowOverride) }

    override fun importOnce(module: DI.Module, allowOverride: Boolean) {
        if (module.name.isEmpty())
            throw IllegalStateException("importOnce must be given a named module.")
        if (module.name !in importedModules)
            import(module, allowOverride)
    }

    override fun onReady(cb: DirectDI.() -> Unit) = containerBuilder.onReady(cb)

    override fun RegisterContextTranslator(translator: ContextTranslator<*, *>) =
        containerBuilder.registerContextTranslator(translator)
}

internal open class DIMainBuilderImpl(allowSilentOverride: Boolean) : DIBuilderImpl(
    null,
    "",
    HashSet(),
    DIContainerBuilderImpl(true, allowSilentOverride, HashMap(), ArrayList(), ArrayList())
), DI.MainBuilder {

    override val externalSources: MutableList<ExternalSource> = ArrayList()

    override var fullDescriptionOnError: Boolean = DI.defaultFullDescriptionOnError
    override var fullContainerTreeOnError: Boolean = DI.defaultFullContainerTreeOnError

    override fun extend(di: DI, allowOverride: Boolean, copy: Copy) {
        val keys = copy.keySet(di.container.tree)

        containerBuilder.extend(di.container, allowOverride, keys)
        externalSources += di.container.tree.externalSources
        importedModules.addAll(
            containerBuilder.bindingsMap
                .flatMap { it.value.map { it.fromModule } }
                .filterNotNull()
        )
    }

    override fun extend(directDI: DirectDI, allowOverride: Boolean, copy: Copy) {
        val keys = copy.keySet(directDI.container.tree)

        containerBuilder.extend(directDI.container, allowOverride, keys)
        externalSources += directDI.container.tree.externalSources
        importedModules.addAll(
            containerBuilder.bindingsMap
                .flatMap { it.value.map { it.fromModule } }
                .filterNotNull()
        )
    }
}
