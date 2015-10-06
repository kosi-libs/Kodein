package com.github.salomonbrys.kodein

/**
 * Allows to lazily create a Kodein object
 */
public fun lazyKodein(init: Kodein.Builder.() -> Unit): Lazy<Kodein> = lazy { Kodein(init) }

public inline fun <reified T : Any> lazyInstance(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<T>       = lazy { kodein().instance<T>(tag) }
public inline fun <reified T : Any> lazyProvider(tag: Any? = null, noinline kodein: () -> Kodein) : Lazy<() -> T> = lazy { kodein().provider<T>(tag) }

/**
 * To be used as a property delegate to inject an instance
 */
public inline fun <reified T : Any> Kodein.injectInstance(tag: Any? = null) : Lazy<T>       = lazyInstance(tag) { this }

/**
 * To be used as a property delegate to inject a provider
 */
public inline fun <reified T : Any> Kodein.injectProvider(tag: Any? = null) : Lazy<() -> T> = lazyProvider(tag) { this }

/**
 * To be used as a property delegate to inject an instance
 */
public inline fun <reified T : Any> Lazy<Kodein>.injectInstance(tag: Any? = null) : Lazy<T> = lazyInstance(tag) { this.value }

/**
 * To be used as a property delegate to inject a provider
 */
public inline fun <reified T : Any> Lazy<Kodein>.injectProvider(tag: Any? = null) : Lazy<() -> T> = lazyProvider(tag) { this.value }

/**
 * To be used as a property delegate to inject an instance
 */
public inline fun <reified T : Any> (() -> Kodein).injectInstance(tag: Any? = null) : Lazy<T> = lazyInstance(tag) { this() }

/**
 * To be used as a property delegate to inject a provider
 */
public inline fun <reified T : Any> (() -> Kodein).injectProvider(tag: Any? = null) : Lazy<() -> T> = lazyProvider(tag) { this() }
