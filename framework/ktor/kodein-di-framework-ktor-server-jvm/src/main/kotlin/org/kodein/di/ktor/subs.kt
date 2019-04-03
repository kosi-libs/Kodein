package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.kodein.di.*

//inline fun subKodein(noinline parentKodein: () -> Kodein, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = Kodein.lazy(allowSilentOverride) {
//    extend(parentKodein(), copy = copy)
//    init()
//}

//inline fun subKodein(parentKodein: Lazy<Kodein>, allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit): LazyKodein {
//    return subKodein({ parentKodein.value }, allowSilentOverride, copy, init)
//}

inline fun Application.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = subKodein(kodein(), allowSilentOverride, copy, init)
inline fun PipelineContext<*, ApplicationCall>.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = subKodein(kodein(), allowSilentOverride, copy, init)
inline fun ApplicationCall.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = subKodein(kodein(), allowSilentOverride, copy, init)
inline fun Route.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit) = subKodein(kodein(), allowSilentOverride, copy, init)