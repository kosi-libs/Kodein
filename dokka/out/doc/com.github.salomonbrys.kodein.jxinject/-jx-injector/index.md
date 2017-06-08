[com.github.salomonbrys.kodein.jxinject](../index.md) / [JxInjector](.)

# JxInjector

`class JxInjector`

Injector that allows to inject instances that use `javax.inject.*` annotations.

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `JxInjector(kodein: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)`)`<br>Injector that allows to inject instances that use `javax.inject.*` annotations. |

### Properties

| Name | Summary |
|---|---|
| [kodein](kodein.md) | `val kodein: `[`Kodein`](../../com.github.salomonbrys.kodein/-kodein/index.md)<br>The kodein object to use to retrieve injections. |

### Functions

| Name | Summary |
|---|---|
| [inject](inject.md) | `fun inject(receiver: Any): Unit`<br>Injects all fields and methods annotated with `@Inject` in `receiver`. |
| [newInstance](new-instance.md) | `fun <T : Any> newInstance(injectFields: Boolean = true): T`<br>Creates a new instance of the given type. |
