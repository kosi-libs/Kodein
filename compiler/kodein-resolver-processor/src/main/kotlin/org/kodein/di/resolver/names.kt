package org.kodein.di.resolver

import com.squareup.kotlinpoet.ClassName

internal object Names {
    const val diPackageName = "org.kodein.di"
    const val diBindingPackageName = "org.kodein.di.bindings"
    private const val resolverPackageName = "org.kodein.di.resolver"

    // DI core APIs
    val DI = ClassName(diPackageName, "DI")
    val DINew = ClassName(diPackageName, "new")
    val DICompanion = ClassName(diPackageName, "DI.Companion")
    val DIContainerBuilder = ClassName(diPackageName, "DIContainer.Builder")
    // DI Resolver APIs
    val DIResolverBuilder = ClassName(resolverPackageName, "DIResolverBuilder")
    val DIResolver = ClassName(resolverPackageName, "DIResolver")
    val Resolved = ClassName(resolverPackageName, "Resolved")
}

