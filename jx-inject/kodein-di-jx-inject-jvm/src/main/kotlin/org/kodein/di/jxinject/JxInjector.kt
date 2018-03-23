package org.kodein.di.jxinject

import org.kodein.di.DKodein
import org.kodein.di.jxinject.internal.JxInjectorContainer

/**
 * Injector that allows to inject instances that use `javax.inject.*` annotations.
 *
 * @property kodein The kodein object to use to retrieve injections.
 */
class JxInjector internal constructor(private val kodein: DKodein, private val container: JxInjectorContainer) {

    /**
     * Injects all fields and methods annotated with `@Inject` in `receiver`.
     *
     * @param receiver The object to inject.
     */
    fun inject(receiver: Any) = container.inject(kodein, receiver)

    /** @suppress */
    @JvmOverloads
    fun <T: Any> newInstance(cls: Class<T>, injectFields: Boolean = true) = container.newInstance(kodein, cls, injectFields)

    /**
     * Creates a new instance of the given type.
     *
     * @param T The type of object to create.
     * @param injectFields Whether to inject the fields & methods of he newly created instance before returning it.
     */
    inline fun <reified T: Any> newInstance(injectFields: Boolean = true) = newInstance(T::class.java, injectFields)


}
