package com.github.salomonbrys.kodein.global

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.KodeinContainer
import com.github.salomonbrys.kodein.TKodein
import java.util.*

/**
 * A class that can be used to configure a kodein object and as a kodein object.
 *
 * The configuration ([addImport] & [addExtend]) needs to happen **before** any dependency retrieval.
 */
class GlobalKodein : Kodein {

    /** @suppress */
    override val kodein: Kodein get() = this

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

            _instance = Kodein {
                for (config in _configs!!)
                    config()
            }
            _configs = null

            return _instance!!
        }
    }

    /**
     * Adds a configuration to the Kodein construction that will be applied when it is constructed.
     *
     * @param config The lambda to be applied when the kodein instance is constructed.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `Kodein` retrieval function.
     */
    fun addConfig(config: Kodein.Builder.() -> Unit) {
        synchronized(this) {
            val configs = _configs ?: throw IllegalStateException("Global Kodein has been accessed and therefore constructed, you cannot add bindings after global Kodein's first access")
            configs.add(config)
        }
    }

    /**
     * Adds a module to the bindings.
     *
     * @param module The module to apply when the kodein instance is constructed.
     * @param allowOverride Whether this module is allowed to override existing bindings.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `Kodein` retrieval function.
     */
    fun addImport(module: Kodein.Module, allowOverride: Boolean = false) = addConfig { import(module, allowOverride) }

    /**
     * Adds the bindings of an existing kodein instance to the bindings.
     *
     * @param kodein The existing kodein instance whose bindings to be apply when the kodein instance is constructed.
     * @param allowOverride Whether these bindings are allowed to override existing bindings.
     * @exception IllegalStateException When calling this function after [getOrConstruct] or any `Kodein` retrieval function.
     */
    fun addExtend(kodein: Kodein, allowOverride: Boolean = false) = addConfig { extend(kodein, allowOverride) }

    /** @suppress */
    override val typed: TKodein get() = getOrConstruct().typed

    /** @suppress */
    override val container: KodeinContainer get() = getOrConstruct().container
}

/**
 * A global One True Kodein.
 */
private var _global = GlobalKodein()

/**
 * A global One True Kodein.
 */
@Suppress("unused")
val Kodein.Companion.global: GlobalKodein get() = _global

/**
 * A `KodeinAware` class that needs no implementation because the kodein used will be the [global] One True Kodein.
 */
interface KodeinGlobalAware : KodeinAware {

    /**
     * The global One True Kodein.
     */
    override val kodein: Kodein get() = Kodein.global

}
