package org.kodein.di.conf

import kotlinx.atomicfu.locks.SynchronizedObject
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DIContainer
import org.kodein.di.internal.maySynchronized
import org.kodein.di.internal.synchronizedIfNull

/**
 * A class that can be used to configure a DI object and as a DI object.
 *
 * If you want it to be mutable, the [mutable] property needs to be set **before** any dependency retrieval.
 * The non-mutable configuration methods ([addImport], [addExtend] & [addConfig]) needs to happen **before** any dependency retrieval.
 */
public class ConfigurableDI : DI {
//    override val container: KodeinContainer
//        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    /** @suppress */
    override val di: DI get() = this

    private val _lock = SynchronizedObject()

    /**
     * Whether this Configurabledi can be mutated.
     *
     * `null` = not set yet. `true` = can be mutated. `false` = cannot be mutated.
     *
     * Note that if not set, this field will be set to false on `first` DI retrieval.
     */
    public var mutable: Boolean? = null
        set(value) {
            if (value == field)
                return
            if (field != null)
                throw IllegalStateException("Mutable field has already been set. You must set the mutable field before first retrieval.")
            field = value
        }

    /**
     * Default constructor.
     */
    public constructor()

    /**
     * Convenient constructor to directly set the mutability.
     *
     * @param mutable Whether this DI can be mutated.
     */
    public constructor(mutable: Boolean) {
        this.mutable = mutable
    }

    /**
     * Configuration lambdas.
     *
     * When constructing the DI instance (upon first retrieval), all configuration lambdas will be applied.
     */
    private var _configs: MutableList<DI.MainBuilder.() -> Unit>? = ArrayList()

    /**
     * DI instance. If it is not null, than it cannot be configured anymore.
     */
    @kotlin.concurrent.Volatile
    private var _instance: DI? = null

    /**
     * Get the DI instance if it has already been constructed, or construct it if not.
     *
     * The first time this function is called is the end of the configuration.
     */
    public fun getOrConstruct(): DI {
        return synchronizedIfNull(
                lock = _lock,
                predicate = { _instance },
                ifNotNull = { it },
                ifNull = {
                    if (mutable == null)
                        mutable = false

                    val configs = checkNotNull(_configs) { "recursive initialization detected" }
                    _configs = null

                    val (di, init) = DI.withDelayedCallbacks {
                        for (config in configs)
                            config()
                    }

                    _instance = di

                    init()
                    di
                }
        )
    }

    /**
     * Clear all the bindings of the DI instance. Needs [mutable] to be true.
     *
     * @throws IllegalStateException if [mutable] is not `true`.
     */
    public fun clear() {
        if (mutable != true)
            throw IllegalStateException("Configurabledi is not mutable, you cannot clear bindings.")

        maySynchronized(_lock) {
            val configs = _configs

            if (configs != null) {
                configs.clear()
            }
            else {
                _instance = null
                _configs = ArrayList()
            }
        }
    }

    /**
     * Whether or not this DI can be configured (meaning that it has not been used for retrieval yet).
     */
    public val canConfigure: Boolean get() = _instance == null

    /**
     * Adds a configuration to the bindings that will be applied when the DI is constructed.
     *
     * @param config The lambda to be applied when the DI instance is constructed.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `DI` retrieval function.
     */
    public fun addConfig(config: DI.MainBuilder.() -> Unit) : ConfigurableDI {
        maySynchronized(_lock) {
            val configs = _configs
            if (configs == null) {
                if (mutable != true)
                    throw IllegalStateException("The non-mutable Configurabledi has been accessed and therefore constructed, you cannot add bindings after first retrieval.")
                val previous = _instance
                _instance = null
                _configs = ArrayList()
                if (previous != null)
                    addExtend(previous)
            }
            _configs!!.add(config)
        }
        return this
    }

    /**
     * Adds a module to the bindings that will be applied when the DI is constructed.
     *
     * @param module The module to apply when the DI instance is constructed.
     * @param allowOverride Whether this module is allowed to override existing bindings.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `DI` retrieval function.
     */
    public fun addImport(module: DI.Module, allowOverride: Boolean = false): ConfigurableDI = addConfig {
        import(module, allowOverride)
    }

    /**
     * Adds the bindings of an existing DI instance to the bindings that will be applied when the DI is constructed.
     *
     * @param di The existing DI instance whose bindings to be apply when the DI instance is constructed.
     * @param allowOverride Whether these bindings are allowed to override existing bindings.
     * @param copy The copy specifications, that defines which bindings will be copied to the new container.
     *   All bindings from the extended container will be accessible in the new container, but only the copied bindings are able to access overridden bindings in this new container.
     *   By default, all bindings that do not hold references (e.g. not singleton or multiton) are copied.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `DI` retrieval function.
     */
    public fun addExtend(di: DI, allowOverride: Boolean = false, copy: Copy = Copy.NonCached): ConfigurableDI = addConfig {
        extend(di, allowOverride, copy)
    }

    /** @suppress */
    override val container: DIContainer get() = getOrConstruct().container
}
