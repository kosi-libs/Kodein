package org.kodein.di.resolver

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import org.kodein.di.DI
import org.kodein.di.resolver.visitor.FunctionResolver
import org.kodein.di.resolver.visitor.ResolverGenerator

@OptIn(KspExperimental::class)
public class KodeinProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> = try {
        resolver.getSymbolsWithAnnotation("org.kodein.di.resolver.DIResolver")
            .filterIsInstance<KSClassDeclaration>()
            .mapNotNull { it.takeUnless { processClass(it, resolver) } }
            .toList()
    } catch (e: IllegalStateException) {
        logger.error("Kodein Resolver: ${e.message}")
        emptyList()
    }

    private fun processClass(classDeclaration: KSClassDeclaration, resolver: Resolver): Boolean {
        if (!classDeclaration.validate()) return false
        if (classDeclaration.classKind != ClassKind.INTERFACE)
            error("${classDeclaration.simpleName.asString()} must be and interface to be annoted with @DIResolver.")

        // Handle DI resolver class generation
        val (resolverClassName, resolverGeneratedClassName, classBuilder) =
            classDeclaration.accept(ResolverGenerator(resolver), Unit)

        val gFile = FileSpec.builder(classDeclaration.packageName.asString(), resolverGeneratedClassName)

        // Handle DI resolver declared functions (local to the current classDeclaration)
        val declaredFunSpecs = classDeclaration.getDeclaredFunctions().map {
            it.accept(FunctionResolver(), classDeclaration.simpleName.asString())
        }
        // Handle DI resolver super types declared functions (from any super type of classDeclaration)
        val superFunSpecs = classDeclaration.getAllSuperTypes()
            .mapNotNull { it.declaration as? KSClassDeclaration }
            .filter { it.isAnnotationPresent(DIResolver::class) }
            .flatMap { superTypeDeclaration ->
                superTypeDeclaration.getDeclaredFunctions().map {
                    it.accept(FunctionResolver(), classDeclaration.simpleName.asString())
                }
            }

        // Create a function to check the consistency of the current DI Resolver
        val checkFun = (declaredFunSpecs + superFunSpecs).fold(
            FunSpec.builder("check")
                .addModifiers(KModifier.OVERRIDE)
        ) { checkFun, data ->
            classBuilder.addFunction(data.funSpec)
            checkFun.addCode(data.checkBlock)
        }.build()

        classBuilder.addFunction(checkFun)


        gFile
            .addImport(Names.diPackageName, "DI", "direct", "instance")
            .addImport(Names.resolverPackageName, "hasFactory")
            .addType(classBuilder.build())

        // Generate a helper function to create an instance of the DI resolver
        val creatorFun = FunSpec.builder("new${resolverClassName}")
            .receiver(DI::class)
            .returns(classDeclaration.toClassName())
            .addCode("return %N(this)", resolverGeneratedClassName)
        gFile.addFunction(creatorFun.build())

        gFile.build().writeTo(codeGenerator, Dependencies(aggregating = false))

        return true
    }
}
