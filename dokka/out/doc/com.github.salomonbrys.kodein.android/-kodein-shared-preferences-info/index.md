[com.github.salomonbrys.kodein.android](../index.md) / [KodeinSharedPreferencesInfo](.)

# KodeinSharedPreferencesInfo

`data class KodeinSharedPreferencesInfo`

A helper class for binding a named SharedPreferences

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `KodeinSharedPreferencesInfo(ctx: Context, name: String, visibility: Int = Context.MODE_PRIVATE)`<br>A helper class for binding a named SharedPreferences |

### Properties

| Name | Summary |
|---|---|
| [ctx](ctx.md) | `val ctx: Context`<br>A context |
| [name](name.md) | `val name: String`<br>The name of the shared preferences. |
| [visibility](visibility.md) | `val visibility: Int`<br>The visibility of the shared preferences, when creating it. |

### Functions

| Name | Summary |
|---|---|
| [getSharedPreferences](get-shared-preferences.md) | `fun getSharedPreferences(): SharedPreferences` |
