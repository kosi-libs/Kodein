[com.github.salomonbrys.kodein](index.md) / [erasedComp1](.)

# erasedComp1

`inline fun <reified T, reified A1> erasedComp1(): `[`CompositeTypeToken`](-composite-type-token/index.md)`<T>`

Creates a [CompositeTypeToken](-composite-type-token/index.md) for an erased generic type.

Example: to create an erased type token representing `Set<String>`, use `erasedComp1<Set<String>, String>()`.

### Parameters

`T` - The main type represented by this type token.

`A1` - The type parameter of the main type.