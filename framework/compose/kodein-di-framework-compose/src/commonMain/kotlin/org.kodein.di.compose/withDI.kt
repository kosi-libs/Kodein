package org.kodein.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.kodein.di.DI
import org.kodein.di.DIContext
import org.kodein.di.On
import org.kodein.di.diContext

/**
 * Attaches a [DI] container to the underlying [Composable] tree
 *
 * @param builder the [DI] container configuration block
 * @param content underlying [Composable] tree that will be able to consume the [DI] container
 */
@Composable
public fun withDI(builder: DI.MainBuilder.() -> Unit, content: @Composable () -> Unit): Unit =
    CompositionLocalProvider(LocalDI provides DI { builder() }) { content() }

/**
 * Creates a [DI] container and imports [DI.Module]s before attaching it to the underlying [Composable] tree
 *
 * @param diModules the [DI.Module]s to import in the newly created [DI] container
 * @param content underlying [Composable] tree that will be able to consume the [DI] container
 */
@Composable
public fun withDI(vararg diModules: DI.Module, content: @Composable () -> Unit): Unit =
    CompositionLocalProvider(LocalDI provides DI { importAll(*diModules)}) { content() }

/**
 * Attaches a [DI] container to the underlying [Composable] tree
 *
 * @param di the [DI] container to attached to the [Composable] tree
 * @param content underlying [Composable] tree that will be able to consume the [DI] container
 */
@Composable
public fun withDI(di: DI, content: @Composable () -> Unit): Unit =
    CompositionLocalProvider(LocalDI provides di) { content() }

/**
 * Attaches a DI context to the underlying [Composable] tree
 *
 * @param context the [DIContext] representing the context type and value
 * @param content underlying [Composable] tree that will be able to access this context
 */
@Suppress("FunctionName")
@Composable
public fun OnDIContext(context: DIContext<*>, content: @Composable () -> Unit) {
    val di = localDI()
    CompositionLocalProvider(LocalDI provides di.On(context)) { content() }
}

/**
 * Attaches a DI context to the underlying [Composable] tree
 *
 * @param context the context value
 * @param content underlying [Composable] tree that will be able to access this context
 */
@Composable
public inline fun <reified C : Any> onDIContext(context: C, crossinline content: @Composable () -> Unit): Unit =
    OnDIContext(diContext(context)) { content() }
