package org.kodein.di.resolver.visitor.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import org.kodein.di.resolver.visitor.BuilderExtensionTypes
import org.kodein.di.resolver.visitor.createExtension

internal fun BuilderExtensionTypes.singletonExtensions(): List<FunSpec> {
    val singleton = createExtension("singleton")
        .addAnnotation(Annotations.uncheckedCast)
        .returns(noArgBindingReturnType("Singleton"))
        .addParameter(Parameters.refMaker)
        .addParameter(Parameters.sync)
        .addParameter(Parameters.creator(receiver = noArgBindingDI, returnType = reifiedCreatedType))
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
            noArgBindingDI,
            reifiedCreatedType
        )
        .build()

    val bindSingleton = createExtension("bindSingleton")
        .addParameter(Parameters.tag)
        .addParameter(Parameters.override)
        .addParameter(Parameters.sync)
        .addParameter(Parameters.creator(receiver = noArgBindingDI, returnType = reifiedCreatedType))
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = singleton(sync = sync, creator = creator))")
        .build()

    val eagerSingleton = createExtension("eagerSingleton")
        .addAnnotation(Annotations.uncheckedCast)
        .returns(eagerBindingReturnType("EagerSingleton"))
        .addParameter(Parameters.creator(receiver = noArgBindingDI, returnType = reifiedCreatedType))
        .addStatement(
            """return EagerSingleton(
                    builder = this.containerBuilder,
                    createdType = generic(), 
                    creator = creator as NoArgBindingDI<Any>.() -> %T
                )""".trimMargin(),
            reifiedCreatedType
        )
        .build()

    val bindEagerSingleton = createExtension("bindEagerSingleton")
        .addParameter(Parameters.tag)
        .addParameter(Parameters.override)
        .addParameter(Parameters.creator(receiver = noArgBindingDI, returnType = reifiedCreatedType))
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = eagerSingleton(creator = creator))")
        .build()

    val singletonsOf = (0..9).fold(listOf<FunSpec.Builder>()) { acc, i ->
        val tv = mutableListOf<TypeVariableName>()
        repeat(i) { tv.add(TypeVariableName("P$it").copy(reified = true)) }

        acc + FunSpec.builder("bindSingletonOf")
            .addModifiers(KModifier.INLINE)
            .addTypeVariable(reifiedCreatedType)
            .also { builder -> tv.forEach(builder::addTypeVariable) }
            .receiver(builder)
            .addParameter(Parameters.creator(parameters = tv.toTypedArray(), returnType = reifiedCreatedType))
            .addParameter(Parameters.tag)
            .addParameter(Parameters.override)
            .addParameter(Parameters.sync)
            .addStatement("return bindSingleton(tag, overrides, sync) { new(creator) }")
    }.map { it.build() }

    return listOf(singleton, bindSingleton, eagerSingleton, bindEagerSingleton) + singletonsOf
}
