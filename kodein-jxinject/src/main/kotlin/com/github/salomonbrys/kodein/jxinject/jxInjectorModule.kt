package com.github.salomonbrys.kodein.jxinject

import com.github.salomonbrys.kodein.*

/**
 * Module that must be imported in order to use [JxInjector].
 */
val jxInjectorModule = Kodein.Module {
    bind() from erasedSingleton { JxInjector(kodein) }
}

/**
 * Utility function that eases the retrieval of a [JxInjector].
 */
val Kodein.jx: JxInjector get() = erasedInstanceOrNull<JxInjector>()
                                  ?: throw IllegalStateException("Did you forget to import(jxInjectorModule)?")

/** @suppress */
fun <T: Annotation> Kodein.Builder.jxQualifier(cls: Class<T>, tagProvider: (T) -> Any) {
    onReady {
        erasedInstance<JxInjector>().registerQualifier(cls, tagProvider)
    }
}

/**
 * Registers a function that transforms a qualifier annotation into a tag.
 *
 * @param T The qualifier annotation type.
 * @receiver Kodein Builder.
 * @param tagProvider A function that transforms an annotation of type `T` into a tag.
 */
internal inline fun <reified T: Annotation> Kodein.Builder.jxQualifier(noinline tagProvider: (T) -> Any) = jxQualifier(T::class.java, tagProvider)

/**
 * Utility function that eases the retrieval of a [JxInjector].
 */
object Jx {
    /**
     * Utility function that eases the retrieval of a [JxInjector].
     */
    @JvmStatic
    fun of(kodein: Kodein) = kodein.jx
}
