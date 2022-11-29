package org.kodein.di.resolver

/**
 * Defines that a given interface can be resolved by linking a DI container
 * and generating some instance accessors through a symbol processor
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
public annotation class Resolved

/**
 * Defines a tag on a bind function in a [Resolved]
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
@MustBeDocumented
public annotation class Tag(val ref: String)
