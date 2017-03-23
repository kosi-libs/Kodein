package com.github.salomonbrys.kodein

/**
 * Creates a referenced singleton, will return always the same object as long as the reference is valid.
 *
 * T generics will be kept.
 *
 * @param T The singleton type.
 * @param refMaker The reference maker that will define the type of reference.
 * @param creator A function that creates the singleton object. Will be called only if the singleton does not already exist or if the reference is not valid anymore.
 */
inline fun <reified T : Any> Kodein.Builder.refSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T) = genericRefSingleton(refMaker, creator)

/**
 * Creates a referenced multiton, for the same argument, will return always the same object as long as the reference is valid.
 *
 * A & T generics will be kept.
 *
 * @param T The multiton type.
 * @param refMaker The reference maker that will define the type of reference.
 * @param creator A function that creates the multiton object. For the same argument, will be called only if the multiton does not already exist or if the reference is not valid anymore.
 */
inline fun <reified A, reified T : Any> Kodein.Builder.refMultiton(refMaker: RefMaker, noinline creator: FactoryKodein.(A) -> T) = genericRefMultiton(refMaker, creator)
