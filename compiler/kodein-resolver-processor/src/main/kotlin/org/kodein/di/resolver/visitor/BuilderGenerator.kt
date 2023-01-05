package org.kodein.di.resolver.visitor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import org.kodein.di.DIContainer
import org.kodein.di.bindings.BindingDI
import org.kodein.di.bindings.BindingDIDelegate
import org.kodein.di.bindings.NoArgBindingDI
import org.kodein.di.bindings.NoArgBindingDIDelegate
import org.kodein.di.resolver.Names

internal class BuilderGenerator : KSEmptyVisitor<CodeGenerator, Unit>() {
    override fun defaultHandler(node: KSNode, data: CodeGenerator) =
        error("KodeinResolverGenerator can only process KSClassDeclaration")

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: CodeGenerator) {
        val resolverClassName = classDeclaration.toClassName()
        val builderGeneratedClassName = ClassName(
            classDeclaration.packageName.asString(),
            "${resolverClassName.simpleName}Builder"
        )

        // DI Builder
        val builderType = generateBuilderType(classDeclaration, builderGeneratedClassName)
        // DI Companion extension function 'of_Resolver'
        val companionExtensionFunction = generateCompanionExtension(resolverClassName, builderGeneratedClassName)
        // Generate BindingDI to allow binder to access resolver API
        val resolverBindingDI = generatedBindingDI(resolverClassName)
        val resolverNoArgBindingDI = generateNoArgBindingDI(resolverClassName)

        val gFile = FileSpec.builder(classDeclaration.packageName.asString(), builderGeneratedClassName.simpleName)
            .addImport(Names.diPackageName, "DI", "DIContainer")
            .addType(builderType)
            .addFunction(companionExtensionFunction)
            .addType(resolverBindingDI)
            .addType(resolverNoArgBindingDI)
            .build()

        gFile.writeTo(data, Dependencies(aggregating = false))
    }

    private fun generateBuilderType(
        classDeclaration: KSClassDeclaration,
        builderGeneratedClassName: ClassName
    ): TypeSpec {
        return TypeSpec.classBuilder(builderGeneratedClassName)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("containerBuilder", DIContainer.Builder::class)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("containerBuilder", Names.DIContainerBuilder)
                    .initializer("containerBuilder")
                    .build()
            )
            .superclass(Names.DIResolverBuilder.parameterizedBy(classDeclaration.toClassName()))
            .addSuperclassConstructorParameter(CodeBlock.of("containerBuilder"))
            .build()
    }

    private fun generateCompanionExtension(
        resolverClassName: ClassName,
        builderGeneratedClassName: ClassName
    ): FunSpec = FunSpec.builder("of${resolverClassName.simpleName}")
        .receiver(Names.DICompanion)
        .addParameter(
            name = "init",
            type = LambdaTypeName.get(
                receiver = builderGeneratedClassName,
                returnType = Unit::class.asTypeName()
            )
        )
        .returns(resolverClassName)
        .addCode(
            CodeBlock.of(
                "return invoke { %T(containerBuilder).init() }.new%N()",
                builderGeneratedClassName, resolverClassName.simpleName
            )
        )
        .build()

    private fun generatedBindingDI(resolverClassName: ClassName): TypeSpec {
        val bindingDIType = BindingDI::class.parameterizedBy(Any::class)
        val bindingDIDelegateType = BindingDIDelegate::class.parameterizedBy(Any::class)
        val generatedBindingDIName = "${resolverClassName.simpleName}BindingDI"

        return bindingDI(resolverClassName, generatedBindingDIName, bindingDIType, bindingDIDelegateType)
            .addType(bindingDICompanion(bindingDIType, bindingDIType, generatedBindingDIName))
            .build()
    }

    private fun generateNoArgBindingDI(resolverClassName: ClassName): TypeSpec {
        val bindingDIType = BindingDI::class.parameterizedBy(Any::class)
        val bindingDIDelegateType = NoArgBindingDIDelegate::class.parameterizedBy(Any::class)
        val noArgBindingDIType = NoArgBindingDI::class.parameterizedBy(Any::class)
        val generatedNoArgBindingDIName = "${resolverClassName.simpleName}NoArgBindingDI"

        return bindingDI(resolverClassName, generatedNoArgBindingDIName, bindingDIType, bindingDIDelegateType)
            .addType(bindingDICompanion(bindingDIType, noArgBindingDIType, generatedNoArgBindingDIName))
            .build()
    }

    private fun bindingDI(
        resolverClassName: ClassName,
        generatedClassName: String,
        bindingDIType: TypeName,
        bindingDIDelegateType: TypeName
    ): TypeSpec.Builder {
        return TypeSpec.classBuilder(generatedClassName)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("_di", bindingDIType)
                    .build()
            )
            .addProperty(
                PropertySpec.builder("_di", bindingDIType)
                    .addModifiers(KModifier.PRIVATE)
                    .initializer("_di")
                    .build()
            )
            .addSuperinterface(resolverClassName, CodeBlock.of("_di.lazy.new%N()", resolverClassName.simpleName))
            .superclass(bindingDIDelegateType)
            .addSuperclassConstructorParameter(CodeBlock.of("_di"))
    }

    private fun bindingDICompanion(parameterType: TypeName, returnType: TypeName,  generatedBindingDIName: String): TypeSpec {
        return TypeSpec.companionObjectBuilder()
            .addFunction(
                FunSpec.builder("factory")
                    .returns(
                        LambdaTypeName.get(
                            parameters = arrayOf(parameterType),
                            returnType = returnType,
                        )
                    )
                    .addStatement("return { di: %T -> %N(di) }", parameterType, generatedBindingDIName)
                    .build()
            )
            .build()
    }
}