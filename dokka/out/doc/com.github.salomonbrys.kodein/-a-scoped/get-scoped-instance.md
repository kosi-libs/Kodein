[com.github.salomonbrys.kodein](../index.md) / [AScoped](index.md) / [getScopedInstance](.)

# getScopedInstance

`protected fun getScopedInstance(kodein: `[`ProviderKodein`](../-provider-kodein/index.md)`, key: `[`Key`](../-kodein/-key/index.md)`, arg: A): T`

Finds an instance inside a scope, or creates it if needs be.

### Parameters

`kodein` - : A Kodein instance to use for transitive dependencies.

`key` - : The key of the instance to get.

`arg` - : The argument to use to get the instance.