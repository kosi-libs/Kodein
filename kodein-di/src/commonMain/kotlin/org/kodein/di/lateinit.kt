package org.kodein.di

import kotlin.reflect.KProperty

/**
 * DI object that defers all method to a base DI object that can be set later.
 *
 * You can use all lazy methods on this even if [baseDI] is not set, but you cannot **retrieve** a value while it is not set.
 */
public class LateInitDI : DI {

    private lateinit var _baseDI: DI
    /**
     * The user is responsible to set this property **before** actually retrieveing a value.
     */
    public var baseDI: DI
        get() = _baseDI
        set(value) {
            _baseDI = value
        }

    override val container: DIContainer get() = baseDI.container
}

/**
 * DI object that defers all method to a DI object that will be created only upon first retrieval.
 */
public class LazyDI(f: () -> DI) : DI {

    /**
     * The real DI object that will be created upon first retrieval (or manual access).
     */
    @Suppress("MemberVisibilityCanBePrivate")
    public val baseDI: DI by lazy(f)

    override val container: DIContainer get() = baseDI.container

    public operator fun getValue(thisRef: Any?, property: KProperty<*>): LazyDI = this
}
