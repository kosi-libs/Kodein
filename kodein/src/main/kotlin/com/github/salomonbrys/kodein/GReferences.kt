package com.github.salomonbrys.kodein

inline fun <reified T : Any> Kodein.Builder.refSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T) = genericRefSingleton(refMaker, creator)

inline fun <reified A, reified T : Any> Kodein.Builder.refMultiton(refMaker: RefMaker, noinline creator: FactoryKodein.(A) -> T) = genericRefMultiton(refMaker, creator)
