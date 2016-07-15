[com.github.salomonbrys.kodein](index.md) / [toInstance](.)

# toInstance

`fun <A, T : Any> `[`InjectedProperty`](-injected-property/index.md)`<(A) -> T>.toInstance(arg: () -> A): Lazy<T>`
`@JvmName("toNullableInstance") fun <A, T : Any> `[`InjectedProperty`](-injected-property/index.md)`<(A) -> T>.toInstance(arg: () -> A): Lazy<T?>`

Transforms an injected factory property into an injected instance property by currying the factory with the given argument.

### Parameters

`A` - The type of argument the factory takes.

`T` - The type of object to retrieve.

`arg` - A function that provides the argument that will be passed to the factory.

**Receiver**
The injected factory to curry.

**Return**
An injected instance property that, when injected, will call the receiver factory with the given argument.

