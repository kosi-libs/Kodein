package org.kodein.di.ktor

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.kodein.di.*

inline fun Application.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit)
        = subKodein(kodein(), allowSilentOverride, copy, init)
inline fun PipelineContext<*, ApplicationCall>.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit)
        = subKodein(kodein(), allowSilentOverride, copy, init)
inline fun ApplicationCall.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit)
        = subKodein(kodein(), allowSilentOverride, copy, init)
inline fun Route.subKodein(allowSilentOverride: Boolean = false, copy: Copy = Copy.NonCached, crossinline init: Kodein.MainBuilder.() -> Unit)
        = subKodein(kodein(), allowSilentOverride, copy, init)