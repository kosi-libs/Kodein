[com.github.salomonbrys.kodein](../index.md) / [InjectedProperty](.)

# InjectedProperty

`abstract class InjectedProperty<out T> : `[`Serializable`](http://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)`, ReadOnlyProperty<Any?, T>`

Read only property delegate for an injected value.

### Properties

| Name | Summary |
|---|---|
| [_type](_type.md) | `abstract val _type: String`<br>The type of the object to inject, *used for debug print only*. |
| [key](key.md) | `val key: `[`Key`](../-kodein/-key/index.md)<br>The key of the value that will be injected. |
| [value](value.md) | `val value: T`<br>The injected value. |

### Functions

| Name | Summary |
|---|---|
| [_getInjection](_get-injection.md) | `abstract fun _getInjection(container: `[`KodeinContainer`](../-kodein-container/index.md)`): T`<br>Gets the injected value from the container. |
| [getValue](get-value.md) | `open operator fun getValue(thisRef: Any?, property: KProperty<*>): T`<br>Get the injected value. |
| [isInjected](is-injected.md) | `fun isInjected(): Boolean` |
| [toString](to-string.md) | `open fun toString(): String`<br>Stringify the injected value *if it has been injected*. |

### Extension Functions

| Name | Summary |
|---|---|
| [toInstance](../to-instance.md) | `fun <A, T : Any> InjectedProperty<(A) -> T>.toInstance(arg: () -> A): Lazy<T?>`<br>Transforms an injected factory property into an injected instance property by currying the factory with the given argument. |
| [toProvider](../to-provider.md) | `fun <A, T : Any> InjectedProperty<(A) -> T>.toProvider(arg: () -> A): Lazy<() -> T>`<br>Transforms an injected nullable factory property into an injected nullable provider property by currying the factory with the given argument. |

### Inheritors

| Name | Summary |
|---|---|
| [InjectedFactoryProperty](../-injected-factory-property/index.md) | `class InjectedFactoryProperty<in A, out T : Any> : InjectedProperty<(A) -> T>`<br>A read-only property delegate that injects a factory. |
| [InjectedInstanceProperty](../-injected-instance-property/index.md) | `class InjectedInstanceProperty<out T : Any> : InjectedProperty<T>`<br>A read-only property delegate that injects an instance. |
| [InjectedNullableFactoryProperty](../-injected-nullable-factory-property/index.md) | `class InjectedNullableFactoryProperty<in A, out T : Any> : InjectedProperty<(A) -> T>`<br>A read-only property delegate that injects a factory, or null if none is found. |
| [InjectedNullableInstanceProperty](../-injected-nullable-instance-property/index.md) | `class InjectedNullableInstanceProperty<out T : Any> : InjectedProperty<T?>`<br>A read-only property delegate that injects an instance, or null if none is found. |
| [InjectedNullableProviderProperty](../-injected-nullable-provider-property/index.md) | `class InjectedNullableProviderProperty<out T : Any> : InjectedProperty<() -> T>`<br>A read-only property delegate that injects a provider, or null if none is found. |
| [InjectedProviderProperty](../-injected-provider-property/index.md) | `class InjectedProviderProperty<out T : Any> : InjectedProperty<() -> T>`<br>A read-only property delegate that injects a provider. |
