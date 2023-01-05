package org.kodein.di.resolver.visitor

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName

internal fun BuilderExtensionSignatureData.providerExtensions(): List<FunSpec> {
    val provider = createExtension("provider")
        .addAnnotation(
            AnnotationSpec.builder(ClassName("kotlin", "Suppress"))
                .addMember(CodeBlock.of("\"UNCHECKED_CAST\""))
                .build()
        )
        .returns(
            ClassName("org.kodein.di.bindings", "Provider")
                .parameterizedBy(Any::class.asClassName(), reifiedType)
        )
        .addParameter(
            ParameterSpec.builder(
                name = "creator",
                type = LambdaTypeName.get(
                    receiver = resolverNoArgBindingDIClassName,
                    returnType = reifiedType
                )
            ).addModifiers(KModifier.NOINLINE).build()
        )
        .addStatement(
            """return Provider(
                    contextType = TypeToken.Any,
                    createdType = generic(), 
                    bindingDIFactory = %T.factory(), 
                    creator = creator as NoArgBindingDI<Any>.() -> %T
                )""".trimMargin(),
            resolverNoArgBindingDIClassName,
            reifiedType
        )
        .build()

    val bindProvider = FunSpec.builder("bindProvider")
        .addModifiers(KModifier.INLINE)
        .addTypeVariable(reifiedType)
        .receiver(builderGeneratedClassName)
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
                type = LambdaTypeName.get(
                    receiver = resolverNoArgBindingDIClassName,
                    returnType = reifiedType
                )
            ).addModifiers(KModifier.NOINLINE).build()
        )
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = provider(creator = creator))")
        .build()

    val providersOf = (0..9).fold(listOf<FunSpec.Builder>()) { acc, i ->
        val tv = mutableListOf<TypeVariableName>()
        repeat(i) { tv.add(TypeVariableName("P$it").copy(reified = true)) }

        acc + FunSpec.builder("bindProviderOf")
            .addModifiers(KModifier.INLINE)
            .addTypeVariable(reifiedType)
            .also { builder -> tv.forEach(builder::addTypeVariable) }
            .receiver(builderGeneratedClassName)
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
                    type = LambdaTypeName.get(
                        parameters = tv.toTypedArray(),
                        returnType = reifiedType
                    )
                ).addModifiers(KModifier.NOINLINE).build()
            )
            .addStatement("return bindProvider(tag, overrides) { new(creator) }")
    }.map { it.build() }

    return listOf(provider, bindProvider) + providersOf
}
