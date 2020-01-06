package org.kodein.di

/**
 * Allow to create an extended version of a given [Kodein] container
 *
 * @param parentKodein the [Kodein] container that will be copied and extended
 * @param allowSilentOverride Whether the configuration block is allowed to non-explicit overrides.
 * @param copy The copy specifications, that defines which bindings will be copied to the new container.
 *   All bindings from the extended container will be accessible in the new container, but only the copied bindings are able to access overridden bindings in this new container.
 *   By default, all bindings that do not hold references (e.g. not singleton or multiton) are copied.
 * @param init [Kodein] container configuration block
 */
@Deprecated(DEPRECATE_7X)
inline fun subKodein(parentKodein: Kodein, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = Kodein.lazy(allowSilentOverride) {
    extend(parentKodein, copy = copy)
    init()
}
