package org.kodein.di.resolver.visitor.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName
import org.kodein.di.resolver.visitor.BuilderExtensionTypes
import org.kodein.di.resolver.visitor.createExtension

internal fun BuilderExtensionTypes.multitonExtensions(): List<FunSpec> {
    val multiton = createExtension("multiton")
        .addAnnotation(Annotations.uncheckedCast)
        .addTypeVariable(reifiedArgumentType)
        .returns(bindingReturnType("Multiton"))
        .addParameter(Parameters.refMaker)
        .addParameter(Parameters.sync)
        .addParameter(
            Parameters.creator(
                receiver = noArgBindingDI,
                parameters = arrayOf(reifiedArgumentType),
                returnType = reifiedCreatedType
            )
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
            reifiedCreatedType
        ).build()

    val bindMultiton = createExtension("bindMultiton")
        .addTypeVariable(reifiedArgumentType)
        .addParameter(Parameters.tag)
        .addParameter(Parameters.override)
        .addParameter(Parameters.sync)
        .addParameter(
            Parameters.creator(
                receiver = noArgBindingDI,
                parameters = arrayOf(reifiedArgumentType),
                returnType = reifiedCreatedType
            )
        )
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = multiton(sync = sync, creator = creator))")
        .build()

    return listOf(multiton, bindMultiton)
}
