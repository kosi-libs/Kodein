package org.kodein.di.resolver.visitor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.UNIT
import com.squareup.kotlinpoet.ksp.toClassName
import org.kodein.di.resolver.Tag

internal data class FunctionResolverData(
    val funSpec: FunSpec,
    val checkBlock: CodeBlock,
    val key: BindKey
)

@OptIn(KspExperimental::class)
internal class FunctionResolver : KSEmptyVisitor<String, FunctionResolverData>() {
    override fun defaultHandler(node: KSNode, data: String): FunctionResolverData =
        error("[$data - KodeinFunctionResolver can only process KSFunctionDeclaration")

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: String): FunctionResolverData {
        val returnType = function.returnType!!.resolve().toClassName()

        if (function.parameters.size > 1)
            error("$data - Multi argument binding are not allowed. You should consider the use of a data class.")

        val argument = function.getArgument()
        val tag = function.getTag()

        val diAccessor = FunSpec.builder(function.simpleName.asString())
            .addModifiers(KModifier.OVERRIDE)
            .returns(returnType)

        if (argument != null) {
            diAccessor.addParameter(argument.name, argument.type)
        }

        val diInstanceStatement = createDiInstanceStatement(returnType, tag, argument)

        diAccessor.addCode(diInstanceStatement)

        val checkBlock = CodeBlock.of(
            """require(di.hasFactory<%N, %N>%L) {
                        |   "Missing·binding·for·%L·while·checking·$data·consistency."
                        |}
                        |""".trimMargin(),
            argument?.type?.simpleName ?: UNIT.simpleName,
            returnType.simpleName,
            tag?.let { "(${it.literal})" } ?: "()",
            buildString {
                append("[${returnType.simpleName}]")
                tag?.let { append("""·+·[tag·=·\"${it.ref}\"]""") }
                argument?.let { append("""·+·[${it.type.simpleName}·argument]""") }
            }
        )

        return FunctionResolverData(
            funSpec = diAccessor.build(),
            checkBlock = checkBlock,
            key = BindKey(
                context = null, // TODO Manage context in the future
                argument = argument?.type,
                type = returnType,
                tag = tag?.ref
            )
        )
    }

    private fun createDiInstanceStatement(returnType: ClassName, tag: BindTag?, argument: BindArgument?): CodeBlock {
        val typeParametersBlock = buildString {
            append("<")
            argument?.let { append("${it.type.simpleName},·") }
            append("${returnType.simpleName}>")
        }

        val instanceCallParameterBlock = buildString {
            append("(")
            tag?.let { append(it.literal) }
            argument?.let {
                if (!endsWith("(")) append(", ")
                append(it.literal)
            }
            append(")")
        }

        return CodeBlock.of(
            "return di.direct.instance%L%L",
            typeParametersBlock,
            instanceCallParameterBlock
        )
    }

    private fun KSFunctionDeclaration.getTag(): BindTag? {
        return getAnnotationsByType(Tag::class).firstOrNull()?.let {
            BindTag(it.ref)
        }
    }

    private fun KSFunctionDeclaration.getArgument(): BindArgument? {
        return parameters.firstOrNull()?.let {
            val type = it.type.resolve().toClassName()
            BindArgument(
                name = it.name?.asString() ?: "arg",
                type = type,
            )
        }
    }
}

private data class BindTag(val ref: String) {
    val literal = "tag·=·\"$ref\""
}

private data class BindArgument(val name: String, val type: ClassName) {
    val literal = "arg·=·$name"
}

internal data class BindKey(
    val context: ClassName?,
    val argument: ClassName?,
    val type: ClassName,
    val tag: String?
) {
    override fun toString(): String = buildString {
        append("\\n bind<${type.simpleName}>")
        append("(")
        if (tag != null) {
            append("""tag = \"$tag\"""")
        }
        if (argument != null) {
            if (!this.endsWith("(")) append(", ")
            append("""arg = ${argument.simpleName}""")
        }
        append(")")
    }
}