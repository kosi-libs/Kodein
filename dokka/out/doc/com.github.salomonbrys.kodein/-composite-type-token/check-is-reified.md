[com.github.salomonbrys.kodein](../index.md) / [CompositeTypeToken](index.md) / [checkIsReified](.)

# checkIsReified

`fun checkIsReified(disp: Any): Unit`

Overrides [TypeToken.checkIsReified](../-type-token/check-is-reified.md)

Checks that the type represented by this TypeToken is reified. Meaning that it is not or does not reference a `TypeVariable`.

### Parameters

`disp` - An object to print if the check fails. *For debug print only*.

### Exceptions

`IllegalArgumentException` - If the type does contain a `TypeVariable`.