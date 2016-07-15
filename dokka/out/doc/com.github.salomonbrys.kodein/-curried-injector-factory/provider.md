[com.github.salomonbrys.kodein](../index.md) / [CurriedInjectorFactory](index.md) / [provider](.)

# provider

`inline fun <reified T : Any> provider(tag: Any? = null): Lazy<() -> T>`

Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](../-kodein-injected-base/inject.md).

### Parameters

`T` - The type of object to retrieve with the provider.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../-kodein-injected-base/inject.md).

`Kodein.DependencyLoopException` - When calling the provider, if the value construction triggered a dependency loop.

**Return**
A lazy property that yields a provider of `T`.

