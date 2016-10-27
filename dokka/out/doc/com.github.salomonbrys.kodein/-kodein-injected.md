[com.github.salomonbrys.kodein](index.md) / [KodeinInjected](.)

# KodeinInjected

`interface KodeinInjected : `[`KodeinInjectedBase`](-kodein-injected-base/index.md)

Any class that extends this interface can be injected "seamlessly".

### Inherited Properties

| Name | Summary |
|---|---|
| [injector](-kodein-injected-base/injector.md) | `abstract val injector: `[`KodeinInjector`](-kodein-injector/index.md)<br>A Kodein Injected class must be within reach of a Kodein Injector object. |

### Inherited Functions

| Name | Summary |
|---|---|
| [inject](-kodein-injected-base/inject.md) | `open fun inject(kodein: `[`Kodein`](-kodein/index.md)`): Unit`<br>Will inject all properties that were created with the [injector](-kodein-injected-base/injector.md) with the values found in the provided Kodein object. |
| [onInjected](-kodein-injected-base/on-injected.md) | `open fun onInjected(cb: (`[`Kodein`](-kodein/index.md)`) -> Unit): Unit`<br>Registers a callback to be called once the [injector](-kodein-injected-base/injector.md) gets injected with a [Kodein](-kodein/index.md) object. |

### Extension Functions

| Name | Summary |
|---|---|
| [erasedFactory](erased-factory.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.erasedFactory(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [erasedFactoryOrNull](erased-factory-or-null.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.erasedFactoryOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [erasedInstance](erased-instance.md) | `fun <T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.erasedInstance(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [erasedInstanceOrNull](erased-instance-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.erasedInstanceOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [erasedProvider](erased-provider.md) | `fun <T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.erasedProvider(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [erasedProviderOrNull](erased-provider-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.erasedProviderOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [genericFactory](generic-factory.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.genericFactory(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type. |
| [genericFactoryOrNull](generic-factory-or-null.md) | `fun <A, T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.genericFactoryOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<(A) -> T>`<br>Gets a lazy factory for the given type, tag and argument type, or null if none is found |
| [genericInstance](generic-instance.md) | `fun <T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.genericInstance(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<T>`<br>Gets a lazy instance for the given type and tag. |
| [genericInstanceOrNull](generic-instance-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.genericInstanceOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<T?>`<br>Gets a lazy instance for the given type and tag. |
| [genericProvider](generic-provider.md) | `fun <T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.genericProvider(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag. |
| [genericProviderOrNull](generic-provider-or-null.md) | `fun <T : Any> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.genericProviderOrNull(tag: Any? = null): `[`InjectedProperty`](-injected-property/index.md)`<() -> T>`<br>Gets a lazy provider for the given type and tag, or null if none is found. |
| [kodein](kodein.md) | `fun `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.kodein(): Lazy<`[`Kodein`](-kodein/index.md)`>`<br>Gets a lazy [Kodein](-kodein/index.md) object. |
| [with](with.md) | `fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [with](../com.github.salomonbrys.kodein.erased/with.md) | `fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.with(arg: () -> A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.with(arg: A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withClass](with-class.md) | `fun <T : `[`LazyKodeinAware`](-lazy-kodein-aware.md)`> T.withClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`<br>Allows to lazily retrieve a provider or an instance from a curried factory with a `Class` argument. |
| [withContext](../com.github.salomonbrys.kodein.android/with-context.md) | `fun KodeinInjected.withContext(ctx: Context): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<Context>`<br>`fun KodeinInjected.withContext(f: Fragment): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<Context>`<br>`fun KodeinInjected.withContext(f: Fragment): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<Context>`<br>`fun KodeinInjected.withContext(d: Dialog): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<Context>`<br>`fun KodeinInjected.withContext(v: View): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<Context>`<br>`fun KodeinInjected.withContext(tsa: AbstractThreadedSyncAdapter): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<Context>`<br>`fun KodeinInjected.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<Context>`<br>`fun KodeinInjected.withContext(l: Loader<*>): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<Context>`<br>Allows to inject an instance or a provider from a factory which takes a `Context` as argument. |
| [withErased](with-erased.md) | `fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.withErased(arg: () -> A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.withErased(arg: A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withGeneric](with-generic.md) | `fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.withGeneric(arg: () -> A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`<br>`fun <A> `[`KodeinInjectedBase`](-kodein-injected-base/index.md)`.withGeneric(arg: A): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<A>`<br>Allows to inject a provider or an instance from a curried factory with an `A` argument. |
| [withKClass](with-k-class.md) | `fun <T : `[`LazyKodeinAware`](-lazy-kodein-aware.md)`> T.withKClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<KClass<*>>`<br>Allows to lazily retrieve a provider or an instance from a curried factory with a `KClass` argument. |
