package org.kodein.di.resolver.visitor.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName
import org.kodein.di.resolver.Names
import org.kodein.di.resolver.visitor.BuilderExtensionTypes

internal fun BuilderExtensionTypes.bindingReturnType(type: String): ParameterizedTypeName {
    return ClassName(Names.diBindingPackageName, type)
        .parameterizedBy(Any::class.asClassName(), reifiedArgumentType, reifiedCreatedType)
}

internal fun BuilderExtensionTypes.noArgBindingReturnType(type: String): ParameterizedTypeName {
    return ClassName(Names.diBindingPackageName, type)
        .parameterizedBy(Any::class.asClassName(), reifiedCreatedType)
}

internal fun BuilderExtensionTypes.eagerBindingReturnType(type: String): ParameterizedTypeName {
    return ClassName(Names.diBindingPackageName, type)
        .parameterizedBy(reifiedCreatedType)
}