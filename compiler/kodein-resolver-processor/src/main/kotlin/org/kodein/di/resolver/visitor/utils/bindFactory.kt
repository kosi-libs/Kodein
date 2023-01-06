package org.kodein.di.resolver.visitor.utils

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.asClassName
import org.kodein.di.resolver.visitor.BuilderExtensionTypes
import org.kodein.di.resolver.visitor.createExtension

internal fun BuilderExtensionTypes.factoryExtensions(): List<FunSpec> {
    val factory = createExtension("factory")
        .addAnnotation(Annotations.uncheckedCast)
        .addTypeVariable(reifiedArgumentType)
        .returns(bindingReturnType("Factory"))
        .addParameter(
            Parameters.creator(
                receiver = bindingDI,
                parameters = arrayOf(reifiedArgumentType),
                returnType = reifiedCreatedType
            )
        )
        .addStatement(
            """return Factory(
                    contextType = TypeToken.Any,
                    argType = generic(), 
                    createdType = generic(), 
                    bindingDIFactory = %T.factory(), 
                    creator = creator as BindingDI<Any>.(%T) -> %T
                )""".trimMargin(),
            bindingDI,
            reifiedArgumentType,
            reifiedCreatedType
        )
        .build()

    val bindFactory = createExtension("bindFactory")
        .addTypeVariable(reifiedArgumentType)
        .addParameter(Parameters.tag)
        .addParameter(Parameters.override)
        .addParameter(
            Parameters.creator(
                receiver = bindingDI,
                parameters = arrayOf(reifiedArgumentType),
                returnType = reifiedCreatedType
            )
        )
        .addStatement("return Bind(tag = tag, overrides = overrides, binding = factory(creator = creator))")
        .build()

    return listOf(factory, bindFactory)
}
