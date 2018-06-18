package org.kodein.di.jxinject

import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.bindings.NoScope
import org.kodein.di.bindings.Singleton
import org.kodein.di.direct
import org.kodein.di.erased
import org.kodein.di.jxinject.internal.JxInjectorContainer

/**
 * Module that must be imported in order to use [JxInjector].
 */
val jxInjectorModule = Kodein.Module("JX Injector") {
    Bind() from Singleton(NoScope(), erased(), erased()) { JxInjectorContainer() }
}

/**
 * Utility function to retrieve a [JxInjector].
 */
val Kodein.jx: JxInjector get() = JxInjector(direct, direct.Instance(erased(), null))

/**
 * Utility function to retrieve a [JxInjector].
 */
val DKodein.jx: JxInjector get() = JxInjector(this, Instance(erased(), null))

/** @suppress */
fun <T: Annotation> Kodein.Builder.jxQualifier(cls: Class<T>, tagProvider: (T) -> Any) {
    onReady {
        Instance<JxInjectorContainer>(erased(), null).registerQualifier(cls, tagProvider)
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

    /**
     * Utility function that eases the retrieval of a [JxInjector].
     */
    @JvmStatic
    fun of(kodein: DKodein) = kodein.jx
}
