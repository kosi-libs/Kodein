package com.github.salomonbrys.kodein

import java.lang.reflect.Type

public class Kodein(public val _container: Container) {

    public data class Bind(
            val type: Type,
            val tag: Any?
    ) {
        override fun toString() = "bind<${type.typeName}>(${ if (tag != null) "\"$tag\"" else "" })"
    }

    public data class Key(
            val bind: Bind,
            val argType: Type
    )

    public class Builder(init: Builder.() -> Unit) {

        internal val _builder = Container.Builder()

        init { this.init() }

        public inner class TypeBinder<T : Any>(private val bind: Bind) {
            public fun <R : T, A> with(factory: Factory<A, R>) = _builder.bind(Key(bind, factory.argType), factory)
        }

        public inner class ConstantBinder(private val tag: String) {
            public fun with(value: Any) = _builder.bind(Key(Bind(value.javaClass, tag), Unit.javaClass), instance(value))
        }

        public inline fun <reified T : Any> bind(tag: Any? = null): TypeBinder<T> = TypeBinder(Bind(typeToken<T>(), tag))

        public fun constant(tag: String): ConstantBinder = ConstantBinder(tag)
    }

    public constructor(init: Kodein.Builder.() -> Unit) : this(Container(Builder(init)._builder))

    public val registered: Map<Kodein.Bind, String> = _container.registered

    /**
     * Exception thrown when there is a dependency loop.
     */
    public class DependencyLoopException internal constructor(message: String) : RuntimeException(message)

    public class NotFoundException(message: String) : RuntimeException(message)

    public inline fun <reified A, reified T : Any> factory(tag: Any? = null): ((A) -> T) = _container.nonNullFactory<A, T>(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), typeToken<A>()))

    public inline fun <reified A, reified T : Any> factoryOrNull(tag: Any? = null): ((A) -> T)? = _container.factoryOrNull<A, T>(Kodein.Key(Kodein.Bind(typeToken<T>(), tag), typeToken<A>()))

    /**
     * Gets a provider for the given type and tag.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     * Throws an IllegalStateException if the provider could not be found.
     */
    public inline fun <reified T : Any> provider(tag: Any? = null): (() -> T) = _container.nonNullProvider(Kodein.Bind(typeToken<T>(), tag))

    /**
     * Gets a provider for the given type and tag, or null if none is found.
     *
     * Whether a provider will re-create a new instance at each call or not depends on the binding scope.
     */
    public inline fun <reified T : Any> providerOrNull(tag: Any? = null): (() -> T)? = _container.providerOrNull(Kodein.Bind(typeToken<T>(), tag))

    /**
     * Gets an instance for the given type and tag.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     * Throws an IllegalStateException if the instance could not be found.
     */
    public inline fun <reified T : Any> instance(tag: Any? = null): T = _container.nonNullProvider<T>(Kodein.Bind(typeToken<T>(), tag)).invoke()

    /**
     * Gets an instance for the given type and tag, or null if none is found.
     *
     * Whether the returned object is a new instance at each call or not depends on the binding scope.
     */
    public inline fun <reified T : Any> instanceOrNull(tag: Any? = null): T? = _container.providerOrNull<T>(Kodein.Bind(typeToken<T>(), tag))?.invoke()
}
