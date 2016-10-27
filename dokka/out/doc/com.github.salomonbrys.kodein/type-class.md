[com.github.salomonbrys.kodein](index.md) / [typeClass](.)

# typeClass

`inline fun <reified T> typeClass(): `[`Class`](http://docs.oracle.com/javase/6/docs/api/java/lang/Class.html)`<T>`

Function used to get a Class object. Same as T::class but with T being possibly nullable.

This should be used only when T is (possibly) nullable. When possible, T::class.java is faster.

### Parameters

`T` - The type to get.

**Return**
The type object representing `T`.

