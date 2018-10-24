package org.kodein.di.jxinject

import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.di.jxinject.internal.JxInjectorContainer
import javax.inject.Named

/**
 * Module that must be imported in order to use [JxInjector].
 */
val jxInjectorModule = Kodein.Module("JX Injector") {
    Bind() from SetBinding<Any?, JxInjectorContainer.Qualifier>(AnyToken, erased(), erasedSet())
    jxQualifier(Named::class.java) { it.value }

    Bind() from Singleton(NoScope(), erased(), erased()) { JxInjectorContainer(Instance(erasedSet())) }
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
@Suppress("UNCHECKED_CAST")
fun <T: Annotation> Kodein.Builder.jxQualifier(cls: Class<T>, tagProvider: (T) -> Any) {
    Bind(erased<JxInjectorContainer.Qualifier>()).InSet(erasedSet()) with InstanceBinding(erased(), JxInjectorContainer.Qualifier(cls, tagProvider as (Annotation) -> Any))
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
