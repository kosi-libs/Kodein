@file:Suppress("ComposableNaming")

package org.kodein.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
public inline fun withDI(
    noinline builder: DI.MainBuilder.() -> Unit,
    crossinline content: @Composable () -> Unit,
) {
    val currentBuilder by rememberUpdatedState(builder)
    val di = remember { DI { currentBuilder() } }
    CompositionLocalProvider(LocalDI provides di) { content() }
}

/**
 * Creates a [DI] container and imports [DI.Module]s before attaching it to the underlying [Composable] tree
 *
 * @param diModules the [DI.Module]s to import in the newly created [DI] container
 * @param content underlying [Composable] tree that will be able to consume the [DI] container
 */
@Composable
public inline fun withDI(
    vararg diModules: DI.Module,
    crossinline content: @Composable () -> Unit,
): Unit = withDI(builder = { importAll(*diModules) }, content)

/**
 * Attaches a [DI] container to the underlying [Composable] tree
 *
 * @param di the [DI] container to attached to the [Composable] tree
 * @param content underlying [Composable] tree that will be able to consume the [DI] container
 */
@Composable
public inline fun withDI(
    di: DI,
    crossinline content: @Composable () -> Unit,
): Unit = CompositionLocalProvider(LocalDI provides di) { content() }

/**
 * Attaches a DI context to the underlying [Composable] tree
 *
 * @param context the [DIContext] representing the context type and value
 * @param content underlying [Composable] tree that will be able to access this context
 */
@Suppress("FunctionName")
@Composable
public inline fun OnDIContext(
    context: DIContext<*>,
    crossinline content: @Composable () -> Unit,
) {
    val di = localDI()
    val ctx = remember(di, context) { di.On(context) }
    CompositionLocalProvider(LocalDI provides ctx) { content() }
}

/**
 * Attaches a DI context to the underlying [Composable] tree
 *
 * @param context the context value
 * @param content underlying [Composable] tree that will be able to access this context
 */
@Composable
public inline fun <reified C : Any> onDIContext(
    context: C,
    crossinline content: @Composable () -> Unit,
) {
    val di = localDI()
    val ctx = remember(di, context) { di.On(diContext(context)) }
    CompositionLocalProvider(LocalDI provides ctx) { content() }
}
