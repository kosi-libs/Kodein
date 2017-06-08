[com.github.salomonbrys.kodein](../index.md) / [kotlin.Lazy](.)

### Extensions for kotlin.Lazy

| Name | Summary |
|---|---|
| [toInstance](to-instance.md) | `fun <A, T : Any> Lazy<(A) -> T>.toInstance(arg: () -> A): Lazy<T>`<br>Transforms a lazy factory property into a lazy instance property by currying the factory argument. |
| [toInstanceOrNull](to-instance-or-null.md) | `fun <A, T : Any> Lazy<(A) -> T>.toInstanceOrNull(arg: () -> A): Lazy<T?>`<br>Transforms a lazy nullable factory property into a lazy nullable instance property by currying the factory argument. |
| [toProvider](to-provider.md) | `fun <A, T : Any> Lazy<(A) -> T>.toProvider(arg: () -> A): Lazy<() -> T>`<br>Transforms a lazy factory property into a lazy provider property by currying the factory argument. |
| [toProviderOrNull](to-provider-or-null.md) | `fun <A, T : Any> Lazy<(A) -> T>.toProviderOrNull(arg: () -> A): Lazy<() -> T>`<br>Transforms a lazy nullable factory property into a lazy nullable provider property by currying the factory argument. |
