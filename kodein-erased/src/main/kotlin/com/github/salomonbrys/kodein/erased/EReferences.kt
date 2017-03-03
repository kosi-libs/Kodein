package com.github.salomonbrys.kodein.erased

import com.github.salomonbrys.kodein.*

inline fun <reified T : Any> Kodein.Builder.refSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T) = erasedRefSingleton(refMaker, creator)

inline fun <reified A, reified T : Any> Kodein.Builder.refMultiton(refMaker: RefMaker, noinline creator: FactoryKodein.(A) -> T) = erasedRefMultiton(refMaker, creator)
