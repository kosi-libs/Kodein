[com.github.salomonbrys.kodein](../../index.md) / [Kodein](../index.md) / [Key](.)

# Key

`data class Key`

In Kodein, each [Factory](../../-factory/index.md) is bound to a Key. A Key holds all informations necessary to retrieve a factory (and therefore an instance).

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Key(bind: `[`Bind`](../-bind/index.md)`, argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`)`<br>In Kodein, each [Factory](../../-factory/index.md) is bound to a Key. A Key holds all informations necessary to retrieve a factory (and therefore an instance). |

### Properties

| Name | Summary |
|---|---|
| [argType](arg-type.md) | `val argType: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)<br>The argument type of the associated factory (Will be `Unit` for a provider). |
| [bind](bind.md) | `val bind: `[`Bind`](../-bind/index.md)<br>The left part of the bind declaration. |
| [description](description.md) | `val description: String`<br>Description using simple type names. The description is as close as possible to the code used to create this key. |
| [fullDescription](full-description.md) | `val fullDescription: String`<br>Description using full type names. The description is as close as possible to the code used to create this key. |

### Functions

| Name | Summary |
|---|---|
| [toString](to-string.md) | `fun toString(): String` |
