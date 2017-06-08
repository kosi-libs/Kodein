[com.github.salomonbrys.kodein](../index.md) / [softReference](.)

# softReference

`object softReference : `[`RefMaker`](../-ref-maker/index.md)

Soft Reference Maker.

Use this with `refSingleton` or `refMultiton` to bind a soft singleton or multiton.

A soft singleton is guaranteed to be unique inside the JVM but not during the application lifetime.
It **may** be GC'd if there are no strong references to it and therefore may be re-created later.

### Functions

| Name | Summary |
|---|---|
| [make](make.md) | `fun <T : Any> make(creator: () -> T): Pair<T, () -> T?>`<br>A Function that creates a reference. |
