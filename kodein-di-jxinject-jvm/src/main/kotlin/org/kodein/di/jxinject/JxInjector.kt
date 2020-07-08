package org.kodein.di.jxinject

import org.kodein.di.*
import org.kodein.di.jxinject.internal.JxInjectorContainer

/**
 * Injector that allows to inject instances that use `javax.inject.*` annotations.
 *
 * @property directDI The di object to use to retrieve injections.
 */
public class JxInjector internal constructor(private val directDI: DirectDI, private val container: JxInjectorContainer) {

    /**
     * Injects all fields and methods annotated with `@Inject` in `receiver`.
     *
     * @param receiver The object to inject.
     */
    public fun inject(receiver: Any): Unit = container.inject(directDI, receiver)

    /** @suppress */
    @JvmOverloads
    public fun <T: Any> newInstance(cls: Class<T>, injectFields: Boolean = true): T = container.newInstance(directDI, cls, injectFields)

    /**
     * Creates a new instance of the given type.
     *
     * @param T The type of object to create.
     * @param injectFields Whether to inject the fields & methods of he newly created instance before returning it.
     */
    public inline fun <reified T: Any> newInstance(injectFields: Boolean = true): T = newInstance(T::class.java, injectFields)


}
