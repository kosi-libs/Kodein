[com.github.salomonbrys.kodein](../index.md) / [LazyKodein](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`LazyKodein(f: () -> `[`Kodein`](../-kodein/index.md)`)`

Constructor with a function (will `lazify` the function).

### Parameters

`f` - A function that returns a Kodein. Guaranteed to be called only once.

`LazyKodein(k: Lazy<`[`Kodein`](../-kodein/index.md)`>)`

An object that wraps a Kodein `Lazy` object and acts both as a `Lazy` property delegate &amp; a function.

### Parameters

`k` - The lazy property delegate to wrap.