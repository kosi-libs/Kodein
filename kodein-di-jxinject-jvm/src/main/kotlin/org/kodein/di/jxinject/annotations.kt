package org.kodein.di.jxinject

/**
 * Defines that this should be injected with the erased binding.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ErasedBinding

/**
 * Defines that the annotated `Function0` is to be injected as a kodein provider.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ProviderFun

/**
 * Defines that the annotated `Function1` is to be injected as a kodein factory.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class FactoryFun

/**
 * Defines that this should be null if there is no corresponding binding.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class OrNull
