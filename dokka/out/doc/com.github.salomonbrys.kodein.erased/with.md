[com.github.salomonbrys.kodein.erased](index.md) / [with](.)

# with

`inline fun <reified A> `[`KodeinInjectedBase`](../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(noinline arg: () -> A): `[`CurriedInjectorFactory`](../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`

Allows to inject a provider or an instance from a curried factory with an `A` argument.

A generics will be erased!

### Parameters

`A` - The type of argument the factory takes.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
Either a [KodeinInjector](../com.github.salomonbrys.kodein/-kodein-injector/index.md) instance or a [KodeinInjected](../com.github.salomonbrys.kodein/-kodein-injected.md) class.

**Return**
An object from which you can inject an instance or a provider.

`inline fun <reified A> `[`KodeinInjectedBase`](../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`

Allows to inject a provider or an instance from a curried factory with an `A` argument.

A generics will be erased!

### Parameters

`A` - The type of argument the factory takes.

`arg` - The argument that will be passed to the factory.

**Receiver**
Either a [KodeinInjector](../com.github.salomonbrys.kodein/-kodein-injector/index.md) instance or a [KodeinInjected](../com.github.salomonbrys.kodein/-kodein-injected.md) class.

**Return**
An object from which you can inject an instance or a provider.

`inline infix fun <reified T : Any> `[`ConstantBinder`](../com.github.salomonbrys.kodein/-kodein/-builder/-constant-binder/index.md)`.with(value: T): Unit`

Binds the previously given tag to the given instance.

T generics will be erased!

### Parameters

`T` - The type of value to bind.

`value` - The instance to bind.

`inline fun <reified A> `[`LazyKodeinAwareBase`](../com.github.salomonbrys.kodein/-lazy-kodein-aware-base/index.md)`.with(noinline arg: () -> A): `[`CurriedLazyKodeinFactory`](../com.github.salomonbrys.kodein/-curried-lazy-kodein-factory/index.md)`<A>`

Allows to get a lazy provider or instance from a curried factory with an `A` argument.

A generics will be erased!

### Parameters

`A` - The type of argument the factory takes.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
Either a [LazyKodein](../com.github.salomonbrys.kodein/-lazy-kodein/index.md) instance or a [LazyKodeinAware](../com.github.salomonbrys.kodein/-lazy-kodein-aware.md) class.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified A> `[`LazyKodeinAwareBase`](../com.github.salomonbrys.kodein/-lazy-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedLazyKodeinFactory`](../com.github.salomonbrys.kodein/-curried-lazy-kodein-factory/index.md)`<A>`

Allows to get a lazy provider or instance from a curried factory with an `A` argument.

A generics will be erased!

### Parameters

`A` - The type of argument the factory takes.

`arg` - The argument that will be passed to the factory.

**Receiver**
Either a [LazyKodein](../com.github.salomonbrys.kodein/-lazy-kodein/index.md) instance or a [LazyKodeinAware](../com.github.salomonbrys.kodein/-lazy-kodein-aware.md) class.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified A> `[`KodeinAwareBase`](../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.with(noinline arg: () -> A): `[`CurriedKodeinFactory`](../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`
`inline fun <reified A> `[`KodeinAwareBase`](../com.github.salomonbrys.kodein/-kodein-aware-base/index.md)`.with(arg: A): `[`CurriedKodeinFactory`](../com.github.salomonbrys.kodein/-curried-kodein-factory/index.md)`<A>`

Allows to get a provider or an instance from a curried factory with an `A` argument.

A generics will be erased!

### Parameters

`A` - The type of argument the factory takes.

**Receiver**
Either a [Kodein](../com.github.salomonbrys.kodein/-kodein/index.md) instance or a [KodeinAware](../com.github.salomonbrys.kodein/-kodein-aware.md) class.

