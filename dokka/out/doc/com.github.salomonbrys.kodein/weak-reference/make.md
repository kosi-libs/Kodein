[com.github.salomonbrys.kodein](../index.md) / [weakReference](index.md) / [make](.)

# make

`fun <T : Any> make(creator: () -> T): Pair<T, () -> T?>`

Overrides [RefMaker.make](../-ref-maker/make.md)

A Function that creates a reference.

### Parameters

`T` - The type of the referenced object.

`creator` - A function that can create a new T.

**Return**
The referenced object and a function that returns the same object if the reference is still valid.

