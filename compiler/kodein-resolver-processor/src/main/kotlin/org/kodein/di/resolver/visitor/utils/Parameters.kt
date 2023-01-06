package org.kodein.di.resolver.visitor.utils

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asClassName
import org.kodein.di.bindings.RefMaker

internal object Parameters {

    fun creator(
        receiver: TypeName? = null,
        vararg parameters: TypeName = emptyArray(),
        returnType: TypeName
    ) = ParameterSpec
        .builder(
            name = "creator",
            type = LambdaTypeName.get(
                receiver = receiver,
                parameters = parameters,
                returnType = returnType
            )
        ).addModifiers(KModifier.NOINLINE)
        .build()


    val tag = ParameterSpec
        .builder("tag", Any::class.asClassName().copy(nullable = true))
        .defaultValue("null")
        .build()

    val override = ParameterSpec
        .builder("overrides", Boolean::class.asClassName().copy(nullable = true))
        .defaultValue("null")
        .build()

    val refMaker = ParameterSpec
        .builder("ref", RefMaker::class.asClassName().copy(nullable = true))
        .defaultValue("null")
        .build()

    val sync = ParameterSpec
        .builder("sync", Boolean::class.asClassName())
        .defaultValue("true")
        .build()

}