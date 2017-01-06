[com.github.salomonbrys.kodein.android](../index.md) / [KodeinIntentService](.)

# KodeinIntentService

`abstract class KodeinIntentService : IntentService, `[`IntentServiceInjector`](../-intent-service-injector/index.md)

A base class that manages an [IntentServiceInjector](../-intent-service-injector/index.md) for easy bootstrapping of Kodein.
Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.

### Parameters

`name` - The name of the service

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `KodeinIntentService()`<br>Default constructor that sets the service name to "KodeinIntentService".`KodeinIntentService(name: String)`<br>A base class that manages an [IntentServiceInjector](../-intent-service-injector/index.md) for easy bootstrapping of Kodein.
Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`. |

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `val injector: `[`KodeinInjector`](../../com.github.salomonbrys.kodein/-kodein-injector/index.md)<br>A Kodein Injected class must be within reach of a Kodein Injector object. |
| [kodeinComponent](kodein-component.md) | `val kodeinComponent: IntentService`<br>A convenient way for sub-interfaces to reference the component (for internal use only) |
| [kodeinScope](kodein-scope.md) | `val kodeinScope: `[`AndroidScope`](../-android-scope/index.md)`<IntentService>`<br>The scope that this component belongs to (for internal use only) |

### Functions

| Name | Summary |
|---|---|
| [destroyInjector](destroy-injector.md) | `fun destroyInjector(): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)`?`<br>Removes the component from its scope. Should be called when the component is being destroyed. |
| [initializeInjector](initialize-injector.md) | `fun initializeInjector(): Unit`<br>Adds bindings specific for this component and injects all the properties created with the injector.
Should be called when the component is being created. |

### Extension Properties

| Name | Summary |
|---|---|
| [appKodein](../android.content.-context/app-kodein.md) | `val Context.appKodein: () -> `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)<br>Function to get the application global Kodein, provided that the Application object is `KodeinAware`. |

### Extension Functions

| Name | Summary |
|---|---|
| [erasedFactory](../../com.github.salomonbrys.kodein/erased-factory.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.erasedFactory(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [erasedFactoryOrNull](../../com.github.salomonbrys.kodein/erased-factory-or-null.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.erasedFactoryOrNull(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [erasedInstance](../../com.github.salomonbrys.kodein/erased-instance.md) | `fun <T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.erasedInstance(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [erasedInstanceOrNull](../../com.github.salomonbrys.kodein/erased-instance-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.erasedInstanceOrNull(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [erasedProvider](../../com.github.salomonbrys.kodein/erased-provider.md) | `fun <T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.erasedProvider(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [erasedProviderOrNull](../../com.github.salomonbrys.kodein/erased-provider-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.erasedProviderOrNull(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [genericFactory](../../com.github.salomonbrys.kodein/generic-factory.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.genericFactory(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [genericFactoryOrNull](../../com.github.salomonbrys.kodein/generic-factory-or-null.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.genericFactoryOrNull(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [genericInstance](../../com.github.salomonbrys.kodein/generic-instance.md) | `fun <T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.genericInstance(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [genericInstanceOrNull](../../com.github.salomonbrys.kodein/generic-instance-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.genericInstanceOrNull(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [genericProvider](../../com.github.salomonbrys.kodein/generic-provider.md) | `fun <T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.genericProvider(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [genericProviderOrNull](../../com.github.salomonbrys.kodein/generic-provider-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.genericProviderOrNull(tag: Any? = null): `[`InjectedProperty`](../../com.github.salomonbrys.kodein/-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [kodein](../../com.github.salomonbrys.kodein/kodein.md) | `fun `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`>`<br>Gets a lazy [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) object. |
| [with](../../com.github.salomonbrys.kodein/with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withContext](../with-context.md) | `fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(ctx: Context): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(f: Fragment): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(f: Fragment): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(d: Dialog): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(v: View): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>Allows to inject an instance or a provider from a factory which takes a `Context` as argument. |
| [withErased](../../com.github.salomonbrys.kodein/with-erased.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.withErased(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.withErased(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withGeneric](../../com.github.salomonbrys.kodein/with-generic.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.withGeneric(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.withGeneric(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
