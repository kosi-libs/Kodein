package org.kodein.di.compose

import androidx.compose.runtime.Composable
import org.kodein.di.DI

@Composable
internal actual fun diFromAppContext(): DI = error("Missing DI container!")
