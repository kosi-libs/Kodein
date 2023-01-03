package org.kodein.di

import org.kodein.di.bindings.*
import org.kodein.type.generic


/**
 * Creates a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested.
 * Guaranteed to be called only once. Should create a new instance.
 * @return A singleton ready to be bound.
 */
public inline fun <C : Any, reified T: Any> DI.BindBuilder.WithScope<C>.singleton(
    ref: RefMaker? = null,
    sync: Boolean = true,
    noinline creator: NoArgBindingDI<C>.() -> T,
): Singleton<C, T> = Singleton(scope, contextType, explicitContext, generic(), ref, sync, creator)

/**
 * Creates an eager singleton: will create an instance as soon as kodein is ready
 * (all bindings are set) and will always return this instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as DI is ready.
 * Guaranteed to be called only once. Should create a new instance.
 * @return An eager singleton ready to be bound.
 */
public inline fun <reified T: Any> DI.Builder.eagerSingleton(
    noinline creator: NoArgBindingDI<Any>.() -> T
): EagerSingleton<T> = EagerSingleton(containerBuilder, generic(), creator)

/**
 * Binds a singleton: will create an instance on first request and will subsequently always return the same instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called the first time an instance is requested.
 * Guaranteed to be called only once. Should create a new instance.
 */
public inline fun <reified T: Any> DI.Builder.bindSingleton(
    tag: Any? = null,
    overrides: Boolean? = null,
    sync: Boolean = true,
    noinline creator: DirectDI.() -> T,
): Unit = Bind(tag = tag, overrides = overrides, binding = singleton(sync = sync, creator = creator))

/**
 * Binds an eager singleton: will create an instance as soon as kodein is ready
 * (all bindings are set) and will always return this instance.
 *
 * T generics will be erased!
 *
 * @param T The created type.
 * @param creator The function that will be called as soon as DI is ready.
 * Guaranteed to be called only once. Should create a new instance.
 */
public inline fun <reified T: Any> DI.Builder.bindEagerSingleton(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline creator: DirectDI.() -> T,
): Unit = Bind(tag = tag, overrides = overrides, binding = eagerSingleton(creator = creator))

 /**
  * Binds a singleton: will create an instance on first request and will subsequently always return the same instance.
  *
  * T generics will be erased!
  *
  * @param T The created type.
  * @param constructor The function reference to the T constructor (e.g. :: T)
  */
 public inline fun <reified T: Any> DI.Builder.bindSingletonOf(
     noinline constructor: () -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any, reified P1> DI.Builder.bindSingletonOf(
     noinline constructor: (P1) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any, reified P1, reified P2> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2, P3) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2, P3, P4) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2, P3, P4, P5) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6,
         reified P7> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6, P7) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6,
         reified P7, reified P8> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6,
         reified P7, reified P8,
         reified P9> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }

 /**
  * @see bindSingletonOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6,
         reified P7, reified P8,
         reified P9, reified P10> DI.Builder.bindSingletonOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
     sync: Boolean = true,
 ): Unit = bindSingleton(tag, overrides, sync) { new(constructor) }
