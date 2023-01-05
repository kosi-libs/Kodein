package org.kodein.di.resolver.visitor

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName

internal fun BuilderExtensionSignatureData.instanceExtensions(): List<FunSpec> {
    val instance = createExtension("instance")
        .returns(
            ClassName("org.kodein.di.bindings", "InstanceBinding")
                .parameterizedBy(reifiedType)
        )
        .addParameter(
            ParameterSpec.builder(
                name = "instance",
                type = reifiedType
            ).build()
        )
        .addStatement("return InstanceBinding(createdType = generic(), instance = instance)",)
        .build()

    val bindInstance = createExtension("bindInstance")
        .addParameter(
            ParameterSpec.builder("tag", Any::class.asClassName().copy(nullable = true))
                .defaultValue("null")
                .build()
        )
        .addParameter(
            ParameterSpec.builder("overrides", Boolean::class.asClassName().copy(nullable = true))
                .defaultValue("null")
                .build()
        )
        .addParameter(
            ParameterSpec.builder(
                name = "creator",
                type = LambdaTypeName.get(returnType = reifiedType)
            ).build()
        )
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = instance(creator()))")
        .build()

    val bindConstant = createExtension("bindConstant")
        .addParameter(
            ParameterSpec.builder("tag", Any::class.asClassName().copy(nullable = true))
                .defaultValue("null")
                .build()
        )
        .addParameter(
            ParameterSpec.builder("overrides", Boolean::class.asClassName().copy(nullable = true))
                .defaultValue("null")
                .build()
        )
        .addParameter(
            ParameterSpec.builder(
                name = "creator",
                type = LambdaTypeName.get(returnType = reifiedType)
            ).build()
        )
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = instance(creator()))")
        .build()

    return listOf(instance, bindInstance, bindConstant)
}
