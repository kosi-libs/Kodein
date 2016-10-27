[com.github.salomonbrys.kodein](../index.md) / [KodeinInjectedBase](.)

# KodeinInjectedBase

`interface KodeinInjectedBase`

Base [KodeinInjected](../-kodein-injected.md) interface.

It is separate from [KodeinInjected](../-kodein-injected.md) because [KodeinInjector](../-kodein-injector/index.md) implements itself KodeinInjectedBase but not [KodeinInjected](../-kodein-injected.md).
This is because there are some extension functions to [KodeinInjected](../-kodein-injected.md) that would not make sense applied to the [KodeinInjector](../-kodein-injector/index.md) object.
For example, [KodeinInjected.withClass](../with-class.md), if applied to [KodeinInjector](../-kodein-injector/index.md), would create a very un-expected result.

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `abstract val injector: `[`KodeinInjector`](../-kodein-injector/index.md)<br>A Kodein Injected class must be within reach of a Kodein Injector object. |

### Functions

| Name | Summary |
|---|---|
| [inject](inject.md) | `open fun inject(kodein: `[`Kodein`](../-kodein/index.md)`): Unit`<br>Will inject all properties that were created with the [injector](injector.md) with the values found in the provided Kodein object. |
| [onInjected](on-injected.md) | `open fun onInjected(cb: (`[`Kodein`](../-kodein/index.md)`) -> Unit): Unit`<br>Registers a callback to be called once the [injector](injector.md) gets injected with a [Kodein](../-kodein/index.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [erasedFactory](../erased-factory.md) | `fun <A, T : Any> KodeinInjectedBase.erasedFactory(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [erasedFactoryOrNull](../erased-factory-or-null.md) | `fun <A, T : Any> KodeinInjectedBase.erasedFactoryOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [erasedInstance](../erased-instance.md) | `fun <T : Any> KodeinInjectedBase.erasedInstance(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [erasedInstanceOrNull](../erased-instance-or-null.md) | `fun <T : Any> KodeinInjectedBase.erasedInstanceOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [erasedProvider](../erased-provider.md) | `fun <T : Any> KodeinInjectedBase.erasedProvider(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [erasedProviderOrNull](../erased-provider-or-null.md) | `fun <T : Any> KodeinInjectedBase.erasedProviderOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [factory](../factory.md) | `fun <A, T : Any> KodeinInjectedBase.factory(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [factory](../../com.github.salomonbrys.kodein.erased/factory.md) | `fun <A, T : Any> KodeinInjectedBase.factory(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [factoryOrNull](../factory-or-null.md) | `fun <A, T : Any> KodeinInjectedBase.factoryOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [factoryOrNull](../../com.github.salomonbrys.kodein.erased/factory-or-null.md) | `fun <A, T : Any> KodeinInjectedBase.factoryOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [genericFactory](../generic-factory.md) | `fun <A, T : Any> KodeinInjectedBase.genericFactory(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [genericFactoryOrNull](../generic-factory-or-null.md) | `fun <A, T : Any> KodeinInjectedBase.genericFactoryOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [genericInstance](../generic-instance.md) | `fun <T : Any> KodeinInjectedBase.genericInstance(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [genericInstanceOrNull](../generic-instance-or-null.md) | `fun <T : Any> KodeinInjectedBase.genericInstanceOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [genericProvider](../generic-provider.md) | `fun <T : Any> KodeinInjectedBase.genericProvider(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [genericProviderOrNull](../generic-provider-or-null.md) | `fun <T : Any> KodeinInjectedBase.genericProviderOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [instance](../instance.md) | `fun <T : Any> KodeinInjectedBase.instance(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [instance](../../com.github.salomonbrys.kodein.erased/instance.md) | `fun <T : Any> KodeinInjectedBase.instance(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [instanceOrNull](../instance-or-null.md) | `fun <T : Any> KodeinInjectedBase.instanceOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [instanceOrNull](../../com.github.salomonbrys.kodein.erased/instance-or-null.md) | `fun <T : Any> KodeinInjectedBase.instanceOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [kodein](../kodein.md) | `fun KodeinInjectedBase.kodein(): Lazy<`[`Kodein`](../-kodein/index.md)`>`<br>Gets a lazy [Kodein](../-kodein/index.md) object. |
| [provider](../provider.md) | `fun <T : Any> KodeinInjectedBase.provider(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [provider](../../com.github.salomonbrys.kodein.erased/provider.md) | `fun <T : Any> KodeinInjectedBase.provider(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [providerOrNull](../provider-or-null.md) | `fun <T : Any> KodeinInjectedBase.providerOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [providerOrNull](../../com.github.salomonbrys.kodein.erased/provider-or-null.md) | `fun <T : Any> KodeinInjectedBase.providerOrNull(tag: Any? = null): `[`InjectedProperty`](../-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [with](../with.md) | `fun <A> KodeinInjectedBase.with(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> KodeinInjectedBase.with(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> KodeinInjectedBase.with(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> KodeinInjectedBase.with(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withErased](../with-erased.md) | `fun <A> KodeinInjectedBase.withErased(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> KodeinInjectedBase.withErased(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withGeneric](../with-generic.md) | `fun <A> KodeinInjectedBase.withGeneric(arg: () -> A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>`fun <A> KodeinInjectedBase.withGeneric(arg: A): `[`CurriedInjectorFactory`](../-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |

### Inheritors

| Name | Summary |
|---|---|
| [KodeinInjected](../-kodein-injected.md) | `interface KodeinInjected : KodeinInjectedBase`<br>Any class that extends this interface can be injected "seamlessly". |
| [KodeinInjector](../-kodein-injector/index.md) | `class KodeinInjector : KodeinInjectedBase`<br>An injector is an object which creates injected property delegates **before** having access to a Kodein instance. |
