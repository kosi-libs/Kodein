[com.github.salomonbrys.kodein](index.md) / [with](.)

# with

`inline fun <reified A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(noinline arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`

Allows to get a lazy provider or instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`

Allows to get a lazy provider or instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - The argument that will be passed to the factory.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.with(noinline arg: () -> A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`

Allows to inject a provider or an instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
An object from which you can inject an instance or a provider.

`inline fun <reified A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`

Allows to inject a provider or an instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - The argument that will be passed to the factory.

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
An object from which you can inject an instance or a provider.

`inline infix fun <reified T : Any> `[`ConstantBinder`](-kodein/-builder/-constant-binder/index.md)`.with(value: T): Unit`

Binds the previously given tag to the given instance.

T generics will be kept.

### Parameters

`T` - The type of value to bind.

`value` - The instance to bind.

`inline fun <reified A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.with(noinline arg: () -> A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`
`inline fun <reified A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`

Allows to get a provider or an instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

**Receiver**
Either a [Kodein](-kodein/index.md) instance or a [KodeinAware](-kodein-aware.md) class.

