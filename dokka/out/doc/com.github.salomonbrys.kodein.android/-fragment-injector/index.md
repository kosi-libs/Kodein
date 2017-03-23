[com.github.salomonbrys.kodein.android](../index.md) / [FragmentInjector](.)

# FragmentInjector

`interface FragmentInjector : `[`AndroidInjector`](../-android-injector/index.md)`<Fragment, `[`AndroidScope`](../-android-scope/index.md)`<Fragment>>`

An interface for adding injection and bindings to a Fragment.

The following bindings are provided:

* [KodeinInjected](../../com.github.salomonbrys.kodein/-kodein-injected.md) = this@Fragment
* Context = Fragment.getActivity
* Activity = Fragment.getActivity
* Fragment = this@Fragment
* FragmentManager = Fragment.getFragmentManager
* LoaderManager = Fragment.getLoaderManager
* LayoutInflater = Fragment.getActivity.getLayoutInflater, Activity.getLayoutInflater

The underlying [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) object will [Kodein.Builder.extend](../../com.github.salomonbrys.kodein/-kodein/-builder/extend.md) from [appKodein](../android.content.-context/app-kodein.md) and from Fragment.getActivity's [Kodein](../../com.github.salomonbrys.kodein/-kodein/index.md) if it implements [KodeinInjected](../../com.github.salomonbrys.kodein/-kodein-injected.md).

### Properties

| Name | Summary |
|---|---|
| [kodeinScope](kodein-scope.md) | `open val kodeinScope: `[`AndroidScope`](../-android-scope/index.md)`<Fragment>`<br>The scope that this component belongs to (for internal use only) |

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
| [destroyInjector](../-android-injector/destroy-injector.md) | `open fun destroyInjector(): `[`ScopeRegistry`](../../com.github.salomonbrys.kodein/-scope-registry/index.md)`?`<br>Removes the component from its scope. Should be called when the component is being destroyed. |
| [provideOverridingModule](../-android-injector/provide-overriding-module.md) | `open fun provideOverridingModule(): `[`Module`](../../com.github.salomonbrys.kodein/-kodein/-module/index.md)<br>Allows the component to override any bindings before injection. |

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

### Inheritors

| Name | Summary |
|---|---|
| [KodeinFragment](../-kodein-fragment/index.md) | `abstract class KodeinFragment : Fragment, FragmentInjector`<br>A base class that manages a FragmentInjector for easy bootstrapping of Kodein.
Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`. |
