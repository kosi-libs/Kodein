[com.github.salomonbrys.kodein.android](../index.md) / [AppCompatActivityInjector](.)

# AppCompatActivityInjector

`interface AppCompatActivityInjector : `[`AndroidInjector`](../-android-injector/index.md)`<AppCompatActivity, `[`AndroidScope`](../-android-scope/index.md)`<AppCompatActivity>>`

An interface for adding injection and bindings to an AppCompatActivity.

The following bindings are provided:

* [KodeinInjected](../../com.github.salomonbrys.kodein/-kodein-injected.md) = this@AppCompatActivity
* Context = this@AppCompatActivity
* Activity = this@AppCompatActivity
* FragmentActivity = this@AppCompatActivity
* AppCompatActivity = this@AppCompatActivity
* FragmentManager = AppCompatActivity.getFragmentManager
* LoaderManager = AppCompatActivity.getLoaderManager
* LayoutInflater = AppCompatActivity.getLayoutInflater
* android.support.v4.app.FragmentManager = AppCompatActivity.getSupportFragmentManager
* android.support.v4.app.LoaderManager = AppCompatActivity.getSupportLoaderManager

The underlying [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) object will [Kodein.Builder.extend](../../com.github.salomonbrys.kodein/-kodein/-builder/extend.md) from [appKodein](../android.content.-context/app-kodein.md).

### Properties

| Name | Summary |
|---|---|
| [kodeinScope](kodein-scope.md) | `open val kodeinScope: `[`AndroidScope`](../-android-scope/index.md)`<AppCompatActivity>`<br>The scope that this component belongs to (for internal use only) |

### Inherited Properties

| Name | Summary |
|---|---|
| [kodeinComponent](../-android-injector/kodein-component.md) | `open val kodeinComponent: T`<br>A convenient way for sub-interfaces to reference the component (for internal use only) |

### Functions

| Name | Summary |
|---|---|
| [initializeInjector](initialize-injector.md) | `open fun initializeInjector(): Unit`<br>Adds bindings specific for this component and injects all the properties created with the injector.
Should be called when the component is being created. |

### Inherited Functions

| Name | Summary |
|---|---|
| [destroyInjector](../-android-injector/destroy-injector.md) | `open fun destroyInjector(): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein.bindings/-scope-registry/index.md)`?`<br>Removes the component from its scope. Should be called when the component is being destroyed. |
| [provideOverridingModule](../-android-injector/provide-overriding-module.md) | `open fun provideOverridingModule(): `[`Module`](../../com.github.salomonbrys.kodein/-kodein/-module/index.md)<br>Allows the component to override any bindings before injection. |

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
| [KodeinAppCompatActivity](../-kodein-app-compat-activity/index.md) | `abstract class KodeinAppCompatActivity : AppCompatActivity, AppCompatActivityInjector`<br>A base class that manages an AppCompatActivityInjector for easy bootstrapping of Kodein.
Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`. |
