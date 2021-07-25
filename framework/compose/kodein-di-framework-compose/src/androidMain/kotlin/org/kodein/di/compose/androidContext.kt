package org.kodein.di.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import org.kodein.di.DI
import org.kodein.di.android.closestDI

/**
 * Access the closest [DI] container attached to the [Context]
 *
 * @throws [ClassCastException] if no [DI] container is declared in the parent [Context]s
 */
@Composable
public fun androidContextDI(): DI {
    val context = LocalContext.current
    val di by closestDI { context }
    return remember { di }
}

// Deprecated since 7.7.0
@Deprecated("Renamed to androidContextDI", ReplaceWith("androidContextDI()"))
@Composable
public fun contextDI(): DI = androidContextDI()

/**
 * Attaches a [DI] container to the underlying [Composable] tree, using the [DI] container attached to the current [Context] (see [contextDI]).
 *
 * @param content underlying [Composable] tree that will be able to consume the [LocalDI] container
 */
// Deprecated since 7.7.0
@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("This is not necessary anymore as the DI container is automatically used if no local DI is defined")
@Composable
public fun withDI(content: @Composable () -> Unit) = CompositionLocalProvider(LocalDI provides androidContextDI()) { content() }

@Composable
internal actual fun diFromAppContext(): DI = androidContextDI()
