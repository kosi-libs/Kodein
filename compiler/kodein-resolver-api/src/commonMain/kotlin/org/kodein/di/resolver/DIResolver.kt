package org.kodein.di.resolver

/**
 * Defines that a DI resolver instance that must check
 * the dependencies linked to it by the symbol processor.
 */
public interface DIResolver {
    public fun check()
}