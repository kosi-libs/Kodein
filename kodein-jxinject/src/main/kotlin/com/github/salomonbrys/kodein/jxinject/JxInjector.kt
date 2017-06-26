package com.github.salomonbrys.kodein.jxinject

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.TT
import com.github.salomonbrys.kodein.TypeToken
import com.github.salomonbrys.kodein.jxinject.internal.JxInjectorContainer
import java.lang.reflect.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

/**
 * Injector that allows to inject instances that use `javax.inject.*` annotations.
 *
 * @property _kodein The kodein object to use to retrieve injections.
 */
class JxInjector internal constructor(private val _kodein: Kodein, private val _container: JxInjectorContainer) {

    /**
     * Injects all fields and methods annotated with `@Inject` in `receiver`.
     *
     * @param receiver The object to inject.
     */
    fun inject(receiver: Any) = _container.inject(_kodein, receiver)

    /** @suppress */
    @JvmOverloads
    fun <T: Any> newInstance(cls: Class<T>, injectFields: Boolean = true) = _container.newInstance(_kodein, cls, injectFields)

    /**
     * Creates a new instance of the given type.
     *
     * @param T The type of object to create.
     * @param injectFields Whether to inject the fields & methods of he newly created instance before returning it.
     */
    inline fun <reified T: Any> newInstance(injectFields: Boolean = true) = newInstance(T::class.java, injectFields)


}
