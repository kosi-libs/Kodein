[com.github.salomonbrys.kodein.android](../index.md) / [AndroidInjector](.)

# AndroidInjector

`interface AndroidInjector<T, out S : `[`AndroidScope`](../-android-scope/index.md)`<T>> : `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)

An interface for adding a [KodeinInjector](../../com.github.salomonbrys.kodein/-kodein-injector/index.md) to Android component classes (Activity, Fragment, Service, etc...)

### Properties

| Name | Summary |
|---|---|
| [kodeinComponent](kodein-component.md) | `open val kodeinComponent: T`<br>A convenient way for sub-interfaces to reference the component (for internal use only) |
| [kodeinScope](kodein-scope.md) | `abstract val kodeinScope: S`<br>The scope that this component belongs to (for internal use only) |

### Functions

| Name | Summary |
|---|---|
| [destroyInjector](destroy-injector.md) | `open fun destroyInjector(): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein.bindings/-scope-registry/index.md)`?`<br>Removes the component from its scope. Should be called when the component is being destroyed. |
| [initializeInjector](initialize-injector.md) | `abstract fun initializeInjector(): Unit`<br>Adds bindings specific for this component and injects all the properties created with the injector.
Should be called when the component is being created. |
| [provideOverridingModule](provide-overriding-module.md) | `open fun provideOverridingModule(): `[`Module`](../../com.github.salomonbrys.kodein/-kodein/-module/index.md)<br>Allows the component to override any bindings before injection. |

### Extension Functions

| Name | Summary |
|---|---|
| [With](../../com.github.salomonbrys.kodein/-with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.With(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [WithF](../../com.github.salomonbrys.kodein/-with-f.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.WithF(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [kodein](../../com.github.salomonbrys.kodein/kodein.md) | `fun `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`>`<br>Gets a lazy [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) object. |
| [with](../../com.github.salomonbrys.kodein/with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withContext](../with-context.md) | `fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(ctx: Context): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(f: Fragment): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(f: Fragment): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(d: Dialog): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(v: View): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>Allows to inject an instance or a provider from a factory which takes a `Context` as argument. |

### Inheritors

| Name | Summary |
|---|---|
| [ActivityInjector](../-activity-injector/index.md) | `interface ActivityInjector : AndroidInjector<Activity, `[`AndroidScope`](../-android-scope/index.md)`<Activity>>`<br>An interface for adding injection and bindings to an Activity. |
| [AppCompatActivityInjector](../-app-compat-activity-injector/index.md) | `interface AppCompatActivityInjector : AndroidInjector<AppCompatActivity, `[`AndroidScope`](../-android-scope/index.md)`<AppCompatActivity>>`<br>An interface for adding injection and bindings to an AppCompatActivity. |
| [BroadcastReceiverInjector](../-broadcast-receiver-injector/index.md) | `interface BroadcastReceiverInjector : AndroidInjector<BroadcastReceiver, `[`AndroidScope`](../-android-scope/index.md)`<BroadcastReceiver>>`<br>An interface for adding injection and bindings to a BroadcastReceiver. |
| [FragmentActivityInjector](../-fragment-activity-injector/index.md) | `interface FragmentActivityInjector : AndroidInjector<FragmentActivity, `[`AndroidScope`](../-android-scope/index.md)`<FragmentActivity>>`<br>An interface for adding injection and bindings to a FragmentActivity. |
| [FragmentInjector](../-fragment-injector/index.md) | `interface FragmentInjector : AndroidInjector<Fragment, `[`AndroidScope`](../-android-scope/index.md)`<Fragment>>`<br>An interface for adding injection and bindings to a Fragment. |
| [IntentServiceInjector](../-intent-service-injector/index.md) | `interface IntentServiceInjector : AndroidInjector<IntentService, `[`AndroidScope`](../-android-scope/index.md)`<IntentService>>`<br>An interface for adding injection and bindings to an IntentService. |
| [ServiceInjector](../-service-injector/index.md) | `interface ServiceInjector : AndroidInjector<Service, `[`AndroidScope`](../-android-scope/index.md)`<Service>>`<br>An interface for adding injection and bindings to a Service. |
| [SupportFragmentInjector](../-support-fragment-injector/index.md) | `interface SupportFragmentInjector : AndroidInjector<Fragment, `[`AndroidScope`](../-android-scope/index.md)`<Fragment>>`<br>An interface for adding injection and bindings to a android.support.v4.app.Fragment. |
