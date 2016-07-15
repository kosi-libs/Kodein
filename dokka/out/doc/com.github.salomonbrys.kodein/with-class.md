[com.github.salomonbrys.kodein](index.md) / [withClass](.)

# withClass

`inline fun <reified T : `[`KodeinAware`](-kodein-aware.md)`> T.withClass(): `[`CurriedKodeinFactory`](-curried-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`

Allows to get a provider or an instance from a curried factory with a `Class` argument.

The provider will give the factory the `Class` of the receiver as argument.

### Parameters

`T` - The type of the receiver, will be the class provided to the factory.

**Receiver**
The object whose class is used.

**Return**
An object from which you can get an instance or a provider.

`inline fun <reified T : `[`KodeinInjected`](-kodein-injected.md)`> T.withClass(): `[`CurriedInjectorFactory`](-curried-injector-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`

Allows to inject a provider or an instance from a curried factory with a `Class` argument.

The provider will give the factory the `Class` of the receiver as argument.

### Parameters

`T` - The type of the receiver, will be the class provided to the factory.

**Receiver**
The object whose class is used.

**Return**
An object from which you can inject an instance or a provider.

`inline fun <reified T : Any> `[`LazyKodein`](-lazy-kodein/index.md)`.withClass(of: T): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`

Allows to lazily retrive a provider or an instance from a curried factory with a `Class` argument.

The provider will give the factory the `Class` of the parameter as argument.

### Parameters

`T` - The type of the parameter, will be the class provided to the factory.

`of` - The object whose class is used.

**Receiver**
The lazy Kodein object to use for injection.

**Return**
An object from which you can inject an instance or a provider.

`inline fun <reified T : `[`LazyKodeinAware`](-lazy-kodein-aware.md)`> T.withClass(): `[`CurriedLazyKodeinFactory`](-curried-lazy-kodein-factory/index.md)`<`[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<*>>`

Allows to lazily retrive a provider or an instance from a curried factory with a `Class` argument.

The provider will give the factory the `Class` of the receiver as argument.

### Parameters

`T` - The type of the receiver, will be the class provided to the factory.

**Receiver**
The object whose class is used.

**Return**
An object from which you can inject an instance or a provider.

