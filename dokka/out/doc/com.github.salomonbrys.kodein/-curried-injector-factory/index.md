[com.github.salomonbrys.kodein](../index.md) / [CurriedInjectorFactory](.)

# CurriedInjectorFactory

`class CurriedInjectorFactory<out A>`

Used to inject lazy providers or instances for factory bound types.

### Parameters

`A` - The type of argument that the factory takes.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CurriedInjectorFactory(injector: `[`KodeinInjector`](../-kodein-injector/index.md)`, arg: () -> A, argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`)`<br>Used to inject lazy providers or instances for factory bound types. |

### Properties

| Name | Summary |
|---|---|
| [arg](arg.md) | `val arg: () -> A`<br>A function that provides the argument that will be passed to the factory. |
| [argType](arg-type.md) | `val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The type of argument that the factory takes. |
| [injector](injector.md) | `val injector: `[`KodeinInjector`](../-kodein-injector/index.md)<br>The injector to use for injections. |

### Extension Functions

| Name | Summary |
|---|---|
| [erasedInstance](../erased-instance.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.erasedInstance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument. |
| [erasedInstanceOrNull](../erased-instance-or-null.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.erasedInstanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [erasedProvider](../erased-provider.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.erasedProvider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument. |
| [erasedProviderOrNull](../erased-provider-or-null.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.erasedProviderOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [genericInstance](../generic-instance.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.genericInstance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument. |
| [genericInstanceOrNull](../generic-instance-or-null.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.genericInstanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [genericProvider](../generic-provider.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.genericProvider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument. |
| [genericProviderOrNull](../generic-provider-or-null.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.genericProviderOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [instance](../instance.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument. |
| [instance](../../com.github.salomonbrys.kodein.erased/instance.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.instance(tag: Any? = null): Lazy<T>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument. |
| [instanceOrNull](../instance-or-null.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [instanceOrNull](../../com.github.salomonbrys.kodein.erased/instance-or-null.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.instanceOrNull(tag: Any? = null): Lazy<T?>`<br>Gets a lazy instance of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [provider](../provider.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument. |
| [provider](../../com.github.salomonbrys.kodein.erased/provider.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.provider(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument. |
| [providerOrNull](../provider-or-null.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
| [providerOrNull](../../com.github.salomonbrys.kodein.erased/provider-or-null.md) | `fun <A, T : Any> CurriedInjectorFactory<A>.providerOrNull(tag: Any? = null): Lazy<() -> T>`<br>Gets a lazy curried provider of `T` for the given tag from a factory with an `A` argument, or null if none is found. |
