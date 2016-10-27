[com.github.salomonbrys.kodein](index.md) / [withGenericClassOf](.)

# withGenericClassOf

`inline fun <reified T : Any> `[`Kodein`](-kodein/index.md)`.withGenericClassOf(of: T): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`

Allows to get a provider or an instance from a curried factory with a `Class` argument.

The provider will give the factory the `Class` of the parameter as argument.

### Parameters

`T` - The type of the parameter, will be the class provided to the factory.

`of` - The object whose class is used.

**Receiver**
The Kodein object to use for retrieval.

**Return**
An object from which you can get an instance or a provider.

