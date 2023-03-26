package org.kodein.di.compose

import android.content.Context
import androidx.compose.runtime.Composable
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

@Composable
internal actual fun diFromAppContext(): DI = androidContextDI()
