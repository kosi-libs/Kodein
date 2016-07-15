[com.github.salomonbrys.kodein](../index.md) / [CurriedInjectorFactory](index.md) / [instanceOrNull](.)

# instanceOrNull

`inline fun <reified T : Any> instanceOrNull(tag: Any? = null): Lazy<T?>`

Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](../-kodein-injected-base/inject.md).

### Parameters

`T` - The type of object to retrieve.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../-kodein-injected-base/inject.md).

**Return**
A lazy property that yields a `T` or null if no factory was found.

