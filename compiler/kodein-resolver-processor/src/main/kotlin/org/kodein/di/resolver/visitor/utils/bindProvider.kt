package org.kodein.di.resolver.visitor.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import org.kodein.di.resolver.visitor.BuilderExtensionTypes
import org.kodein.di.resolver.visitor.createExtension

internal fun BuilderExtensionTypes.providerExtensions(): List<FunSpec> {
    val provider = createExtension("provider")
        .addAnnotation(Annotations.uncheckedCast)
        .returns(noArgBindingReturnType("Provider"))
        .addParameter(Parameters.creator(receiver = noArgBindingDI, returnType = reifiedCreatedType))
        .addStatement(
            """return Provider(
                    contextType = TypeToken.Any,
                    createdType = generic(), 
                    bindingDIFactory = %T.factory(), 
                    creator = creator as NoArgBindingDI<Any>.() -> %T
                )""".trimMargin(),
            noArgBindingDI,
            reifiedCreatedType
        )
        .build()

    val bindProvider = FunSpec.builder("bindProvider")
        .addModifiers(KModifier.INLINE)
        .addTypeVariable(reifiedCreatedType)
        .receiver(builder)
        .addParameter(Parameters.tag)
        .addParameter(Parameters.override)
        .addParameter(Parameters.creator(receiver = noArgBindingDI, returnType = reifiedCreatedType))
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = provider(creator = creator))")
        .build()

    val providersOf = (0..9).fold(listOf<FunSpec.Builder>()) { acc, i ->
        val tv = mutableListOf<TypeVariableName>()
        repeat(i) { tv.add(TypeVariableName("P$it").copy(reified = true)) }

        acc + FunSpec.builder("bindProviderOf")
            .addModifiers(KModifier.INLINE)
            .addTypeVariable(reifiedCreatedType)
            .also { builder -> tv.forEach(builder::addTypeVariable) }
            .receiver(builder)
            .addParameter(Parameters.tag)
            .addParameter(Parameters.override)
            .addParameter(Parameters.creator(parameters = tv.toTypedArray(), returnType = reifiedCreatedType))
            .addStatement("return bindProvider(tag, overrides) { new(creator) }")
    }.map { it.build() }

    return listOf(provider, bindProvider) + providersOf
}
