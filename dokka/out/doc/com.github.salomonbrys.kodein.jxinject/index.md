[com.github.salomonbrys.kodein.jxinject](.)

## Package com.github.salomonbrys.kodein.jxinject

Kodein API to work with `javax.inject.*` annotations.

### Types

| Name | Summary |
|---|---|
| [Jx](-jx/index.md) | `object Jx`<br>Utility function that eases the retrieval of a [JxInjector](-jx-injector/index.md). |
| [JxInjector](-jx-injector/index.md) | `class JxInjector`<br>Injector that allows to inject instances that use `javax.inject.*` annotations. |

### Annotations

| Name | Summary |
|---|---|
| [ErasedBinding](-erased-binding/index.md) | `annotation class ErasedBinding`<br>Defines that this should be injected with the erased binding. |
| [FactoryFun](-factory-fun/index.md) | `annotation class FactoryFun`<br>Defines that the annotated `Function1` is to be injected as a kodein factory. |
| [OrNull](-or-null/index.md) | `annotation class OrNull`<br>Defines that this should be null if there is no corresponding binding. |
| [ProviderFun](-provider-fun/index.md) | `annotation class ProviderFun`<br>Defines that the annotated `Function0` is to be injected as a kodein provider. |

### Properties

| Name | Summary |
|---|---|
| [jx](jx.md) | `val `[`Kodein`](../com.github.salomonbrys.kodein/-kodein/index.md)`.jx: `[`JxInjector`](-jx-injector/index.md)<br>Utility function that eases the retrieval of a [JxInjector](-jx-injector/index.md). |
| [jxInjectorModule](jx-injector-module.md) | `val jxInjectorModule: `[`Module`](../com.github.salomonbrys.kodein/-kodein/-module/index.md)<br>Module that must be imported in order to use [JxInjector](-jx-injector/index.md). |
