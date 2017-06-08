[com.github.salomonbrys.kodein.android](../index.md) / [KodeinBroadcastReceiver](.)

# KodeinBroadcastReceiver

`abstract class KodeinBroadcastReceiver : BroadcastReceiver, `[`BroadcastReceiverInjector`](../-broadcast-receiver-injector/index.md)

A base class that manages a [BroadcastReceiverInjector](../-broadcast-receiver-injector/index.md) for easy bootstrapping of Kodein.
Injections will be available as soon as [onBroadcastReceived](on-broadcast-received.md) is called and will be destroyed immediately after it returns.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `KodeinBroadcastReceiver()`<br>A base class that manages a [BroadcastReceiverInjector](../-broadcast-receiver-injector/index.md) for easy bootstrapping of Kodein.
Injections will be available as soon as [onBroadcastReceived](on-broadcast-received.md) is called and will be destroyed immediately after it returns. |

### Properties

| Name | Summary |
|---|---|
| [context](context.md) | `var context: Context?`<br>Property that needs to be set in BroadcastReceiver.onReceive. |
| [injector](injector.md) | `val injector: `[`KodeinInjector`](../../com.github.salomonbrys.kodein/-kodein-injector/index.md)<br>A Kodein Injected class must be within reach of a Kodein Injector object. |
| [kodeinComponent](kodein-component.md) | `val kodeinComponent: BroadcastReceiver`<br>A convenient way for sub-interfaces to reference the component (for internal use only) |
| [kodeinScope](kodein-scope.md) | `val kodeinScope: `[`AndroidScope`](../-android-scope/index.md)`<BroadcastReceiver>`<br>The scope that this component belongs to (for internal use only) |

### Functions

| Name | Summary |
|---|---|
| [destroyInjector](destroy-injector.md) | `fun destroyInjector(): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein.bindings/-scope-registry/index.md)`?`<br>Removes the component from its scope. Should be called when the component is being destroyed. |
| [initializeInjector](initialize-injector.md) | `fun initializeInjector(): Unit`<br>Adds bindings specific for this component and injects all the properties created with the injector.
Should be called when the component is being created. |
| [onBroadcastReceived](on-broadcast-received.md) | `abstract fun onBroadcastReceived(context: Context, intent: Intent): Unit`<br>Override this instead of onReceive to handle received broadcast intents. |

### Extension Functions

| Name | Summary |
|---|---|
| [With](../../com.github.salomonbrys.kodein/-with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.With(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [WithF](../../com.github.salomonbrys.kodein/-with-f.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.WithF(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [kodein](../../com.github.salomonbrys.kodein/kodein.md) | `fun `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`>`<br>Gets a lazy [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) object. |
| [with](../../com.github.salomonbrys.kodein/with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withContext](../with-context.md) | `fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(ctx: Context): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(f: Fragment): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(f: Fragment): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(d: Dialog): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(v: View): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>Allows to inject an instance or a provider from a factory which takes a `Context` as argument. |
