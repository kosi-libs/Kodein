[com.github.salomonbrys.kodein](../../index.md) / [KodeinInjector](../index.md) / [TInjector](index.md) / [instance](.)

# instance

`@JvmOverloads fun instance(type: `[`Type`](http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Type.html)`, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<Any>`

Creates an injected instance property delegate.

### Parameters

`type` - The type of object that will held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../../-kodein-injected-base/inject.md).

**Return**
A property delegate that will lazily provide an instance.

`@JvmOverloads fun <T : Any> instance(type: `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<T>`
`@JvmOverloads fun <T : Any> instance(type: `[`TypeToken`](../../-type-token/index.md)`<T>, tag: Any? = null): `[`InjectedProperty`](../../-injected-property/index.md)`<T>`

Creates an injected instance property delegate.

### Parameters

`T` - The type of object that will held by this property.

`type` - The type of object that will held by this property.

`tag` - The bound tag, if any.

### Exceptions

`KodeinInjector.UninjectedException` - When accessing the property, if it was accessed before calling [KodeinInjectedBase.inject](../../-kodein-injected-base/inject.md).

**Return**
A property delegate that will lazily provide an instance of `T`.

