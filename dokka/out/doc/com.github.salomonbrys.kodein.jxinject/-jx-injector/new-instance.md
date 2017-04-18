[com.github.salomonbrys.kodein.jxinject](../index.md) / [JxInjector](index.md) / [newInstance](.)

# newInstance

`inline fun <reified T : Any> newInstance(injectFields: Boolean = true): T`

Creates a new instance of the given type.

### Parameters

`T` - The type of object to create.

`injectFields` - Whether to inject the fields &amp; methods of he newly created instance before returning it.