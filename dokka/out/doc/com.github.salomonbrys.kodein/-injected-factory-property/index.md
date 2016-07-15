[com.github.salomonbrys.kodein](../index.md) / [InjectedFactoryProperty](.)

# InjectedFactoryProperty

`class InjectedFactoryProperty<in A, out T : Any> : `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`

A read-only property delegate that injects a factory.

### Parameters

`key` - The key of the factory that will be injected.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `InjectedFactoryProperty(key: `[`Key`](../-kodein/-key/index.md)`)`<br>A read-only property delegate that injects a factory. |

### Properties

| Name | Summary |
|---|---|
| [_type](_type.md) | `val _type: String`<br>The type of the object to inject, *used for debug print only*. |

### Inherited Properties

| Name | Summary |
|---|---|
| [key](../-injected-property/key.md) | `val key: `[`Key`](../-kodein/-key/index.md)<br>The key of the value that will be injected. |
| [value](../-injected-property/value.md) | `val value: T`<br>The injected value. |

### Functions

| Name | Summary |
|---|---|
| [_getInjection](_get-injection.md) | `fun _getInjection(container: `[`KodeinContainer`](../-kodein-container/index.md)`): (A) -> T`<br>Gets the injected value from the container. |

### Inherited Functions

| Name | Summary |
|---|---|
| [getValue](../-injected-property/get-value.md) | `open operator fun getValue(thisRef: Any?, property: KProperty<*>): T`<br>Get the injected value. |
| [isInjected](../-injected-property/is-injected.md) | `fun isInjected(): Boolean` |
| [toString](../-injected-property/to-string.md) | `open fun toString(): String`<br>Stringify the injected value *if it has been injected*. |
