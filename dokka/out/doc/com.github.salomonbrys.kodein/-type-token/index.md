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
| [getRaw](get-raw.md) | `abstract fun getRaw(): TypeToken<T>`<br>Returns the raw type represented by this type. |
| [getSuper](get-super.md) | `abstract fun getSuper(): TypeToken<in T>?`<br>Returns the parent type of the type represented by this TypeToken, if any. |
| [isGeneric](is-generic.md) | `abstract fun isGeneric(): Boolean`<br>Returns whether the type represented by this TypeToken is generic. |
| [isWildcard](is-wildcard.md) | `abstract fun isWildcard(): Boolean`<br>Returns whether the type represented by this TypeToken is generic and is entirely wildcard. |
| [simpleDispString](simple-disp-string.md) | `abstract fun simpleDispString(): String`<br>The simple (a.k.a. not fully qualified) name of the type represented by this TypeToken. |

### Inheritors

| Name | Summary |
|---|---|
| [CompositeTypeToken](../-composite-type-token/index.md) | `class CompositeTypeToken<T> : TypeToken<T>`<br>A composite type token represents a generic class in an erased manner. |
