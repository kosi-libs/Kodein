[com.github.salomonbrys.kodein.bindings](../index.md) / [ScopeRegistry](.)

# ScopeRegistry

`class ScopeRegistry`

Represents a scope: used to store and retrieve scoped singleton objects.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ScopeRegistry()`<br>Represents a scope: used to store and retrieve scoped singleton objects. |

### Properties

| Name | Summary |
|---|---|
| [size](size.md) | `val size: Int`<br>The number of singleton objects currently created in this scope. |

### Functions

| Name | Summary |
|---|---|
| [clear](clear.md) | `fun clear(): Unit`<br>Remove all objects from the scope. |
| [contains](contains.md) | `operator fun contains(bind: `[`Bind`](../../com.github.salomonbrys.kodein/-kodein/-bind/index.md)`<*>): Boolean` |
| [get](get.md) | `operator fun <T : Any> get(bind: `[`Bind`](../../com.github.salomonbrys.kodein/-kodein/-bind/index.md)`<T>): T?` |
| [getOrCreate](get-or-create.md) | `fun <T : Any> getOrCreate(bind: `[`Bind`](../../com.github.salomonbrys.kodein/-kodein/-bind/index.md)`<T>, creator: () -> T): T`<br>Either get a singleton object if it exists in this scope, or create it if it does not. |
| [isEmpty](is-empty.md) | `fun isEmpty(): Boolean` |
| [objects](objects.md) | `fun objects(): HashMap<`[`Bind`](../../com.github.salomonbrys.kodein/-kodein/-bind/index.md)`<*>, Any>` |
| [remove](remove.md) | `fun <T : Any> remove(bind: `[`Bind`](../../com.github.salomonbrys.kodein/-kodein/-bind/index.md)`<T>): T?`<br>Remove a singleton object from this scope, if it exist. |
