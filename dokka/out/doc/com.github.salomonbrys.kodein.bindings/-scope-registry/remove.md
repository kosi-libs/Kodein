[com.github.salomonbrys.kodein.bindings](../index.md) / [ScopeRegistry](index.md) / [remove](.)

# remove

`fun <T : Any> remove(bind: `[`Bind`](../../com.github.salomonbrys.kodein/-kodein/-bind/index.md)`<T>): T?`

Remove a singleton object from this scope, if it exist.

### Parameters

`bind` - The type and tag of the singleton object to remove.

**Return**
The removed object, if it was found in the scope.

