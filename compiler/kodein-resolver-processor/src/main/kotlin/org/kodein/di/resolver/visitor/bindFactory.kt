package org.kodein.di.resolver.visitor

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName

internal fun BuilderExtensionSignatureData.factoryExtensions(): List<FunSpec> {
    val factory = createExtension("factory")
        .addAnnotation(
            AnnotationSpec.builder(ClassName("kotlin", "Suppress"))
                .addMember(CodeBlock.of("\"UNCHECKED_CAST\""))
                .build()
        )
        .addTypeVariable(reifiedArgumentType)
        .returns(
            ClassName("org.kodein.di.bindings", "Factory")
                .parameterizedBy(Any::class.asClassName(), reifiedArgumentType, reifiedType)
        )
        .addParameter(
            ParameterSpec.builder(
                name = "creator",
                type = LambdaTypeName.get(
                    receiver = resolverBindingDIClassName,
                    parameters = arrayOf(reifiedArgumentType),
                    returnType = reifiedType
                )
            ).addModifiers(KModifier.NOINLINE).build()
        )
        .addStatement(
            """return Factory(
                    contextType = TypeToken.Any,
                    argType = generic(), 
                    createdType = generic(), 
                    bindingDIFactory = %T.factory(), 
                    creator = creator as BindingDI<Any>.(%T) -> %T
                )""".trimMargin(),
            resolverBindingDIClassName,
            reifiedArgumentType,
            reifiedType
        )
        .build()

    val bindFactory = FunSpec.builder("bindFactory")
        .addModifiers(KModifier.INLINE)
        .addTypeVariable(reifiedArgumentType)
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
                    receiver = resolverBindingDIClassName,
                    parameters = arrayOf(reifiedArgumentType),
                    returnType = reifiedType
                )
            ).addModifiers(KModifier.NOINLINE).build()
        )
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = factory(creator = creator))")
        .build()

    return listOf(factory, bindFactory)
}
