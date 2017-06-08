[com.github.salomonbrys.kodein](../index.md) / [weakReference](.)

# weakReference

`object weakReference : `[`RefMaker`](../-ref-maker/index.md)

Weak Reference Maker.

Use this with `refSingleton` or `refMultiton` to bind a weak singleton or multiton.

A weak singleton is guaranteed to be unique inside the JVM but not during the application lifetime.
It **will** be GC'd if there are no strong references to it and therefore may be re-created later.

### Functions

| Name | Summary |
|---|---|
| [make](make.md) | `fun <T : Any> make(creator: () -> T): Pair<T, () -> T?>`<br>A Function that creates a reference. |
