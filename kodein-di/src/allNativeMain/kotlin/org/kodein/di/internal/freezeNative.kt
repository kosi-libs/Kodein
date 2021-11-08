package org.kodein.di.internal

import kotlin.native.concurrent.ensureNeverFrozen as nativeEnsureNeverFrozen


public actual fun <T : Any> T.ensureNeverFrozen() : Unit = nativeEnsureNeverFrozen()
