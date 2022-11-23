package org.kodein.di.resolver

/**
 * Defines that a given interface can be resolved by linking a DI container
 * and generating some instance accessors through a symbol processor
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
public annotation class DIResolver
