package org.kodein.di.compose

import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import org.kodein.di.DI
import org.kodein.di.DIAware

/**
 * Access the closest [DI] container attached to the [Context]
 *
 * @throws [ClassCastException] if no [DI] container is declared in the parent [Context]s
 */
@Composable
public fun contextDI(): DI {
    var context: Context? = LocalContext.current
    while (context != null) {
        if (context is DIAware) return context.di
        context = if (context is ContextWrapper) context.baseContext else null
    }
    return (LocalContext.current.applicationContext as DIAware).di
}

/**
 * Attaches a [DI] container to the underlying [Composable] tree, using the [DI] container attached to the current [Context] (see [contextDI]).
 *
 * @param content underlying [Composable] tree that will be able to consume the [LocalDI] container
 */
@Composable
public fun withDI(content: @Composable () -> Unit) = CompositionLocalProvider(LocalDI provides contextDI()) { content() }
