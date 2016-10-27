[com.github.salomonbrys.kodein](../index.md) / [kotlin.Lazy](index.md) / [toInstance](.)

# toInstance

`inline fun <A, T : Any> Lazy<(A) -> T>.toInstance(crossinline arg: () -> A): Lazy<T>`

Transforms a lazy factory property into a lazy instance property by currying the factory argument.

### Parameters

`A` - The type of argument the factory held by this property takes.

`T` - The type of object to retrieve with the factory held by this property.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
The factory to curry.

**Return**
A property that yields an instance of `T`.

`@JvmName("toNullableInstance") inline fun <A, T : Any> Lazy<(A) -> T>.toInstance(crossinline arg: () -> A): Lazy<T?>`

Transforms a lazy nullable factory property into a lazy nullable instance property by currying the factory argument.

### Parameters

`A` - The type of argument the factory held by this property takes.

`T` - The type of object to retrieve with the factory held by this property.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
The factory to curry.

**Return**
A property that yields an instance of `T`, or null if no factory was found.

