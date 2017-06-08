[com.github.salomonbrys.kodein.android](../index.md) / [KodeinAppCompatActivity](.)

# KodeinAppCompatActivity

`abstract class KodeinAppCompatActivity : AppCompatActivity, `[`AppCompatActivityInjector`](../-app-compat-activity-injector/index.md)

A base class that manages an [AppCompatActivityInjector](../-app-compat-activity-injector/index.md) for easy bootstrapping of Kodein.
Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `KodeinAppCompatActivity()`<br>A base class that manages an [AppCompatActivityInjector](../-app-compat-activity-injector/index.md) for easy bootstrapping of Kodein.
Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`. |

### Properties

| Name | Summary |
|---|---|
| [injector](injector.md) | `val injector: `[`KodeinInjector`](../../com.github.salomonbrys.kodein/-kodein-injector/index.md)<br>A Kodein Injected class must be within reach of a Kodein Injector object. |
| [kodeinComponent](kodein-component.md) | `val kodeinComponent: AppCompatActivity`<br>A convenient way for sub-interfaces to reference the component (for internal use only) |
| [kodeinScope](kodein-scope.md) | `val kodeinScope: `[`AndroidScope`](../-android-scope/index.md)`<AppCompatActivity>`<br>The scope that this component belongs to (for internal use only) |

### Functions

| Name | Summary |
|---|---|
| [destroyInjector](destroy-injector.md) | `fun destroyInjector(): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein.bindings/-scope-registry/index.md)`?`<br>Removes the component from its scope. Should be called when the component is being destroyed. |
| [initializeInjector](initialize-injector.md) | `fun initializeInjector(): Unit`<br>Adds bindings specific for this component and injects all the properties created with the injector.
Should be called when the component is being created. |

### Extension Properties

| Name | Summary |
|---|---|
| [appKodein](../android.content.-context/app-kodein.md) | `val Context.appKodein: () -> `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)<br>Function to get the application global Kodein, provided that the Application object is `KodeinAware`. |

### Extension Functions

| Name | Summary |
|---|---|
| [With](../../com.github.salomonbrys.kodein/-with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.With(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [WithF](../../com.github.salomonbrys.kodein/-with-f.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.WithF(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<A>, arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [kodein](../../com.github.salomonbrys.kodein/kodein.md) | `fun `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`>`<br>Gets a lazy [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) object. |
| [with](../../com.github.salomonbrys.kodein/with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [with](../../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](../../com.github.salomonbrys.kodein/-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withContext](../with-context.md) | `fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(ctx: Context): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(f: Fragment): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(f: Fragment): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(d: Dialog): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(v: View): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>`fun `[`KodeinInjected`](../../com.github.salomonbrys.kodein/-kodein-injected.md)`.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](../../com.github.salomonbrys.kodein/-curried-injector-factory/index.md)`<Context>`<br>Allows to inject an instance or a provider from a factory which takes a `Context` as argument. |
