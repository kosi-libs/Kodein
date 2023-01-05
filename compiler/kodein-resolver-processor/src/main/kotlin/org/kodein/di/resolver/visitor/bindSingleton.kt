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
import org.kodein.di.bindings.RefMaker

internal fun BuilderExtensionSignatureData.singletonExtensions(): List<FunSpec> {
    val singleton = createExtension("singleton")
        .addAnnotation(
            AnnotationSpec.builder(ClassName("kotlin", "Suppress"))
                .addMember(CodeBlock.of("\"UNCHECKED_CAST\""))
                .build()
        )
        .returns(
            ClassName("org.kodein.di.bindings", "Singleton")
                .parameterizedBy(Any::class.asClassName(), reifiedType)
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
                    returnType = reifiedType
                )
            ).addModifiers(KModifier.NOINLINE).build()
        )
        .addStatement(
            """return Singleton(
                    scope = NoScope(),
                    contextType = TypeToken.Any,
                    explicitContext = false,
                    createdType = generic(), 
                    refMaker = ref,
                    sync = sync,
                    bindingDIFactory = %T.factory(),
                    creator = creator as NoArgBindingDI<Any>.() -> %T
                )""".trimMargin(),
            resolverNoArgBindingDIClassName,
            reifiedType
        )
        .build()

    val bindSingleton = createExtension("bindSingleton")
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
                    returnType = reifiedType
                )
            ).addModifiers(KModifier.NOINLINE).build()
        )
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = singleton(sync = sync, creator = creator))")
        .build()

    val eagerSingleton = createExtension("eagerSingleton")
        .addAnnotation(
            AnnotationSpec.builder(ClassName("kotlin", "Suppress"))
                .addMember(CodeBlock.of("\"UNCHECKED_CAST\""))
                .build()
        )
        .returns(
            ClassName("org.kodein.di.bindings", "EagerSingleton")
                .parameterizedBy(reifiedType)
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
            """return EagerSingleton(
                    builder = this.containerBuilder,
                    createdType = generic(), 
                    creator = creator as NoArgBindingDI<Any>.() -> %T
                )""".trimMargin(),
            reifiedType
        )
        .build()

    val bindEagerSingleton = createExtension("bindEagerSingleton")
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
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = eagerSingleton(creator = creator))")
        .build()

    val singletonsOf = (0..9).fold(listOf<FunSpec.Builder>()) { acc, i ->
        val tv = mutableListOf<TypeVariableName>()
        repeat(i) { tv.add(TypeVariableName("P$it").copy(reified = true)) }

        acc + FunSpec.builder("bindSingletonOf")
            .addModifiers(KModifier.INLINE)
            .addTypeVariable(reifiedType)
            .also { builder -> tv.forEach(builder::addTypeVariable) }
            .receiver(builderGeneratedClassName)
            .addParameter(
                ParameterSpec.builder(
                    name = "creator",
                    type = LambdaTypeName.get(
                        parameters = tv.toTypedArray(),
                        returnType = reifiedType
                    )
                ).addModifiers(KModifier.NOINLINE).build()
            )
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
            .addStatement("return bindSingleton(tag, overrides, sync) { new(creator) }")
    }.map { it.build() }

    return listOf(singleton, bindSingleton, eagerSingleton, bindEagerSingleton) + singletonsOf
}
