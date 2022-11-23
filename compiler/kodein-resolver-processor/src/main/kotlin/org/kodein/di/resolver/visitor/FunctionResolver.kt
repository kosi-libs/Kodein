package org.kodein.di.resolver.visitor

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.toClassName

internal data class FunctionResolverData(
    val funSpec: FunSpec,
    val checkBlock: CodeBlock
)

internal class FunctionResolver : KSEmptyVisitor<Unit, FunctionResolverData>() {
    override fun defaultHandler(node: KSNode, data: Unit): FunctionResolverData =
        error("KodeinFunctionResolver can only process KSFunctionDeclaration")

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit): FunctionResolverData {
        val returnType = function.returnType!!.resolve().toClassName()
        val arg = function.parameters.firstOrNull()
        val funSpec = FunSpec.builder(function.simpleName.asString())
            .addModifiers(KModifier.OVERRIDE)
            .returns(returnType)
            .addStatement("return di.direct.instance<%N>()", returnType.simpleName)

        arg?.let {
            val argumentType = it.type.resolve().toClassName()
            funSpec.addParameter(it.name?.asString()!!, argumentType)
        }

        val checkBlock = CodeBlock.of(
            """require(di.hasFactory<%N, %N>()) {
                        |   "Missing [%N] binding while checking container consistency."
                        |}
                        |""".trimMargin(),
            returnType.simpleName,
            arg?.type?.resolve()?.toClassName()?.simpleName ?: UNIT.simpleName,
            returnType.simpleName
        )

        return FunctionResolverData(funSpec.build(), checkBlock)
    }
}