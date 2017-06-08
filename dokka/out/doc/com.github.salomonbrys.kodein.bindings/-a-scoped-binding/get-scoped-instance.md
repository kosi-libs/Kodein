[com.github.salomonbrys.kodein.bindings](../index.md) / [AScopedBinding](index.md) / [getScopedInstance](.)

# getScopedInstance

`protected fun getScopedInstance(kodein: `[`NoArgBindingKodein`](../-no-arg-binding-kodein/index.md)`, key: `[`Key`](../../com.github.salomonbrys.kodein/-kodein/-key/index.md)`<A, T>, arg: A): T`

Finds an instance inside a scope, or creates it if needs be.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

`arg` - : The argument to use to get the instance.