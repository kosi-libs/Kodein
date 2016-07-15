[com.github.salomonbrys.kodein](index.md) / [withKClassOf](.)

# withKClassOf

`inline fun <reified T : Any> `[`Kodein`](-kodein/index.md)`.withKClassOf(of: T): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<KClass<*>>`

Allows to get a provider or an instance from a curried factory with a `Class` argument.

The provider will give the factory the `Class` of the parameter as argument.

### Parameters

`T` - The type of the parameter, will be the class provided to the factory.

`of` - The object whose class is used.

**Receiver**
The Kodein object to use for retrieval.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified T : Any> `[`KodeinInjector`](-kodein-injector/index.md)`.withKClassOf(of: T): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<KClass<*>>`

Allows to inject a provider or an instance from a curried factory with a `KClass` argument.

The provider will give the factory the `KClass` of the parameter as argument.

### Parameters

`T` - The type of the parameter, will be the class provided to the factory.

`of` - The object whose class is used.

**Receiver**
The Injector object to use for retrieval.

**Return**
An object from which you can inject an instance or a provider.

