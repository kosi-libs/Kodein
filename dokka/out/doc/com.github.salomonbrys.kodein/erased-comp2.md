[com.github.salomonbrys.kodein](index.md) / [erasedComp2](.)

# erasedComp2

`inline fun <reified T, reified A1, reified A2> erasedComp2(): `[`CompositeTypeToken`](-composite-type-token/index.md)`<T>`

Creates a [CompositeTypeToken](-composite-type-token/index.md) for an erased generic type.

Example: to create an erased type token representing `Map<Int, String>`, use `erasedComp2<Map<Int, String>, Int, String>()`.

### Parameters

`T` - The main type represented by this type token.

`A1` - The first type parameter of the main type.

`A2` - The second type parameter of the main type.