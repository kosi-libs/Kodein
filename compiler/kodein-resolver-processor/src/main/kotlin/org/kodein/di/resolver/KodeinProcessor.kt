package org.kodein.di.resolver

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
import com.squareup.kotlinpoet.ksp.writeTo
import org.kodein.di.resolver.visitor.DIResolverGenerator

public class KodeinProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> = try {
        resolver.getSymbolsWithAnnotation(Names.Resolved.canonicalName)
            .filterIsInstance<KSClassDeclaration>()
            .filterNot { processClass(it, resolver) }
            .toList()
    } catch (e: IllegalStateException) {
        logger.error("Kodein Resolver: ${e.message}")
        emptyList()
    }

    private fun processClass(classDeclaration: KSClassDeclaration, resolver: Resolver): Boolean {
        if (!classDeclaration.validate()) return false
        if (classDeclaration.classKind != ClassKind.INTERFACE)
            error("${classDeclaration.simpleName.asString()} must be and interface to be annoted with @${Names.Resolved}.")

        // Handle DI resolver class generation
        val (resolverGeneratedClassName, diResolver, creatorFun) =
            classDeclaration.accept(DIResolverGenerator(resolver), Unit)

        val gFile = FileSpec.builder(classDeclaration.packageName.asString(), resolverGeneratedClassName)
            .addImport(Names.diPackageName, "DI", "direct", "instance", "hasFactory")
            .addType(diResolver)

        gFile.addFunction(creatorFun)

        gFile.build().writeTo(codeGenerator, Dependencies(aggregating = false))

        return true
    }
}
