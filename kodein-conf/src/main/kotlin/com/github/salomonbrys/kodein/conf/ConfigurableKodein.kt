package com.github.salomonbrys.kodein.conf

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinContainer
import com.github.salomonbrys.kodein.TKodein
import java.util.*

/**
 * A class that can be used to configure a kodein object and as a kodein object.
 *
 * If you want it to be mutable, the [mutable] property needs to be set **before** any dependency retrieval.
 * The non-mutable configuration methods ([addImport], [addExtend] & [addConfig]) needs to happen **before** any dependency retrieval.
 */
class ConfigurableKodein : Kodein {

    /** @suppress */
    override val kodein: Kodein get() = this

    /**
     * Whether this ConfigurableKodein can be mutated.
     *
     * `null` = not set yet. `true` = can be mutated. `false` = cannot be mutated.
     *
     * Note that if not set, this field will be set to false on `first` Kodein retrieval.
     */
    var mutable: Boolean? = null
        set(value) {
            if (value == field)
                return
            if (field != null)
                throw IllegalStateException("Mutable field has already been set")
            field = value
        }

    /**
     * Default constructor.
     */
    constructor()

    /**
     * Convenient constructor to directly set the mutability.
     *
     * @param mutable Whether this Kodein can be mutated.
     */
    constructor(mutable: Boolean) {
        this.mutable = mutable
    }

    /**
     * Configuration lambdas.
     *
     * When constructing the Kodein instance (upon first retrieval), all configuration lambdas will be applied.
     */
    private var _configs: MutableList<Kodein.Builder.() -> Unit>? = LinkedList()

    /**
     * Kodein instance. If it is not null, than it cannot be configured anymore.
     */
    private var _instance: Kodein? = null

    /**
     * Get the kodein instance if it has already been constructed, or construct it if not.
     *
     * The first time this function is called is the end of the configuration.
     */
    fun getOrConstruct(): Kodein {
        if (_instance != null)
            return _instance!!

        synchronized(this) {
            if (_instance != null)
                return _instance!!

            if (mutable == null)
                mutable = false

            _instance = Kodein {
                for (config in _configs!!)
                    config()
            }
            _configs = null

            return _instance!!
        }
    }

    /**
     * Reset the Kodein instance, allowing it to be configured again. Needs [mutable] to be true.
     *
     * By default, it will reset it with no configured bindings.
     * If [extend] is true, all bindings from the old kodein will be transferred into the new kodein.
     *
     * @param keep Whether or not to keep the old bindings into the new Kodein instance.
     * @throws IllegalStateException if [mutable] is not `true`.
     */
    fun mutateReset(keep: Boolean = false) {
        synchronized(this) {
            val previous = _instance

            if (previous == null) {
                if (!keep) {
                    if (mutable != true)
                        throw IllegalStateException("ConfigurableKodein is not mutable")

                    _configs?.clear()
                }
                return
            }

            if (mutable != true)
                throw IllegalStateException("ConfigurableKodein is not mutable")

            _instance = null
            _configs = LinkedList()

            if (keep)
                addConfig { extend(previous) }
        }
    }

    /**
     * Whether or not this Kodein can be configured (meaning that it has not been used for retrieval yet.
     */
    val canConfigure: Boolean get() = _instance == null

    /**
     * Adds a configuration to the bindings that will be applied when the Kodein is constructed.
     *
     * @param config The lambda to be applied when the kodein instance is constructed.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `Kodein` retrieval function.
     */
    fun addConfig(config: Kodein.Builder.() -> Unit) {
        synchronized(this) {
            val configs = _configs ?: throw IllegalStateException("ConfigurableKodein has been accessed and therefore constructed, you cannot add bindings after first retrieval")
            configs.add(config)
        }
    }

    /**
     * Adds a configuration to the bindings that will extend the existing bindings.
     *
     * @param config The lambda to be applied when the kodein instance is constructed.
     * @throws IllegalStateException if [mutable] is not `true`.
     */
    fun mutateAddConfig(config: Kodein.Builder.() -> Unit) {
        synchronized(this) {
            mutateReset(true)
            addConfig(config)
        }
    }

    /**
     * Adds a module to the bindings that will be applied when the Kodein is constructed.
     *
     * @param module The module to apply when the kodein instance is constructed.
     * @param allowOverride Whether this module is allowed to override existing bindings.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `Kodein` retrieval function.
     */
    fun addImport(module: Kodein.Module, allowOverride: Boolean = false) = addConfig { import(module, allowOverride) }

    /**
     * Adds the bindings of an existing kodein instance to the bindings that will be applied when the Kodein is constructed.
     *
     * @param kodein The existing kodein instance whose bindings to be apply when the kodein instance is constructed.
     * @param allowOverride Whether these bindings are allowed to override existing bindings.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `Kodein` retrieval function.
     */
    fun addExtend(kodein: Kodein, allowOverride: Boolean = false) = addConfig { extend(kodein, allowOverride) }

    /**
     * Adds a module to the bindings that will extend the existing bindings.
     *
     * @param module The module to apply when the kodein instance is constructed.
     * @param allowOverride Whether this module is allowed to override existing bindings.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `Kodein` retrieval function.
     */
    fun mutateAddImport(module: Kodein.Module, allowOverride: Boolean = false) = mutateAddConfig { import(module, allowOverride) }

    /**
     * Adds the bindings of an existing kodein instance that will extend the existing bindings.
     *
     * @param kodein The existing kodein instance whose bindings to be apply when the kodein instance is constructed.
     * @param allowOverride Whether these bindings are allowed to override existing bindings.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `Kodein` retrieval function.
     */
    fun mutateAddExtend(kodein: Kodein, allowOverride: Boolean = false) = mutateAddConfig { extend(kodein, allowOverride) }

    /** @suppress */
    override val typed: TKodein get() = getOrConstruct().typed

    /** @suppress */
    override val container: KodeinContainer get() = getOrConstruct().container
}