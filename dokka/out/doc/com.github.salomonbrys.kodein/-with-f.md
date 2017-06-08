[com.github.salomonbrys.kodein](index.md) / [WithF](.)

# WithF

`fun <A> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.WithF(argType: `[`TypeToken`](-type-token/index.md)`<A>, arg: () -> A): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>`

Allows to get a provider or an instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

**Receiver**
Either a [Kodein](-kodein/index.md) instance or a [KodeinAware](-kodein-aware.md) class.

`fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.WithF(argType: `[`TypeToken`](-type-token/index.md)`<A>, arg: () -> A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`

Allows to inject a provider or an instance from a curried factory with an `A` argument.

### Parameters

`A` - The type of argument the factory takes.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
An object from which you can inject an instance or a provider.

`fun <A> `[`LazyKodeinAwareBase`](-lazy-kodein-aware-base/index.md)`.WithF(argType: `[`TypeToken`](-type-token/index.md)`<A>, arg: () -> A): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`

Allows to get a lazy provider or instance from a curried factory with an `A` argument.

A generics will be kept.

### Parameters

`A` - The type of argument the factory takes.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
Either a [LazyKodein](-lazy-kodein/index.md) instance or a [LazyKodeinAware](-lazy-kodein-aware.md) class.

**Return**
An object from which you can get an instance or a provider.

