[com.github.salomonbrys.kodein](../index.md) / [KodeinInjector](index.md) / [kodein](.)

# kodein

`fun kodein(): Lazy<`[`Kodein`](../-kodein/index.md)`>`

Creates a property delegate that will hold a Kodein instance.

### Exceptions

`UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../-kodein-injected-base/inject.md).

**Return**
A property delegate that will lazily provide a Kodein instance.

