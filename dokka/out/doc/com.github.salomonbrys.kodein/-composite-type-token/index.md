[com.github.salomonbrys.kodein](../index.md) / [CompositeTypeToken](.)

# CompositeTypeToken

`class CompositeTypeToken<T> : `[`TypeToken`](../-type-token/index.md)`<T>`

A composite type token represents a generic class in an erased manner.

For example, the type `Map<String, List<String>>` can be represented as:

```
CompositeTypeToken(erased<Map<*, *>>(), erased<String>(), CompositeTypeToken(erased<List<*>(), erased<String>()))
```

Note that you should rather use the [erasedComp1](../erased-comp1.md), [erasedComp2](../erased-comp2.md) or [erasedComp3](../erased-comp3.md) functions to create a composite type token.

### Parameters

`T` - The main type represented by this type token.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `CompositeTypeToken(main: `[`TypeToken`](../-type-token/index.md)`<T>, vararg params: `[`TypeToken`](../-type-token/index.md)`<*>)`<br>A composite type token represents a generic class in an erased manner. |

### Properties

| Name | Summary |
|---|---|
| [main](main.md) | `val main: `[`TypeToken`](../-type-token/index.md)`<T>`<br>The main type represented by this type token. |
| [params](params.md) | `vararg val params: Array<out `[`TypeToken`](../-type-token/index.md)`<*>>`<br>The type parameters of the main type. |

### Functions

| Name | Summary |
|---|---|
| [checkIsReified](check-is-reified.md) | `fun checkIsReified(disp: Any): Unit`<br>Checks that the type represented by this TypeToken is reified. Meaning that it is not or does not reference a `TypeVariable`. |
| [fullDispString](full-disp-string.md) | `fun fullDispString(): String`<br>The fully qualified name of the type represented by this TypeToken. |
| [getRaw](get-raw.md) | `fun getRaw(): `[`TypeToken`](../-type-token/index.md)`<T>`<br>Returns the raw type represented by this type. |
| [getSuper](get-super.md) | `fun getSuper(): `[`TypeToken`](../-type-token/index.md)`<in T>?`<br>Returns the parent type of the type represented by this TypeToken, if any. |
| [isGeneric](is-generic.md) | `fun isGeneric(): Boolean`<br>Returns whether the type represented by this TypeToken is generic. |
| [isWildcard](is-wildcard.md) | `fun isWildcard(): Boolean`<br>Returns whether the type represented by this TypeToken is generic and is entirely wildcard. |
| [simpleDispString](simple-disp-string.md) | `fun simpleDispString(): String`<br>The simple (a.k.a. not fully qualified) name of the type represented by this TypeToken. |
