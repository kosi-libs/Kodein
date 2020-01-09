package org.kodein.di

/**
 * Allow to create an extended version of a given [DI] container
 *
 * @param parentDI the [DI] container that will be copied and extended
 * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
 * @param copy The copy specifications, that defines which bindings will be copied to the new container.
 *   All bindings from the extended container will be accessible in the new container, but only the copied bindings are able to access overridden bindings in this new container.
 *   By default, all bindings that do not hold references (e.g. not singleton or multiton) are copied.
 * @param init [DI] container configuration block
 */
inline fun subDI(parentDI: DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = DI.lazy(allowSilentOverride) {
    extend(parentDI, copy = copy)
    init()
}
@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("subDI(parentDI, allowSilentOverride, copy, init)"), DeprecationLevel.ERROR)
inline fun subKodein(parentDI: DI, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: DI.MainBuilder.() -> Unit) = DI.lazy(allowSilentOverride) {
    extend(parentDI, copy = copy)
    init()
}