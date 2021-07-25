package org.kodein.di.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import org.kodein.di.DI

/**
 * DI container holder that can be shared and accessed across the Composable tree
 *
 * Note that the current container can be different depending on the Composable node
 * see [CompositionLocalProvider] / [withDI] / [subDI]
 *
 * @throws [IllegalStateException] if no DI container is attached to the Composable tree
 */
internal val LocalDI: ProvidableCompositionLocal<DI?> = compositionLocalOf { null }

@Composable
internal expect fun diFromAppContext(): DI

/**
 * Composable helper function to access the current DI container from the Composable tree (if there is one!)
 *
 * @throws [IllegalStateException] if no DI container is attached to the Composable tree
 */
@Composable
public fun localDI(): DI = LocalDI.current ?: diFromAppContext()
