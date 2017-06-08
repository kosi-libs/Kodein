[com.github.salomonbrys.kodein](index.md) / [kodein](.)

# kodein

`fun `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](-kodein/index.md)`>`

Gets a lazy [Kodein](-kodein/index.md) object.

The returned property should not be accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](-kodein-injected-base/inject.md).

**Receiver**
Either a [KodeinInjector](-kodein-injector/index.md) instance or a [KodeinInjected](-kodein-injected.md) class.

**Return**
A lazy property that yields a [Kodein](-kodein/index.md).

