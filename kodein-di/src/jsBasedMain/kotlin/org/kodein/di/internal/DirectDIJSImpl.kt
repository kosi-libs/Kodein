package org.kodein.di.internal

import org.kodein.di.DIContainer
import org.kodein.di.DIContext
import org.kodein.di.DirectDI

// https://youtrack.jetbrains.com/issue/KT-61573
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal actual class DirectDIImpl actual constructor(container: DIContainer, context: DIContext<*>) : DirectDIBaseImpl(container, context),
    DirectDI
