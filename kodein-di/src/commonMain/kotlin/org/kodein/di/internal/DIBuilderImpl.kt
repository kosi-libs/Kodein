package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.type.TypeToken

internal open class DIBuilderImpl internal constructor(
        private val moduleName: String?,
        private val prefix: String,
        internal val importedModules: MutableSet<String>,
        override val containerBuilder: DIContainerBuilderImpl
) : DI.Builder {

    override val contextType = TypeToken.Any

    override val scope: Scope<Any?> get() = NoScope() // Recreating a new NoScope every-time *on purpose*!

    inner class TypeBinder<T : Any> internal constructor(val type: TypeToken<out T>, val tag: Any?, val overrides: Boolean?) : DI.Builder.TypeBinder<T> {
        internal val containerBuilder get() = this@DIBuilderImpl.containerBuilder

        override infix fun <C : Any, A> with(binding: DIBinding<in C, in A, out T>) = containerBuilder.bind(DI.Key(binding.contextType, binding.argType, type, tag), binding, moduleName, overrides)
    }

    inner class DirectBinder internal constructor(private val _tag: Any?, private val _overrides: Boolean?) : DI.Builder.DirectBinder {
        override infix fun <C : Any, A, T: Any> from(binding: DIBinding<in C, in A, out T>) {
            if (binding.createdType == TypeToken.Unit) {
                throw IllegalArgumentException("Using `bind() from` with a *Unit* ${binding.factoryName()} is most likely an error. If you are sure you want to bind the Unit type, please use `bind<Unit>() with ${binding.factoryName()}`.")
            }
            containerBuilder.bind(DI.Key(binding.contextType, binding.argType, binding.createdType, _tag), binding, moduleName, _overrides)
        }
    }

    inner class ConstantBinder internal constructor(private val _tag: Any, private val _overrides: Boolean?) : DI.Builder.ConstantBinder {
        @Suppress("FunctionName")
        override fun <T: Any> With(valueType: TypeToken<out T>, value: T) = Bind(tag = _tag, overrides = _overrides) from InstanceBinding(valueType, value)
    }

    @Suppress("FunctionName")
    override fun <T : Any> Bind(type: TypeToken<out T>, tag: Any?, overrides: Boolean?) = TypeBinder(type, tag, overrides)

    @Suppress("FunctionName")
    override fun Bind(tag: Any?, overrides: Boolean?): DirectBinder = DirectBinder(tag, overrides)

    override fun constant(tag: Any, overrides: Boolean?) = ConstantBinder(tag, overrides)

    override fun import(module: DI.Module, allowOverride: Boolean) {
        val moduleName = prefix + module.name
        if (moduleName.isNotEmpty() && moduleName in importedModules) {
            throw IllegalStateException("Module \"$moduleName\" has already been imported!")
        }
        importedModules += moduleName
        DIBuilderImpl(moduleName, prefix + module.prefix, importedModules, containerBuilder.subBuilder(allowOverride, module.allowSilentOverride)).apply(module.init)
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

    override fun RegisterContextTranslator(translator: ContextTranslator<*, *>) = containerBuilder.registerContextTranslator(translator)

}

internal open class DIMainBuilderImpl(allowSilentOverride: Boolean) : DIBuilderImpl(null, "", HashSet(), DIContainerBuilderImpl(true, allowSilentOverride, HashMap(), ArrayList(), ArrayList())), DI.MainBuilder {

    override val externalSources: MutableList<ExternalSource> = ArrayList()

    override var fullDescriptionOnError: Boolean = DI.defaultFullDescriptionOnError

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
