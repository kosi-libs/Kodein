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
import org.kodein.di.bindings.RefMaker

internal fun BuilderExtensionSignatureData.multitonExtensions(): List<FunSpec> {
    val multiton = createExtension("multiton")
        .addAnnotation(
            AnnotationSpec.builder(ClassName("kotlin", "Suppress"))
                .addMember(CodeBlock.of("\"UNCHECKED_CAST\""))
                .build()
        )
        .addTypeVariable(reifiedArgumentType)
        .returns(
            ClassName("org.kodein.di.bindings", "Multiton")
                .parameterizedBy(Any::class.asClassName(), reifiedArgumentType, reifiedType)
        )
        .addParameter(
            ParameterSpec.builder("ref", RefMaker::class.asClassName().copy(nullable = true))
                .defaultValue("null")
                .build()
        )
        .addParameter(
            ParameterSpec.builder("sync", Boolean::class.asClassName())
                .defaultValue("true")
                .build()
        )
        .addParameter(
            ParameterSpec.builder(
                name = "creator",
                type = LambdaTypeName.get(
                    receiver = resolverNoArgBindingDIClassName,
                    parameters = arrayOf(reifiedArgumentType),
                    returnType = reifiedType
                )
            ).addModifiers(KModifier.NOINLINE).build()
        )
        .addStatement(
            """return Multiton(
                    scope = NoScope(),
                    contextType = TypeToken.Any,
                    explicitContext = false,
                    argType = generic(), 
                    createdType = generic(), 
                    refMaker = ref,
                    sync = sync,
                    creator = creator as BindingDI<Any>.(%T) -> %T
                )""".trimMargin(),
            reifiedArgumentType,
            reifiedType
        ).build()

    val bindMultiton = createExtension("bindMultiton")
        .addTypeVariable(reifiedArgumentType)
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
            ParameterSpec.builder("sync", Boolean::class.asClassName())
                .defaultValue("true")
                .build()
        )
        .addParameter(
            ParameterSpec.builder(
                name = "creator",
                type = LambdaTypeName.get(
                    receiver = resolverNoArgBindingDIClassName,
                    parameters = arrayOf(reifiedArgumentType),
                    returnType = reifiedType
                )
            ).addModifiers(KModifier.NOINLINE).build()
        )
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = multiton(sync = sync, creator = creator))")
        .build()

    return listOf(multiton, bindMultiton)
}
