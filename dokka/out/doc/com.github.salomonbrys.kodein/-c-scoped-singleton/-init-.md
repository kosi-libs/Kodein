[com.github.salomonbrys.kodein](../index.md) / [CScopedSingleton](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`CScopedSingleton(contextType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, createdType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, _scope: `[`Scope`](../-scope/index.md)`<C>, creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.(C) -> T)`

Concrete scoped singleton factory, effectively a `factory { Scope -> T }`.

### Parameters

`C` - The scope context type.

`T` - The singleton type.