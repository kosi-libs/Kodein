package com.github.salomonbrys.kodein

/**
 * Must be implemented by property injected classes
 */
public interface KodeinHolder {
    public val kodein: Kodein
}

/**
 * Allows to lazily create a Kodein object
 */
public fun lazyKodein(init: Kodein.Builder.() -> Unit): Lazy<Kodein> = lazy { Kodein(init) }

public inline fun <reified T : Any> lazyInstance(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<T>       = lazy { kodein().instance<T>(tag) }
public inline fun <reified T : Any> lazyProvider(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<() -> T> = lazy { kodein().provider<T>(tag) }

/**
 * To be used as a property delegate to inject an instance
 */
public inline fun <reified T : Any> KodeinHolder.injectInstance(tag: Any? = null) : Lazy<T>       = lazyInstance(tag) { kodein }

/**
 * To be used as a property delegate to inject a provider
 */
public inline fun <reified T : Any> KodeinHolder.injectProvider(tag: Any? = null) : Lazy<() -> T> = lazyProvider(tag) { kodein }
