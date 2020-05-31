package org.kodein.di.jxinject

import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.di.jxinject.internal.JxInjectorContainer
import org.kodein.type.TypeToken
import org.kodein.type.erased
import javax.inject.Named

/**
 * Module that must be imported in order to use [JxInjector].
 */
val jxInjectorModule = DI.Module("JX Injector") {
    Bind() from SetBinding<Any, JxInjectorContainer.Qualifier>(TypeToken.Any, org.kodein.type.generic(), erasedSet())
    jxQualifier(Named::class.java) { it.value }

    Bind() from Singleton(NoScope(), org.kodein.type.generic(), org.kodein.type.generic()) {
        JxInjectorContainer(Instance<Set<JxInjectorContainer.Qualifier>>(erasedSet<JxInjectorContainer.Qualifier>()))
    }
}

/**
 * Utility function to retrieve a [JxInjector].
 */
val DI.jx: JxInjector get() = JxInjector(direct, direct.Instance(org.kodein.type.generic(), null))

/**
 * Utility function to retrieve a [JxInjector].
 */
val DirectDI.jx: JxInjector get() = JxInjector(this, Instance(org.kodein.type.generic(), null))

/** @suppress */
@Suppress("UNCHECKED_CAST")
fun <T: Annotation> DI.Builder.jxQualifier(cls: Class<T>, tagProvider: (T) -> Any) {
    Bind(erased<JxInjectorContainer.Qualifier>()).InSet(erasedSet()) with InstanceBinding(org.kodein.type.generic(), JxInjectorContainer.Qualifier(cls, tagProvider as (Annotation) -> Any))
}

/**
 * Registers a function that transforms a qualifier annotation into a tag.
 *
 * @param T The qualifier annotation type.
 * @receiver DI Builder.
 * @param tagProvider A function that transforms an annotation of type `T` into a tag.
 */
internal inline fun <reified T: Annotation> DI.Builder.jxQualifier(noinline tagProvider: (T) -> Any) = jxQualifier(T::class.java, tagProvider)

/**
 * Utility function that eases the retrieval of a [JxInjector].
 */
object Jx {
    /**
     * Utility function that eases the retrieval of a [JxInjector].
     */
    @JvmStatic
    fun of(di: DI) = di.jx

    /**
     * Utility function that eases the retrieval of a [JxInjector].
     */
    @JvmStatic
    fun of(directDI: DirectDI) = directDI.jx
}
