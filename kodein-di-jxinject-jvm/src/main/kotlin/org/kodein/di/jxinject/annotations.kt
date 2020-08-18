package org.kodein.di.jxinject

/**
 * Defines that this should be injected with the erased binding.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
public annotation class ErasedBinding

/**
 * Defines that the annotated `Function0` is to be injected as a di provider.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
public annotation class ProviderFun

/**
 * Defines that the annotated `Function1` is to be injected as a di factory.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
public annotation class FactoryFun

/**
 * Defines that this should be null if there is no corresponding binding.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
public annotation class OrNull
