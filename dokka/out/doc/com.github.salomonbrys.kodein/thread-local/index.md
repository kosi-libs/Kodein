[com.github.salomonbrys.kodein](../index.md) / [threadLocal](.)

# threadLocal

`object threadLocal : `[`RefMaker`](../-ref-maker/index.md)

Thread Local Reference Maker.

Use this with `refSingleton` or `refMultiton` to bind a thread local singleton or multiton.

A thread local singleton is guaranteed to be unique inside a thread.

### Functions

| Name | Summary |
|---|---|
| [make](make.md) | `fun <T : Any> make(creator: () -> T): Pair<T, () -> T?>`<br>A Function that creates a reference. |
