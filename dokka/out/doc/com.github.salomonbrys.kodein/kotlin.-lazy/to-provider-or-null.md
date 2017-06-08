[com.github.salomonbrys.kodein](../index.md) / [kotlin.Lazy](index.md) / [toProviderOrNull](.)

# toProviderOrNull

`inline fun <A, T : Any> Lazy<(A) -> T>.toProviderOrNull(crossinline arg: () -> A): Lazy<() -> T>`

Transforms a lazy nullable factory property into a lazy nullable provider property by currying the factory argument.

### Parameters

`A` - The type of argument the factory held by this property takes.

`T` - The type of object to retrieve with the factory held by this property.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
The factory to curry.

**Return**
A property that yields a provider of `T`, or null if no factory was found.

