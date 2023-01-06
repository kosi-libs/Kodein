package org.kodein.di.resolver.visitor.utils

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock

internal object Annotations {
    val uncheckedCast = AnnotationSpec.builder(ClassName("kotlin", "Suppress"))
        .addMember(CodeBlock.of("\"UNCHECKED_CAST\""))
        .build()
}
