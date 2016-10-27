package com.github.salomonbrys.kodein.erased

import com.github.salomonbrys.kodein.*


/**
 * Creates a scoped singleton factory, effectively a `factory { Scope -> T }`.
 *
 * C & T generics will be erased!
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
@Suppress("unused")
inline fun <reified C, reified T : Any> Kodein.Builder.scopedSingleton(scope: Scope<C>, noinline creator: Kodein.(C) -> T)
        = erasedScopedSingleton(scope, creator)

/**
 * Creates an auto-scoped singleton provider, effectively a `provider { -> T }`.
 *
 * T generics will be erased!
 *
 * @param C The scope context type.
 * @param T The singleton type.
 * @param scope The scope object in which the singleton will be stored.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.
 */
@Suppress("unused")
inline fun <C, reified T : Any> Kodein.Builder.autoScopedSingleton(scope: AutoScope<C>, noinline creator: Kodein.(C) -> T)
        = erasedAutoScopedSingleton(scope, creator)
