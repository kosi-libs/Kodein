package com.github.salomonbrys.kodein.global

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.KodeinContainer
import com.github.salomonbrys.kodein.TKodein
import java.util.*

class GlobalKodein : Kodein {

    override val kodein: Kodein get() = this

    private var _configs: MutableList<Kodein.Builder.() -> Unit>? = LinkedList()

    private var _instance: Kodein? = null

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

    private fun _addConfig(config: Kodein.Builder.() -> Unit) {
        synchronized(this) {
            val configs = _configs ?: throw IllegalStateException("Global Kodein has been accessed and therefore constructed, you cannot add bindings after global Kodein's first access")
            configs.add(config)
        }
    }

    fun addImport(module: Kodein.Module, allowOverride: Boolean = false) = _addConfig { import(module, allowOverride) }

    fun addExtend(kodein: Kodein, allowOverride: Boolean = false) = _addConfig { extend(kodein, allowOverride) }

    override val typed: TKodein get() = getOrConstruct().typed

    override val container: KodeinContainer get() = getOrConstruct().container
}

private var _global = GlobalKodein()

val Kodein.Companion.global: GlobalKodein get() = _global

interface KodeinGlobalAware : KodeinAware {

    override val kodein: Kodein get() = Kodein.global

}
