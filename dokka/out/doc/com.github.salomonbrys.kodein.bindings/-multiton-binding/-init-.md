[com.github.salomonbrys.kodein.bindings](../index.md) / [MultitonBinding](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`MultitonBinding(argType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<in A>, createdType: `[`TypeToken`](../../com.github.salomonbrys.kodein/-type-token/index.md)`<out T>, creator: `[`BindingKodein`](../-binding-kodein/index.md)`.(A) -> T)`

Concrete multiton: will create one and only one instance for each argument.
Will create the instance on first time a given argument is used and will subsequently always return the same instance for the same argument.

### Parameters

`T` - The created type.