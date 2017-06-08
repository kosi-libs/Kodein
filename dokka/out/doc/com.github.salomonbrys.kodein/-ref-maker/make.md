[com.github.salomonbrys.kodein](../index.md) / [RefMaker](index.md) / [make](.)

# make

`abstract fun <T : Any> make(creator: () -> T): Pair<T, () -> T?>`

A Function that creates a reference.

### Parameters

`T` - The type of the referenced object.

`creator` - A function that can create a new T.

**Return**
The referenced object and a function that returns the same object if the reference is still valid.

