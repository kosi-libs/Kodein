package org.kodein.di.jxinject

import org.kodein.di.*
import org.kodein.di.bindings.*
import org.kodein.di.jxinject.internal.JxInjectorContainer
import org.kodein.type.erased
import javax.inject.Named

/**
 * Module that must be imported in order to use [JxInjector].
 */
public val jxInjectorModule: DI.Module = DI.Module("JX Injector") {
    bindSet<JxInjectorContainer.Qualifier>()
    jxQualifier(Named::class.java) { it.value }

    bind<JxInjectorContainer>() with Singleton(NoScope(), erased(), false, erased()) { JxInjectorContainer(Instance(erasedSet())) }
}

/**
 * Utility function to retrieve a [JxInjector].
 */
public val DI.jx: JxInjector get() = JxInjector(direct, direct.Instance(erased(), null))

/**
 * Utility function to retrieve a [JxInjector].
 */
public val DirectDI.jx: JxInjector get() = JxInjector(this, Instance(erased(), null))

/** @suppress */
@Suppress("UNCHECKED_CAST")
public fun <T: Annotation> DI.Builder.jxQualifier(cls: Class<T>, tagProvider: (T) -> Any) {
    InBindSet(type = erased<JxInjectorContainer.Qualifier>()) {
        add { InstanceBinding(erased(), JxInjectorContainer.Qualifier(cls, tagProvider as (Annotation) -> Any)) }
    }
}

/**
 * Registers a function that transforms a qualifier annotation into a tag.
 *
 * @param T The qualifier annotation type.
 * @receiver DI Builder.
 * @param tagProvider A function that transforms an annotation of type `T` into a tag.
 */
internal inline fun <reified T : Annotation> DI.Builder.jxQualifier(noinline tagProvider: (T) -> Any) =
    jxQualifier(T::class.java, tagProvider)

/**
 * Utility function that eases the retrieval of a [JxInjector].
 */
public object Jx {
    /**
     * Utility function that eases the retrieval of a [JxInjector].
     */
    @JvmStatic
   public fun of(di: DI): JxInjector = di.jx

    /**
     * Utility function that eases the retrieval of a [JxInjector].
     */
    @JvmStatic
    public fun of(directDI: DirectDI): JxInjector = directDI.jx
}
