package org.kodein.di.resolver.visitor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import org.kodein.di.resolver.Names

internal class BuilderExtensionGenerator : KSEmptyVisitor<CodeGenerator, Unit>() {
    override fun defaultHandler(node: KSNode, data: CodeGenerator) =
        error("KodeinResolverGenerator can only process KSClassDeclaration")

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: CodeGenerator) {
        val extensionSignature = BuilderExtensionSignatureData.of(classDeclaration)

        val gFile = FileSpec.builder(
            packageName = classDeclaration.packageName.asString(),
            fileName = "${extensionSignature.builderGeneratedClassName.simpleName}Extensions"
        )
            .addImport("org.kodein.type", "TypeToken", "generic")
            .addImport(
                Names.diPackageName,
                "new"
            )
            .addImport(
                Names.diBindingPackageName,
                "BindingDI",
                "NoArgBindingDI",
                "Factory",
                "InstanceBinding",
                "Multiton",
                "Provider",
                "NoScope",
                "RefMaker",
            )

        extensionSignature.factoryExtensions().forEach { gFile.addFunction(it) }
        extensionSignature.instanceExtensions().forEach { gFile.addFunction(it) }
        extensionSignature.multitonExtensions().forEach { gFile.addFunction(it) }
        extensionSignature.providerExtensions().forEach { gFile.addFunction(it) }
        extensionSignature.singletonExtensions().forEach { gFile.addFunction(it) }

        gFile.build().writeTo(data, Dependencies(aggregating = false))
    }
}

internal fun BuilderExtensionSignatureData.createExtension(name: String): FunSpec.Builder {
    return FunSpec.builder(name)
        .addModifiers(KModifier.INLINE)
        .addTypeVariable(reifiedType)
        .receiver(builderGeneratedClassName)
}

internal data class BuilderExtensionSignatureData(
    val resolverClassName: ClassName,
    val resolverBindingDIClassName: ClassName,
    val resolverNoArgBindingDIClassName: ClassName,
    val builderGeneratedClassName: ClassName,
    val reifiedArgumentType: TypeVariableName,
    val reifiedType: TypeVariableName
) {
    companion object {
        fun of(classDeclaration: KSClassDeclaration): BuilderExtensionSignatureData {
            val resolverClassName = classDeclaration.toClassName()
            return BuilderExtensionSignatureData(
                resolverClassName = resolverClassName,
                resolverBindingDIClassName = ClassName(
                    classDeclaration.packageName.asString(),
                    "${resolverClassName.simpleName}BindingDI"
                ),
                resolverNoArgBindingDIClassName = ClassName(
                    classDeclaration.packageName.asString(),
                    "${resolverClassName.simpleName}NoArgBindingDI"
                ),
                builderGeneratedClassName = ClassName(
                    classDeclaration.packageName.asString(),
                    "${resolverClassName.simpleName}Builder"
                ),
                reifiedArgumentType = TypeVariableName("A", Any::class).copy(reified = true),
                reifiedType = TypeVariableName("T", Any::class).copy(reified = true)
            )
        }
    }
}