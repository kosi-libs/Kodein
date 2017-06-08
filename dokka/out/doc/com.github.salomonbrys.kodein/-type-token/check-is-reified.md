[com.github.salomonbrys.kodein](../index.md) / [TypeToken](index.md) / [checkIsReified](.)

# checkIsReified

`abstract fun checkIsReified(disp: Any): Unit`

Checks that the type represented by this TypeToken is reified. Meaning that it is not or does not reference a `TypeVariable`.

### Parameters

`disp` - An object to print if the check fails. *For debug print only*.

### Exceptions

`IllegalArgumentException` - If the type does contain a `TypeVariable`.