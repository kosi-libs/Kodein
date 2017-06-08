[com.github.salomonbrys.kodein.bindings](../index.md) / [ScopeRegistry](index.md) / [getOrCreate](.)

# getOrCreate

`fun <T : Any> getOrCreate(bind: `[`Bind`](../../com.github.salomonbrys.kodein/-kodein/-bind/index.md)`<T>, creator: () -> T): T`

Either get a singleton object if it exists in this scope, or create it if it does not.

### Parameters

`T` - The type of the singleton object to get / create.

`bind` - The type and tag of the singleton object to get / create.

`creator` - A function that creates the singleton object. Will be called only if the singleton does not already exist in the scope.