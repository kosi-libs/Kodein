package org.kodein.di

import kotlin.reflect.KProperty

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("LateInitDI"), DeprecationLevel.ERROR)
typealias LateInitKodein = LateInitDI
/**
 * DI object that defers all method to a base DI object that can be set later.
 *
 * You can use all lazy methods on this even if [baseDI] is not set, but you cannot **retrieve** a value while it is not set.
 */
class LateInitDI : DI {

    private lateinit var _baseDI: DI
    /**
     * The user is responsible to set this property **before** actually retrieveing a value.
     */
    var baseDI: DI
        get() = _baseDI
        set(value) {
            _baseDI = value
        }
    @Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("baseDI"), DeprecationLevel.ERROR)
    var baseKodein
        get() = _baseDI
        set(value) {
            _baseDI = value
        }

    override val container: DIContainer get() = baseDI.container
}

@Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("LazyDI"), DeprecationLevel.ERROR)
typealias LazyKodein = LazyDI
/**
 * DI object that defers all method to a DI object that will be created only upon first retrieval.
 */
class LazyDI(f: () -> DI) : DI {

    /**
     * The real DI object that will be created upon first retrieval (or manual access).
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val baseDI by lazy(f)
    @Deprecated(DEPRECATED_KODEIN_7X, ReplaceWith("baseDI"), DeprecationLevel.ERROR)
    val baseKodein by lazy { baseDI }

    override val container: DIContainer get() = baseDI.container

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = this
}
