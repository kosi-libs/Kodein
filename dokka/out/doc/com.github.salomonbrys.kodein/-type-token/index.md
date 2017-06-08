[com.github.salomonbrys.kodein](../index.md) / [TypeToken](.)

# TypeToken

`interface TypeToken<T>`

An interface that contains a simple Type but is parameterized to enable type safety.

### Parameters

`T` - The type represented by this object.

### Functions

| Name | Summary |
|---|---|
| [checkIsReified](check-is-reified.md) | `abstract fun checkIsReified(disp: Any): Unit`<br>Checks that the type represented by this TypeToken is reified. Meaning that it is not or does not reference a `TypeVariable`. |
| [fullDispString](full-disp-string.md) | `abstract fun fullDispString(): String`<br>The fully qualified name of the type represented by this TypeToken. |
| [getRawIfGeneric](get-raw-if-generic.md) | `abstract fun getRawIfGeneric(): TypeToken<T>?`<br>Returns the raw type of the type represented by this TypeToken if it is generic.
If the type is not generic, return null. |
| [getRawIfWildcard](get-raw-if-wildcard.md) | `abstract fun getRawIfWildcard(): TypeToken<T>?`<br>Returns the raw type if the type represented by this TypeToken is generic and is entirely wildcard. |
| [getSuper](get-super.md) | `abstract fun getSuper(): TypeToken<in T>?`<br>Returns the parent type of the type represented by this TypeToken, if any. |
| [simpleDispString](simple-disp-string.md) | `abstract fun simpleDispString(): String`<br>The simple (a.k.a. not fully qualified) name of the type represented by this TypeToken. |
