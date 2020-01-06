package org.kodein.di.internal

import org.kodein.di.*
import org.kodein.di.bindings.*

@Deprecated(DEPRECATE_7X)
internal open class KodeinBuilderImpl internal constructor(
        private val moduleName: String?,
        private val prefix: String,
        internal val importedModules: MutableSet<String>,
        override val containerBuilder: KodeinContainerBuilderImpl
) : Kodein.Builder {

    override val contextType = AnyToken

    override val scope: Scope<Any?> get() = NoScope() // Recreating a new NoScope every-time *on purpose*!

    inner class TypeBinder<T : Any> internal constructor(val type: TypeToken<out T>, val tag: Any?, val overrides: Boolean?) : Kodein.Builder.TypeBinder<T> {
        internal val containerBuilder get() = this@KodeinBuilderImpl.containerBuilder

        override infix fun <C, A> with(binding: KodeinBinding<in C, in A, out T>) = containerBuilder.bind(Kodein.Key(binding.contextType, binding.argType, type, tag), binding, moduleName, overrides)
    }

    inner class DirectBinder internal constructor(private val _tag: Any?, private val _overrides: Boolean?) : Kodein.Builder.DirectBinder {
        override infix fun <C, A, T: Any> from(binding: KodeinBinding<in C, in A, out T>) {
            if (binding.createdType == UnitToken) {
                throw IllegalArgumentException("Using `bind() from` with a *Unit* ${binding.factoryName()} is most likely an error. If you are sure you want to bind the Unit type, please use `bind<Unit>() with ${binding.factoryName()}`.")
            }
            containerBuilder.bind(Kodein.Key(binding.contextType, binding.argType, binding.createdType, _tag), binding, moduleName, _overrides)
        }
    }

    inner class ConstantBinder internal constructor(private val _tag: Any, private val _overrides: Boolean?) : Kodein.Builder.ConstantBinder {
        @Suppress("FunctionName")
        override fun <T: Any> With(valueType: TypeToken<out T>, value: T) = Bind(tag = _tag, overrides = _overrides) from InstanceBinding(valueType, value)
    }

    @Suppress("FunctionName")
    override fun <T : Any> Bind(type: TypeToken<out T>, tag: Any?, overrides: Boolean?) = TypeBinder(type, tag, overrides)

    @Suppress("FunctionName")
    override fun Bind(tag: Any?, overrides: Boolean?): DirectBinder = DirectBinder(tag, overrides)

    override fun constant(tag: Any, overrides: Boolean?) = ConstantBinder(tag, overrides)

    override fun import(module: Kodein.Module, allowOverride: Boolean) {
        val moduleName = prefix + module.name
        if (moduleName.isNotEmpty() && moduleName in importedModules) {
            throw IllegalStateException("Module \"$moduleName\" has already been imported!")
        }
        importedModules += moduleName
        KodeinBuilderImpl(moduleName, prefix + module.prefix, importedModules, containerBuilder.subBuilder(allowOverride, module.allowSilentOverride)).apply(module.init)
    }

    override fun importAll(modules: Iterable<Kodein.Module>, allowOverride: Boolean) =
            modules.forEach { import(it, allowOverride) }

    override fun importAll(vararg modules: Kodein.Module, allowOverride: Boolean) =
            modules.forEach { import(it, allowOverride) }

    override fun importOnce(module: Kodein.Module, allowOverride: Boolean) {
        if (module.name.isEmpty())
            throw IllegalStateException("importOnce must be given a named module.")
        if (module.name !in importedModules)
            import(module, allowOverride)
    }

    override fun onReady(cb: DKodein.() -> Unit) = containerBuilder.onReady(cb)

    override fun RegisterContextTranslator(translator: ContextTranslator<*, *>) = containerBuilder.registerContextTranslator(translator)

}

@Deprecated(DEPRECATE_7X)
internal open class KodeinMainBuilderImpl(allowSilentOverride: Boolean) : KodeinBuilderImpl(null, "", HashSet(), KodeinContainerBuilderImpl(true, allowSilentOverride, HashMap(), ArrayList(), ArrayList())), Kodein.MainBuilder {

    override val externalSources: MutableList<ExternalSource> = ArrayList()

    override var fullDescriptionOnError: Boolean = Kodein.defaultFullDescriptionOnError

    override fun extend(kodein: Kodein, allowOverride: Boolean, copy: Copy) {
        val keys = copy.keySet(kodein.container.tree)

        containerBuilder.extend(kodein.container, allowOverride, keys)
        externalSources += kodein.container.tree.externalSources
        importedModules.addAll(
                containerBuilder.bindingsMap
                        .flatMap { it.value.map { it.fromModule } }
                        .filterNotNull()
        )
    }

    override fun extend(dkodein: DKodein, allowOverride: Boolean, copy: Copy) {
        val keys = copy.keySet(dkodein.container.tree)

        containerBuilder.extend(dkodein.container, allowOverride, keys)
        externalSources += dkodein.container.tree.externalSources
        importedModules.addAll(
                containerBuilder.bindingsMap
                        .flatMap { it.value.map { it.fromModule } }
                        .filterNotNull()
        )
    }
}
