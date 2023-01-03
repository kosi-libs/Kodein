package org.kodein.di

import org.kodein.di.bindings.NoArgBindingDI
import org.kodein.di.bindings.Provider
import org.kodein.type.generic

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T generics will be erased!
 *
 * A provider is like a [factory], but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 * @return A provider ready to be bound.
 */
public inline fun <C : Any, reified T: Any> DI.BindBuilder<C>.provider(
    noinline creator: NoArgBindingDI<C>.() -> T
): Provider<C, T> = Provider(contextType, generic(), creator)

/**
 * Creates a factory: each time an instance is needed, the function [creator] function will be called.
 *
 * T generics will be erased!
 *
 * A provider is like a [factory], but without argument.
 *
 * @param T The created type.
 * @param creator The function that will be called each time an instance is requested. Should create a new instance.
 */
public inline fun <reified T: Any> DI.Builder.bindProvider(
    tag: Any? = null,
    overrides: Boolean? = null,
    noinline creator: DirectDI.() -> T
): Unit = Bind(tag = tag, overrides = overrides, binding = provider(creator = creator))

// TODO This fails with Kotlin/JS Legacy
//  Re-enable this with IR only target.
//  see https://youtrack.jetbrains.com/issue/KT-39225/KJS-MarkerError-on-runtime-when-using-new-typeToken-via-Kodein-7
 /**
  * Creates a factory: each time an instance is needed, the function [constructor] function will be called.
  *
  * T generics will be erased!
  *
  * @param T The created type.
  * @param constructor The function reference to the T constructor (e.g. :: T)
  */
 public inline fun <reified T: Any> DI.Builder.bindProviderOf(
     noinline constructor: () -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }


 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any, reified P1> DI.Builder.bindProviderOf(
     noinline constructor: (P1) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any, reified P1, reified P2> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2, P3) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2, P3, P4) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2, P3, P4, P5) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6,
         reified P7> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6, P7) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6,
         reified P7, reified P8> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6,
         reified P7, reified P8,
         reified P9> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }

 /**
  * @see bindProviderOf
  */
 public inline fun <reified T: Any,
         reified P1, reified P2,
         reified P3, reified P4,
         reified P5, reified P6,
         reified P7, reified P8,
         reified P9, reified P10> DI.Builder.bindProviderOf(
     noinline constructor: (P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> T,
     tag: Any? = null,
     overrides: Boolean? = null,
 ): Unit = bindProvider(tag, overrides) { new(constructor) }
