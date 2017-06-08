[com.github.salomonbrys.kodein.bindings](.)

## Package com.github.salomonbrys.kodein.bindings

### Types

| Name | Summary |
|---|---|
| [AScopedBinding](-a-scoped-binding/index.md) | `abstract class AScopedBinding<A, out C, T : Any>`<br>A factory to bind a type and tag into a [Scope](-scope/index.md) or an [AutoScope](-auto-scope/index.md). |
| [ASingleton](-a-singleton/index.md) | `abstract class ASingleton<T : Any> : `[`NoArgBinding`](-no-arg-binding/index.md)`<T>`<br>SingletonBinding base: will create an instance on first request and will subsequently always return the same instance. |
| [AutoScope](-auto-scope/index.md) | `interface AutoScope<C> : `[`Scope`](-scope/index.md)`<C>`<br>An object that can, in addition to being a regular scope, can also get a context from a static environment. |
| [AutoScopedSingletonBinding](-auto-scoped-singleton-binding/index.md) | `class AutoScopedSingletonBinding<C, T : Any> : `[`AScopedBinding`](-a-scoped-binding/index.md)`<Unit, C, T>, `[`NoArgBinding`](-no-arg-binding/index.md)`<T>`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |
| [Binding](-binding/index.md) | `interface Binding<in A, T : Any>`<br>Base class that knows how to get an instance. |
| [BindingKodein](-binding-kodein/index.md) | `interface BindingKodein : `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)<br>Kodein interface to be passed to factory scope methods. |
| [EagerSingletonBinding](-eager-singleton-binding/index.md) | `class EagerSingletonBinding<T : Any> : `[`ASingleton`](-a-singleton/index.md)`<T>`<br>Concrete eager singleton: will create an instance as soon as kodein is ready (all bindings are set) and will always return this instance. |
| [FactoryBinding](-factory-binding/index.md) | `class FactoryBinding<A, T : Any> : `[`Binding`](-binding/index.md)`<A, T>`<br>Concrete factory: each time an instance is needed, the function [creator](-factory-binding/creator.md) function will be called. |
| [InstanceBinding](-instance-binding/index.md) | `class InstanceBinding<T : Any> : `[`NoArgBinding`](-no-arg-binding/index.md)`<T>`<br>Concrete instance provider: will always return the given instance. |
| [MultitonBinding](-multiton-binding/index.md) | `class MultitonBinding<A, T : Any> : `[`Binding`](-binding/index.md)`<A, T>`<br>Concrete multiton: will create one and only one instance for each argument.
Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument. |
| [NoArgBinding](-no-arg-binding/index.md) | `interface NoArgBinding<T : Any> : `[`Binding`](-binding/index.md)`<Unit, T>`<br>[Binding](-binding/index.md) specialization that has no argument. |
| [NoArgBindingKodein](-no-arg-binding-kodein/index.md) | `interface NoArgBindingKodein : `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)<br>Kodein interface to be passed to provider or instance scope methods. |
| [ProviderBinding](-provider-binding/index.md) | `class ProviderBinding<T : Any> : `[`NoArgBinding`](-no-arg-binding/index.md)`<T>`<br>Concrete provider: each time an instance is needed, the function [creator](-provider-binding/creator.md) function will be called. |
| [Scope](-scope/index.md) | `interface Scope<in C>`<br>An object capable of providing a [ScopeRegistry](-scope-registry/index.md) for a given `C` context. |
| [ScopeRegistry](-scope-registry/index.md) | `class ScopeRegistry`<br>Represents a scope: used to store and retrieve scoped singleton objects. |
| [ScopedSingletonBinding](-scoped-singleton-binding/index.md) | `class ScopedSingletonBinding<C, T : Any> : `[`AScopedBinding`](-a-scoped-binding/index.md)`<C, C, T>, `[`Binding`](-binding/index.md)`<C, T>`<br>Concrete scoped singleton factory, effectively a `factory { Scope -> T }`. |
| [SingletonBinding](-singleton-binding/index.md) | `class SingletonBinding<T : Any> : `[`ASingleton`](-a-singleton/index.md)`<T>`<br>Concrete singleton: will create an instance on first request and will subsequently always return the same instance. |
