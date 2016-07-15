[com.github.salomonbrys.kodein](index.md) / [lazy](.)

# lazy

`val `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.lazy: `[`LazyKodein`](-lazy-kodein/index.md)

Allows lazy retrieval from a [Kodein](-kodein/index.md) or [KodeinAware](-kodein-aware.md) object.

`val <A> `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<A>.lazy: `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<A>`

Allows lazy retrieval.

Example: `val manager: Manager by withClass().lazy.instance()`

### Parameters

`A` - The type of argument to pass to the curried factory.

`fun Kodein.Companion.lazy(f: `[`Builder`](-kodein/-builder/index.md)`.() -> Unit): `[`LazyKodein`](-lazy-kodein/index.md)

You can use the result of this function as a property delegate *or* as a function.

### Parameters

`f` - The function to get a Kodein, guaranteed to be called only once.