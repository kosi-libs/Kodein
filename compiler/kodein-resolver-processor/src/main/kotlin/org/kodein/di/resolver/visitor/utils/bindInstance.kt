package org.kodein.di.resolver.visitor.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.kodein.di.resolver.visitor.BuilderExtensionTypes
import org.kodein.di.resolver.visitor.createExtension

internal fun BuilderExtensionTypes.instanceExtensions(): List<FunSpec> {
    val instance = createExtension("instance")
        .returns(eagerBindingReturnType("InstanceBinding"))
        .addParameter(ParameterSpec.builder(name = "instance", type = reifiedCreatedType).build())
        .addStatement("return InstanceBinding(createdType = generic(), instance = instance)")
        .build()

    val bindInstance = createExtension("bindInstance")
        .addParameter(Parameters.tag)
        .addParameter(Parameters.override)
        .addParameter(Parameters.creator(returnType = reifiedCreatedType))
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = instance(creator()))")
        .build()

    val bindConstant = createExtension("bindConstant")
        .addParameter(Parameters.tag)
        .addParameter(Parameters.override)
        .addParameter(Parameters.creator(returnType = reifiedCreatedType))
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = instance(creator()))")
        .build()

    return listOf(instance, bindInstance, bindConstant)
}
