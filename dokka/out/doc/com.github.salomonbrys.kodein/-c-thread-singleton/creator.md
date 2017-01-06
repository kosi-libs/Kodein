[com.github.salomonbrys.kodein](../index.md) / [CThreadSingleton](index.md) / [creator](.)

# creator

`val creator: `[`ProviderKodein`](../-provider-kodein/index.md)`.() -> T`

The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.

### Property

`creator` - The function that will be called the first time an instance is requested in a thread. Guaranteed to be called only once per thread. Should create a new instance.