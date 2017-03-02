package com.github.salomonbrys.kodein

inline fun <reified T : Any> Kodein.Builder.refSingleton(refMaker: RefMaker, noinline creator: ProviderKodein.() -> T) = genericRefSingleton(refMaker, creator)
