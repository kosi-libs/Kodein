package org.kodein.di.resolver.visitor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import org.kodein.di.DI
import org.kodein.di.resolver.Names
import org.kodein.di.resolver.Resolved

internal data class DIResolverGeneratorData(
    val resolverGeneratedClassName: String,
    val diResolver: TypeSpec,
    val resolverCreator: FunSpec
)

@OptIn(KspExperimental::class)
internal class DIResolverGenerator(
    private val resolver: Resolver
) : KSEmptyVisitor<Unit, DIResolverGeneratorData>() {
    override fun defaultHandler(node: KSNode, data: Unit): DIResolverGeneratorData =
        error("KodeinResolverGenerator can only process KSClassDeclaration")

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit): DIResolverGeneratorData {
        val resolverClassName = classDeclaration.toClassName().simpleName
        val resolverGeneratedClassName = "${resolverClassName}Generated"

        val classBuilder: TypeSpec.Builder = TypeSpec.classBuilder(resolverGeneratedClassName)

        classDeclaration.superTypes.find {
            it.resolve().toClassName() == Names.DIResolver
        } ?: error("$resolverClassName must extend interface ${Names.DIResolver.simpleName}.")

        classBuilder.apply {
            addModifiers(KModifier.INTERNAL)
            addSuperinterface(classDeclaration.toClassName())
            addSuperinterface(Names.DIResolver)

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

        // Handle DI resolver declared functions (local to the current classDeclaration)
        val declaredFunSpecs = classDeclaration.getDeclaredFunctions().map {
            it.accept(FunctionResolver(), classDeclaration.simpleName.asString())
        }
        // Handle DI resolver super types declared functions (from any super type of classDeclaration)
        val superFunSpecs = classDeclaration.getAllSuperTypes()
            .mapNotNull { it.declaration as? KSClassDeclaration }
            .filter { it.isAnnotationPresent(Resolved::class) }
            .flatMap { superTypeDeclaration ->
                superTypeDeclaration.getDeclaredFunctions().map {
                    it.accept(FunctionResolver(), classDeclaration.simpleName.asString())
                }
            }

        val allFun = (declaredFunSpecs + superFunSpecs)

        // Create a function to check the consistency of the current DI Resolver
        val checkFun = allFun.fold(
            FunSpec.builder("check")
                .addModifiers(KModifier.OVERRIDE)
        ) { checkFun, gFunc ->
            classBuilder.addFunction(gFunc.funSpec)
            checkFun.addCode(gFunc.checkBlock)
        }

        classBuilder.addFunction(checkFun.build())

        // Create a toString function to list every keys resolved by the current resovler
        val toStringStatement = buildString {
            append("""return "$resolverClassName manages the following binding:"""")
            allFun.map { it.key }.forEach { append(" +\n \"$it\"") }
        }

        classBuilder.addFunction(
            FunSpec.builder("toString")
                .returns(String::class)
                .addModifiers(KModifier.OVERRIDE)
                .addStatement(toStringStatement)
                .build()
        )

        // Generate a helper function to create an instance of the DI resolver
        val creatorFunc = FunSpec.builder("new${resolverClassName}")
            .receiver(DI::class)
            .returns(classDeclaration.toClassName())
            .addCode("return %N(this)", resolverGeneratedClassName)

        return DIResolverGeneratorData(
            resolverGeneratedClassName = resolverGeneratedClassName,
            diResolver = classBuilder.build(),
            resolverCreator = creatorFunc.build()
        )
    }
}