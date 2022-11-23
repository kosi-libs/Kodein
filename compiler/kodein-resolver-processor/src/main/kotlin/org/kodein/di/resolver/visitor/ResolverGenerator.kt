package org.kodein.di.resolver.visitor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import org.kodein.di.resolver.Names

internal data class ResolverGeneratorData(
    val resolverClassName: String,
    val resolverGeneratedClassName: String = "${resolverClassName}Generated",
    val classBuilder: TypeSpec.Builder = TypeSpec.classBuilder(resolverGeneratedClassName)
)

internal class ResolverGenerator(
    private val resolver: Resolver
) : KSEmptyVisitor<Unit, ResolverGeneratorData>() {
    override fun defaultHandler(node: KSNode, data: Unit): ResolverGeneratorData =
        error("KodeinResolverGenerator can only process KSClassDeclaration")

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit): ResolverGeneratorData {
        val resolverClassName = classDeclaration.toClassName().simpleName
        val generatorData = ResolverGeneratorData(resolverClassName)

        classDeclaration.superTypes.find {
            it.resolve().toClassName() == Names.DIChecker
        } ?: error("$resolverClassName must extend interface DIChecker.")

        generatorData.classBuilder.apply {
            addModifiers(KModifier.INTERNAL)
            addSuperinterface(classDeclaration.toClassName())
            addSuperinterface(Names.DIChecker)

            primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("di", Names.DI)
                    .build()
            )

            addProperty(
                PropertySpec.builder("di", Names.DI)
                    .initializer("di")
                    .addModifiers(KModifier.PRIVATE)
                    .build()
            )
        }

        return generatorData
    }
}