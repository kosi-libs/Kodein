package com.github.salomonbrys.kodein

import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty

/**
 * Must be implemented by property injected classes
 */
public interface KodeinHolder {
    public val kodein: Kodein
}

/**
 * Allows to lazily create a Kodein object
 */
public fun <T: Any> T.lazyKodein(init: Kodein.Builder.() -> Unit): ReadOnlyProperty<Any, Kodein>
        = Delegates.lazy { Kodein(init) }

public inline fun <reified T : Any> lazyInstance(tag: Any? = null, noinline kodein: () -> Kodein) : ReadOnlyProperty<Any, T>       = Delegates.lazy { kodein().instance<T>(tag) }
public inline fun <reified T : Any> lazyProvider(tag: Any? = null, noinline kodein: () -> Kodein) : ReadOnlyProperty<Any, () -> T> = Delegates.lazy { kodein().provider<T>(tag) }

/**
 * To be used as a property delegate to inject an instance
 */
public inline fun <reified T : Any> KodeinHolder.injectInstance(tag: Any? = null) : ReadOnlyProperty<Any, T>       = lazyInstance(tag) { kodein }

/**
 * To be used as a property delegate to inject a provider
 */
public inline fun <reified T : Any> KodeinHolder.injectProvider(tag: Any? = null) : ReadOnlyProperty<Any, () -> T> = lazyProvider(tag) { kodein }
