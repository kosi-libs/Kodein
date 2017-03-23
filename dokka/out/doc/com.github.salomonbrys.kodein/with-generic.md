[com.github.salomonbrys.kodein](index.md) / [withGeneric](.)

# withGeneric

`inline fun <reified A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.withGeneric(noinline arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`

Allows to get a lazy provider or instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.withGeneric(arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`

Allows to get a lazy provider or instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - The argument that will be passed to the factory.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.withGeneric(noinline arg: () -> A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`

Allows to inject a provider or an instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
An object from which you can inject an instance or a provider.

`inline fun <reified A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.withGeneric(arg: A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`

Allows to inject a provider or an instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - The argument that will be passed to the factory.

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
An object from which you can inject an instance or a provider.

`inline fun <reified A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.withGeneric(noinline arg: () -> A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`
`inline fun <reified A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.withGeneric(arg: A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`

Allows to get a provider or an instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

**Receiver**
Either a [Kodein](-kodein/index.md) instance or a [KodeinAware](-kodein-aware.md) class.

