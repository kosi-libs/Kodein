[com.github.salomonbrys.kodein](index.md) / [withKClass](.)

# withKClass

`inline fun <reified T : `[`KodeinAware`](-kodein-aware.md)`> T.withKClass(): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<KClass<*>>`

Allows to get a provider or an instance from a curried factory with a `KClass` argument.

The provider will give the factory the `KClass` of the receiver as argument.

### Parameters

`T` - The type of the receiver, will be the class provided to the factory.

**Receiver**
The object whose class is used.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified T : `[`KodeinInjected`](-kodein-injected.md)`> T.withKClass(): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<KClass<*>>`

Allows to inject a provider or an instance from a curried factory with a `KClass` argument.

The provider will give the factory the `KClass` of the receiver as argument.

### Parameters

`T` - The type of the receiver, will be the class provided to the factory.

**Receiver**
The object whose class is used.

**Return**
An object from which you can inject an instance or a provider.

`inline fun <reified T : Any> `[`LazyKodein`](-lazy-kodein/index.md)`.withKClass(of: T): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<KClass<*>>`

Allows to lazily retrieve a provider or an instance from a curried factory with a `KClass` argument.

The provider will give the factory the `KClass` of the parameter as argument.

### Parameters

`T` - The type of the parameter, will be the class provided to the factory.

`of` - The object whose class is used.

**Receiver**
The lazy Kodein object to use for injection.

**Return**
An object from which you can inject an instance or a provider.

`inline fun <reified T : `[`LazyKodeinAware`](-lazy-kodein-aware.md)`> T.withKClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<KClass<*>>`

Allows to lazily retrieve a provider or an instance from a curried factory with a `KClass` argument.

The provider will give the factory the `KClass` of the receiver as argument.

### Parameters

`T` - The type of the receiver, will be the class provided to the factory.

**Receiver**
The object whose class is used.

**Return**
An object from which you can inject an instance or a provider.

