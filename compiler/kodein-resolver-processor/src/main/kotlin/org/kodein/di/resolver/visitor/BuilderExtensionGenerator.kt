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
import org.kodein.di.resolver.visitor.utils.factoryExtensions
import org.kodein.di.resolver.visitor.utils.instanceExtensions
import org.kodein.di.resolver.visitor.utils.multitonExtensions
import org.kodein.di.resolver.visitor.utils.providerExtensions
import org.kodein.di.resolver.visitor.utils.singletonExtensions

internal class BuilderExtensionGenerator : KSEmptyVisitor<CodeGenerator, Unit>() {
    override fun defaultHandler(node: KSNode, data: CodeGenerator) =
        error("KodeinResolverGenerator can only process KSClassDeclaration")

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: CodeGenerator) {
        val extensionSignature = BuilderExtensionTypes(classDeclaration)

        val gFile = FileSpec.builder(
            packageName = classDeclaration.packageName.asString(),
            fileName = "${extensionSignature.builder.simpleName}Extensions"
        )
            .addImport(
                Names.kaveritPackageName,
                "TypeToken", "generic"
            )
            .addImport(
                Names.diPackageName,
                "new"
            )
            .addImport(
                Names.diBindingPackageName,
                "BindingDI","NoArgBindingDI","Factory","InstanceBinding","Multiton","Provider","Singleton","NoScope","RefMaker"
            )

        extensionSignature.factoryExtensions().forEach { gFile.addFunction(it) }
        extensionSignature.instanceExtensions().forEach { gFile.addFunction(it) }
        extensionSignature.multitonExtensions().forEach { gFile.addFunction(it) }
        extensionSignature.providerExtensions().forEach { gFile.addFunction(it) }
        extensionSignature.singletonExtensions().forEach { gFile.addFunction(it) }

        gFile.build().writeTo(data, Dependencies(aggregating = false))
    }
}

internal fun BuilderExtensionTypes.createExtension(name: String): FunSpec.Builder {
    return FunSpec.builder(name)
        .addModifiers(KModifier.INLINE)
        .addTypeVariable(reifiedCreatedType)
        .receiver(builder)
}

internal data class BuilderExtensionTypes(
    private val classDeclaration: KSClassDeclaration,
) {
    private val diResolver: ClassName by lazy { classDeclaration.toClassName() }
    private val classNameFactory = { suffix: String ->
        ClassName(classDeclaration.packageName.asString(), "${diResolver.simpleName}$suffix")
    }

    val builder: ClassName by lazy { classNameFactory("Builder") }
    val bindingDI: ClassName by lazy { classNameFactory("BindingDI") }

    val noArgBindingDI: ClassName by lazy { classNameFactory("NoArgBindingDI") }

    val reifiedArgumentType: TypeVariableName by lazy {
        TypeVariableName("A", Any::class).copy(reified = true)
    }

    val reifiedCreatedType: TypeVariableName by lazy {
        TypeVariableName("T", Any::class).copy(reified = true)
    }
}