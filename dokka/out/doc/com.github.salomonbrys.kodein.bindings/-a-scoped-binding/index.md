[com.github.salomonbrys.kodein.bindings](../index.md) / [AScopedBinding](.)

# AScopedBinding

`abstract class AScopedBinding<A, out C, T : Any>`

A factory to bind a type and tag into a [Scope](../-scope/index.md) or an [AutoScope](../-auto-scope/index.md).

### Parameters

`A` - The type of argument that is needed to get a `C` context.

`C` - The type of context that will be used to get a [ScopeRegistry](../-scope-registry/index.md).

`T` - The singleton type.

`_creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.

### Functions

| Name | Summary |
|---|---|
| [getContextAndRegistry](get-context-and-registry.md) | `abstract fun getContextAndRegistry(arg: A): Pair<C, `[`ScopeRegistry`](../-scope-registry/index.md)`>`<br>Retrieve the scope context and registry associated with the given argument. |
| [getScopedInstance](get-scoped-instance.md) | `fun getScopedInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<A, T>, arg: A): T`<br>Finds an instance inside a scope, or creates it if needs be. |

### Inheritors

| Name | Summary |
|---|---|
| [AutoScopedSingletonBinding](../-auto-scoped-singleton-binding/index.md) | `class AutoScopedSingletonBinding<C, T : Any> : AScopedBinding<Unit, C, T>, `[`NoArgBinding`](../-no-arg-binding/index.md)`<T>`<br>Concrete auto-scoped singleton provider, effectively a `provider { -> T }`. |
| [ScopedSingletonBinding](../-scoped-singleton-binding/index.md) | `class ScopedSingletonBinding<C, T : Any> : AScopedBinding<C, C, T>, `[`Binding`](../-binding/index.md)`<C, T>`<br>Concrete scoped singleton factory, effectively a `factory { Scope -> T }`. |
