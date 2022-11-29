package org.kodein.di.resolver

import com.squareup.kotlinpoet.ClassName

internal object Names {
    const val diPackageName = "org.kodein.di"
    const val resolverPackageName = "org.kodein.di.resolver"

    val DI = ClassName(diPackageName, "DI")
    val DIResolver = ClassName(resolverPackageName, "DIResolver")
    val Resolved = ClassName(resolverPackageName, "Resolved")
}

