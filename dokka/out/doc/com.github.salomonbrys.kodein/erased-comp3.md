[com.github.salomonbrys.kodein](index.md) / [erasedComp3](.)

# erasedComp3

`inline fun <reified T, reified A1, reified A2, reified A3> erasedComp3(): `[`CompositeTypeToken`](-composite-type-token/index.md)`<T>`

Creates a [CompositeTypeToken](-composite-type-token/index.md) for an erased generic type.

Example: to create an erased type token representing `Triple<Int, String, Int>`, use `erasedComp3<Triple<Int, String, Int>, Int, String, Int>()`.

### Parameters

`T` - The main type represented by this type token.

`A1` - The type parameter of the main type.

`A2` - The second type parameter of the main type.

`A3` - The third type parameter of the main type.