[com.github.salomonbrys.kodein](../index.md) / [InjectedProperty](index.md) / [getValue](.)

# getValue

`open operator fun getValue(thisRef: Any?, property: KProperty<*>): T`

Get the injected value.

### Exceptions

`KodeinInjector.UninjectedException` - If the value is accessed before the [KodeinInjector](../-kodein-injector/index.md) that created this property is [injected](../-kodein-injector/inject.md).