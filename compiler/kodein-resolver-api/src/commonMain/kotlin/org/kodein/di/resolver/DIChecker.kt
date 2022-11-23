package org.kodein.di.resolver

/**
 * Defines that a DI aware instance must check
 * the dependencies linked to it by the symbol processor.
 */
public interface DIChecker {
    public fun check()
}