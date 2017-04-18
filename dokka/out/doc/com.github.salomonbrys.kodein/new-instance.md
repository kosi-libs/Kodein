[com.github.salomonbrys.kodein](index.md) / [newInstance](.)

# newInstance

`inline fun <T> `[`KodeinAwareBase`](-kodein-aware-base/index.md)`.newInstance(creator: `[`Kodein`](-kodein/index.md)`.() -> T): T`

Allows to create a new instance of an unbound object with the same API as when bounding one.

### Parameters

`T` - The type of object to create.

`creator` - A function that do create the object.